package ch.armisz.cli;

import ch.armisz.cli.event.internal.EventResult;
import ch.armisz.cli.event.internal.EventResult.Level;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventResultTests {

    private static class SimpleEventResult extends EventResult {

        public SimpleEventResult(Level level, String message) {
            super(level, message);
        }
    }

    @Test
    public void testCreate() {
        EventResult result = new SimpleEventResult(Level.INFO, "OK");

        assertThat(result.getOrigin()).isNull();
        assertThat(result.getLevel()).isEqualTo(Level.INFO);
        assertThat(result.getMessage()).isEqualTo("OK");
    }

    @Test(expected = NullPointerException.class)
    public void testCreateLevelMissing() {
        new SimpleEventResult(null, "OK");
    }

    @Test(expected = NullPointerException.class)
    public void testCreateMessageMissing() {
        new SimpleEventResult(Level.INFO, null);
    }

    @Test
    public void testSetOrigin() {
        EventResult result = new SimpleEventResult(Level.INFO, "OK");
        result.setOrigin(getClass().getSimpleName());

        assertThat(result.getOrigin()).isEqualTo(getClass().getSimpleName());
    }

    @Test(expected = NullPointerException.class)
    public void testSetOriginMissing() {
        EventResult result = new SimpleEventResult(Level.INFO, "OK");
        result.setOrigin(null);
    }
}
