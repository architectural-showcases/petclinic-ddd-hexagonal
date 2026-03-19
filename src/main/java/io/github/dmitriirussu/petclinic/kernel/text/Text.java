package io.github.dmitriirussu.petclinic.kernel.text;

public final class Text {

    private final String value;
    private final String canonical;

    private Text(String value, String canonical) {
        this.value     = value;
        this.canonical = canonical;
    }

    public static Text of(String raw, TextPolicy policy) {
        String n = TextRules.normalize(raw);
        if (policy.notEmpty() && n.isEmpty())
            throw new IllegalArgumentException("Text cannot be empty");
        TextRules.validateLength(n, policy.min(), policy.max());
        if (policy.lettersOnly())
            TextRules.validateCharacters(n, policy.digitsAllowed()); // ← передаём флаг
        TextRules.validatePunctuation(n);
        return new Text(n, TextRules.canonical(n));
    }

    public String value()     { return value; }
    public String canonical() { return canonical; }

    @Override public boolean equals(Object o) {
        return o instanceof Text t && canonical.equals(t.canonical);
    }
    @Override public int hashCode() { return canonical.hashCode(); }
    @Override public String toString() { return value; }
}
