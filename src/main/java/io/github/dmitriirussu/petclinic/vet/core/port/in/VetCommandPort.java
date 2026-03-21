package io.github.dmitriirussu.petclinic.vet.core.port.in;

import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.VetId;
import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.VetName;

public interface VetCommandPort {
    VetId createVet(VetName name);
    void  updateVet(VetId id, VetName name);
    void  deleteVet(VetId id);
    void  addSpecialty(VetId vetId, String specialtyName);
    void  removeSpecialty(VetId vetId, String specialtyName);
}
