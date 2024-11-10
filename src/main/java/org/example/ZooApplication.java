package org.example;

// the attraction should maintain the count of ticketed visitors.
// attraction and zooattraction confusion

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

interface Attraction {
    int getID();
    String getName();
    String getDescription();
    void setDescription(String description);
    double getTicketPrice();
}


class Zoo {
    private String name;
    private List<Attraction> attractions;
    private List<Animal> animals;
    private List<Visitor> visitors;

    public Zoo(String name) {
        this.name = name;
        this.attractions = new ArrayList<>();
        this.animals = new ArrayList<>();
        this.visitors=new ArrayList<>();
    }

    @Override
    public String toString() {
        return name;
    }

    public Visitor registerVisitor(Scanner scanner) {
        System.out.print("Enter Visitor Name: ");
        String name = scanner.next();
        System.out.print("Enter Visitor Age: ");
        int age = scanner.nextInt();
        System.out.print("Enter Visitor Phone Number: ");
        String phone = scanner.next();
        System.out.print("Enter Initial Balance: ");
        double balance = scanner.nextDouble();
        System.out.print("Enter Visitor Email: ");
        String email = scanner.next();
        System.out.print("Enter Visitor Password: ");
        String password = scanner.next();

        visitors.add(new Visitor(name, age, phone, balance, email, password));
        return new Visitor(name, age, phone, balance, email, password);
    }

    public Visitor loginVisitor( Scanner scanner) {
        System.out.print("Enter Visitor Email: ");
        String email = scanner.next();
        System.out.print("Enter Visitor Password: ");
        String password = scanner.next();

        for (Visitor visitor : visitors) {
            if (visitor.getEmail().equals(email) && visitor.getPassword().equals(password)) {
                return visitor;
            }
        }

        System.out.println("Login failed. Invalid email or password.");
        return null;
    }

    public void addAttraction(Attraction attraction) {
        attractions.add(attraction);
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public List<Animal> getAnimals() {
        return animals;
    }
}

// Class to represent a visitor
class Visitor {
    private String name;
    private int age;
    private String phoneNumber;
    private double balance;
    private String email;
    private String password;
    private boolean isPremiumMember;

    public Visitor(String name, int age, String phoneNumber, double balance, String email, String password) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.email = email;
        this.password = password;
        this.isPremiumMember = false;
    }

    @Override
    public String toString() {
        String a;
        if (this.isPremiumMember==false)
        {
            a="Basic";
        }
        else
        {
            a="Premium";
        }
        return "Name: "+name+", Age: "+age+", Phone no.: "+phoneNumber+", Balance: "+balance+", Email: "+email+", Password: "+password+", "+a+" Member";
    }

    public String getEmail()
    {
        return this.email;
    }
    public String getPassword()
    {
        return this.password;
    }
    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double bal)
    {
        this.balance=bal;
    }

    public boolean isPremiumMember() {
        return isPremiumMember;
    }

    public void setPremiumMember(boolean premiumMember) {
        isPremiumMember = premiumMember;
    }

}


class AttractionsManager {
    private List<ZooAttraction> attractions;

    public AttractionsManager() {
        this.attractions = new ArrayList<>();
    }

    public List<ZooAttraction> getAttractions()
    {
        return attractions;
    }
    // Method to add a new attraction
    public void addAttraction(String name, String description, double ticketPrice) {
        ZooAttraction attraction = new ZooAttraction(name, description, ticketPrice);
        attractions.add(attraction);
    }

    // Method to view all attractions
    public void viewAttractions() {
        Scanner s=new Scanner(System.in);
        System.out.println("Attractions in the Zoo:");
        for (Attraction attraction : attractions) {
            System.out.println(attraction.getID() + ". " + attraction.getName() + " (Ticket Price: $" + attraction.getTicketPrice() + ")");
        }
        System.out.println("");
        System.out.print("Enter your choice: ");
        int c=s.nextInt();
        ZooAttraction z=attractions.get(c-1);
        System.out.println(z.getDescription());
    }

    // Method to modify an attraction's description
    public void modifyAttractionDescription(int attractionID, String newDescription) {
        for (Attraction attraction : attractions) {
            if (attraction.getID() == attractionID) {
                attraction.setDescription(newDescription);
                System.out.println("Attraction description modified successfully.");
                return;
            }
        }
        System.out.println("Attraction not found.");
    }

