package enums;

public enum SortType {
    DEFAULT("По умолчанию"),
    CHEAPER("Дешевле"),
    EXPENSIVE("Дороже"),
    BY_DATE("По дате");

    private final String displayName;

    SortType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
