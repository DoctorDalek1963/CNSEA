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
}

public class PizzaSystem {

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

    public static void main(String[] args) {
        System.out.println("Please choose a topping:");
        System.out.println();

        for (int i = 0; i < pizzaToppings.length; i++) {
            System.out.println((i + 1) + ") " + pizzaToppings[i].displayOption());
        }
    }
}
