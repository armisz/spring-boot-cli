package ch.armisz.cli;

import ch.armisz.cli.event.internal.EventResult;
import ch.armisz.cli.event.internal.EventResult.Level;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventResultTests {

    @Test
    public void testCreate() {
        EventResult result = new EventResult(Level.INFO, "OK");

        assertThat(result.getOrigin()).isNull();
        assertThat(result.getLevel()).isEqualTo(Level.INFO);
        assertThat(result.getMessage()).isEqualTo("OK");
    }

    @Test(expected = NullPointerException.class)
    public void testCreateLevelMissing() {
        new EventResult(null, "OK");
    }

    @Test(expected = NullPointerException.class)
    public void testCreateMessageMissing() {
        new EventResult(Level.INFO, null);
    }

    @Test
    public void testSetOrigin() {
        EventResult result = new EventResult(Level.INFO, "OK");
        result.setOrigin(getClass().getSimpleName());

        assertThat(result.getOrigin()).isEqualTo(getClass().getSimpleName());
    }

    @Test(expected = NullPointerException.class)
    public void testSetOriginMissing() {
        EventResult result = new EventResult(Level.INFO, "OK");
        result.setOrigin(null);
    }
}
