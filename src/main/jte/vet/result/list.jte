@param java.util.List<io.github.dmitriirussu.petclinic.vet.domain.Vet> vets
@param int currentPage
@param int totalPages

@template.layout.main(title = "Veterinarians", content = @`

    <h2>Veterinarians</h2>

    <table id="vets" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Specialties</th>
        </tr>
        </thead>
        <tbody>
        @for(var vet : vets)
            <tr>
                <td>${vet.getFirstName()} ${vet.getLastName()}</td>
                <td>
                    @if(vet.getSpecialtyCount() == 0)
                        <span>none</span>
                    @else
                        @for(var s : vet.getSpecialties())
                            <span>${s.getName()}</span>
                        @endfor
                    @endif
                </td>
            </tr>
        @endfor
        </tbody>
    </table>

    @if(totalPages > 1)
        <div>
            <span>Pages:</span>
            <span>[</span>
            @for(int i = 1; i <= totalPages; i++)
                @if(currentPage == i)
                    <span>${i}</span>
                @else
                    <a href="/vets?page=${i}">${i}</a>
                @endif
            @endfor
            <span>]&nbsp;</span>

            @if(currentPage > 1)
                <a href="/vets?page=1" title="first" class="fa fa-fast-backward"></a>
                <a href="/vets?page=${currentPage - 1}" title="previous" class="fa fa-step-backward"></a>
            @else
                <span title="first" class="fa fa-fast-backward"></span>
                <span title="previous" class="fa fa-step-backward"></span>
            @endif

            @if(currentPage < totalPages)
                <a href="/vets?page=${currentPage + 1}" title="next" class="fa fa-step-forward"></a>
                <a href="/vets?page=${totalPages}" title="last" class="fa fa-fast-forward"></a>
            @else
                <span title="next" class="fa fa-step-forward"></span>
                <span title="last" class="fa fa-fast-forward"></span>
            @endif
        </div>
    @endif

`)