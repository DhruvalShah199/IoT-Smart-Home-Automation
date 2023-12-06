To run the server: Run the SHServerApplication.java file
To run the client: Run the SHClientApplication.java file

Credentials to login as an admin: Username - admin1 Password: 123

MySQL Query for local device:
CREATE DATABASE smartHome;
use smartHome;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50)
);

CREATE TABLE admins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

DELETE FROM users;

SELECT * FROM users;

SELECT * FROM admins;
ALTER TABLE users
ADD COLUMN is_admin BOOLEAN DEFAULT FALSE;

INSERT INTO admins (username, password)
VALUES ('admin1', '123');

INSERT INTO users (username, password)
VALUES ('user1', '1234');