import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;

class User{
    protected String userId;
    protected String password;
    protected int role;

    public User(String userId, String password, int role){
        this.userId = userId;
        this.password = password;
        this.role = role;
    }

    public String getUserId(){ return userId; }
    public String getPassword(){ return password; }
    public int getRole(){ return role; }
}

class Customer extends User{
    public Customer(String userId, String password){
        super(userId, password, 1);
    }
}

class Admin extends User{
    public Admin(String userId, String password){
        super(userId, password, 2);
    }
}

class Item{
    private static int itemCount = 0;
    private String itemId;
    private String name;
    private String description;
    private ArrayList<String> variants;
    private ArrayList<Double> price;
    private ArrayList<Integer> quantity;

    public Item(String name, String description, ArrayList<String> variants, ArrayList<Double> price, ArrayList<Integer> quantity){
        this.itemId = "I" + itemCount++;
        this.name = name;
        this.description = description;
        this.variants = new ArrayList<>(variants);
        this.price = new ArrayList<>(price);
        this.quantity = new ArrayList<>(quantity);
    }

    public void setName(String name){
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void addVariant(String variant, Double price, int quantity){
        this.variants.add(variant);
        this.price.add(price);
        this.quantity.add(quantity);
    }

    public String getItemId(){ return itemId; }
    public String getItemName(){ return name; }
    public String getItemDescription(){ return description; }
    public String getVariant(int index){ return variants.get(index); }
    public Double getVariantPrice(int index){ return price.get(index); }
    public int getVariantQuantity(int index){ return quantity.get(index); }
    public int getVariantCount(){ return variants.size(); }

    public void updateVariant(int variantIndex, String variantName, Double variantPrice, int variantQuantity){
        variants.set(variantIndex, variantName);
        price.set(variantIndex, variantPrice);
        quantity.set(variantIndex, variantQuantity);
    }

    public void decrementVariantQuantity(int variantIndex){
        quantity.set(variantIndex, quantity.get(variantIndex) - 1);
    }

    @Override
    public String toString(){
        String str = itemId + " " + name + "\n" + "Description: " + description + "\nVariants\n";

        for(int i = 0; i < variants.size(); i++){
            str = str + (i+1) + ". " + variants.get(i) + "  Price: RM " + price.get(i) + "  Quantity: " + quantity.get(i) + "\n";
        }

        return str;
    }
}

class Cart{
    private static int cartCount = 0;
    private String cartId;
    private Customer customer;
    private ArrayList<Item> items;
    private ArrayList<Integer> variantIndex;
    private String status;

    public Cart(Customer customer, ArrayList<Item> items, ArrayList<Integer> variantIndex, String status){
        this.cartId = "CART" + cartCount++;
        this.customer = customer;
        this.items = new ArrayList<>(items);
        this.variantIndex = new ArrayList<>(variantIndex);
        this.status = status;
    }

    public void addItem(Item item, int index){
        items.add(item);
        variantIndex.add(index);
    }

    public void updateStatus(String status){
        this.status = status;
    }

    public String getCartId(){ return cartId; }
    public String getCustomerId(){ return customer.getUserId(); }
    public int getItemCount(){ return items.size(); }
    public String getItemId(int i){ return items.get(i).getItemId(); }
    public String getItemName(int i){ return items.get(i).getItemName(); }
    public int getVariantIndex(int i){ return variantIndex.get(i); }
    public String getVariantName(int i, int v){ return items.get(i).getVariant(v); }
    public Double getVariantPrice(int i, int v){ return items.get(i).getVariantPrice(v); }
    public String getStatus(){ return status; }

    @Override
    public String toString(){
        String str = "CartId: " + cartId + "\n" + "CustomerId: " + customer.userId + "\n";

        for(int i = 0; i < items.size(); i++){
            str = str + (i+1) + ". " + items.get(i).getItemId() + " " + items.get(i).getItemName() + "  Variant: " + items.get(i).getVariant(variantIndex.get(i)) + "\n";
        }

        return str;
    }
}

class CartHistory{
    private ArrayList<Cart> carts;

    public CartHistory(){
        carts = new ArrayList<>();
    }

    public void addCart(Cart cart){
        carts.add(cart);
        System.out.println(cart);
        System.out.println("Cart Added with ID: " + cart.getCartId());
    }

