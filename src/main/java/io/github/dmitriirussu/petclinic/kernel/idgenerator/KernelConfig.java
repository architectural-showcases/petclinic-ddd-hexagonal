package io.github.dmitriirussu.petclinic.kernel.idgenerator;

import io.github.dmitriirussu.petclinic.kernel.UuidV7Generator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KernelConfig {

    @Bean
    public IdGenerator idGenerator() {
        return new UuidV7Generator();
    }
}
