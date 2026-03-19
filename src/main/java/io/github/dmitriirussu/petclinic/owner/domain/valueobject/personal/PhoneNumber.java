package io.github.dmitriirussu.petclinic.owner.domain.valueobject.personal;

import io.github.dmitriirussu.petclinic.kernel.SingleValueObject;
import io.github.dmitriirussu.petclinic.kernel.exception.DomainValidationException;

import java.util.Objects;
import java.util.regex.Pattern;

public final class PhoneNumber extends SingleValueObject<String> {

    private static final Pattern PATTERN =
            Pattern.compile("^\\+?[0-9\\s()\\-]{5,20}$");

    private PhoneNumber(String value) {
        super(validated(value));
    }

    public static PhoneNumber of(String value) { return new PhoneNumber(value); }

    private static String validated(String value) {
        String t = Objects.requireNonNull(value, "PhoneNumber required").trim();
        if (!PATTERN.matcher(t).matches())
            throw new DomainValidationException("Invalid phone number: " + t);
        return t;
    }
}
