package io.github.dmitriirussu.petclinic.owner.adapter.out.jdbc;

import io.github.dmitriirussu.petclinic.owner.adapter.out.mapper.OwnerRowMapper;
import io.github.dmitriirussu.petclinic.owner.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.domain.aggregate.OwnerFactory;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.port.out.OwnerReadRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class JdbcOwnerReadRepository implements OwnerReadRepository {

    private static final String FIND_BY_ID_QUERY = """
            SELECT
                o.id          AS owner_id,
                o.first_name  AS owner_first_name,
                o.last_name   AS owner_last_name,
                o.street      AS owner_street,
                o.city        AS owner_city,
                o.telephone   AS owner_telephone,
                p.id          AS pet_id,
                p.name        AS pet_name,
                p.birth_date  AS pet_birth_date,
                p.type        AS pet_type,
                v.id          AS visit_id,
                v.visit_date  AS visit_date,
                v.description AS visit_description
            FROM owners o
            LEFT JOIN pets    p ON p.owner_id = o.id
            LEFT JOIN visits  v ON v.pet_id   = p.id
            WHERE o.id = :id
            """;

    private static final String PAGINATED_ALL_QUERY = """
            SELECT
                o.id          AS owner_id,
                o.first_name  AS owner_first_name,
                o.last_name   AS owner_last_name,
                o.street      AS owner_street,
                o.city        AS owner_city,
                o.telephone   AS owner_telephone,
                p.id          AS pet_id,
                p.name        AS pet_name,
                p.birth_date  AS pet_birth_date,
                p.type        AS pet_type,
                v.id          AS visit_id,
                v.visit_date  AS visit_date,
                v.description AS visit_description
            FROM (
                SELECT * FROM owners
                ORDER BY last_name
                LIMIT :limit OFFSET :offset
            ) o
            LEFT JOIN pets    p ON p.owner_id = o.id
            LEFT JOIN visits  v ON v.pet_id   = p.id
            """;

    private static final String PAGINATED_BY_NAME_QUERY = """
            SELECT
                o.id          AS owner_id,
                o.first_name  AS owner_first_name,
                o.last_name   AS owner_last_name,
                o.street      AS owner_street,
                o.city        AS owner_city,
                o.telephone   AS owner_telephone,
                p.id          AS pet_id,
                p.name        AS pet_name,
                p.birth_date  AS pet_birth_date,
                p.type        AS pet_type,
                v.id          AS visit_id,
                v.visit_date  AS visit_date,
                v.description AS visit_description
            FROM (
                SELECT * FROM owners
                WHERE LOWER(last_name) LIKE :prefix
                ORDER BY last_name
                LIMIT :limit OFFSET :offset
            ) o
            LEFT JOIN pets    p ON p.owner_id = o.id
            LEFT JOIN visits  v ON v.pet_id   = p.id
            """;

    private final JdbcClient     jdbc;
    private final OwnerRowMapper mapper;

    public JdbcOwnerReadRepository(JdbcClient jdbc, OwnerFactory factory) {
        this.jdbc   = Objects.requireNonNull(jdbc);
        this.mapper = new OwnerRowMapper(factory);
    }

    @Override
    public Optional<Owner> findById(OwnerId id) {
        return mapper.map(
                jdbc.sql(FIND_BY_ID_QUERY)
                        .param("id", id.value())
                        .query().listOfRows()
        ).stream().findFirst();
    }

    @Override
    public List<Owner> findAll(int page, int pageSize) {
        return mapper.map(
                jdbc.sql(PAGINATED_ALL_QUERY)
                        .param("limit",  pageSize)
                        .param("offset", (page - 1) * pageSize)
                        .query().listOfRows()
        );
    }

    @Override
    public List<Owner> findByLastName(String lastName, int page, int pageSize) {
        return mapper.map(
                jdbc.sql(PAGINATED_BY_NAME_QUERY)
                        .param("prefix", lastName.toLowerCase() + "%")
                        .param("limit",  pageSize)
                        .param("offset", (page - 1) * pageSize)
                        .query().listOfRows()
        );
    }

    @Override
    public int countAll() {
        return jdbc.sql("SELECT COUNT(*) FROM owners")
                .query(Integer.class)
                .single();
    }

    @Override
    public int countByLastName(String lastName) {
        return jdbc.sql("SELECT COUNT(*) FROM owners WHERE LOWER(last_name) LIKE LOWER(:prefix)")
                .param("prefix", lastName + "%")
                .query(Integer.class)
                .single();
    }
}