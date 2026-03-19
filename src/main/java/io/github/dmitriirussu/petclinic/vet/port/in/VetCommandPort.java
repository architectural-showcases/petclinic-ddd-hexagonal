package io.github.dmitriirussu.petclinic.vet.port.in;

import io.github.dmitriirussu.petclinic.vet.domain.valueobject.VetId;
import io.github.dmitriirussu.petclinic.vet.domain.valueobject.VetName;

public interface VetCommandPort {
    VetId createVet(VetName name);
    void  updateVet(VetId id, VetName name);
    void  deleteVet(VetId id);
    void  addSpecialty(VetId vetId, String specialtyName);
    void  removeSpecialty(VetId vetId, String specialtyName);
}
