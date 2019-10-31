package ch.armisz.cli.event.internal;

@FunctionalInterface
public interface EventHandler<T> {

    void handle(T event);
}
