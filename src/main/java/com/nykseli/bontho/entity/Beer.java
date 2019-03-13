package com.nykseli.bontho.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Beer {

    /**
     * Auto generated id for recognizing the entrys from apart
     *
     * SQL:
     * id INT NOT NULL AUTO_INCREMENT
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Integer that defines what user owns this entry.
     *
     * SQL:
     * user_id INT NOT NULL
     */
    @Column(nullable = false)
    private Integer userId;

    /**
     * Brand of the beer
     *
     * SQL:
     * beer_brand VARCHAR(30) NOT NULL
     */
    @Column(nullable = false)
    private String beerBrand = "";

    /**
     * Name of the beer
     *
     * SQL:
     * beer_name VARCHAR(30) NOT NULL
     */
    @Column(nullable = false)
    private String beerName = "";

    /**
     * How many beers we have
     *
     * SQL:
     * amount INT DEFAULT 0
     */
    @Column(columnDefinition = "default '0'")
    private double amount = 0;

    /**
     * Time stamp for when the beer entry was entered
     *
     * SQL:
     *  created TIMESTAMP NOT NULL DEFAULT NOW()
     */
    @Column
    @GeneratedValue
    private LocalDate created;

    /**
     * Status of the beer.
     * <p>
     * 0: bought
     * 1: chilled
     * 2: opened
     * 3: drank
     *
     * SQL:
     * beer_status INT DEFAULT 0
     */
    @Column(columnDefinition = "default '0'")
    private Integer beerStatus;

    public Beer(Integer userId, String brand, String name, Integer amount, LocalDate created, Integer beerStatus) {
        this.userId = userId;
        this.beerBrand = brand;
        this.beerName = name;
        this.amount = amount;
        this.created = created;
        this.beerStatus = beerStatus;
    }

    protected Beer() {
        // No arg constructor for the JPA
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get the value of beerBrand
     *
     * @return the value of beerBrand
     */
    public String getBrand() {
        return this.beerBrand;
    }

    /**
     * Set the value of beerBrand
     *
     * @param beerBrand new value of beerBrand
     */
    public void setBrand(String beerBrand) {
        this.beerBrand = beerBrand;
    }

    /**
     * Get the value of beerName
     *
     * @return the value of beerName
     */
    public String getName() {
        return this.beerName;
    }

    /**
     * Set the value of beerName
     *
     * @param beerName new value of beerName
     */
    public void setName(String beerName) {
        this.beerName = beerName;
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
     * Get the value of beerStatus
     *
     * @return the value of beerStatus
     */
    public Integer getStatus() {
        return beerStatus;
    }

    /**
     * Set the value of beerStatus
     *
     * @param beerStatus new value of beerStatus
     */
    public void setStatus(Integer beerStatus) {
        this.beerStatus = beerStatus;
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

    @Override
    public String toString() {
        return this.beerBrand + " " + this.beerName;
    }
}
