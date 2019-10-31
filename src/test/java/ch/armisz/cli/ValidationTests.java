package ch.armisz.cli;

import ch.armisz.cli.event.ValidateEvent;
import ch.armisz.cli.event.internal.EventHandler;
import ch.armisz.cli.service.ValidationService;
import ch.armisz.cli.service.ValidationServiceWithHigherPriority;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidationTests extends AbstractTests {

    @Autowired
    private List<EventHandler<ValidateEvent>> validators;

    @Test
    public void testOrder() {
        assertThat(validators).hasSize(2);
        assertThat(validators.get(0)).isInstanceOf(ValidationServiceWithHigherPriority.class);
        assertThat(validators.get(1)).isInstanceOf(ValidationService.class);
    }

}
