package com.IndividualBookNook.tests;

import dao.BookDAO;
import dao.AccessoryDAO;
import dao.CartDAO;
import dao.OrderDAO;
import entity.Order;
import entity.OrderItem;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//covers the cart -> order -> stock -> order history flow used by CheckoutServlet
public class TestCheckoutFlow extends DatabaseTest {
    private static final int USER_ID = 2; //johnDoe in seed.sql

    private final CartDAO cartDAO = new CartDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final BookDAO bookDAO = new BookDAO();
    private final AccessoryDAO accessoryDAO = new AccessoryDAO();

    @Test
    public void addingSameItemTwiceIncreasesQuantity() throws SQLException {
        cartDAO.addOrUpdateCartItem(USER_ID, 3, "book", 8.75);
        cartDAO.addOrUpdateCartItem(USER_ID, 3, "book", 8.75);

        assertEquals(1, cartDAO.getCartItems(USER_ID).size());
        assertEquals(2, cartDAO.getCartItems(USER_ID).get(0).getQuantity());
    }

    @Test
    public void cartTotalSumsBooksAndAccessories() throws SQLException {
        cartDAO.addOrUpdateCartItem(USER_ID, 3, "book", 8.75);
        cartDAO.addOrUpdateCartItem(USER_ID, 3, "book", 8.75);
        cartDAO.addOrUpdateCartItem(USER_ID, 1, "accessory", 2.99);

        assertEquals(20.49, cartDAO.calculateTotalCartPrice(USER_ID), 0.001);
    }

    @Test
    public void removeFromCartDeletesOnlyThatLine() throws SQLException {
        cartDAO.addOrUpdateCartItem(USER_ID, 1, "book", 10.99);
        cartDAO.addOrUpdateCartItem(USER_ID, 2, "accessory", 14.99);
        int firstId = cartDAO.getCartItems(USER_ID).get(0).getId();

        cartDAO.removeFromCart(firstId);

        assertEquals(1, cartDAO.getCartItems(USER_ID).size());
    }

    @Test
    public void checkoutCreatesOrderUpdatesStockAndClearsCart() throws SQLException {
        cartDAO.addOrUpdateCartItem(USER_ID, 3, "book", 8.75);
        cartDAO.addOrUpdateCartItem(USER_ID, 3, "book", 8.75);
        cartDAO.addOrUpdateCartItem(USER_ID, 1, "accessory", 2.99);

        double total = cartDAO.calculateTotalCartPrice(USER_ID);
        int orderId = orderDAO.createOrder(USER_ID, total);
        orderDAO.saveOrderItems(USER_ID, orderId);
        orderDAO.updateStockAfterPurchase(USER_ID);
        cartDAO.clearCart(USER_ID);

        assertEquals(5, bookDAO.getBookById(3).getStock());
        assertEquals(45, accessoryDAO.getAccessoryById(1).getStock());
        assertTrue(cartDAO.getCartItems(USER_ID).isEmpty());

        List<Order> orders = orderDAO.getUserOrders(USER_ID);
        assertEquals(1, orders.size());
        assertEquals(orderId, orders.get(0).getId());
        assertEquals(20.49, orders.get(0).getTotalPrice(), 0.001);

        List<OrderItem> items = orders.get(0).getItems();
        assertEquals(2, items.size());
        OrderItem book = items.stream().filter(i -> "book".equals(i.getType())).findFirst().orElseThrow();
        assertEquals("1984", book.getName());
        assertEquals(2, book.getQuantity());
        assertEquals(8.75, book.getPriceAtPurchase(), 0.001);
    }

    @Test
    public void orderKeepsPriceAtPurchaseAfterPriceChange() throws SQLException {
        cartDAO.addOrUpdateCartItem(USER_ID, 1, "book", 10.99);
        int orderId = orderDAO.createOrder(USER_ID, cartDAO.calculateTotalCartPrice(USER_ID));
        orderDAO.saveOrderItems(USER_ID, orderId);

        bookDAO.updateBook(1, "The Great Gatsby", "F. Scott Fitzgerald", 99.0, 4, 1);

        OrderItem item = orderDAO.getUserOrders(USER_ID).get(0).getItems().get(0);
        assertEquals(10.99, item.getPriceAtPurchase(), 0.001);
    }
}
