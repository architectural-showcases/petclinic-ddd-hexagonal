package io.github.dmitriirussu.petclinic.owner.adapter.out.jdbc;

import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.pet.PetType;
import io.github.dmitriirussu.petclinic.owner.core.port.out.PetTypeCatalog;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@Profile("jdbc")
public class JdbcPetTypeCatalog implements PetTypeCatalog {

    private final JdbcClient jdbc;

    public JdbcPetTypeCatalog(JdbcClient jdbc) {
        this.jdbc = Objects.requireNonNull(jdbc);
    }

    @Override
    public PetType findByName(String name) {
        return jdbc.sql("SELECT name FROM pet_types WHERE LOWER(name) = LOWER(:name)")
                .param("name", name)
                .query((rs, _) -> PetType.of(rs.getString("name")))
                .optional()
                .orElseThrow(() -> new IllegalArgumentException("Unknown type: " + name));
    }

    @Override
    public List<PetType> getAllTypes() {
        return jdbc.sql("SELECT name FROM pet_types ORDER BY name")
                .query((rs, _) -> PetType.of(rs.getString("name")))
                .list();
    }
}
