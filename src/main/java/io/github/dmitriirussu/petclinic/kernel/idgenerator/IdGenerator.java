package io.github.dmitriirussu.petclinic.kernel.idgenerator;

/**
 * Port for generating unique user identifiers.
 * Default implementation produces UUID v7 (time-based, sortable).
 */
@FunctionalInterface
public interface IdGenerator {
    String nextId();
}
