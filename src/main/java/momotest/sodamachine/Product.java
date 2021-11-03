package momotest.sodamachine;

/**
 *
 * @author User
 */
public enum Product {
    COKE("Coke", 10000), PEPSI("Pepsi", 10000), SODA("Soda", 20000);
    private long price;
    private String name;

    private Product(String name, long price) {
        this.price = price;
        this.name = name;
    }

    void print() {
        System.out.printf("Product: %s. Price: %d\n", this.name, this.price);
    }

    Product select() {
        return this;
    }

    public long getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
    
    
}