    public void printTable(){
        System.out.printf("%-8s | %-15s | %-10s | %-20s | %-20s | %-10s | %-10s\n", 
            "CartId", "CustomerId", "ItemId", "Items", "Variants", "Price", "Status");
        for(Cart cart : carts){
            System.out.printf("%-8s | %-15s | %-10s | %-20s | %-20s | %10.2f | %-10s\n", 
                cart.getCartId(), 
                cart.getCustomerId(), 
                cart.getItemId(0), 
                cart.getItemName(0), 
                cart.getVariantName(0, cart.getVariantIndex(0)), 
                cart.getVariantPrice(0, cart.getVariantIndex(0)), 
                cart.getStatus());

            if(cart.getItemCount() > 1){
                for(int i = 1; i < cart.getItemCount(); i++){
                    System.out.printf("%-8s | %-15s | %-10s | %-20s | %-20s | %10.2f | %-10s\n", 
                        " ", 
                        " ", 
                        cart.getItemId(i), 
                        cart.getItemName(i),
                        cart.getVariantName(i, cart.getVariantIndex(i)), 
                        cart.getVariantPrice(i, cart.getVariantIndex(i)), 
                        " "
                    );
                }
            }
        }
        System.out.println();
    }

    public void updateStatus(Scanner scanner){
        System.out.println("<<<<< Update Cart Status >>>>>");
        System.out.print("CartId: ");
        String cartId = scanner.nextLine();

        for(Cart cart : carts){
            if(cart.getCartId().equals(cartId)){
                System.out.println(cart);
                System.out.println("Statuses");
                System.out.println("1. Processing");
                System.out.println("2. Shipped");
                System.out.println("3. Completed");
                int status = -1;

                while (true) {
                    try {
                        System.out.print("New Status: ");
                        status = Integer.parseInt(scanner.nextLine());
                        
                        if (status < 1 || status > 3) {
                            System.out.println("Invalid status\n");
                            continue;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input\n");
                    }
                }

                String statusString = "Processing";
                if(status == 1){
                    statusString = "Processing";
                }
                else if(status == 2){
                    statusString = "Shipped";
                }
                else if(status == 3){
                    statusString = "Completed";
                }

                cart.updateStatus(statusString);
                System.out.println("Status sucessfully update\n");
                System.out.println(cart);
                return;
            }
        }
        System.out.println("Cart ID not found\n");
    }
}

public class ShoppingCart{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        // Data Initialisation
        // Users
        ArrayList<User> users = new ArrayList<>();
        users.add(new Customer("C1", "123"));
        users.add(new Customer("C2", "123"));
        users.add(new Customer("C3", "123"));
        users.add(new Admin("A1", "123"));

        // Items
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(
            "Notebook",
            "College-ruled, 100 pages",
            new ArrayList<>(List.of("Blue Cover", "Red Cover")),
            new ArrayList<>(List.of(2.49, 2.49)),
            new ArrayList<>(List.of(50, 75))
        ));

        items.add(new Item(
            "Backpack",
            "Water-resistant backpack with multiple compartments",
            new ArrayList<>(List.of("Black", "Gray", "Navy Blue")),
            new ArrayList<>(List.of(29.99, 34.99, 32.50)),
            new ArrayList<>(List.of(20, 15, 10))
        ));

        items.add(new Item(
            "Water Bottle",
            "1-liter BPA-free plastic bottle",
            new ArrayList<>(List.of("Green", "Clear", "Blue")),
            new ArrayList<>(List.of(9.99, 8.99, 10.49)),
            new ArrayList<>(List.of(100, 80, 90))
        ));

        // Add Cart
        Cart cart1 = new Cart(
            (Customer) users.get(0),
            new ArrayList<>(List.of(items.get(0), items.get(1))), 
            new ArrayList<>(List.of(0, 1)), 
            "Pending"
        );

        Cart cart2 = new Cart(
            (Customer) users.get(1),
            new ArrayList<>(List.of(items.get(2))),
            new ArrayList<>(List.of(2)),
            "Completed"
        );

        Cart cart3 = new Cart(
            (Customer) users.get(2),
            new ArrayList<>(List.of(items.get(1), items.get(2))),
            new ArrayList<>(List.of(0, 1)),
            "Shipped"
        );

        // CartList
        CartHistory carts = new CartHistory();
        carts.addCart(cart1);
        carts.addCart(cart2);
        carts.addCart(cart3);

        // Main Logic
        Boolean loggedIn = false;

