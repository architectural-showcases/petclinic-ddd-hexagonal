package io.github.dmitriirussu.petclinic.vet.adapter.out.jooq;

import io.github.dmitriirussu.petclinic.vet.domain.Vet;
import io.github.dmitriirussu.petclinic.vet.domain.valueobject.Specialty;
import io.github.dmitriirussu.petclinic.vet.domain.valueobject.VetId;
import io.github.dmitriirussu.petclinic.vet.port.out.VetWriteRepository;
import org.jooq.DSLContext;
import static org.jooq.impl.DSL.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@Profile("jooq")
public class JooqVetWriteRepository implements VetWriteRepository {

    private interface Sql {
        String VETS             = "vets";
        String VET_SPECIALTIES  = "vet_specialties";
    }

    private final DSLContext dsl;

    public JooqVetWriteRepository(DSLContext dsl) {
        this.dsl = Objects.requireNonNull(dsl);
    }

    @Override
    public void save(Vet vet) {
        dsl.insertInto(table(Sql.VETS))
                .set(field("id"),         vet.getId().value())
                .set(field("first_name"), vet.getFirstName())
                .set(field("last_name"),  vet.getLastName())
                .execute();

        vet.getSpecialties().forEach(s -> saveSpecialty(vet.getId(), s));
    }

    @Override
    public void update(VetId id, Vet vet) {
        dsl.update(table(Sql.VETS))
                .set(field("first_name"), vet.getFirstName())
                .set(field("last_name"),  vet.getLastName())
                .where(field("id").eq(id.value()))
                .execute();

        deleteSpecialtiesByVetId(id);
        vet.getSpecialties().forEach(s -> saveSpecialty(id, s));
    }

    @Override
    public void delete(VetId id) {
        dsl.deleteFrom(table(Sql.VETS))
                .where(field("id").eq(id.value()))
                .execute();
    }

    private void saveSpecialty(VetId vetId, Specialty specialty) {
        dsl.insertInto(table(Sql.VET_SPECIALTIES))
                .set(field("vet_id"), vetId.value())
                .set(field("specialty_name"), specialty.getName())
                .execute();
    }

    private void deleteSpecialtiesByVetId(VetId vetId) {
        dsl.deleteFrom(table(Sql.VET_SPECIALTIES))
                .where(field("vet_id").eq(vetId.value()))
                .execute();
    }
}
