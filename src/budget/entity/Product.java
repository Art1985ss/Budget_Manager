package budget.entity;

import budget.Category;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class Product implements Comparable<Product> {
    private String name;
    private BigDecimal price;
    private Category category;

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Product() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String formatToSave() {
        return name + " | " + price + " | " + category.name() + "\n";
    }

    @Override
    public String toString() {
        return String.format("%s $%.2f", name, price);
    }

    @Override
    public int compareTo(@NotNull Product product) {
        return product.price.compareTo(this.price);
    }
}
