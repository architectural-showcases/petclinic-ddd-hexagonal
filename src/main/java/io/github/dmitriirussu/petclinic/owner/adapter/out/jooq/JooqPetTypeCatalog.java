package io.github.dmitriirussu.petclinic.owner.adapter.out.jooq;

import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.pet.PetType;
import io.github.dmitriirussu.petclinic.owner.core.port.out.PetTypeCatalog;
import org.jooq.DSLContext;
import static org.jooq.impl.DSL.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@Profile("jooq")
public class JooqPetTypeCatalog implements PetTypeCatalog {

    private final DSLContext dsl;

    public JooqPetTypeCatalog(DSLContext dsl) {
        this.dsl = Objects.requireNonNull(dsl);
    }

    @Override
    public PetType findByName(String name) {
        return dsl.select(field("name"))
                .from(table("pet_types"))
                .where(field("name").likeIgnoreCase(name))
                .fetchOptional(r -> PetType.of(r.get(field("name"), String.class)))
                .orElseThrow(() -> new IllegalArgumentException("Unknown type: " + name));
    }

    @Override
    public List<PetType> getAllTypes() {
        return dsl.select(field("name"))
                .from(table("pet_types"))
                .orderBy(field("name"))
                .fetch(r -> PetType.of(r.get(field("name"), String.class)));
    }
}
