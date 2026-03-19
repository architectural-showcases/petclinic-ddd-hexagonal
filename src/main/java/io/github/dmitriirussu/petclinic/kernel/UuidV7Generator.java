package io.github.dmitriirussu.petclinic.kernel;

import com.fasterxml.uuid.Generators;
import io.github.dmitriirussu.petclinic.kernel.idgenerator.IdGenerator;

public class UuidV7Generator implements IdGenerator {

    @Override
    public String nextId() {
        return Generators.timeBasedEpochGenerator().generate().toString();
    }
}
