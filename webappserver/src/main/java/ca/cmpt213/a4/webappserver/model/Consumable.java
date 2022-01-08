package ca.cmpt213.a4.webappserver.model;

import java.time.LocalDateTime;

/**
 * Consumable interface
 */
public interface Consumable extends Comparable<Consumable>  {

    // abstract methods:
    int getType();
    String getName();
    String getNotes();
    double getPrice();
    double getSize();
    LocalDateTime getExpiryDate();
    boolean isExpired();
    boolean inSevenDays();

}