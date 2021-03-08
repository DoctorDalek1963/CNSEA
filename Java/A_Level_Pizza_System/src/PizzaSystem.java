import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

class PizzaTopping {
    private final String name;
    private final String description;
    private final float price;

    public PizzaTopping(String name, String description, float price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String displayOption() { return name + " - " + description + " - " + PizzaSystem.floatToPrice(price); }

    public String getName() { return name; }

    public float getPrice() { return price; }
}

public class PizzaSystem {

    static Scanner inputScanner = new Scanner(System.in);

    static ArrayList<PizzaTopping> orderItems = new ArrayList<>();

    public static PizzaTopping[] pizzaToppings = {
            new PizzaTopping("Cheese & Tomato", "Italian-style six-cheese blend", 7.5F),
            new PizzaTopping("BBQ Chicken", "Chargrilled chicken, barbeque sauce, bacon, onions", 7.9F),
            new PizzaTopping("Meat Feast", "Ham, pepperoni, sausage, bacon, spicy beef", 8.1F),
            new PizzaTopping("Piri Piri Chicken", "Chilli pepper sauce, chargrilled chicken", 8.8F),
            new PizzaTopping("Hawaii", "Ham, pineapple, mushrooms", 8.9F),
            new PizzaTopping("Mediterranean", "Chorizo, Italian–style sausage, jalapeno sausage", 9.5F),
            new PizzaTopping("The Mexican", "Jalapeno peppers, red peppers, spicy beef, onions", 9.7F),
            new PizzaTopping("The Works", "Pepperoni, sausage, ham, mushrooms, green peppers", 9.9F)
    };

    /**
     * Take user input as integer corresponding to item in PizzaTopping[] array. Will continue to loop until input is an integer in the range of the array.
     *
     * @param array Array of PizzaTopping objects
     * @return User inputted integer corresponding to array index. DOES NOT return array element.
     */
    private static int takeIntegerInputForArray(PizzaTopping[] array) {
        // Ask the user to pick an option and keep looping until their input is valid
        int inputNum = 0;
        boolean validInput = false;
        while (!validInput) {
            String input = inputScanner.nextLine();
            // Attempt to parse input as integer
            try {
                inputNum = parseInt(input);
                // Make sure input is in range
                if (inputNum > 0 & inputNum < (array.length + 1)) {
                    validInput = true;
                    inputNum -= 1;
                    System.out.println();
                    System.out.println("You have selected: " + array[inputNum].getName());
                    // Confirm selection
                    System.out.println("Is this correct?");
                    if (inputScanner.nextLine().matches("[Yy].*")) {
                        System.out.println();
                        break;
                    } else {
                        System.out.println();
                        System.out.println("Please enter your corrected choice.");
                        validInput = false; // Set this to false to run the loop again
                    }
                } else {
                    System.out.println();
                    System.out.println("That number is not in the valid range of 1-" + array.length + ". Please try again.");
                }
            } catch (NumberFormatException e) { // If input cannot be parsed as integer, try again
                System.out.println();
                System.out.println("That is not a number. Please try again.");
            }
        }
        return inputNum;
    }

    private static int selectItemFromOrder(String prompt) {
        System.out.println(prompt);
        System.out.println();

        for (int i = 0; i < orderItems.size(); i++) {
            System.out.println((i + 1) + ") " + orderItems.get(i).getName());
        }

        return takeIntegerInputForArray(orderItems.toArray(new PizzaTopping[0]));
    }

    public static String floatToPrice(float number) {
        String nonPaddedPrice = Float.toString(number);

        String beforeDecimal = nonPaddedPrice.split("[.]")[0];
        String afterDecimal = nonPaddedPrice.split("[.]")[1]; // The . is in [] because IntelliJ doesn't like "\." and I don't know why

        // In this scenario, the price will never have more than 1 decimal point
        if (afterDecimal.length() == 1) {
            afterDecimal += "0";
        } else if (afterDecimal.length() == 0) {
            afterDecimal = "00";
        }

        return "£" + beforeDecimal + "." + afterDecimal;
    }

