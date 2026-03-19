package io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.formdto;

import io.github.dmitriirussu.petclinic.owner.domain.entity.Pet;

public class PetFormDto {

    private String name;
    private String birthDate;
    private String type;

    public PetFormDto() {}

    public static PetFormDto from(Pet pet) {
        PetFormDto dto = new PetFormDto();
        dto.name      = pet.getName();
        dto.birthDate = pet.getBirthDate().toString();
        dto.type      = pet.getType();
        return dto;
    }

    public String getName()      { return name; }
    public String getBirthDate() { return birthDate; }
    public String getType()      { return type; }

    public void setName(String v)      { this.name      = v; }
    public void setBirthDate(String v) { this.birthDate = v; }
    public void setType(String v)      { this.type      = v; }
}
