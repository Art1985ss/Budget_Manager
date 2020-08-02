package budget;

import budget.entity.Product;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private static final File FILE_TO_SAVE = new File("purchases.txt");

    public static void save(ProductRepository productRepository) {
        int size = productRepository.size();
        try (FileWriter writer = new FileWriter(FILE_TO_SAVE, false)) {
            writer.write(productRepository.getBalance().toString() + "\n");
            for (int i = 0; i < size; i++) {
                writer.write(productRepository.get(i).formatToSave());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load(ProductRepository productRepository) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Path.of(String.valueOf(FILE_TO_SAVE)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lines.isEmpty()) {
            return;
        }
        productRepository.setBalance(new BigDecimal(lines.get(0)));
        for (int i = 1; i < lines.size(); i++) {
            Product product = new Product();
            String[] data = lines.get(i).trim().split("\\s+\\|\\s+");
            product.setName(data[0]);
            product.setPrice(new BigDecimal(data[1]));
            product.setCategory(Category.valueOf(data[2]));
            productRepository.add(product);
        }
    }
}