        while(true){
            // Login
            User user = login(scanner, users);
            if(user == null){
                loggedIn = false;
            }
            else if(user.getRole() == 1 || user.getRole() == 2){
                loggedIn = true;
            }

            while(loggedIn){
                // Customer
                if(user.getRole() == 1){
                    switch(customerMenu(scanner)){
                        case 1: // Add cart // Done
                            System.out.println("<<<<< Items Available >>>>>");
                            printItemTable(items);
                            carts.addCart(createCart(scanner, user, items));
                            break;
                        case 2: // Logout // Done
                            user = null;
                            loggedIn = false;
                            System.out.println("Sucessfully logged out\n");
                            break;
                    }
                }
                // Admin
                else if(user.getRole() == 2){
                    switch(adminMenu(scanner)){
                        case 1: // View Carts // Done
                            carts.printTable();
                            break;
                        case 2: // Update Cart Status // Done
                            carts.updateStatus(scanner);
                            break;
                        case 3: // View Items
                            printItemTable(items);
                            break;
                        case 4: // Update Item // Done
                            updateItem(scanner, items);
                            break;
                        case 5: // Add Item // Done
                            items.add(createItem(scanner));
                            break;
                        case 6: // Logout // Done
                            user = null;
                            loggedIn = false;
                            System.out.println("Successfully logged out\n");
                            break;
                        case 7: // Quit
                            scanner.close();
                            System.exit(0);
                    }
                }
            }
        }
    }

    public static User login(Scanner scanner, ArrayList<User> users){
        System.out.println("The GHL Shooopppping Gliccchedd Carrrt");
        System.out.println("<<<<< Login >>>>>");
        System.out.print("UserID: ");
        String userId = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        for(User user : users){
            if(userId.equals(user.getUserId())){
                if(password.equals(user.getPassword())){
                    System.out.println("Sucessfully logged in.\n");
                    return user;
                }
                else{
                    System.out.println("Incorrect Password.\n");
                }
            }
        }
        System.out.println("UserID not found.\n");
        return null;
    }

