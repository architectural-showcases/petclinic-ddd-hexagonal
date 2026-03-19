package io.github.dmitriirussu.petclinic.kernel.text;

public record TextPolicy(int min, int max, boolean notEmpty,
                         boolean lettersOnly, boolean digitsAllowed) {

    public static TextPolicy personName() {
        return new TextPolicy(2,  50,  true, true, false);
    }
    public static TextPolicy petName()    {
        return new TextPolicy(1,  30,  true, true, false);
    }
    public static TextPolicy address()    {
        return new TextPolicy(2,  100, true, true, true);
    } // ← цифры ok
    public static TextPolicy city()       {
        return new TextPolicy(2,  50,  true, true, false);
    }
    public static TextPolicy description(){
        return new TextPolicy(1,  500, true, false, true);
    }

    public static TextPolicy specialty() {
        return new TextPolicy(2, 50, true, true, false);
    }

    public static TextPolicy of(int min, int max, boolean lettersOnly) {
        return new TextPolicy(min, max, true, lettersOnly, false);
    }
}
