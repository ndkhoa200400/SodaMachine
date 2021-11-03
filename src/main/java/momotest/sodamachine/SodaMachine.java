/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package momotest.sodamachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author User
 */
public class SodaMachine {

    private final Inventory<Cash> cashInventory;
    private final Inventory<Product> productInventory;
    private final List<Product> historyProducts; //history of products sold within a day.

    private long currentBalance;
    private Product currentProduct;

    private Promotion promotion;

    SodaMachine() {
        cashInventory = new Inventory<Cash>();
        productInventory = new Inventory<Product>();
        historyProducts = new ArrayList();
        currentBalance = 0;
        init();
    }

    // initial states for soda machine
    // such as money for change, selling products
    // 5 items for each inventory
    private void init() {

        for (Cash c : Cash.values()) {
            cashInventory.add(c, 2);

        }
        for (Product p : Product.values()) {
            productInventory.add(p, 5);
        }

    }

    /**
     *
     * @param p: Product that user wants to select
     */
    public long selectProduct(Product p) {
        if (productInventory.hasItem(p)) {
            if (productInventory.isInStock(p)) {
                currentProduct = p;
                System.out.println("Selecting " + currentProduct.getName() + " (" + p.getPrice() + ")");
                return currentProduct.getPrice();
            } else {
                throw new SoldOutException("Sold out! Please select another product.");
            }
        } else {
            throw new NotFoundProductExpcetion("No products found!");
        }

    }

    /**
     * Purchase selected product and get changes if any
     *
     * @return List of Cash that contains changes
     */
    public Pair<Product, List<Cash>> purchaseAndGetChanges() {
        System.out.println("Making purchasing...");
        if (currentProduct != null) {
            // check balance
            if (isFullPaid()) {
                // check sufficient changes 
                long remainingChange = currentBalance - currentProduct.getPrice();
                List<Cash> changes = this.getChange(remainingChange);

                productInventory.deduct(currentProduct, 1);
                if (this.promotion != null) {

                    Product promoteProduct = promotion.runPromote(currentProduct);
                    if (promoteProduct != null) {
                        promotion.winPromote(productInventory, currentProduct);
                    }
                }
                Pair<Product, List<Cash>> result = new Pair<>(currentProduct, changes);
                collectChange(changes);
                return result;

            } else {
                long remainingBalance = currentProduct.getPrice() - currentBalance;
                throw new NotFullPaidException("Price not full paid, remaining : ", remainingBalance);

            }
        } else {
            throw new NotSelectedProductExpcetion("Please select a product before making purchase");
        }

    }

    /**
     * Cancel request and reset state
     *
     * @return List of cash contains refund money
     */
    public List<Cash> refund() {
        System.out.println("Making refund...");
        List<Cash> refund = getChange(currentBalance);
        collectChange(refund);

        return refund;
    }

    public void applyPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    /**
     * insert cash to the machine to purchase product
     * @param cash Cash that user wants to insert into machine
     */
    public void insertCash(Cash cash) {
        System.out.println("Receiving " + cash.getValue());
        currentBalance += cash.getValue();
        cashInventory.add(cash, 1);
    }

    public Product printSelectingProduct(){
        return this.currentProduct;
    }
    
    /**
     * check if user inserted enough cash into machine
     */
    private boolean isFullPaid() {
        return currentBalance >= currentProduct.getPrice();
    }

    /**
     * Calculate the change based on the amount, the machine may not have enough
     * cashes to give change to the user.
     *
     * @param amount: amount that want to get change
     * @return List of Cash contains changes
     */
    public List<Cash> getChange(long amount) {
        List<Cash> change = Collections.EMPTY_LIST;

        if (amount > 0) {

            List<Cash> sortedCash = new ArrayList();
            change = new ArrayList();
            long balance = amount;

            // appy Gready Algorithm to find changes
            for (Cash c : cashInventory.getInventory().keySet()) {
                sortedCash.add(c);
            }
            Collections.sort(sortedCash, new Comparator<Cash>() {
                @Override
                public int compare(Cash o1, Cash o2) {
                    return o1.getValue() < o2.getValue() ? 1 : -1;
                }

            });

            for (int i = 0; i < sortedCash.size(); i++) {
                Cash cash = sortedCash.get(i);
                int nb = (int) (balance / cash.getValue());
                if (nb > 0) {
                    int quantity = Math.min(cashInventory.getQuantity(cash), nb);
                    balance -= quantity * cash.getValue();
                    for (int q = 0; q < quantity; q++) {
                        change.add(cash);
                    }
                }
            }
            // Not enough changes to give the user.
            if (balance > 0) {
                throw new NotSufficientChangeException("Not sufficient change! Please try another request.");
            }

        }
        return change;
    }

    /**
     * Print statistic of soda machine
     */
    public void printStats() {
        long totalSales = 0;
        for (Product p : historyProducts) {
            totalSales += p.getPrice();
        }
        System.out.println("=== Statistic of Soda Machine===");
        System.out.println("Total Sales : " + totalSales);
        System.out.println("Current Item Inventory : " + productInventory.getInventory());
        System.out.println("Current Cash Inventory : " + cashInventory.getInventory());
        if (promotion != null) {
            System.out.println("Running promotion " + promotion.getClass().getTypeName());
        }

    }

    /**
     * Collect change and decrease the cash inventory, reset all states.
     *
     * @param change
     */
    private void collectChange(List<Cash> change) {
        for (Cash c : change) {
            this.cashInventory.deduct(c, 1);
        }
        currentBalance = 0;
        historyProducts.add(currentProduct);
        currentProduct = null;
    }

}
