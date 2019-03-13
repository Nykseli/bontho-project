/**
 * Class for the sql User table
 */

package com.nykseli.bontho.entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    /**
     * Auto increment user to avoid collisions. userId is used to get data from
     * other tables
     *
     * SQL: user_id INT NOT NULL AUTO_INCREMENT
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    /**
     * Just a plaintext username with max length of 30
     *
     * SQL: username VARCHAR(30) NOT NULL
     */
    @Column(nullable = false)
    private String username;

    /**
     * SHA-256 digest of the users password and the pass_salt column
     *
     * SQL: user_pass VARCHAR(64) NOT NULL
     */
    @Column(nullable = false)
    private String userPass;

    /**
     * Use (pseudo)random 16 byte string to salt the password. Salt is put in front
     * of the password.
     *
     * SQL: pass_salt VARCHAR(16) NOT NULL
     */
    @Column(nullable = false)
    private String passSalt;

    public User(Integer userId, String username, String userPass, String passSalt) {
        this.userId = userId;
        this.username = username;
        this.userPass = userPass;
        this.passSalt = passSalt;
    }

    protected User() {
        // No arg constructor for the JPA
    }

    /**
     * Set the userid. This should not be used since it's auto incremented each time
     * a new user is created.
     * <p>
     * User id is also used as to id user in other tables so changing this value can
     * create collisions
     *
     * @param userId new value of userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Get the value of userId
     *
     * @return the value of userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Set the value of username
     *
     * @param username new value of username
     */
    public void setUserName(String username) {
        this.username = username;
    }

    /**
     * Get the value of username
     *
     * @return the value of username
     */
    public String getUserName() {
        return username;
    }

    /**
     * Set the value of userPass
     *
     * @param userPass new value of user password
     */
    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    /**
     * Get the value of user password
     *
     * @return the value of user password
     */
    public String getUserPass() {
        return userPass;
    }

    /**
     * Set the value of brand
     *
     * @param brand new value of brand
     */
    public void setSaltPass(String passSalt) {
        this.passSalt = passSalt;
    }

    /**
     * Get the value of password salt
     *
     * @return the value of password salt
     */
    public String getPassSalt() {
        return passSalt;
    }

    /**
     * Update the userPass with the salted hash of the password.
     * <p>
     * This should be only used when the user is is first created.
     */
    public void passwordHash() {
        this.userPass = createHash(userPass, passSalt);
    }

    /**
     * Check if the plaintext password matches the users password hash
     *
     * @param password the plaintext password we want to test
     * @return boolean value for successful or nonsuccessful
     */
    public boolean matchPassword(String password) {
        return userPass.equals(createHash(password, passSalt));
    }

    /**
     * Create a salted hash of the plaintext password.
     *
     * @param userPass plaintext password that we want to hash
     * @param passSalt salt for the password
     * @return hash formatted to hexadesimal string
     */
    public static String createHash(String userPass, String passSalt) {
        String hash = "";
        // We want to create the hash by appending the userPass to userSalt
        String message = passSalt + userPass;
        // We want to use SHA-256 so get the digest instance for it
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException err) {
            return "";
        }

        // Create the digest from the message bytes
        md.update((message.getBytes()));

        // Update the this.userPass to be the formated hashvalue
        Formatter format = new Formatter();
        for (byte b : md.digest()) {
            format.format("%02x", b);
        }
        hash = format.toString();
        format.close();

        return hash;
    }

}
