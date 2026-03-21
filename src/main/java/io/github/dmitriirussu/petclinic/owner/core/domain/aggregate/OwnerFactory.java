package io.github.dmitriirussu.petclinic.owner.core.domain.aggregate;

import io.github.dmitriirussu.petclinic.kernel.idgenerator.IdGenerator;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.personal.Address;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.personal.OwnerName;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.personal.PhoneNumber;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OwnerFactory {

    private final IdGenerator idGenerator;

    public OwnerFactory(IdGenerator idGenerator) {
        this.idGenerator = Objects.requireNonNull(idGenerator);
    }

    public Owner create(OwnerName name, Address address, PhoneNumber telephone) {
        return new Owner(
                OwnerId.of(idGenerator.nextId()),
                name,
                address,
                telephone
        );
    }

    public Owner restore(OwnerId id, OwnerName name, Address address, PhoneNumber telephone) {
        return new Owner(id, name, address, telephone);
    }
}
