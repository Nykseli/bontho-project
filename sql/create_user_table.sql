/**
* Command for creating the user database table that contains the login info
* and the unique user_id key
*/

CREATE TABLE IF NOT EXISTS user (
    /**
    * Auto increment user to avoid collisions.
    * user_id is used to get data from other tables
    */
    user_id INT NOT NULL AUTO_INCREMENT,
    /* Just a plaintext username */
    username VARCHAR(30) NOT NULL,
    /**
    * We want to use SHA-256 hashing algorithm.
    * SHA-256 digest output is always 64 chars long
    */
    user_pass VARCHAR(64) NOT NULL,
    /**
    * Use (pseudo)random 16 byte string to salt the password.
    * Salt is put in front of the password
    */
    pass_salt VARCHAR(16) NOT NULL,
    /* auto incremented user_id is primary key */
    PRIMARY KEY (user_id)
);

