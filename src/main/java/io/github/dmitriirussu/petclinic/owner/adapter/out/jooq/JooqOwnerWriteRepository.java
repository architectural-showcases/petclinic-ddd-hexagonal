package io.github.dmitriirussu.petclinic.owner.adapter.out.jooq;

import io.github.dmitriirussu.petclinic.owner.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.domain.entity.Pet;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.domain.visit.Visit;
import io.github.dmitriirussu.petclinic.owner.port.out.OwnerWriteRepository;
import static org.jooq.impl.DSL.*;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@Profile("jooq")
public class JooqOwnerWriteRepository implements OwnerWriteRepository {

    private final DSLContext dsl;

    public JooqOwnerWriteRepository(DSLContext dsl) {
        this.dsl = Objects.requireNonNull(dsl);
    }

    // ─── Owner ───────────────────────────────────────────────

    @Override
    public void save(Owner owner) {
        dsl.insertInto(table("owners"))
                .set(field("id"),         owner.getId().value())
                .set(field("first_name"), owner.getFirstName())
                .set(field("last_name"),  owner.getLastName())
                .set(field("street"),     owner.getStreet())
                .set(field("city"),       owner.getCity())
                .set(field("telephone"),  owner.getTelephone())
                .execute();

        owner.getPets().forEach(pet -> savePet(owner.getId(), pet));
    }

    @Override
    public void update(OwnerId id, Owner owner) {
        dsl.update(table("owners"))
                .set(field("first_name"), owner.getFirstName())
                .set(field("last_name"),  owner.getLastName())
                .set(field("street"),     owner.getStreet())
                .set(field("city"),       owner.getCity())
                .set(field("telephone"),  owner.getTelephone())
                .where(field("id").eq(id.value()))
                .execute();

        deletePetsByOwnerId(id);
        owner.getPets().forEach(pet -> savePet(id, pet));
    }

    @Override
    public void delete(OwnerId id) {
        dsl.deleteFrom(table("owners"))
                .where(field("id").eq(id.value()))
                .execute();
    }

    // ─── Pet ─────────────────────────────────────────────────

    private void savePet(OwnerId ownerId, Pet pet) {
        dsl.insertInto(table("pets"))
                .set(field("id"),         pet.getId().value())
                .set(field("owner_id"),   ownerId.value())
                .set(field("name"),       pet.getName())
                .set(field("birth_date"), pet.getBirthDate())
                .set(field("type"),       pet.getType())
                .execute();

        pet.getVisits().forEach(visit -> saveVisit(pet, visit));
    }

    private void deletePetsByOwnerId(OwnerId ownerId) {
        dsl.deleteFrom(table("pets"))
                .where(field("owner_id").eq(ownerId.value()))
                .execute();
    }

    // ─── Visit ───────────────────────────────────────────────

    private void saveVisit(Pet pet, Visit visit) {
        dsl.insertInto(table("visits"))
                .set(field("id"),          visit.getId().value())
                .set(field("pet_id"),      pet.getId().value())
                .set(field("visit_date"),  visit.getDate())
                .set(field("description"), visit.getDescription())
                .execute();
    }
}