    public static int customerMenu(Scanner scanner){
        int choice = -1;

        while (true) {
            try {
                System.out.println("<<<<< Menu >>>>>");
                System.out.println("1. Add Cart");
                System.out.println("2. Logout");

                System.out.print("Enter choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice >= 1 && choice <= 2) {
                    System.out.println();
                    return choice;
                } else {
                    System.out.println("Invalid Choice\n");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.\n");
                scanner.nextLine();
            }
        }
    }

    public static Cart createCart(Scanner scanner, User user, ArrayList<Item> items){
        System.out.println("<<<<< New Cart >>>>>");
        ArrayList<Item> cartItems = new ArrayList<>();
        ArrayList<Integer> variantIndexes = new ArrayList<>();

        boolean addItem = true;
        int addItemInput = 1;
        boolean itemFound = false;
        while(addItem){
            System.out.print("ItemId: ");
            String itemId = scanner.nextLine();

            for(Item item : items){
                if(item.getItemId().equals(itemId)){
                    itemFound = true;
                    System.out.println(item);
                    int variantIndex = 0;

                    if(item.getVariantCount() == 1){
                        variantIndex = 1;
                    }
                    else{
                        System.out.print("Variant Number: ");
                        variantIndex = scanner.nextInt();
                        scanner.nextLine();

                        while(variantIndex <= 0 || variantIndex > item.getVariantCount()){
                            System.out.println("Invalid Variant");
                            System.out.print("Variant Number: ");
                            variantIndex = scanner.nextInt();
                            scanner.nextLine();
                        }
                    }
                    if(item.getVariantQuantity(variantIndex - 1) == 0){
                        System.out.println("Item out of stock\n");
                    }
                    else{
                        item.decrementVariantQuantity(variantIndex - 1);
                        cartItems.add(item);
                        variantIndexes.add(variantIndex - 1);
                    }
                    break;
                }
            }
            if(!itemFound){
                System.out.println("Invalid Item ID.\n");
            }

            System.out.print("Do you want to add another item? (0-No | 1-Yes): ");
            addItemInput = scanner.nextInt();
            scanner.nextLine();
            System.out.println("");

            if(addItemInput == 0){
                if(cartItems.size() == 0){
                    System.out.println("Add at least one item to the cart.");
                }
                else{
                    addItem = false;
                }
            }
        }
        
        return new Cart((Customer)user, cartItems, variantIndexes, "Pending");
    }

    public static int adminMenu(Scanner scanner){
        int choice = -1;

        while (true) {
            try {
                System.out.println("<<<<< Menu >>>>>");
                System.out.println("1. View Carts");
                System.out.println("2. Update Cart Status");
                System.out.println("3. View Items");
                System.out.println("4. Update Item");
                System.out.println("5. Add Item");
                System.out.println("6. Logout");
                System.out.println("7. Quit");

                System.out.print("Enter choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice >= 1 && choice <= 7) {
                    System.out.println();
                    return choice;
                } else {
                    System.out.println("Invalid Choice.\n");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number. \n");
                scanner.nextLine();
            }
        }
    }

    public static void printItemTable(ArrayList<Item> items){
        System.out.printf("%-10s | %-20s | %-20s | %-20s | %10s | %-10s\n", 
            "ItemId", "Items", "Description", "Variants", "Price", "Quantity");
        for(Item item : items){
            System.out.printf("%-10s | %-20s | %-20s | %-20s | %10s | %-10s\n", 
                item.getItemId(),
                truncate(item.getItemName(), 20),
                truncate(item.getItemDescription(), 20),
                truncate(item.getVariant(0), 20),
                item.getVariantPrice(0),
                item.getVariantQuantity(0)
            );

            if(item.getVariantCount() > 1){
                for(int i = 1; i < item.getVariantCount(); i++){
                    System.out.printf("%-10s | %-20s | %-20s | %-20s | %10s | %-10s\n", 
                        " ",
                        " ",
                        " ",
                        truncate(item.getVariant(i), 20),
                        item.getVariantPrice(i),
                        item.getVariantQuantity(i)
                    );
                }
            }
        }
        System.out.println();
    }

    public static void updateItem(Scanner scanner, ArrayList<Item> items){
        System.out.println("<<<<< Update Item >>>>>");
        System.out.print("ItemID: ");
        String itemId = scanner.nextLine();

        int choice = -1;

        for(Item item : items){
            if(item.getItemId().equals(itemId)){
                System.out.println(item);
                while(choice != 4){
                    try {
                        System.out.println("Update " + itemId);
                        System.out.println("1. Update Name");
                        System.out.println("2. Update Description");
                        System.out.println("3. Update Variant");
                        System.out.println("4. Add Variant");
                        System.out.println("5. Quit Updating");
                        System.out.print("Choice: ");
                        choice = scanner.nextInt();
                        scanner.nextLine();

                        if (choice >= 1 && choice <= 5) {
                            switch(choice){
                                case 1: // Update Name
                                    System.out.print("New Name: ");
                                    item.setName(scanner.nextLine());
                                    break;
                                case 2: // Update Description 
                                    System.out.print("New Description: ");
                                    item.setDescription(scanner.nextLine());
                                    break;
                                case 3: 
                                    System.out.print("Variant Number to Update: ");
                                    int variantNumber = scanner.nextInt();
                                    scanner.nextLine();
                                    if(variantNumber > item.getVariantCount()){
                                        System.out.println("Invalid Variant Number\n");
                                    }
                                    else{
                                        System.out.print("New Variant Name: ");
                                        String variantName = scanner.nextLine();
                                        System.out.print("New Variant Price: RM ");
                                        Double variantPrice = scanner.nextDouble();
                                        scanner.nextLine();
                                        System.out.print("New Variant Quantity: ");
                                        int variantQuantity = scanner.nextInt();
                                        scanner.nextLine();

                                        item.updateVariant(variantNumber - 1, variantName, variantPrice, variantQuantity);
                                        System.out.println("Variant Updated\n");
                                    }
                                    break;
                                case 4: // Add Variant
                                    System.out.print("New Variant Name: ");
                                    String variantName = scanner.nextLine();
                                    System.out.print("New Variant Price: RM ");
                                    Double variantPrice = scanner.nextDouble();
                                    scanner.nextLine();
                                    System.out.print("New Variant Quantity: ");
                                    int variantQuantity = scanner.nextInt();
                                    scanner.nextLine();

                                    item.addVariant(variantName, variantPrice, variantQuantity);
                                    System.out.println("Variant Added\n");
                                    break;
                                case 5:
                                    return;
                            }
                        } else {
                            System.out.println("Invalid Choice\n");
                        }
                        
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.\n");
                        scanner.nextLine();
                    }
                }
                return;
            };
        }

        System.out.println("ItemID not found.\n");
    }

    public static Item createItem(Scanner scanner){
        System.out.println("<<<<< New Item >>>>>");
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        ArrayList<String> variants = new ArrayList<>();
        ArrayList<Double> price = new ArrayList<>();
        ArrayList<Integer> quantity = new ArrayList<>();

        System.out.print("Number of variants: ");
        int variantNo = scanner.nextInt();
        scanner.nextLine();

        for(int i = 1; i <= variantNo; i++){
            System.out.println("Variant " + i);
            System.out.print("Variant Name: ");
            variants.add(scanner.nextLine());
            System.out.print("Variant Price: RM ");
            price.add(scanner.nextDouble());
            scanner.nextLine();
            System.out.print("Variant Quantity: ");
            quantity.add(scanner.nextInt());
            scanner.nextLine();
        }
        return new Item(name, description, variants, price, quantity);
    }

    public static String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        } else {
            return text.substring(0, maxLength - 3) + "...";
        }
    }
}