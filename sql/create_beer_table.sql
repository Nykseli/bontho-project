CREATE TABLE IF NOT EXISTS beer (
    /* Auto generated id for recognizing the entrys from apart */
    id INT NOT NULL AUTO_INCREMENT,
    /* Who owns the beer */
    user_id INT NOT NULL,
    /* Brand of the beer */
    beer_brand VARCHAR(30) NOT NULL,
    /* The name of the beer */
    beer_name VARCHAR(30) NOT NULL,
    /* How many beers we have */
    amount INT DEFAULT 0,
    /**
    * Use an int to tell te status.
    * It's easier to change the status texts later
    */
    beer_status INT DEFAULT 0,
    /* Time stamp for when the beer entry was entered */
    created TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id)

);
