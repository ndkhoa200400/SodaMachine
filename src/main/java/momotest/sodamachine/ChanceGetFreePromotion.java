package momotest.sodamachine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Promotion to have a chance to get 1 free product when purchasing 3 consecutive products
 * @author User
 */
public class ChanceGetFreePromotion implements Promotion {

    double rate = 0.1;
    List<Product> historyAward = new ArrayList();     // history reward of a day
    long budget = Cash.TWENTYK.getValue();    // budget a day
    Random generator = new Random();
    Product currentProduct;
    int numConsecutiveProduct = 1;
    int maxConsecutiveNum = 3;
    LocalDate lastRun = LocalDate.now();
//    long checkTime = 2000;

    ChanceGetFreePromotion() {

    }

    ChanceGetFreePromotion(int maxConsecutiveNum) {
        this.maxConsecutiveNum = maxConsecutiveNum;
    }

    ChanceGetFreePromotion(double rate) {
        this.rate = rate;
    }

    ChanceGetFreePromotion(double rate, int maxConsecutiveNum) {
        this.rate = rate;
        this.maxConsecutiveNum = maxConsecutiveNum;
    }

    @Override
    public Product runPromote(Product product) {
        checkDate();
        if (getBudget() < budget) {
            return checkAndGetAward(product);
        } else {
            System.out.println("running out of budget" + getBudget());
        }
        return null;
    }

    @Override
    public void winPromote(Inventory productInvetory, Product product) {

        if (productInvetory.isInStock(product)) {
            System.out.println("Congrats! You got 1 free " + product.getName() + " from our promotion");
            productInvetory.deduct(product, 1);
        }

    }

    private long getBudget() {
        long total = 0;
        for (Product p : historyAward) {
            total += p.getPrice();
        }

        return total;
    }

    private Product checkAndGetAward(Product p) {
        if (currentProduct == null || !(p.getName().equals(currentProduct.getName()))) {
            numConsecutiveProduct = 1;
            currentProduct = p;

        } else {
            numConsecutiveProduct++;
            return collectPromote();
        }
        return null;
    }

    /**
     * Check number consecutive product and check the rate
     *
     * @return product
     */
    private Product collectPromote() {
        if (numConsecutiveProduct >= maxConsecutiveNum) {
            double random = generator.nextDouble();
            if (random < rate) {
                Product p = currentProduct;
                historyAward.add(currentProduct);
                reset();
                return p;
            } else {
                reset();
            }

        }
        return null;
    }

    private void reset() {
        numConsecutiveProduct = 1;
        currentProduct = null;
        rate = 0.2;

    }

    private void resetNewDay() {
        reset();
        // If no items were sold on the previous day 
        // increase rate 
        if (historyAward.isEmpty()) {
            rate = 0.5;
        } else {
            rate = 0.1;
        }
        historyAward.clear();
    }

    private void checkDate() {
        // get now
        LocalDate now = LocalDate.now();

        // check if now is after lastRun => new day has come
        if (now.isAfter(lastRun)) {
            resetNewDay();
            lastRun = now;
        }

    }
}
