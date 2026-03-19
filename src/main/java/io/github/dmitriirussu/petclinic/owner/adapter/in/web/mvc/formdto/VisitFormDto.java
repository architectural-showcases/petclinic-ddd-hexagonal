package io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.formdto;

public class VisitFormDto {

    private String date;
    private String description;

    public VisitFormDto() {}

    public String getDate()        { return date; }
    public String getDescription() { return description; }

    public void setDate(String v)        { this.date        = v; }
    public void setDescription(String v) { this.description = v; }
}
