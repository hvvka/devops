package pl.edu.pwr.twwo.domain.enumeration;

/**
 * The FormaStudiow enumeration.
 */
public enum FormaStudiow {
    STACJONARNE("Stacjonarne"), NIESTACJONARNE("Niestacjonarne");

    private final String value;

    FormaStudiow(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
