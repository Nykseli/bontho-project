package com.nykseli.bontho.backend;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("serial")
public class Beer implements Serializable, Cloneable {
    private Long id;
    private String firstName = "";
    private String lastName = "";
    private String brand = "";
    private String name = "";
    private double amount = 0;
    private LocalDate created;
    private BeerStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the value of brand
     *
     * @return the value of brand
     */
    public String getBrand() {
        return this.brand;
    }

    /**
     * Set the value of brand
     *
     * @param brand new value of brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of amount
     *
     * @return the value of amount
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * Set the value of amount
     *
     * @param amount new value of amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public BeerStatus getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(BeerStatus status) {
        this.status = status;
    }

    /**
     * Get the value of birthDate
     *
     * @return the value of birthDate
     */
    public LocalDate getCreated() {
        return created;
    }

    /**
     * Set the value of created
     *
     * @param created new value of created
     */
    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public boolean isPersited() {
        return this.id != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (this.id == null) {
            return false;
        }
        if (obj instanceof Beer && obj.getClass().equals(this.getClass())) {
            return this.id.equals(((Beer) obj).id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (id == null ? 0 : id.hashCode());
        return hash;
    }

    @Override
    public Beer clone() throws CloneNotSupportedException {
        return (Beer) super.clone();
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }
}
