package pl.edu.pwr.twwo.domain.enumeration;

/**
 * The RodzajPrzedmiotu enumeration.
 */
public enum RodzajPrzedmiotu {
    OBOWIAZKOWY("Obowiązkowy"), WYBIERALNY("Wybieralny"), OGOLNOUCZELNIANY("Ogólnouczelniany");

    private final String value;

    RodzajPrzedmiotu(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
