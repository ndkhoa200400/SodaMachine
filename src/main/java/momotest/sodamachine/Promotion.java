package momotest.sodamachine;

/**
 *
 * @author User
 */
public interface Promotion {

    public Product runPromote(Product product);
    
    public void winPromote(Inventory productInvetory, Product product);
}
