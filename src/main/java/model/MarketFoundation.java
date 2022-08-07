package model;

public class MarketFoundation {
    private final String code;
    private final String type;
    private final String name;

    public MarketFoundation(String code, String type, String name) {
        this.code = code;
        this.type = type;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
