package io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.formdto;

import io.github.dmitriirussu.petclinic.owner.core.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.core.domain.entity.Pet;

import java.util.stream.Collectors;

public class OwnerListItemDto {

    private final String id;
    private final String fullName;
    private final String street;
    private final String city;
    private final String telephone;
    private final String petNames;

    public OwnerListItemDto(Owner owner) {
        this.id        = owner.getId().value();
        this.fullName  = owner.getFirstName() + " " + owner.getLastName();
        this.street    = owner.getStreet();
        this.city      = owner.getCity();
        this.telephone = owner.getTelephone();
        this.petNames  = owner.getPets().stream()
                .map(Pet::getName)
                .collect(Collectors.joining(", "));
    }

    public String getId()        { return id; }
    public String getFullName()  { return fullName; }
    public String getStreet()    { return street; }
    public String getCity()      { return city; }
    public String getTelephone() { return telephone; }
    public String getPetNames()  { return petNames; }
}
