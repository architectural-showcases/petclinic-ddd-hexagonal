package io.github.dmitriirussu.petclinic.owner.adapter.out.mapper;

import io.github.dmitriirussu.petclinic.owner.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.domain.aggregate.OwnerFactory;
import io.github.dmitriirussu.petclinic.owner.domain.entity.Pet;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.PetId;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.VisitId;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.personal.Address;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.personal.OwnerName;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.personal.PhoneNumber;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.pet.BirthDate;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.pet.PetName;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.pet.PetType;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.visit.VisitDate;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.visit.VisitDescription;
import io.github.dmitriirussu.petclinic.owner.domain.visit.Visit;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OwnerRowMapper {

    private final OwnerFactory factory;

    public OwnerRowMapper(OwnerFactory factory) {
        this.factory = factory;
    }

    public List<Owner> map(List<Map<String, Object>> rows) {
        List<Map<String, Object>> normalized = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Map<String, Object> norm = new LinkedHashMap<>();
            row.forEach((k, v) -> norm.put(k.toLowerCase(), v));
            normalized.add(norm);
        }

        Map<String, Owner> owners = new LinkedHashMap<>();
        Map<String, Map<String, Pet>> pets = new LinkedHashMap<>();

        for (Map<String, Object> row : normalized) {  // ← normalized вместо rows
            String ownerId = (String) row.get("owner_id");

            owners.computeIfAbsent(ownerId, id -> buildOwner(row));
            pets.computeIfAbsent(ownerId, id -> new LinkedHashMap<>());

            String petId = (String) row.get("pet_id");
            if (petId != null) {
                pets.get(ownerId).computeIfAbsent(petId, id -> buildPet(row));

                String visitId = (String) row.get("visit_id");
                if (visitId != null) {
                    pets.get(ownerId).get(petId).addVisit(buildVisit(row));
                }
            }
        }

        owners.forEach((ownerId, owner) -> {
            Map<String, Pet> ownerPets = pets.get(ownerId);
            if (ownerPets != null)
                ownerPets.values().forEach(owner::addPet);
        });

        return new ArrayList<>(owners.values());
    }

    private Owner buildOwner(Map<String, Object> row) {
        return factory.restore(
                OwnerId.of((String) row.get("owner_id")),
                OwnerName.of(
                        (String) row.get("owner_first_name"),
                        (String) row.get("owner_last_name")),
                Address.of(
                        (String) row.get("owner_street"),
                        (String) row.get("owner_city")),
                PhoneNumber.of((String) row.get("owner_telephone"))
        );
    }

    private Pet buildPet(Map<String, Object> row) {
        return new Pet(
                PetId.of((String) row.get("pet_id")),
                PetName.of((String) row.get("pet_name")),
                BirthDate.of(((java.sql.Date) row.get("pet_birth_date")).toLocalDate()),
                PetType.of((String) row.get("pet_type"))
        );
    }

    private Visit buildVisit(Map<String, Object> row) {
        return new Visit(
                VisitId.of((String) row.get("visit_id")),
                VisitDate.restore(((java.sql.Date) row.get("visit_date")).toLocalDate()),
                VisitDescription.of((String) row.get("visit_description"))
        );
    }
}