    // Method to remove an attraction
    public void removeAttraction(int attractionID) {
        Attraction attractionToRemove = null;
        for (Attraction attraction : attractions) {
            if (attraction.getID() == attractionID) {
                attractionToRemove = attraction;
                break;
            }
        }
        if (attractionToRemove != null) {
            attractions.remove(attractionToRemove);
            System.out.println("Attraction removed successfully.");
        } else {
            System.out.println("Attraction not found.");
        }
    }

    public void scheduleEvent(int id, boolean status, double price)
    {
        for (ZooAttraction attraction : attractions)
        {
            if (attraction.getID()==id)
            {
                // set status and price
                attraction.setStatus(status);
                attraction.setTicketPrice(price);
            }
        }
    }
}

class SpecialDeal {
    private int minAttractions;
    private double discountPercentage;

    public SpecialDeal(int minAttractions, double discountPercentage) {
        this.minAttractions = minAttractions;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public String toString() {
        return "Minimum Attractions Required: "+minAttractions+", Discount: "+discountPercentage;
    }

    public int getMinAttractions() {
        return minAttractions;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public String getDescription()
    {
        return "If a person buys more than " + this.minAttractions + " attractions, they get a special discount of " + this.discountPercentage + " on the total amount";
    }

    public void setDiscountPercentage(double perc)
    {
        this.discountPercentage=perc;
    }
}



// Concrete implementation of Attraction
class ZooAttraction implements Attraction {
    private static AtomicInteger attractionIDCounter = new AtomicInteger(1);

    private boolean status;
    private final int ID;
    private String name;
    private String description;
    private double ticketPrice;

    public ZooAttraction(String name, String description, double ticketPrice) {
        this.ID = attractionIDCounter.getAndIncrement();
        this.name = name;
        this.description = description;
        this.ticketPrice = ticketPrice;
        this.status=true;
    }

    @Override
    public String toString() {
        String b;
        if (status==false)
        {
            b="closed";
        }
        else
        {
            b="open";
        }
        return "ID: "+ID+", Status: "+b+", Name: "+name+", Description: "+description+", Ticket Price: "+ticketPrice;
    }

    public void setTicketPrice(double tprice) {this.ticketPrice=tprice;}
    public void setStatus(boolean stat) {this.status=stat;}
    @Override
    public int getID() {
        return ID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public double getTicketPrice() {
        return ticketPrice;
    }
}

class Discount {
    private String category;
    private double percentage;

    public Discount(String category, double percentage) {
        this.category = category;
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "Category: "+category+", Percentage: "+percentage;
    }

    public String getCategory() {
        return category;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percent)
    {
        this.percentage=percent;
    }
}

class Admin {
    private static final String UserName = "admin";
    private static final String Pass= "admin123";

    public boolean Verify(String user, String pass)
    {
        if (UserName.equals(user) && Pass.equals(pass))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

//admin class
//attraction class
// attraction should maintain the count of ticketed visitors.
//animal class
// 3 types of animals, 2 types at least for each type,
//set discounts
//set special deals.
//visitor class
// visitor statistic is a random value add that.

abstract class Animal {
    private String name;
    private String type;

    public Animal(String name, String type) {
        this.name = name;
        this.type = type;
    }
    // Provide a general description method (can be overridden by specific animal types)
    public String getAnimalDescription() {
        return "This is a " + type + " named " + name;
    }
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String typ)
    {
        this.type=typ;
    }
    public abstract String makeSound();
}

class Mammal extends Animal {
    public Mammal(String name) {
        super(name, "Mammal");
    }

    @Override
    public String makeSound() {
        return "Roar";
    }

    @Override
    public String getAnimalDescription() {return "Mammal Description";}
}

class Amphibian extends Animal {
    public Amphibian(String name) {
        super(name, "Amphibian");
    }

    @Override
    public String makeSound() {
        return "Croak";
    }

    @Override
    public String getAnimalDescription() {return "Amphibian Description";}
}

class Reptile extends Animal {
    public Reptile(String name) {
        super(name, "Reptile");
    }

    @Override
    public String makeSound() {
        return "Hiss";
    }
    @Override
    public String getAnimalDescription() {return "Reptile Description";}
}

class AnimalsManager {
    private List<Animal> animals;

    public AnimalsManager() {
        this.animals = new ArrayList<>();
    }

    // Method to add a new animal
    public List<Animal> getAnimals()
    {
        return animals;
    }
    public void addAnimal(Animal animal) {
        animals.add(animal);
        System.out.println("Animal added to the Zoo.");
    }

    // Method to view all animals
    public void viewAnimals() {
        System.out.println("Animals in the Zoo:");
        for (Animal animal : animals) {
            System.out.println("Name: " + animal.getName() + " | Type: " + animal.getType());
        }
    }

    // Method to update animal details
    public void updateAnimalDetails(String animalName, String newType) {
        for (Animal animal : animals) {
            if (animal.getName().equalsIgnoreCase(animalName)) {
                animal.setType(newType);
                System.out.println("Animal details updated successfully.");
                return;
            }
        }
        System.out.println("Animal not found.");
    }

    // Method to remove an animal
    public void removeAnimal(String animalName) {
        Animal animalToRemove = null;
        for (Animal animal : animals) {
            if (animal.getName().equalsIgnoreCase(animalName)) {
                animalToRemove = animal;
                break;
            }
        }
        if (animalToRemove != null) {
            animals.remove(animalToRemove);
            System.out.println("Animal removed successfully.");
        } else {
            System.out.println("Animal not found.");
        }
    }
}

class DiscountsManager {
    private List<Discount> discounts;

    public DiscountsManager() {
        this.discounts = new ArrayList<>();
    }

    // Method to add a new discount
    public void addDiscount(String category, double percentage) {
        Discount discount = new Discount(category, percentage);
        discounts.add(discount);
        System.out.println("Discount added successfully.");
    }

    // Method to view all discounts
    public void viewDiscounts() {
        System.out.println("Discounts available:");
        for (Discount discount : discounts) {
            System.out.println("Category: " + discount.getCategory() + " | Percentage: " + discount.getPercentage() + "%");
        }
    }

    // Method to modify an existing discount
    public void modifyDiscount(String category, double newPercentage) {
        for (Discount discount : discounts) {
            if (discount.getCategory().equalsIgnoreCase(category)) {
                discount.setPercentage(newPercentage);
                System.out.println("Discount modified successfully.");
                return;
            }
        }
        System.out.println("Discount category not found.");
    }
    public List<Discount> getDiscounts()
    {
        return discounts;
    }

    // Method to remove a discount
    public void removeDiscount(String category) {
        Discount discountToRemove = null;
        for (Discount discount : discounts) {
            if (discount.getCategory().equalsIgnoreCase(category)) {
                discountToRemove = discount;
                break;
            }
        }
        if (discountToRemove != null) {
            discounts.remove(discountToRemove);
            System.out.println("Discount removed successfully.");
        } else {
            System.out.println("Discount category not found.");
        }
    }
}

class SpecialDealsManager {
    private List<SpecialDeal> specialDeals;

    public SpecialDealsManager() {
        this.specialDeals =new ArrayList<>();
    }

    // Method to add a new special deal
    public void addSpecialDeal(String description, int numberOfAttractions, double discountPercentage) {
        SpecialDeal specialDeal = new SpecialDeal( numberOfAttractions, discountPercentage);
        specialDeals.add(specialDeal);
        System.out.println("Special deal added successfully.");
    }

    // Method to view all special deals
    public void viewSpecialDeals() {
        System.out.println("Special Deals available:");
        for (SpecialDeal specialDeal : specialDeals) {
            System.out.println("Description: " + specialDeal.getDescription() + " | Number of Attractions Required: " + specialDeal.getMinAttractions() + " | Discount: " + specialDeal.getDiscountPercentage() + "%");
        }
    }

    // Method to modify an existing special deal
    public void modifySpecialDeal(int minatt, double percent, double newDiscountPercentage) {
        for (SpecialDeal specialDeal : specialDeals) {
            if (specialDeal.getMinAttractions()==minatt && specialDeal.getDiscountPercentage()==percent) {
                specialDeal.setDiscountPercentage(newDiscountPercentage);
                System.out.println("Special deal modified successfully.");
                return;
            }
        }
        System.out.println("Special deal not found.");
    }

    // Method to remove a special deal
    public void removeSpecialDeal(int minatt, double percent) {
        SpecialDeal specialDealToRemove = null;
        for (SpecialDeal specialDeal : specialDeals) {
            if (specialDeal.getMinAttractions()==minatt && specialDeal.getDiscountPercentage()==percent) {
                specialDealToRemove = specialDeal;
                break;
            }
        }
        if (specialDealToRemove != null) {
            specialDeals.remove(specialDealToRemove);
            System.out.println("Special deal removed successfully.");
        } else {
            System.out.println("Special deal not found.");
        }
    }
}

public class ZooApplication {

    // Add this method in the ZooBuddiesApp class

    // Add this method in the ZooBuddiesApp class
// Add this method in the ZooBuddiesApp class

    // Add this method in the ZooBuddiesApp class

    private static void visitAttractions(AttractionsManager attractionsManager, Visitor currentVisitor) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Visit Attractions:");

        if (currentVisitor.isPremiumMember() == false) {
            System.out.println("Sorry, Basic Members need to buy separate tickets for the attractions.");
        } else if (currentVisitor.isPremiumMember() == true) {
            List<ZooAttraction> attractions = attractionsManager.getAttractions();

            for (int i = 0; i < attractions.size(); i++) {
                System.out.println((i + 1) + ". " + attractions.get(i).getName());
            }

            System.out.print("Select an Attraction to Visit: ");
            int attractionChoice = scanner.nextInt();

            if (attractionChoice >= 1 && attractionChoice <= attractions.size()) {
                ZooAttraction selectedAttraction = attractions.get(attractionChoice - 1);
                System.out.println("Welcome to " + selectedAttraction.getName() + ". Enjoy your visit!");
            } else {
                System.out.println("Invalid attraction choice.");
            }
        }
    }

    private static void visitAnimals(AnimalsManager animalsManager) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Visit Animals:");
        List<Animal> animals = animalsManager.getAnimals();

        for (int i = 0; i < animals.size(); i++) {
            System.out.println((i + 1) + ". " + animals.get(i).getName() + " (" + animals.get(i).getType() + ")");
        }

        System.out.print("Select an Animal to Visit: ");
        int animalChoice = scanner.nextInt();

        if (animalChoice >= 1 && animalChoice <= animals.size()) {
            Animal selectedAnimal = animals.get(animalChoice - 1);
            System.out.println("You have chosen to visit " + selectedAnimal.getName() + " (" + selectedAnimal.getType() + ").");

            // Simulate visitor interaction with the animal
            System.out.println("Do you want to feed the animal or read about it?");
            System.out.println("1. Feed the animal");
            System.out.println("2. Read about the animal");
            System.out.print("Enter your choice: ");
            int interactionChoice = scanner.nextInt();

            if (interactionChoice == 1) {
                // Simulate feeding the animal
                System.out.println("You chose to feed the animal.");
                selectedAnimal.makeSound(); // Calls the makeNoise method for the selected animal
            } else if (interactionChoice == 2) {
                // Simulate reading about the animal
                System.out.println("You chose to read about the animal.");
                System.out.println(selectedAnimal.getAnimalDescription()); // Display the animal's description
            } else {
                System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("Invalid animal choice.");
        }
    }

    private static void buyTickets(AttractionsManager attractionsManager, Visitor currentVisitor, DiscountsManager discountsManager, SpecialDealsManager specialDealsManager) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Buy Tickets:");
        List<ZooAttraction> attractions = attractionsManager.getAttractions();

        for (int i = 0; i < attractions.size(); i++) {
            System.out.println((i + 1) + ". " + attractions.get(i).getName() + " (₹" + attractions.get(i).getTicketPrice() + ")");
        }

        System.out.print("Select an Attraction to Buy a Ticket: ");
        int attractionChoice = scanner.nextInt();

        if (attractionChoice >= 1 && attractionChoice <= attractions.size()) {
            Attraction selectedAttraction = attractions.get(attractionChoice - 1);

            if (currentVisitor.getBalance() >= selectedAttraction.getTicketPrice()) {
                currentVisitor.setBalance(currentVisitor.getBalance() - selectedAttraction.getTicketPrice());
                System.out.println("The ticket for " + selectedAttraction.getName() + " was purchased successfully. Your balance is now ₹" + currentVisitor.getBalance());
            } else {
                System.out.println("Insufficient balance to purchase the ticket for " + selectedAttraction.getName() + ".");
            }
        } else {
            System.out.println("Invalid attraction choice.");
        }
    }

    private static void buyMembership(Visitor currentVisitor, DiscountsManager d) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Buy Membership:");
        System.out.println("1. Basic Membership (₹20)");
        System.out.println("2. Premium Membership (₹50)");
        System.out.print("Enter your choice: ");
        int membershipChoice = scanner.nextInt();

        System.out.println("");
        System.out.print("Enter Discount Code: ");
        String code=scanner.next();

        int k=0;
        double j=0;
        for (Discount dis : d.getDiscounts())
        {
            if (dis.getCategory().equals(code))
            {
                k=1;
                j=dis.getPercentage();
                break;
            }
        }
        if (k==1)
        {
            if (membershipChoice == 1) {
                if (currentVisitor.getBalance() >= 20.0*(100-j)/100) {
                    currentVisitor.setBalance(currentVisitor.getBalance() - 20.0*(100-j)/100);
                    System.out.println("Basic Membership purchased successfully. Your balance is now ₹" + currentVisitor.getBalance());
                } else {
                    System.out.println("Insufficient balance to purchase Basic Membership.");
                }
            } else if (membershipChoice == 2) {
                if (currentVisitor.getBalance() >= 50.0*(100-j)/100) {
                    currentVisitor.setBalance(currentVisitor.getBalance() - 50.0*(100-j)/100);
                    System.out.println("Premium Membership purchased successfully. Your balance is now ₹" + currentVisitor.getBalance());
                } else {
                    System.out.println("Insufficient balance to purchase Premium Membership.");
                }
            } else {
                System.out.println("Invalid choice.");
            }
            return;
        }

        if (membershipChoice == 1) {
            if (currentVisitor.getBalance() >= 20.0) {
                currentVisitor.setBalance(currentVisitor.getBalance() - 20.0);
                System.out.println("Basic Membership purchased successfully. Your balance is now ₹" + currentVisitor.getBalance());
            } else {
                System.out.println("Insufficient balance to purchase Basic Membership.");
            }
        } else if (membershipChoice == 2) {
            if (currentVisitor.getBalance() >= 50.0) {
                currentVisitor.setBalance(currentVisitor.getBalance() - 50.0);
                System.out.println("Premium Membership purchased successfully. Your balance is now ₹" + currentVisitor.getBalance());
            } else {
                System.out.println("Insufficient balance to purchase Premium Membership.");
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin=new Admin();
        Zoo zoo = new Zoo("ZOOtopia");
        AttractionsManager attractionsManager = new AttractionsManager();
        AnimalsManager animalsManager = new AnimalsManager();
        DiscountsManager discountsManager = new DiscountsManager();
        SpecialDealsManager specialDealsManager = new SpecialDealsManager();
        List<String> feedbackLst=new ArrayList<>();

        attractionsManager.addAttraction("Botanical_Garden", "ZOOtopia offers an adventure ride that allows you to explore unexplored trails. Buy your ticket now!", 15);
        attractionsManager.addAttraction("Dinosaur Show", "ZOOtopia offers an adventure ride that allows you to explore unexplored trails. Buy your ticket now!", 12);

        specialDealsManager.addSpecialDeal("Buy 2 tickets and get 15% off", 2,15);
        specialDealsManager.addSpecialDeal("Buy 3 tickets and get 30% off",3,30);

        discountsManager.addDiscount("Minor",10);
        discountsManager.addDiscount("Senior Citizen",20);

        Animal animal1=new Mammal("Rat");
        Animal animal2=new Mammal("Monkey");
        Animal animal3=new Amphibian("Frog");
        Animal animal4=new Amphibian("Salamander");
        Animal animal5=new Reptile("Crocodile");
        Animal animal6=new Reptile("King Cobra");

        animalsManager.addAnimal(animal1);
        animalsManager.addAnimal(animal2);
        animalsManager.addAnimal(animal3);
        animalsManager.addAnimal(animal4);
        animalsManager.addAnimal(animal5);
        animalsManager.addAnimal(animal6);

        while (true) {
            System.out.println("");
            System.out.println("Welcome to ZOOtopia!");
            System.out.println();
            System.out.println("1. Enter as Admin");
            System.out.println("2. Enter as a Visitor");
            System.out.println("3. View Special Deals");
            System.out.println("");
            System.out.print("Enter your choice: ");

            int choice=0;
            choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println("");

            if (choice==1)
            {

                String name;
                String pass;
                System.out.print("Enter Admin Username: ");
                name=scanner.nextLine();
                System.out.print("Enter Admin Password: ");
                pass=scanner.nextLine();
                System.out.println();

                int m=0;

                // check if credentials are correct.
                if (admin.Verify(name,pass)==true)
                {
                    m=1;
                }

                if (m==0)
                {
                    System.out.println("");
                    System.out.println("Error: Given Username and Password not registered!");
                }
                else
                {
                    System.out.println("Logged in as Admin.");
                    System.out.println();

                    int choice1=0;

                    while (choice1!=8)
                    {
                        System.out.println("Admin Menu:");
                        System.out.println("1. Manage Attractions");
                        System.out.println("2. Manage Animals");
                        System.out.println("3. Schedule Events");
                        System.out.println("4. Set Discounts");
                        System.out.println("5. Set Special Deal");
                        System.out.println("6. View Visitor Stats");
                        System.out.println("7. View Feedback");
                        System.out.println("8. Exit");
                        System.out.println("");
                        System.out.print("Enter your choice: ");

                        choice1=scanner.nextInt();

                            if (choice1==1)
                            {
                                int choice2=0;

                                    System.out.println("");
                                    System.out.println("Manage Attractions:");
                                    System.out.println("1. Add Attraction");
                                    System.out.println("2. View Attractions");
                                    System.out.println("3. Modify Attraction");
                                    System.out.println("4. Remove Attraction");
                                    System.out.println("5. Exit");
                                    System.out.println("");
                                    System.out.print("Enter your choice: ");

                                    choice2=scanner.nextInt();

                                    System.out.println("");
                                    if (choice2==1)
                                    {
                                        // register attraction
                                        // Add Attraction
                                        System.out.print("Enter Attraction Name: ");
                                        String Uname = scanner.next();
                                        System.out.print("Enter Attraction Description: ");
                                        scanner.nextLine();  // Consume the newline
                                        String description = scanner.nextLine();
                                        System.out.print("Enter Ticket Price: ");
                                        double ticketPrice = scanner.nextDouble();

                                        attractionsManager.addAttraction(Uname, description, ticketPrice);
                                        System.out.println("Attraction added successfully.");
                                    }
                                    else if (choice2==2) {
                                        //viewAttractions;
                                        attractionsManager.viewAttractions();

                                    }
                                    else if (choice2==3)
                                    {
                                        // Modify Attraction
                                        System.out.print("Enter Attraction Id: ");
                                        int id= scanner.nextInt();
                                        scanner.nextLine();
                                        System.out.print("Write modified description: ");
                                        String desc=scanner.nextLine();
                                        attractionsManager.modifyAttractionDescription(id,desc);
                                    }
                                    else if (choice2==4)
                                    {
                                        // Remove Attraction
                                        System.out.print("Enter Attraction Id: ");
                                        int id= scanner.nextInt();
                                        attractionsManager.removeAttraction(id);
                                    }

                            }
                            else if (choice1==2)
                            {
                                int choice2=0;


                                    System.out.println("");
                                    System.out.println("Manage Animals:");
                                    System.out.println("1. Add Animal");
                                    System.out.println("2. Update Animal Details");
                                    System.out.println("3. Remove Animal");
                                    System.out.println("4. Exit");
                                    System.out.println("");
                                    System.out.print("Enter your choice: ");

                                    choice2=scanner.nextInt();

                                    System.out.println("");

                                    if (choice2==1)
                                    {
                                        // Add Animal
                                        scanner.nextLine();
                                        System.out.print("Enter Animal Name: ");
                                        String aname=scanner.next();
                                        System.out.print("Enter Animal type (Mammal/Amphibian/Reptile): ");
                                        String atype=scanner.next();
                                        if (atype.equals("Mammal"))
                                        {;
                                            Animal mammal=new Mammal(aname);
                                            animalsManager.addAnimal(mammal);
                                        }
                                        else if (atype.equals("Amphibian"))
                                        {
                                            Animal amphibian=new Amphibian(aname);
                                            animalsManager.addAnimal(amphibian);
                                        }
                                        else if (atype.equals("Reptile"))
                                        {
                                            Animal reptile=new Reptile(aname);
                                            animalsManager.addAnimal(reptile);
                                        }
                                        else
                                        {
                                            System.out.println("Invalid animal type.");
                                        }


                                    }
                                    else if (choice2==2)
                                    {
                                        // Update Animal Details
                                        System.out.print("Enter Animal Name: ");
                                        String aname= scanner.nextLine();
                                        scanner.nextLine();
                                        System.out.print("Enter new Animal type: ");
                                        String atype= scanner.nextLine();
                                        animalsManager.updateAnimalDetails(aname,atype);
                                    }
                                    else if (choice2==3)
                                    {
                                        // Remove Animal
                                        System.out.print("Enter Animal Name: ");
                                        scanner.nextLine();
                                        String aname= scanner.nextLine();
                                        animalsManager.removeAnimal(aname);
                                    }
                            }
                            else if (choice1==3)
                            {
                                // Schedule Events
                                System.out.print("Enter Attraction Id: ");
                                int aname= scanner.nextInt();
                                scanner.nextLine();
                                System.out.print("Enter Attraction Status: ");
                                boolean stat= scanner.nextBoolean();
                                System.out.print("Enter Attraction Ticket Price: ");
                                double tprice= scanner.nextDouble();
                                attractionsManager.scheduleEvent(aname,stat,tprice);
                            }
                            else if (choice1==4)
                            {
                                System.out.println("Set Discounts:");
                                System.out.println("1. Add Discount");
                                System.out.println("2. Modify Discount");
                                System.out.println("3. Remove Discount");
                                System.out.println("4. Exit");
                                System.out.println("");
                                System.out.print("Enter your choice:");

                                int choice2=0;

                                choice2= scanner.nextInt();

                                if (choice2==1)
                                {
                                    System.out.print("Enter Discount Category: ");
                                    String cat=scanner.nextLine();
                                    scanner.nextLine();
                                    System.out.print("Enter Discount Percentage (e.g., 20 for 20%): ");
                                    double dis= scanner.nextInt();
                                    discountsManager.addDiscount(cat,dis);
                                    System.out.println("");

                                }
                                else if (choice2==2)
                                {
                                    // Modify Discount
                                    System.out.print("Enter Discount Category: ");
                                    String cat=scanner.nextLine();
                                    scanner.nextLine();
                                    System.out.print("Enter New Discount Percentage: ");
                                    double dis= scanner.nextInt();
                                    discountsManager.modifyDiscount(cat,dis);
                                    System.out.println("");
                                }
                                else if (choice2==3)
                                {
                                    // Remove Discount
                                    System.out.print("Enter Discount Category: ");
                                    String cat=scanner.nextLine();
                                    discountsManager.removeDiscount(cat);
                                }
                            }
                            else if (choice1==5)
                            {
                                // Set Special Deal
                                System.out.println("Set Special Deal:");
                                System.out.println("1. Add Special Deal");
                                System.out.println("2. View Special deals");
                                System.out.println("3. Modify Special deals");
                                System.out.println("4. Remove Special deal");
                                System.out.println("5. Exit");
                                System.out.println("");
                                System.out.print("Enter your choice: ");
                                int choiced=scanner.nextInt();
                                System.out.println("");

                                if (choiced==1)
                                {
                                    System.out.print("Enter Minimum number of attraction: ");
                                    int noa= scanner.nextInt();
                                    System.out.print("Enter Discount Percentage: ");
                                    double disperc= scanner.nextDouble();
                                    specialDealsManager.addSpecialDeal("",noa,disperc);
                                }
                                else if (choiced==2)
                                {
                                    specialDealsManager.viewSpecialDeals();
                                }
                                else if (choiced==3)
                                {
                                    System.out.print("Enter min. no of attraction of special deal: ");
                                    int minattr= scanner.nextInt();
                                    System.out.print("Enter discount percentage of special deal");
                                    double percent= scanner.nextDouble();
                                    System.out.print("Enter new discount percentage of special deal");
                                    double newpercent= scanner.nextDouble();
                                    specialDealsManager.modifySpecialDeal(minattr,percent,newpercent);
                                }
                                else if (choiced==4)
                                {
                                    System.out.print("Enter min. no. of attraction of special deal: ");
                                    int minatt= scanner.nextInt();
                                    System.out.print("Enter discount percentage of special deal");
                                    double percent = scanner.nextDouble();
                                    specialDealsManager.removeSpecialDeal(minatt,percent);
                                }
                            }
                            else if (choice1==6)
                            {
                                // View Visitor Stats (random data for demonstration)
                                int totalVisitors = 1200;
                                double totalRevenue =15000;
                                String mostPopularAttraction = "Jungle Safari";

                                System.out.println("Visitor Statistics:");
                                System.out.println("- Total Visitors: " + totalVisitors);
                                System.out.println("- Total Revenue: $" + totalRevenue);
                                System.out.println("- Most Popular Attraction: " + mostPopularAttraction);
                            }
                            else if (choice1==7)
                            {
                                System.out.println("Feedback from Visitors:");
                                for (String feedbck : feedbackLst) {
                                    System.out.println(feedbck);
                                }
                            }
                            else if (choice1==8)
                            {
                                System.out.println("Logged out.");
                                System.out.println("");
                            }
                    }
                }
            }
            else if (choice==2)
            {
                int choice1=0;
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("");
                System.out.print("Enter your choice: ");

                choice1=scanner.nextInt();
                System.out.println("");


                if (choice1==1)
                {
                    zoo.registerVisitor(scanner);
                    System.out.println("Registration is successfull");
                    System.out.println("");
                    //Register Visitor;

                }
                else if (choice1==2)
                {
                    Visitor v=zoo.loginVisitor(scanner);

                    // Login check
                    if (v!=null)
                    {
                        System.out.println("Login Successful.");
                        System.out.println("");

                        int choice2=0;

                        while (choice2!=9)
                        {
                            System.out.println("Visitor Menu:");
                            System.out.println("1. Explore the Zoo");
                            System.out.println("2. Buy Membership");
                            System.out.println("3. Buy Tickets");
                            System.out.println("4. View Discounts");
                            System.out.println("5. View Special Deals");
                            System.out.println("6. Visit Animals");
                            System.out.println("7. Visit Attractions");
                            System.out.println("8. Leave Feedback");
                            System.out.println("9. Log Out");
                            System.out.println("");
                            System.out.print("Enter your choice: ");

                            choice2=scanner.nextInt();

                            System.out.println("");

                            if (choice2==1)
                            {
                                System.out.println("Explore the Zoo:");
                                System.out.println("1. View Attractions");
                                System.out.println("2. View Animals");
                                System.out.println("3. Exit");
                                System.out.println("");
                                System.out.print("Enter your choice: ");

                                int choice3=0;
                                choice3=scanner.nextInt();

                                System.out.println("");

                                if (choice3==1)
                                {
                                    // view attractions
                                    attractionsManager.viewAttractions();
                                }
                                else if (choice3==2)
                                {
                                    //viewAnimals;
                                    animalsManager.viewAnimals();
                                }
                            }
                            else if (choice2==2)
                            {
                                //buyMembership;
                                buyMembership(v, discountsManager);
                            }
                            else if (choice2==3)
                            {
                                //buyTickets;
                                buyTickets(attractionsManager,v,discountsManager,specialDealsManager);
                            }
                            else if (choice2==4)
                            {
                                //viewDiscounts;
                                discountsManager.viewDiscounts();
                            }
                            else if (choice2==5)
                            {
                                //viewSpecialDeals
                                specialDealsManager.viewSpecialDeals();
                            }
                            else if (choice2==6)
                            {
                                //visit animals
                                visitAnimals(animalsManager);
                            }
                            else if (choice2==7)
                            {
                                // visit attraction
                                visitAttractions(attractionsManager,v);
                            }
                            else if (choice2==8)
                            {
                                System.out.println("Leave Feedback: ");
                                System.out.print("Enter your feedback (max 200 characters): ");
                                scanner.nextLine();
                                String feedbck= scanner.nextLine();
                                feedbackLst.add(feedbck);
                            }
                            else if (choice2==9)
                            {
                                System.out.println("Logged out.");
                            }
                        }
                    }
                }
            }
            else if (choice==3)
            {
                specialDealsManager.viewSpecialDeals();
            }
        }
    }
}
