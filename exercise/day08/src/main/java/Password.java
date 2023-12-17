import java.util.Optional;

record Password(String value) {

    Password {
        if (value.length() < 8) {
            throw new IllegalArgumentException();
        }
        if (value.toLowerCase().equals(value)) {
            throw new IllegalArgumentException();
        }
        if (value.toUpperCase().equals(value)) {
            throw new IllegalArgumentException();
        }
        if (value.replaceAll("[0-9]", "").equals(value)) {
            throw new IllegalArgumentException();
        }
        if (value.replaceAll("[\\.$%&@#]", "").equals(value)) {
            throw new IllegalArgumentException();
        }
    }

    public static Optional<Password> from(String value) {
        try {
            return Optional.of(new Password(value));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
