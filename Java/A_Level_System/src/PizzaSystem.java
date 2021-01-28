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

    public String displayOption() {
        return name + " - " + description + " - £" + price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public float getPriceAsFloat() {
        return parseFloat(price);
    }
}

public class PizzaSystem {

    static Scanner inputScanner = new Scanner(System.in);

    private ArrayList<PizzaTopping> orderItems;

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

    private static void addToppingToOrder() {
        System.out.println("Please choose a topping:");
        System.out.println();

        for (int i = 0; i < pizzaToppings.length; i++) {
            System.out.println((i + 1) + ") " + pizzaToppings[i].displayOption());
        }

        // Ask the user to pick an option and keep looping until their input is valid
        boolean validInput = false;
        while (!validInput) {
            String input = inputScanner.nextLine();
            try {
                int inputNum = parseInt(input);
                if (inputNum > 0 & inputNum < (pizzaToppings.length + 1)) {
                    validInput = true;
                    inputNum -= 1;
                    System.out.println("You have selected: " + pizzaToppings[inputNum].getName());
                } else {
                    System.out.println("That number is not in the valid range of 1-" + pizzaToppings.length + ". Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("That is not a number. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        addToppingToOrder();
    }
}
