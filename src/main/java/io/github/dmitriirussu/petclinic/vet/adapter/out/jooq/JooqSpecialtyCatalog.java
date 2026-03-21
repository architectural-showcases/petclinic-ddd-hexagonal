package io.github.dmitriirussu.petclinic.vet.adapter.out.jooq;

import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.Specialty;
import io.github.dmitriirussu.petclinic.vet.core.port.out.SpecialtyCatalog;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static org.jooq.impl.DSL.*;

@Repository
@Profile("jooq")
public class JooqSpecialtyCatalog implements SpecialtyCatalog {

    private interface Sql {
        String TABLE = "specialties";
        String NAME  = "name";
    }

    private final DSLContext dsl;

    public JooqSpecialtyCatalog(DSLContext dsl) {
        this.dsl = Objects.requireNonNull(dsl);
    }

    @Override
    public Specialty findByName(String name) {
        return dsl
                .select(field(Sql.NAME, String.class))
                .from(table(Sql.TABLE))
                .where(lower(field(Sql.NAME, String.class)).eq(name.toLowerCase()))
                .fetchOptional(r -> Specialty.of(r.get(field(Sql.NAME, String.class))))
                .orElseThrow(() -> new IllegalArgumentException("Unknown specialty: " + name));
    }

    @Override
    public List<Specialty> getAllSpecialties() {
        return dsl
                .select(field(Sql.NAME, String.class))
                .from(table(Sql.TABLE))
                .orderBy(field(Sql.NAME))
                .fetch(r -> Specialty.of(r.get(field(Sql.NAME, String.class))));
    }
}
