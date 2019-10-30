package ch.armisz.cli.event;

@FunctionalInterface
public interface EventHandler<T> {

    void trigger(T event);
}
