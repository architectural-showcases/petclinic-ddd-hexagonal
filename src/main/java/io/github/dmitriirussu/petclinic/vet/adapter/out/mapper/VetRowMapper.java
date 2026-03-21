package io.github.dmitriirussu.petclinic.vet.adapter.out.mapper;

import io.github.dmitriirussu.petclinic.vet.core.domain.Vet;
import io.github.dmitriirussu.petclinic.vet.core.domain.VetFactory;
import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.Specialty;
import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.VetId;
import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.VetName;

import java.util.*;

public class VetRowMapper {

    private final VetFactory factory;

    public VetRowMapper(VetFactory factory) {
        this.factory = Objects.requireNonNull(factory);
    }

    public List<Vet> map(List<Map<String, Object>> rows) {
        List<Map<String, Object>> normalized = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Map<String, Object> norm = new LinkedHashMap<>();
            row.forEach((k, v) -> norm.put(k.toLowerCase(), v));
            normalized.add(norm);
        }

        Map<String, Vet> vets = new LinkedHashMap<>();

        for (Map<String, Object> row : normalized) {
            String vetId = (String) row.get("vet_id");

            vets.computeIfAbsent(vetId, id -> buildVet(row));

            String specialtyName = (String) row.get("specialty_name");
            if (specialtyName != null) {
                vets.get(vetId).addSpecialty(Specialty.of(specialtyName));
            }
        }

        return new ArrayList<>(vets.values());
    }

    private Vet buildVet(Map<String, Object> row) {
        return factory.restore(
                VetId.of((String) row.get("vet_id")),
                VetName.of(
                        (String) row.get("vet_first_name"),
                        (String) row.get("vet_last_name"))
        );
    }
}
