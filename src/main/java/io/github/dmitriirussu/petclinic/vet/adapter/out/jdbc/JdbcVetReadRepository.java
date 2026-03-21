package io.github.dmitriirussu.petclinic.vet.adapter.out.jdbc;

import io.github.dmitriirussu.petclinic.kernel.pagination.Page;
import io.github.dmitriirussu.petclinic.vet.adapter.out.mapper.VetRowMapper;
import io.github.dmitriirussu.petclinic.vet.core.domain.Vet;
import io.github.dmitriirussu.petclinic.vet.core.domain.VetFactory;
import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.VetId;
import io.github.dmitriirussu.petclinic.vet.core.port.out.VetReadRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class JdbcVetReadRepository implements VetReadRepository {

    private static final String BASE_QUERY = """
            SELECT
                v.id         AS vet_id,
                v.first_name AS vet_first_name,
                v.last_name  AS vet_last_name,
                s.specialty_name
            FROM (
                SELECT * FROM vets
                ORDER BY last_name
                LIMIT :limit OFFSET :offset
            ) v
            LEFT JOIN vet_specialties s ON s.vet_id = v.id
            """;

    private static final String FIND_BY_ID_QUERY = """
            SELECT
                v.id         AS vet_id,
                v.first_name AS vet_first_name,
                v.last_name  AS vet_last_name,
                s.specialty_name
            FROM vets v
            LEFT JOIN vet_specialties s ON s.vet_id = v.id
            WHERE v.id = :id
            """;

    private final JdbcClient   jdbc;
    private final VetRowMapper mapper;

    public JdbcVetReadRepository(JdbcClient jdbc, VetFactory factory) {
        this.jdbc   = Objects.requireNonNull(jdbc);
        this.mapper = new VetRowMapper(factory);
    }

    @Override
    public Optional<Vet> findById(VetId id) {
        return mapper.map(
                jdbc.sql(FIND_BY_ID_QUERY)
                        .param("id", id.value())
                        .query().listOfRows()
        ).stream().findFirst();
    }

    @Override
    public Page<Vet> findAll(int page, int pageSize) {
        int total = jdbc.sql("SELECT COUNT(*) FROM vets")
                .query(Integer.class)
                .single();

        List<Vet> content = mapper.map(
                jdbc.sql(BASE_QUERY)
                        .param("limit",  pageSize)
                        .param("offset", (page - 1) * pageSize)
                        .query().listOfRows()
        );

        return new Page<>(content, total);
    }
}