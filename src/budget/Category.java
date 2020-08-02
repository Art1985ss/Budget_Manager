package budget;

import java.util.Arrays;
import java.util.function.Predicate;

public enum Category {
    FOOD(1, "Food"),
    ENTERTAINMENT(3, "Entertainment"),
    CLOTHES(2, "Clothes"),
    OTHER(4, "Other");

    private final int id;
    private final String text;

    Category(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public static Category getInstance(String idInput) throws BudgedException {
        int id = Integer.parseInt(idInput);
        Predicate<Category> predicate = menuOptions -> menuOptions.id == id;
        return Arrays.stream(values()).filter(predicate).findAny()
                .orElseThrow(() -> new BudgedException("Invalid menu selected"));
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return id + ") " + text;
    }

}
