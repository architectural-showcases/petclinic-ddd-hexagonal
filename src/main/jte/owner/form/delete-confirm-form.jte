@param io.github.dmitriirussu.petclinic.owner.core.domain.aggregate.Owner owner

@template.layout.main(title = "Delete Owner", content = @`

<h2>Подтверждение удаления</h2>

<p>Удалить владельца — «${owner.getFirstName()} ${owner.getLastName()}»?</p>

<form action="/owners/${owner.getId().value()}/delete" method="post">
    <button type="submit">Удалить</button>
</form>

<hr>
<a href="/owners/${owner.getId().value()}">Назад к владельцу</a>
&nbsp;|&nbsp;
<a href="/owners">На главную</a>

`)