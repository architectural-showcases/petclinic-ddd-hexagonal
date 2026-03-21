@param io.github.dmitriirussu.petclinic.owner.core.domain.aggregate.Owner owner
@param io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.formdto.PetFormDto petForm
@param String petId
@param java.util.List<io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.pet.PetType> petTypes
@param String error

@template.layout.main(title = "${petId != null ? \"Edit Pet\" : \"New Pet\"}", content = @`

    <h2>${petId != null ? "Edit Pet" : "New Pet"}</h2>

    @if(error != null && !error.isEmpty())
        <div class="alert alert-danger">${error}</div>
    @endif

    <form action="/owners/${owner.getId().value()}/pets/${petId != null ? petId + "/edit" : "new"}" class="form-horizontal" method="post">

        <div class="form-group has-feedback">

            <div class="form-group">
                <label class="col-sm-2 control-label">Owner</label>
                <div class="col-sm-10">
                    <span>${owner.getFirstName()} ${owner.getLastName()}</span>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">Name</label>
                <div class="col-sm-10">
                    <input class="form-control" type="text" name="name"
                           value="${petForm.getName() != null ? petForm.getName() : ""}"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">Birth Date</label>
                <div class="col-sm-10">
                    <input class="form-control" type="date" name="birthDate"
                           max="${java.time.LocalDate.now().toString()}"
                           value="${petForm.getBirthDate() != null ? petForm.getBirthDate() : ""}"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">Type</label>
                <div class="col-sm-10">
                    <select class="form-control" name="type">
                        @for(var t : petTypes)
                            @if(petForm.getType() != null && petForm.getType().equals(t.getName()))
                                <option value="${t.getName()}" selected>${t.getName()}</option>
                            @else
                                <option value="${t.getName()}">${t.getName()}</option>
                            @endif
                        @endfor
                    </select>
                </div>
            </div>

        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-primary" type="submit">
                    ${petId != null ? "Update Pet" : "Add Pet"}
                </button>
            </div>
        </div>

    </form>

    <a href="/owners/${owner.getId().value()}" class="btn btn-default">Back to Owner</a>

`)