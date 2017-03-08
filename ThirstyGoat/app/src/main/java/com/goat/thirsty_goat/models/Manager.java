package com.goat.thirsty_goat.models;

/**
 * This class represents a manager, which can do everything a worker can do, plus view historical
 * reports and trends of water purity; they can also delete individual reports.
 */
public class Manager extends User {

    private String newAttribute;

    public Manager() {
        super();
    }
}
