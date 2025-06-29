# Online Shopping Cart System  
**Goe Jie Ying (A23CS0224)**  
**Lam Yoke Yu (A23CS0233)**
[Video Presentation Link](https://drive.google.com/file/d/17-Dbb2TPiXqPb0uQNWTDTSZNkf_Hsoqc/view?usp=sharing)
---

## 1.0 Project Description

This project is a console-based online shopping cart system built in Java. It supports:

- **Customers**: Browse and add items to the shopping cart  
- **Administrators**: Manage items and view/update customer orders (shopping carts)

The system uses object-oriented design principles to simulate real e-commerce scenarios and applies Java's collection framework and exception handling to enhance functionality and stability.

---

## 2.0 Functional Requirements

The Online Shopping Cart System provides role-based functionality for both administrators and customers.

### 2.1 Admin Functionalities

- **View All Carts**  
  - Admins can view all shopping carts created by customers.  
  - Each cart displays customer ID, item IDs, names, variant details, prices, and status.

- **Update Cart Status**  
  - Admins can select a cart by its ID and update its status.  
  - Each cart’s status can be changed to either **Processing**, **Shipped**, or **Completed**.

- **View Items**  
  - Admins can view the item catalog in a table format.  
  - Each item shows its ID, name, description, variants, price, and quantity.

- **Update Items**  
  - Admins can select an item by ID and:  
  - Change the item’s name, OR  
  - Change the item’s description, OR  
  - Update a specific variant (name, price, quantity), OR  
  - Add new variants to the item

- **Add New Item**  
  Admins can add new items to the catalog by entering:  
  - Item name and description  
  - One or more variants (name, price, quantity)

### 2.2 Customer Functionalities

- **Add Cart (Place Order)**  
  - Customers can view available items and their variants.  
  - Customers select items by their item ID and choose a variant.  
  - The system checks if the selected variant is in stock.  
  - Once confirmed, the item is added to the customer’s cart.  
  - Multiple items can be added before confirming the cart.

---

## 3.0 Topics Implemented

### 3.1 Chapter 5: Vectors & Collections

The `ArrayList` class is used to manage dynamic collections of objects. It stores different objects such as users, items, and cart records without predefining the number of entries. The use of `ArrayList` allows the system to easily add, remove, and loop through elements.

**Examples:**
- `ArrayList<User> users`: stores both `Customer` and `Admin` accounts  
- `ArrayList<Item> items`: stores the list of items  
- `ArrayList<Integer> variantIndex`: stores selected variants of a product

---

### 3.2 Chapter 6: Class Relationships

#### 3.2.1 Association

The `Cart` class has an association with the `Customer` class. Each cart belongs to a specific customer and the customer is stored as a reference.

**Example:**
```java
private Customer customer;
```

#### 3.2.2 Aggregation

The `CartHistory` class stores all `Cart` objects in an `ArrayList`. These carts can exist independently from the history, which shows a "has-a" but not "owns" relationship.

**Example:**
```java
private ArrayList<Cart> carts;
```

#### 3.2.3 Composition

The `Cart` class holds selected items and variant indices. When a `Cart` is destroyed, its combination of items and selections no longer holds meaning.

**Example:**
```java
private ArrayList<Item> items;
private ArrayList<Integer> variantIndex;
```

---

### 3.3 Chapter 7: Inheritance

Inheritance is used in this system to promote code reuse and reduce redundancy. A base class `User` is defined with shared attributes and methods such as `userId`, `password`, and `role`. The `Customer` and `Admin` classes extend the `User` class to inherit these common features.

**Example:**
```java
class User {
    protected String userId;
    protected String password;
    protected int role;
    ...
}

class Customer extends User { ... }
class Admin extends User { ... }
```

---

### 3.4 Chapter 8: Polymorphism

Polymorphism allows a single variable of a superclass type to refer to objects of different subclasses. In this system, both `Customer` and `Admin` objects are stored in an `ArrayList<User>`.

**Example:**
```java
ArrayList<User> users = new ArrayList<>();
users.add(new Customer("C1", "123"));
users.add(new Admin("A1", "123"));
```

The system uses `user.getRole()` to determine whether the logged-in user is a customer or an admin and responds accordingly. This is an example of runtime polymorphism.

---

### 3.5 Chapter 9: Exception Handling

Exception handling is used in the program to manage errors that occur during user input. This prevents the program from crashing and improves user experience. `try-catch` blocks are used to catch exceptions such as `InputMismatchException` and `NumberFormatException`.

**Example:**
```java
try {
    choice = scanner.nextInt();
    scanner.nextLine();
} catch (InputMismatchException e) {
    System.out.println("Invalid input. Please enter a number.");
    scanner.nextLine();
}
```

This ensures that if a user enters invalid input (such as a letter when a number is expected), the system will display an error message and continue running.

## 4.0 UML Class Diagram
<figure>
<figcaption>Class Diagram for Online Shopping Cart System<figcaption>
    <img src="/OOP Project Class Diagram.png">
</figure>
