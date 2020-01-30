package pl.edu.pwr.twwo.domain.enumeration;

/**
 * The FormaZaliczenia enumeration.
 */
public enum FormaZaliczenia {

    EGZAMIN("Egzamin"), NA_OCENE("Zaliczenie na ocenę");

    private final String value;

    FormaZaliczenia(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
