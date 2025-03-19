package enums;

public enum SubCategory {
    OFFICE_SUPPLIES("Оргтехника и расходники", "Оргтехника и расходники"),
    PHONES("Телефоны", "Телефоны"),
    DESKTOPS("Настольные компьютеры", "Настольные компьютеры"),
    AUDIO_VIDEO("Аудио и видео", "Аудио и видео"),
    PHOTO_EQUIPMENT("Фототехника", "Фототехника"),
    COMPUTER_ACCESSORIES("Товары для компьютера", "Товары для компьютера"),
    TABLETS_EBOOKS("Планшеты и электронные книги", "Планшеты и электронные книги"),
    GAMES_SOFTWARE("Игры, приставки и программы", "Игры, приставки и программы"),
    LAPTOPS("Ноутбуки", "Ноутбуки");

    private final String displayName;
    private final String dataName;

    SubCategory(String displayName, String dataName) {
        this.displayName = displayName;
        this.dataName = dataName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDataName() {
        return dataName;
    }

}
