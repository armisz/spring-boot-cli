package ch.armisz.cli.event.internal;

@FunctionalInterface
public interface EventHandler<I extends Event, O extends EventResult> {

    O handle(I event);
}
