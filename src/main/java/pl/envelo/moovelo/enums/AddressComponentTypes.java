package pl.envelo.moovelo.enums;

public enum AddressComponentTypes {

    STREET_NAME("route"),
    STREET_NUMBER("street_number"),
    POSTAL_CODE("postal_code"),
    CITY("locality"),
    COUNTRY("country");

    private final String description;

    AddressComponentTypes(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
