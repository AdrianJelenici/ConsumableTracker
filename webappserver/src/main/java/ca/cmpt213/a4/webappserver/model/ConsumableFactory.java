package ca.cmpt213.a4.webappserver.model;

import java.time.LocalDateTime;

/**
 * Class that generates consumable objects
 */
public class ConsumableFactory {

    /**
     * Method to generate a consumable object
     * @param type - the type of consumable (food or drink)
     * @param name - the name of the consumable
     * @param notes - notes associated with the consumable
     * @param price - price of the consumable
     * @param size - weight or volume of the consumable
     * @param expiryDate - expiry date of the consumable
     * @return - a consumable object
     */
    public Consumable getInstance(int type, String name, String notes, double price, double size, LocalDateTime expiryDate) {

        if (type == 1) {
            return new FoodInfo(name, notes, price, size, expiryDate);
        } else if (type == 2) {
            return new DrinkInfo(name, notes, price, size, expiryDate);
        }
        return null;

    }

}