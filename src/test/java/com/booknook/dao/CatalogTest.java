package com.booknook.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

// admin product and category management
public class CatalogTest extends DatabaseTest {
    private final BookDAO bookDAO = new BookDAO();
    private final AccessoryDAO accessoryDAO = new AccessoryDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Test
    public void seedDataIsLoaded() throws SQLException {
        assertEquals(3, bookDAO.getAllBooks().size());
        assertEquals(3, accessoryDAO.getAllAccessories().size());
        assertEquals(3, categoryDAO.getAllCategoriesWithId().size());
    }

    @Test
    public void addEditDeleteBook() throws SQLException {
        bookDAO.addBook("Dune", "Frank Herbert", 9.99, 3, 3);
        assertEquals(4, bookDAO.getAllBooks().size());

        int id = bookDAO.getAllBooks().get(3).getId();
        bookDAO.updateBook(id, "Dune", "Frank Herbert", 11.0, 2, 3);
        assertEquals(11.0, bookDAO.getBookById(id).getPrice(), 0.001);
        assertEquals(2, bookDAO.getBookById(id).getStock());

        bookDAO.deleteBook(id);
        assertNull(bookDAO.getBookById(id));
    }

    @Test
    public void addEditDeleteAccessory() throws SQLException {
        accessoryDAO.addAccessory("Tote Bag", 7.5, 10);
        int id = accessoryDAO.getAllAccessories().get(3).getId();

        accessoryDAO.updateAccessory(id, "Canvas Tote", 8.0, 9);
        assertEquals("Canvas Tote", accessoryDAO.getAccessoryById(id).getName());

        accessoryDAO.deleteAccessory(id);
        assertNull(accessoryDAO.getAccessoryById(id));
    }

    @Test
    public void addEditDeleteCategory() throws SQLException {
        categoryDAO.addCategory("Poetry");
        int id = categoryDAO.getAllCategoriesWithId().get(3).getId();

        assertTrue(categoryDAO.updateCategory(id, "Poems"));
        assertEquals("Poems", categoryDAO.getCategoryById(id).getName());

        categoryDAO.deleteCategory(id);
        assertNull(categoryDAO.getCategoryById(id));
    }

    @Test
    public void searchMatchesTitleOrAuthor() throws SQLException {
        assertEquals(1, bookDAO.searchBooks("orwell").size());
        assertEquals(1, bookDAO.searchBooks("Gatsby").size());
        assertEquals(1, accessoryDAO.searchAccessories("lamp").size());
    }
}
