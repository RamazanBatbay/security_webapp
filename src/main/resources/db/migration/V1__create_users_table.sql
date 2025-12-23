CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
<<<<<<< HEAD
    password VARCHAR(255) NOT NULL,
    role VARCHAR(100) NOT NULL
);
=======
    password VARCHAR(255) NOT NULL
);
>>>>>>> origin/master
