package budget;

import budget.entity.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProductRepository {
    private final List<Product> list = new ArrayList<>();

    private Category category;
    private Predicate<Product> predicate;
    private BigDecimal balance = BigDecimal.ZERO;

    public void add(Product product) {
        if (product == null) {
            return;
        }
        list.add(product);
    }

    public Product get(int index) {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

    public void sort() {
        Collections.sort(list);
    }

    public String dataByType() {
        StringBuilder sb = new StringBuilder();
        sb.append("Types:").append("\n");
        Arrays.stream(Category.values()).forEach(cat -> {
            this.setCategory(cat);
            sb.append(category.getText())
                    .append(" - ")
                    .append(String.format("$%.2f", getTotalPrice()))
                    .append("\n");
        });
//        setCategory(null);
//        sb.append(String.format("Total sum: $%.2f", getTotalPrice())).append("\n");
        return sb.toString();
    }

    public String categoryData(Category category) {
        this.setCategory(category);
        sort();
        return this.toString();
    }

    public BigDecimal getTotalPrice() {
        return list.stream()
                .filter(predicate)
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setCategory(Category category) {
        this.category = category;
        predicate = product -> {
            if (category == null) {
                return true;
            }
            return product.getCategory().equals(category);
        };
    }


    public boolean isEmpty() {
        return list.isEmpty();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        if (list.isEmpty()) {
            return "Purchase list is empty!";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(category != null ? category.getText() : "All").append(":").append("\n");
        List<Product> listToPrint = list.stream().filter(predicate).collect(Collectors.toList());
        if (listToPrint.isEmpty()) {
            sb.append("Purchase list is empty!").append("\n");
        } else {
            listToPrint.forEach(product -> sb.append(product).append("\n"));
            sb.append(String.format("Total sum: $%.2f", getTotalPrice())).append("\n");
        }
        return sb.toString();
    }
}
