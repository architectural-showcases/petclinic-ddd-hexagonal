package io.github.dmitriirussu.petclinic.vet.adapter.out.jdbc;

import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.Specialty;
import io.github.dmitriirussu.petclinic.vet.core.port.out.SpecialtyCatalog;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@Profile("jdbc")
public class JdbcSpecialtyCatalog implements SpecialtyCatalog {

    private final JdbcClient jdbc;

    public JdbcSpecialtyCatalog(JdbcClient jdbc) {
        this.jdbc = Objects.requireNonNull(jdbc);
    }

    @Override
    public Specialty findByName(String name) {
        return jdbc.sql("SELECT name FROM specialties WHERE LOWER(name) = LOWER(:name)")
                .param("name", name)
                .query((rs, _) -> Specialty.of(rs.getString("name")))
                .optional()
                .orElseThrow(() -> new IllegalArgumentException("Unknown specialty: " + name));
    }

    @Override
    public List<Specialty> getAllSpecialties() {
        return jdbc.sql("SELECT name FROM specialties ORDER BY name")
                .query((rs, _) -> Specialty.of(rs.getString("name")))
                .list();
    }
}

