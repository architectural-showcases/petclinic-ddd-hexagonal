package io.github.dmitriirussu.petclinic.owner.adapter.out.jooq;

import io.github.dmitriirussu.petclinic.owner.adapter.out.mapper.OwnerRowMapper;
import io.github.dmitriirussu.petclinic.owner.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.domain.aggregate.OwnerFactory;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.port.out.OwnerReadRepository;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
@Profile("jooq")
public class JooqOwnerReadRepository implements OwnerReadRepository {

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
            WHERE o.id = ?
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
                LIMIT ? OFFSET ?
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
            WHERE LOWER(last_name) LIKE ?
            ORDER BY last_name
            LIMIT ? OFFSET ?
        ) o
        LEFT JOIN pets    p ON p.owner_id = o.id
        LEFT JOIN visits  v ON v.pet_id   = p.id
        """;

    private final DSLContext     dsl;
    private final OwnerRowMapper mapper;

    public JooqOwnerReadRepository(DSLContext dsl, OwnerFactory factory) {
        this.dsl    = Objects.requireNonNull(dsl);
        this.mapper = new OwnerRowMapper(factory);
    }

    @Override
    public Optional<Owner> findById(OwnerId id) {
        return mapper.map(
                dsl.fetch(FIND_BY_ID_QUERY, id.value()).intoMaps()
        ).stream().findFirst();
    }

    @Override
    public List<Owner> findAll(int page, int pageSize) {
        return mapper.map(
                dsl.fetch(PAGINATED_ALL_QUERY, pageSize, (page - 1) * pageSize).intoMaps()
        );
    }

    @Override
    public List<Owner> findByLastName(String lastName, int page, int pageSize) {
        return mapper.map(
                dsl.fetch(PAGINATED_BY_NAME_QUERY,
                                lastName.toLowerCase() + "%",
                                pageSize,
                                (page - 1) * pageSize)
                        .intoMaps()
        );
    }
    @Override
    public int countAll() {
        return dsl.fetchOne("SELECT COUNT(*) FROM owners").get(0, Integer.class);
    }

    @Override
    public int countByLastName(String lastName) {
        return dsl.fetchOne(
                "SELECT COUNT(*) FROM owners WHERE LOWER(last_name) LIKE ?",
                lastName.toLowerCase() + "%"
        ).get(0, Integer.class);
    }
}