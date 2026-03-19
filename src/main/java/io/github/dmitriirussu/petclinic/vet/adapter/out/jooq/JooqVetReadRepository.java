package io.github.dmitriirussu.petclinic.vet.adapter.out.jooq;

import io.github.dmitriirussu.petclinic.vet.adapter.out.mapper.VetRowMapper;
import io.github.dmitriirussu.petclinic.vet.domain.Vet;
import io.github.dmitriirussu.petclinic.vet.domain.VetFactory;
import io.github.dmitriirussu.petclinic.vet.domain.valueobject.VetId;
import io.github.dmitriirussu.petclinic.vet.port.out.VetReadRepository;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Profile("jooq")
public class JooqVetReadRepository implements VetReadRepository {

    private static final String FIND_BY_ID_QUERY = """
            SELECT
                v.id           AS vet_id,
                v.first_name   AS vet_first_name,
                v.last_name    AS vet_last_name,
                s.specialty_name
            FROM vets v
            LEFT JOIN vet_specialties s ON s.vet_id = v.id
            WHERE v.id = ?
            """;

    private static final String PAGINATED_QUERY = """
            SELECT
                v.id           AS vet_id,
                v.first_name   AS vet_first_name,
                v.last_name    AS vet_last_name,
                s.specialty_name
            FROM (
                SELECT * FROM vets
                ORDER BY last_name
                LIMIT ? OFFSET ?
            ) v
            LEFT JOIN vet_specialties s ON s.vet_id = v.id
            """;

    private final DSLContext   dsl;
    private final VetRowMapper mapper;

    public JooqVetReadRepository(DSLContext dsl, VetFactory factory) {
        this.dsl    = Objects.requireNonNull(dsl);
        this.mapper = new VetRowMapper(factory);
    }

    @Override
    public Optional<Vet> findById(VetId id) {
        return mapper.map(
                dsl.fetch(FIND_BY_ID_QUERY, id.value()).intoMaps()
        ).stream().findFirst();
    }

    @Override
    public List<Vet> findAll(int page, int pageSize) {
        return mapper.map(
                dsl.fetch(PAGINATED_QUERY, pageSize, (page - 1) * pageSize).intoMaps()
        );
    }

    @Override
    public int countAll() {
        return dsl.fetchOne("SELECT COUNT(*) FROM vets").get(0, Integer.class);
    }
}