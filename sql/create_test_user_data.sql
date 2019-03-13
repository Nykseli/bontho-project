/**
* Create two test users
*/

/* Username: admin Password: admin */
INSERT INTO user(username, user_pass, pass_salt)
VALUES(
    "admin",
    "a0584e764b9f5502c11e2ebbf5e5a1add911c2454386ad062387c17359c9ddcc",
    "1234567890123456"
);

/* Username: testaccount Password: password1234 */
INSERT INTO user(username, user_pass, pass_salt)
VALUES(
    "testaccount",
    "d6368e7156f1ab6b922160ab5e6fde4dc12360461b4644665971242c871632cd",
    "kfjeoapskdkkqeit"
);
