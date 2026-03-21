package io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.formdto;

import io.github.dmitriirussu.petclinic.owner.core.domain.aggregate.Owner;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OwnerFormDto {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Telephone is required")
    @Pattern(regexp = "^\\+?[0-9\\s()\\-]{5,20}$", message = "Invalid phone number format")
    private String telephone;

    public OwnerFormDto() {}

    public static OwnerFormDto from(Owner owner) {
        OwnerFormDto dto = new OwnerFormDto();
        dto.firstName = owner.getFirstName();
        dto.lastName  = owner.getLastName();
        dto.street    = owner.getStreet();
        dto.city      = owner.getCity();
        dto.telephone = owner.getTelephone();
        return dto;
    }

    public String getFirstName()  { return firstName; }
    public String getLastName()   { return lastName; }
    public String getStreet()     { return street; }
    public String getCity()       { return city; }
    public String getTelephone()  { return telephone; }

    public void setFirstName(String v)  { this.firstName  = v; }
    public void setLastName(String v)   { this.lastName   = v; }
    public void setStreet(String v)     { this.street     = v; }
    public void setCity(String v)       { this.city       = v; }
    public void setTelephone(String v)  { this.telephone  = v; }
}