    private static PizzaTopping chooseTopping(String prompt) {
        System.out.println(prompt);
        System.out.println();

        for (int i = 0; i < pizzaToppings.length; i++) {
            System.out.println((i + 1) + ") " + pizzaToppings[i].displayOption());
        }

        return pizzaToppings[takeIntegerInputForArray(pizzaToppings)];
    }

    private static void showFullOrder() {
        float total = 0;
        System.out.println("Here is your full order:");
        System.out.println();

        for (PizzaTopping item : orderItems) {
            System.out.println(item.getName());
            total += item.getPrice();
        }

        System.out.println();
        System.out.println("That comes to a total of " + floatToPrice(total));
        System.out.println();
    }

    private static void editOrder() {
        if ((orderItems.size() < 1)) {
            System.out.println("Your order is empty.");
            return;
        }
        int selection = selectItemFromOrder("Please select one item from your order to replace:");
        PizzaTopping originalItem = orderItems.get(selection);

        // Create two temporary lists, discarding the item chosen to be replaced
        List<PizzaTopping> tempStartList = orderItems.subList(0, selection);
        List<PizzaTopping> tempEndList = orderItems.subList(selection + 1, orderItems.size());

        ArrayList<PizzaTopping> tempStart = new ArrayList<>(tempStartList);
        ArrayList<PizzaTopping> tempEnd = new ArrayList<>(tempEndList);

        // Get the user to choose a new item and add it to the end of the first temp list
        PizzaTopping newItem = chooseTopping("Please choose a topping to replace it with:");
        tempStart.add(newItem);

        // Replace orderItems with the two temporary lists
        orderItems.clear();
        orderItems.addAll(tempStart);
        orderItems.addAll(tempEnd);

        System.out.println("Successfully replaced " + originalItem.getName() + " with " + newItem.getName() + ".");
    }

    private static void removeItemFromOrder() {
        if ((orderItems.size() < 1)) {
            System.out.println("Your order is empty.");
            return;
        }
        int selection = selectItemFromOrder("Please select one item from your order to remove:");
        String item = orderItems.get(selection).getName();
        orderItems.remove(selection);
        System.out.println(item + " has been removed.");
    }

    private static boolean confirmOrder() {
        float total = 0;
        StringBuilder orderString = new StringBuilder();

        for (PizzaTopping item : orderItems) {
            total += item.getPrice();
            orderString.append(item.getName()).append(",");
        }

        orderString.append(floatToPrice(total));

        try {
            // True enables append mode
            PrintWriter printWriter = new PrintWriter(new FileWriter("orders.csv", true));
            printWriter.println(orderString.toString());
            printWriter.close();
            System.out.println("Order confirmed.");
            return true;
        } catch (IOException e) {
            System.out.println("Failed to write to orders.csv");
            return false;
        }
    }

    public static void main(String[] args) {
        boolean orderConfirmed = false;

        while (!orderConfirmed) {
            System.out.println("Please choose an option:");
            System.out.println();
            System.out.println("1) Add a pizza to your order");
            System.out.println("2) Show your full order");
            System.out.println("3) Edit your order");
            System.out.println("4) Remove a pizza from your order");
            System.out.println("5) Confirm order");

            // Ask the user to pick an option and keep looping until their input is valid
            boolean validInput = false;
            int inputNum = 0;
            while (!validInput) {
                String input = inputScanner.nextLine();
                // Attempt to parse input as integer
                try {
                    inputNum = parseInt(input);
                    System.out.println();
                    if (inputNum > 0 & inputNum <= 5) {
                        validInput = true; // Break from loop
                    } else {
                        System.out.println("That number is not in the valid range of 1-5. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println();
                    System.out.println("That is not a number. Please try again.");
                }
            }

            switch (inputNum) {
                case 1 -> orderItems.add(chooseTopping("Please choose a topping:"));
                case 2 -> showFullOrder();
                case 3 -> editOrder();
                case 4 -> removeItemFromOrder();
                case 5 -> orderConfirmed = confirmOrder();
            }
        }
    }
}
