package io.github.dmitriirussu.petclinic.owner.adapter.out.jdbc;

import io.github.dmitriirussu.petclinic.owner.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.domain.entity.Pet;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.domain.visit.Visit;
import io.github.dmitriirussu.petclinic.owner.port.out.OwnerWriteRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@Profile("jdbc")
public class JdbcOwnerWriteRepository implements OwnerWriteRepository {

    private final JdbcClient jdbc;

    public JdbcOwnerWriteRepository(JdbcClient jdbc) {
        this.jdbc = Objects.requireNonNull(jdbc);
    }

    // ─── Owner ───────────────────────────────────────────────

    @Override
    public void save(Owner owner) {
        jdbc.sql("""
                INSERT INTO owners (id, first_name, last_name, street, city, telephone)
                VALUES (:id, :firstName, :lastName, :street, :city, :telephone)
                """)
                .param("id",        owner.getId().value())
                .param("firstName", owner.getFirstName())
                .param("lastName",  owner.getLastName())
                .param("street",    owner.getStreet())
                .param("city",      owner.getCity())
                .param("telephone", owner.getTelephone())
                .update();

        owner.getPets().forEach(pet -> savePet(owner.getId(), pet));
    }

    @Override
    public void update(OwnerId id, Owner owner) {
        jdbc.sql("""
                UPDATE owners
                SET first_name = :firstName,
                    last_name  = :lastName,
                    street     = :street,
                    city       = :city,
                    telephone  = :telephone
                WHERE id = :id
                """)
                .param("id",        id.value())
                .param("firstName", owner.getFirstName())
                .param("lastName",  owner.getLastName())
                .param("street",    owner.getStreet())
                .param("city",      owner.getCity())
                .param("telephone", owner.getTelephone())
                .update();

        // удаляем старых питомцев и вставляем заново
        deletePetsByOwnerId(id);
        owner.getPets().forEach(pet -> savePet(id, pet));
    }

    @Override
    public void delete(OwnerId id) {
        jdbc.sql("DELETE FROM owners WHERE id = :id")
                .param("id", id.value())
                .update();
    }

    // ─── Pet ─────────────────────────────────────────────────

    private void savePet(OwnerId ownerId, Pet pet) {
        jdbc.sql("""
                INSERT INTO pets (id, owner_id, name, birth_date, type)
                VALUES (:id, :ownerId, :name, :birthDate, :type)
                """)
                .param("id",        pet.getId().value())
                .param("ownerId",   ownerId.value())
                .param("name",      pet.getName())
                .param("birthDate", pet.getBirthDate())
                .param("type",      pet.getType())
                .update();

        pet.getVisits().forEach(visit -> saveVisit(pet, visit));
    }

    private void deletePetsByOwnerId(OwnerId ownerId) {
        jdbc.sql("DELETE FROM pets WHERE owner_id = :ownerId")
                .param("ownerId", ownerId.value())
                .update();
    }

    // ─── Visit ───────────────────────────────────────────────

    private void saveVisit(Pet pet, Visit visit) {
        jdbc.sql("""
                INSERT INTO visits (id, pet_id, visit_date, description)
                VALUES (:id, :petId, :visitDate, :description)
                """)
                .param("id",          visit.getId().value())
                .param("petId",       pet.getId().value())
                .param("visitDate",   visit.getDate())
                .param("description", visit.getDescription())
                .update();
    }
}
