@param io.github.dmitriirussu.petclinic.owner.core.domain.aggregate.Owner owner
@param String message

@template.layout.main(title = "Owner Information", content = @`

    <h2>Owner Information</h2>

    @if(message != null && !message.isEmpty())
        <div class="alert alert-success" id="success-message">
            <span>${message}</span>
        </div>
    @endif

    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><b>${owner.getFirstName()} ${owner.getLastName()}</b></td>
        </tr>
        <tr>
            <th>Address</th>
            <td>${owner.getStreet()}</td>
        </tr>
        <tr>
            <th>City</th>
            <td>${owner.getCity()}</td>
        </tr>
        <tr>
            <th>Telephone</th>
            <td>${owner.getTelephone()}</td>
        </tr>
    </table>

    <a href="/owners/${owner.getId().value()}/edit" class="btn btn-primary">Edit Owner</a>
    <a href="/owners/${owner.getId().value()}/pets/new" class="btn btn-primary">Add New Pet</a>

    <br/><br/><br/>

    <h2>Pets and Visits</h2>

    <table class="table table-striped">
        @for(var pet : owner.getPets())
            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Name</dt>
                        <dd>${pet.getName()}</dd>
                        <dt>Birth Date</dt>
                        <dd>${pet.getBirthDate().toString()}</dd>
                        <dt>Type</dt>
                        <dd>${pet.getType()}</dd>
                    </dl>
                </td>
                <td valign="top">
                    <table class="table-condensed">
                        <thead>
                        <tr>
                            <th>Visit Date</th>
                            <th>Description</th>
                        </tr>
                        </thead>
                        @for(var visit : pet.getVisits())
                            <tr>
                                <td>${visit.getDate().toString()}</td>
                                <td>${visit.getDescription()}</td>
                            </tr>
                        @endfor
                        <tr>
                            <td><a href="/owners/${owner.getId().value()}/pets/${pet.getId().value()}/edit">Edit Pet</a></td>
                            <td><a href="/owners/${owner.getId().value()}/pets/${pet.getId().value()}/visits/new">Add Visit</a></td>
                        </tr>
                    </table>
                </td>
            </tr>
        @endfor
    </table>

    <script>
        function hideMessages() {
            setTimeout(function () {
                var msg = document.getElementById("success-message");
                if (msg) msg.style.display = "none";
            }, 3000);
        }
        hideMessages();
    </script>

`)