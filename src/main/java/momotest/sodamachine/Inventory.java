
package momotest.sodamachine;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to hold items inside such as products or cashes
 * @author User
 */
public class Inventory<T> {

    private Map<T, Integer> inventory = new HashMap<T, Integer>();

    public Map<T, Integer> getInventory() {
        return this.inventory;
    }

    public int getQuantity(T item) {
        Integer value = inventory.get(item);
        return value == null ? 0 : value;
    }

    public void add(T item, int quantity) {
        if (hasItem(item)) {
            int count = inventory.get(item);
            inventory.put(item, count + quantity);
        } else {
            inventory.put(item, quantity);
        }

    }

    public void deduct(T item, int quantity) {
        if (isInStock(item)) {
            int count = inventory.get(item);
            inventory.put(item, count - quantity);
        }
    }

    public boolean isInStock(T item) {
        return getQuantity(item) > 0;
    }

    public boolean hasItem(T item) {
        Integer value = inventory.get(item);
        return value != null;
    }

    public void clear() {
        inventory.clear();
    }

}
