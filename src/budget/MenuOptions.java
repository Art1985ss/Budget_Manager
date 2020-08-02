package budget;

import java.util.Arrays;
import java.util.function.Predicate;

public enum MenuOptions {
    ADD_INCOME(1, "Add income"),
    ADD_PRODUCT(2, "Add product"),
    SHOW_PURCHASES(3, "Show list of purchases"),
    BALANCE(4, "Balance"),
    SAVE(5, "Save"),
    LOAD(6, "Load"),
    SORT(7, "Analyze (Sort)"),
    EXIT(0, "Exit");

    private final int id;
    private final String text;

    MenuOptions(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public static MenuOptions getInstance(String idInput) throws BudgedException {
        int id = Integer.parseInt(idInput);
        Predicate<MenuOptions> predicate = menuOptions -> menuOptions.id == id;
        return Arrays.stream(values()).filter(predicate).findAny()
                .orElseThrow(() -> new BudgedException("Invalid menu selected"));
    }

    @Override
    public String toString() {
        return id + ") " + text;
    }
}
