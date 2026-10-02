-- Demo data loaded when a new database is created.
-- Demo accounts: admin / admin123 and johnDoe / password123

INSERT INTO Categories (id, name) VALUES
    (1, 'Fiction'),
    (2, 'Non-Fiction'),
    (3, 'Science Fiction');

INSERT INTO Books (id, title, author, price, stock, category_id) VALUES
    (1, 'The Great Gatsby', 'F. Scott Fitzgerald', 10.99, 4, 1),
    (2, 'To Kill a Mockingbird', 'Harper Lee', 12.50, 7, 2),
    (3, '1984', 'George Orwell', 8.75, 7, 3);

INSERT INTO Accessories (id, name, price, stock) VALUES
    (1, 'Bookmark', 2.99, 46),
    (2, 'Reading Lamp', 14.99, 13),
    (3, 'Book Cover', 5.50, 27);

INSERT INTO Users (id, username, email, password, role) VALUES
    (1, 'admin', 'admin@example.com', 'admin123', 'admin'),
    (2, 'johnDoe', 'john@example.com', 'password123', 'user');
