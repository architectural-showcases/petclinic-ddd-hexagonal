@param io.github.dmitriirussu.petclinic.owner.core.domain.aggregate.Owner owner
@param io.github.dmitriirussu.petclinic.owner.core.domain.entity.Pet pet
@param io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.formdto.VisitFormDto visitForm
@param String error

@template.layout.main(title = "New Visit", content = @`

    <h2>New Visit</h2>

    @if(error != null && !error.isEmpty())
        <div class="alert alert-danger">${error}</div>
    @endif

    <b>Pet</b>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Birth Date</th>
            <th>Type</th>
            <th>Owner</th>
        </tr>
        </thead>
        <tr>
            <td>${pet.getName()}</td>
            <td>${pet.getBirthDate().toString()}</td>
            <td>${pet.getType()}</td>
            <td>${owner.getFirstName()} ${owner.getLastName()}</td>
        </tr>
    </table>

    <form action="/owners/${owner.getId().value()}/pets/${pet.getId().value()}/visits/new" class="form-horizontal" method="post">

        <div class="form-group has-feedback">

            <div class="form-group">
                <label class="col-sm-2 control-label">Date</label>
                <div class="col-sm-10">
                    <input class="form-control" type="date" name="date"
                           min="${java.time.LocalDate.now().toString()}"
                           value="${visitForm.getDate() != null ? visitForm.getDate() : ""}"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">Description</label>
                <div class="col-sm-10">
                    <input class="form-control" type="text" name="description"
                           value="${visitForm.getDescription() != null ? visitForm.getDescription() : ""}"/>
                </div>
            </div>

        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-primary" type="submit">Add Visit</button>
            </div>
        </div>

    </form>

    <br/>

    <b>Previous Visits</b>
    <table class="table table-striped">
        <tr>
            <th>Date</th>
            <th>Description</th>
        </tr>
        @for(var visit : pet.getVisits())
            <tr>
                <td>${visit.getDate().toString()}</td>
                <td>${visit.getDescription()}</td>
            </tr>
        @endfor
    </table>

`)