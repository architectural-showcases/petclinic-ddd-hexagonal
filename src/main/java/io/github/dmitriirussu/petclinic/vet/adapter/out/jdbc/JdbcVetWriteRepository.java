package io.github.dmitriirussu.petclinic.vet.adapter.out.jdbc;

import io.github.dmitriirussu.petclinic.vet.core.domain.Vet;
import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.Specialty;
import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.VetId;
import io.github.dmitriirussu.petclinic.vet.core.port.out.VetWriteRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@Profile("jdbc")
public class JdbcVetWriteRepository implements VetWriteRepository {

    private final JdbcClient jdbc;

    public JdbcVetWriteRepository(JdbcClient jdbc) {
        this.jdbc = Objects.requireNonNull(jdbc);
    }

    @Override
    public void save(Vet vet) {
        jdbc.sql("""
                INSERT INTO vets (id, first_name, last_name)
                VALUES (:id, :firstName, :lastName)
                """)
                .param("id",        vet.getId().value())
                .param("firstName", vet.getFirstName())
                .param("lastName",  vet.getLastName())
                .update();

        vet.getSpecialties().forEach(s -> saveSpecialty(vet.getId(), s));
    }

    @Override
    public void update(VetId id, Vet vet) {
        jdbc.sql("""
                UPDATE vets
                SET first_name = :firstName,
                    last_name  = :lastName
                WHERE id = :id
                """)
                .param("id",        id.value())
                .param("firstName", vet.getFirstName())
                .param("lastName",  vet.getLastName())
                .update();

        deleteSpecialtiesByVetId(id);
        vet.getSpecialties().forEach(s -> saveSpecialty(id, s));
    }

    @Override
    public void delete(VetId id) {
        jdbc.sql("DELETE FROM vets WHERE id = :id")
                .param("id", id.value())
                .update();
    }

    private void saveSpecialty(VetId vetId, Specialty specialty) {
        jdbc.sql("""
                INSERT INTO vet_specialties (vet_id, specialty_name)
                VALUES (:vetId, :name)
                """)
                .param("vetId", vetId.value())
                .param("name",  specialty.getName())
                .update();
    }

    private void deleteSpecialtiesByVetId(VetId vetId) {
        jdbc.sql("DELETE FROM vet_specialties WHERE vet_id = :vetId")
                .param("vetId", vetId.value())
                .update();
    }
}
