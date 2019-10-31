package ch.armisz.cli.service.internal;

@FunctionalInterface
public interface EventHandler<T> {

    void handle(T event);
}
