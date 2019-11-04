package ch.armisz.cli;

import ch.armisz.cli.event.ValidateEvent;
import ch.armisz.cli.event.ValidationResult;
import ch.armisz.cli.event.internal.ErrorResult;
import ch.armisz.cli.event.internal.EventHandler;
import ch.armisz.cli.event.internal.EventResult;
import ch.armisz.cli.event.internal.EventResult.Level;
import ch.armisz.cli.event.internal.EventResults;
import ch.armisz.cli.service.EventService;
import ch.armisz.cli.service.ValidationService;
import ch.armisz.cli.service.ValidationServiceWithHighPriority;
import ch.armisz.cli.service.ValidationServiceWithLowPriority;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EventServiceTests extends AbstractApplicationTests {

    @Autowired
    private List<EventHandler<ValidateEvent, ValidationResult>> validators;

    @Autowired
    EventService eventService;

    @Test
    public void testInjectionOrder() {
        assertThat(validators).hasSize(3);
        assertThat(validators.get(0)).isInstanceOf(ValidationServiceWithHighPriority.class);
        assertThat(validators.get(1)).isInstanceOf(ValidationService.class);
        assertThat(validators.get(2)).isInstanceOf(ValidationServiceWithLowPriority.class);
    }

    @Test
    public void testResults() {
        EventResults results = eventService.trigger(new ValidateEvent("someTarget"));

        assertThat(results).isNotNull();

        assertThat(results.getResults(Level.INFO)).hasSize(1);
        assertThat(results.getResults(Level.WARNING)).hasSize(1);
        assertThat(results.getResults(Level.ERROR)).hasSize(0);
        assertThat(results.getResults(null)).hasSize(0);

        assertThat(results.hasLevel(Level.INFO)).isTrue();
        assertThat(results.hasLevel(Level.WARNING)).isTrue();
        assertThat(results.hasLevel(Level.ERROR)).isFalse();
        assertThat(results.hasLevel(null)).isFalse();

        assertThat(results.getResults()).hasSize(2);

        EventResult result = results.getResults().get(0);
        assertThat(result.getOrigin()).isEqualTo(ValidationServiceWithHighPriority.class.getSimpleName());
        assertThat(result.getLevel()).isEqualTo(Level.INFO);

        result = results.getResults().get(1);
        assertThat(result.getOrigin()).isEqualTo(ValidationServiceWithLowPriority.class.getSimpleName());
        assertThat(result.getLevel()).isEqualTo(Level.WARNING);
    }

    @Test
    public void testResultsOnFail() {
        EventResults results = eventService.trigger(new ValidateEvent("fail"));
        testResultsOnFail(results, "Fail", ValidationResult.class);
    }

    @Test
    public void testResultsOnException() {
        EventResults results = eventService.trigger(new ValidateEvent("ex"));
        testResultsOnFail(results, "Fail with exception", ErrorResult.class);
    }

    private void testResultsOnFail(EventResults results, String errorMessage, Class errorResultClass) {
        assertThat(results).isNotNull();

        assertThat(results.getResults(Level.INFO)).hasSize(1);
        assertThat(results.getResults(Level.WARNING)).hasSize(1);
        assertThat(results.getResults(Level.ERROR)).hasSize(1);

        assertThat(results.getResults()).hasSize(3);

        EventResult result = results.getResults().get(0);
        assertThat(result.getOrigin()).isEqualTo(ValidationServiceWithHighPriority.class.getSimpleName());
        assertThat(result.getClass()).isEqualTo(ValidationResult.class);
        assertThat(result.getLevel()).isEqualTo(Level.INFO);

        result = results.getResults().get(1);
        assertThat(result.getOrigin()).isEqualTo(ValidationService.class.getSimpleName());
        assertThat(result.getMessage()).isEqualTo(errorMessage);
        assertThat(result.getClass()).isEqualTo(errorResultClass);
        assertThat(result.getLevel()).isEqualTo(Level.ERROR);

        result = results.getResults().get(2);
        assertThat(result.getOrigin()).isEqualTo(ValidationServiceWithLowPriority.class.getSimpleName());
        assertThat(result.getClass()).isEqualTo(ValidationResult.class);
        assertThat(result.getLevel()).isEqualTo(Level.WARNING);
    }

}
