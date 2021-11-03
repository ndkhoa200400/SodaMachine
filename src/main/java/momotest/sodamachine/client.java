package momotest.sodamachine;

import java.util.List;

/**
 *
 * @author User
 */
public class client {

    public static void printInvoice(Pair<Product, List<Cash>> invoice) {
        System.out.println("You got " + invoice.getFirst().getName());
        if (invoice.getSecond().size() > 0) {
            System.out.println("Change: " + invoice.getSecond());
        }
    }

    public static void main(String[] args) {

        SodaMachine sodaMachine = new SodaMachine();
        Promotion chanceGetFreePromotion = new ChanceGetFreePromotion();
        sodaMachine.applyPromotion(chanceGetFreePromotion);
        sodaMachine.printStats();
        try {
            sodaMachine.selectProduct(Product.SODA);
            sodaMachine.selectProduct(Product.PEPSI);
            System.out.println("You are selecting " + sodaMachine.printSelectingProduct());
            sodaMachine.insertCash(Cash.TWENTYK);
            printInvoice(sodaMachine.purchaseAndGetChanges());

            System.out.println("");

            sodaMachine.selectProduct(Product.PEPSI);
            sodaMachine.insertCash(Cash.TWO_THOUK);

            printInvoice(sodaMachine.purchaseAndGetChanges());

            System.out.println("");

            sodaMachine.selectProduct(Product.PEPSI);
            sodaMachine.insertCash(Cash.TENK);

            printInvoice(sodaMachine.purchaseAndGetChanges());

            System.out.println("");

            sodaMachine.selectProduct(Product.PEPSI);
            sodaMachine.insertCash(Cash.TWENTYK);

            List<Cash> change = sodaMachine.refund();
            System.out.println("Cash from refund " + change);

            System.out.println("");
            printInvoice(sodaMachine.purchaseAndGetChanges());

        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        sodaMachine.printStats();
    }
}
