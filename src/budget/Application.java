package budget;

import budget.entity.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;

public class Application {

    private final Scanner scanner = new Scanner(System.in);
    private final ProductRepository productRepository = new ProductRepository();

    private void printMenu() {
        Arrays.stream(MenuOptions.values()).forEach(System.out::println);
    }

    public void execute() {
        boolean run = true;
        do {
            printMenu();
            try {
                run = userInput();
            } catch (BudgedException e) {
                System.out.println(e.getMessage());
            }
        } while (run);
    }

    private boolean userInput() throws BudgedException {
        MenuOptions menuOption = MenuOptions.getInstance(scanner.nextLine());
        System.out.println();
        switch (menuOption) {
            case ADD_INCOME:
                addIncome();
                break;
            case ADD_PRODUCT:
                addProduct();
                break;
            case SHOW_PURCHASES:
                showProducts();
                break;
            case BALANCE:
                System.out.printf("Balance $%.2f%n", productRepository.getBalance());
                break;
            case SAVE:
                save();
                break;
            case LOAD:
                load();
                break;
            case SORT:
                sort();
                break;
            case EXIT:
                System.out.println("Bye!");
                return false;
            default:
                return true;
        }
        System.out.println();
        return true;
    }

    private void addIncome() {
        System.out.println("Enter income:");
        productRepository.setBalance(
                productRepository.getBalance().add(new BigDecimal(scanner.nextLine())));
        System.out.println("Income was added!");
    }

    private void addProduct() {
        while (true) {
            printCategoryMenu(false);
            String userInput = scanner.nextLine();
            System.out.println();
            if ("5".equals(userInput)) {
                return;
            }
            Category category = Category.getInstance(userInput);
            Product product = new Product();
            product.setCategory(category);
            System.out.println("Enter product name:");
            product.setName(scanner.nextLine());
            System.out.println("Enter its price:");
            product.setPrice(new BigDecimal(scanner.nextLine()));
            System.out.println("Purchase was added!");
            productRepository.add(product);
            productRepository.setBalance(productRepository.getBalance().subtract(product.getPrice()));
        }
    }

    private void printCategoryMenu(boolean showAll) {
        System.out.println();
        System.out.println("Choose the type of product");
        Arrays.stream(Category.values()).forEach(System.out::println);
        int categorySize = Category.values().length;
        if (showAll) {
            System.out.println(++categorySize + ") All");
        }
        System.out.println(++categorySize + ") Back");
    }

    private void showProducts() {
        if (productRepository.isEmpty()) {
            System.out.println(productRepository);
            return;
        }
        while (true) {
            printCategoryMenu(true);
            String userInput = scanner.nextLine();
            System.out.println();
            if ("6".equals(userInput)) {
                return;
            } else if ("5".equals(userInput)) {
                productRepository.setCategory(null);
            } else {
                productRepository.setCategory(Category.getInstance(userInput));
            }
            System.out.println(productRepository);
        }
    }

    private void save() {
        FileService.save(productRepository);
        System.out.println("Purchases were saved!");
    }

    private void load() {
        FileService.load(productRepository);
        System.out.println("Purchases were loaded!");
    }

    private void sort() {
        while (true) {
            System.out.println("How do you want to sort?\n" +
                    "1) Sort all purchases\n" +
                    "2) Sort by type\n" +
                    "3) Sort certain type\n" +
                    "4) Back");
            int selected = Integer.parseInt(scanner.nextLine());
            System.out.println();
            switch (selected) {
                case 1:
                    productRepository.sort();
                    productRepository.setCategory(null);
                    System.out.println(productRepository);
                    break;
                case 2:
                    System.out.println(productRepository.dataByType());
                    break;
                case 3:
                    sortByCertain();
                    break;
                case 4:
                default:
                    return;
            }
            System.out.println();
        }
    }

    private void sortByCertain() {
        System.out.println();
        System.out.println("Choose the type of products");
        Arrays.stream(Category.values()).forEach(System.out::println);
        Category category = Category.getInstance(scanner.nextLine());
        System.out.println();
        System.out.println(productRepository.categoryData(category));
    }
}
