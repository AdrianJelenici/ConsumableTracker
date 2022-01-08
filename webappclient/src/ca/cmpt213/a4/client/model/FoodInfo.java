package ca.cmpt213.a4.client.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Class for storing food information
 */
public class FoodInfo implements Consumable {

    private final int type = 1; // for loadjson
    private String name;
    private String notes;
    private double price;
    private double size; // printed as weight
    private LocalDateTime expiryDate;

    /**
     * Main constructor to create a food item
     * @param name - the name of the food item
     * @param notes - anything of note
     * @param price - the price of the food item
     * @param expiryDate - the expiry date of the food item
     */
    public FoodInfo (String name, String notes, double price,
                     double size, LocalDateTime expiryDate) {
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.size = size;
        this.expiryDate = expiryDate;
    }

    /**
     * Method to retrieve the type of the consumable
     * @return - the consumable's type
     */
    @Override
    public int getType() {
        return type;
    }

    /**
     * Method to retrieve the name of the food item
     * @return - the food item's name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Method to retrieve the notes of the food item
     * @return - the food item's notes
     */
    @Override
    public String getNotes() {
        return notes;
    }

    /**
     * Method to retrieve the price of the food item
     * @return - the food item's price
     */
    @Override
    public double getPrice() {
        return price;
    }

    /**
     * Method to retrieve the size of the food item
     * @return - the food item's size
     */
    @Override
    public double getSize() {
        return size;
    }


    /**
     * Method to retrieve the expiry date of the food item
     * @return - the food item's expiry date
     */
    @Override
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    /**
     * Method to tell whether the food item is expired
     * @return - true if expired, false otherwise
     */
    @Override
    public boolean isExpired() {
        LocalDateTime today = LocalDateTime.now();
        long days = ChronoUnit.DAYS.between(expiryDate, today);
        if (days <= 0) {
            return false;
        }
        return true;
    }

    /**
     * Method to tell whether the food item will expire in seven days
     * @return - true if it will, false otherwise
     */
    @Override
    public boolean inSevenDays() {
        LocalDateTime today = LocalDateTime.now();
        long days = ChronoUnit.DAYS.between(expiryDate, today);
        if (-7 <= days && days <= 0) {
            return true;
        }
        return false;
    }

    /**
     * ToString method for printing out class info
     * @return - a string containing all the class info
     */
    @Override
    public String toString() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateTimeString = expiryDate.format(formatter);

        String stringPrice = String.format("%.2f", price);
        String stringWeight = String.format("%.2f", size);

        LocalDateTime today = LocalDateTime.now();
        long days = ChronoUnit.DAYS.between(expiryDate, today);

        if (days == 0) {
            return "<html> Name: " + name + "<br>" +
                    "Notes: " + notes + "<br>" +
                    "Price: " + stringPrice + "<br>" +
                    "Weight: " + stringWeight + "<br>" +
                    "Expiry Date: " + dateTimeString + "<br>" +
                    "This food item will expire today <br> <html>";

        } else if (days < 0) {
            return "<html> Name: " + name + "<br>" +
                    "Notes: " + notes + "<br>" +
                    "Price: " + stringPrice + "<br>" +
                    "Weight: " + stringWeight + "<br>" +
                    "Expiry Date: " + dateTimeString + "<br>" +
                    "This food item will expire in " + Math.abs(days) + " day(s) <br> <html>";

        }
        return "<html> Name: " + name + "<br>" +
                "Notes: " + notes + "<br>" +
                "Price: " + stringPrice + "<br>" +
                "Weight: " + stringWeight + "<br>" +
                "Expiry Date: " + dateTimeString + "<br>" +
                "This food item is expired for " + days + " day(s) <br> <html>";
    }

    /**
     * Method to compare two consumables by their expiry dates
     * @param o - the consumable item we want to compare to
     * @return - a different integer depending on whether the param is bigger, smaller, or equal
     */
    @Override
    public int compareTo(Consumable o) {
        return expiryDate.compareTo(o.getExpiryDate());
    }

}
