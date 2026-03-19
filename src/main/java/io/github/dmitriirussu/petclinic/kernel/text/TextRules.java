package io.github.dmitriirussu.petclinic.kernel.text;

import io.github.dmitriirussu.petclinic.kernel.exception.DomainValidationException;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public final class TextRules {

    private static final Pattern LETTERS_ONLY =
            Pattern.compile("^[\\p{L}\\p{M}\\s\\-'.,]+$");      // без цифр

    private static final Pattern LETTERS_AND_DIGITS =
            Pattern.compile("^[\\p{L}\\p{M}\\p{N}\\s\\-'.,]+$"); // с цифрами

    private TextRules() {}

    public static String normalize(String raw) {
        Objects.requireNonNull(raw, "Text required");
        return Normalizer.normalize(raw, Normalizer.Form.NFC)
                .strip()
                .replaceAll("\\s+", " ");
    }

    public static void validateLength(String text, int min, int max) {
        int len = text.codePointCount(0, text.length());
        if (len < min || len > max)
            throw new DomainValidationException(
                    "Length must be " + min + "-" + max + " chars, got " + len);
    }

    public static void validateCharacters(String text, boolean digitsAllowed) {
        Pattern p = digitsAllowed ? LETTERS_AND_DIGITS : LETTERS_ONLY;
        if (!p.matcher(text).matches())
            throw new DomainValidationException("Invalid characters: " + text);
    }

    public static void validatePunctuation(String text) {
        if (text.startsWith("-") || text.endsWith("-"))
            throw new DomainValidationException("Invalid punctuation: " + text);
    }

    public static String canonical(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFC)
                .toLowerCase(Locale.ROOT)
                .strip();
    }
}
