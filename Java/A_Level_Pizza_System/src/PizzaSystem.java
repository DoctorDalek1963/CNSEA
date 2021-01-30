import java.util.*;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

class PizzaTopping {
    private final String name;
    private final String description;
    private final String price;

    public PizzaTopping(String name, String description, String price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String displayOption() { return name + " - " + description + " - £" + price; }

    public String getName() { return name; }

    public float getPriceAsFloat() { return parseFloat(price); }
}

public class PizzaSystem {

    static Scanner inputScanner = new Scanner(System.in);

    static ArrayList<PizzaTopping> orderItems = new ArrayList<>();

    public static PizzaTopping[] pizzaToppings = {
            new PizzaTopping("Cheese & Tomato", "Italian-style six-cheese blend", "7.50"),
            new PizzaTopping("BBQ Chicken", "Chargrilled chicken, barbeque sauce, bacon, onions", "7.90"),
            new PizzaTopping("Meat Feast", "Ham, pepperoni, sausage, bacon, spicy beef", "8.10"),
            new PizzaTopping("Piri Piri Chicken", "Chilli pepper sauce, chargrilled chicken", "8.80"),
            new PizzaTopping("Hawaii", "Ham, pineapple, mushrooms", "8.90"),
            new PizzaTopping("Mediterranean", "Chorizo, Italian–style sausage, jalapeno sausage", "9.50"),
            new PizzaTopping("The Mexican", "Jalapeno peppers, red peppers, spicy beef, onions", "9.70"),
            new PizzaTopping("The Works", "Pepperoni, sausage, ham, mushrooms, green peppers", "9.90")
    };

    private static PizzaTopping chooseTopping() {
        System.out.println("Please choose a topping:");
        System.out.println();

        for (int i = 0; i < pizzaToppings.length; i++) {
            System.out.println((i + 1) + ") " + pizzaToppings[i].displayOption());
        }

        return pizzaToppings[takeIntegerInputForArray(pizzaToppings)];
    }

    private static String floatToPrice(float number) {
        String nonPaddedPrice = Float.toString(number);

        String beforeDecimal = nonPaddedPrice.split("[.]")[0];
        String afterDecimal = nonPaddedPrice.split("[.]")[1]; // The . is in [] because IntelliJ doesn't like "\." and I don't know why

        // In this scenario, the price will never have more than 1 decimal point
        if (afterDecimal.length() == 1) {
            afterDecimal += "0";
        } else if (afterDecimal.length() == 0) {
            afterDecimal = "00";
        }

        return beforeDecimal + "." + afterDecimal;
    }

    private static void showFullOrder() {
        float total = 0;
        System.out.println("Here is your full order:");
        System.out.println();

        for (PizzaTopping item : orderItems) {
            System.out.println(item.getName());
            total += item.getPriceAsFloat();
        }

        System.out.println();
        System.out.println("That comes to a total of £" + floatToPrice(total));
    }

    /**
     * Take user input as integer corresponding to item in PizzaTopping[] array. Will continue to loop until input is an integer in the range of the array.
     *
     * @param array Array of PizzaTopping objects
     * @return User inputted integer. DOES NOT return array element.
     */
    private static int takeIntegerInputForArray(PizzaTopping[] array) {
        // Ask the user to pick an option and keep looping until their input is valid
        boolean validInput = false;
        while (!validInput) {
            String input = inputScanner.nextLine();
            // Attempt to parse input as integer
            try {
                int inputNum = parseInt(input);
                // Make sure input is in range
                if (inputNum > 0 & inputNum < (array.length + 1)) {
                    validInput = true;
                    inputNum -= 1;
                    System.out.println("You have selected: " + array[inputNum].getName());
                    // Confirm selection
                    System.out.println("Is this correct?");
                    if (inputScanner.nextLine().matches("[Yy].*")) {
                        return inputNum;
                    } else {
                        System.out.println("Please enter your corrected choice.");
                        validInput = false; // Set this to false to run the loop again
                    }
                } else {
                    System.out.println("That number is not in the valid range of 1-" + array.length + ". Please try again.");
                }
            } catch (NumberFormatException e) { // If input cannot be parsed as integer, try again
                System.out.println("That is not a number. Please try again.");
            }
        }
        throw new RuntimeException("This exception should never be reached. If it gets thrown, something has gone horribly wrong.");
    }

    private static int selectItemFromOrder(String prompt) {
        System.out.println(prompt);
        System.out.println();

        for (int i = 0; i < orderItems.size(); i++) {
            System.out.println((i + 1) + ") " + orderItems.get(i).getName());
        }

        return takeIntegerInputForArray(orderItems.toArray(new PizzaTopping[0]));
    }

    private static void editOrder() {
        // TODO: then allow them to choose a new topping in place of that item
        if (!(orderItems.size() > 0)) {
            System.out.println("Your order is empty.");
            return;
        }
        int selection = selectItemFromOrder("Please select one item from your order to edit:");
        PizzaTopping newItem = chooseTopping();

        // TODO: Replace orderItems.get(selection) with newItem
        // Apparently this would be easier to do with a HashMap, so I'll learn how to do that later
    }

    private static void removeItemFromOrder() {
        if (!(orderItems.size() > 0)) {
            System.out.println("Your order is empty.");
            return;
        }
        int selection = selectItemFromOrder("Please select one item from your order to remove:");
        String item = orderItems.get(selection).getName();
        orderItems.remove(selection);
        System.out.println(item + " has been removed.");
    }

    private static boolean confirmOrder() {
        // TODO: write order to file
        return true;
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
                    if (inputNum > 0 & inputNum <= 5) {
                        validInput = true; // Break from loop
                    } else {
                        System.out.println("That number is not in the valid range of 1-5. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("That is not a number. Please try again.");
                }
            }

            switch (inputNum) {
                case 1 -> orderItems.add(chooseTopping());
                case 2 -> showFullOrder();
                case 3 -> editOrder();
                case 4 -> removeItemFromOrder();
                case 5 -> orderConfirmed = confirmOrder();
            }
        }
    }
}
