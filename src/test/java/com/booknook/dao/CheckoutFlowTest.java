package com.booknook.dao;

import static org.junit.jupiter.api.Assertions.*;

import com.booknook.entity.Order;
import com.booknook.entity.OrderItem;
import java.util.List;
import org.junit.jupiter.api.Test;

// covers the cart -> order -> stock -> order history flow used by CheckoutServlet
public class CheckoutFlowTest extends DatabaseTest {
    private static final int USER_ID = 2; // johnDoe in seed.sql

    private final CartDAO cartDAO = new CartDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final BookDAO bookDAO = new BookDAO();
    private final AccessoryDAO accessoryDAO = new AccessoryDAO();

    @Test
    public void addingSameItemTwiceIncreasesQuantity() throws Exception {
        cartDAO.addOrUpdateCartItem(USER_ID, 3, "book");
        cartDAO.addOrUpdateCartItem(USER_ID, 3, "book");

        assertEquals(1, cartDAO.getCartItems(USER_ID).size());
        assertEquals(2, cartDAO.getCartItems(USER_ID).get(0).getQuantity());
    }

    @Test
    public void cartTotalSumsBooksAndAccessories() throws Exception {
        cartDAO.addOrUpdateCartItem(USER_ID, 3, "book");
        cartDAO.addOrUpdateCartItem(USER_ID, 3, "book");
        cartDAO.addOrUpdateCartItem(USER_ID, 1, "accessory");

        assertEquals(20.49, cartDAO.calculateTotalCartPrice(USER_ID), 0.001);
    }

    @Test
    public void removeFromCartDeletesOnlyThatLine() throws Exception {
        cartDAO.addOrUpdateCartItem(USER_ID, 1, "book");
        cartDAO.addOrUpdateCartItem(USER_ID, 2, "accessory");
        int firstId = cartDAO.getCartItems(USER_ID).get(0).getId();

        cartDAO.removeFromCart(firstId, USER_ID);

        assertEquals(1, cartDAO.getCartItems(USER_ID).size());
    }

    @Test
    public void cannotRemoveAnotherUsersCartItem() throws Exception {
        cartDAO.addOrUpdateCartItem(USER_ID, 1, "book");
        int itemId = cartDAO.getCartItems(USER_ID).get(0).getId();

        cartDAO.removeFromCart(itemId, 1);

        assertEquals(1, cartDAO.getCartItems(USER_ID).size());
    }

    @Test
    public void checkoutCreatesOrderUpdatesStockAndClearsCart() throws Exception {
        cartDAO.addOrUpdateCartItem(USER_ID, 3, "book");
        cartDAO.addOrUpdateCartItem(USER_ID, 3, "book");
        cartDAO.addOrUpdateCartItem(USER_ID, 1, "accessory");

        int orderId = orderDAO.checkout(USER_ID);

        assertEquals(5, bookDAO.getBookById(3).getStock());
        assertEquals(45, accessoryDAO.getAccessoryById(1).getStock());
        assertTrue(cartDAO.getCartItems(USER_ID).isEmpty());

        List<Order> orders = orderDAO.getUserOrders(USER_ID);
        assertEquals(1, orders.size());
        assertEquals(orderId, orders.get(0).getId());
        assertEquals(20.49, orders.get(0).getTotalPrice(), 0.001);

        List<OrderItem> items = orders.get(0).getItems();
        assertEquals(2, items.size());
        OrderItem book = items.stream()
                .filter(i -> "book".equals(i.getType()))
                .findFirst()
                .orElseThrow();
        assertEquals("1984", book.getName());
        assertEquals(2, book.getQuantity());
        assertEquals(8.75, book.getPriceAtPurchase(), 0.001);
    }

    @Test
    public void orderKeepsPriceAtPurchaseAfterPriceChange() throws Exception {
        cartDAO.addOrUpdateCartItem(USER_ID, 1, "book");
        orderDAO.checkout(USER_ID);

        bookDAO.updateBook(1, "The Great Gatsby", "F. Scott Fitzgerald", 99.0, 4, 1);

        OrderItem item = orderDAO.getUserOrders(USER_ID).get(0).getItems().get(0);
        assertEquals(10.99, item.getPriceAtPurchase(), 0.001);
    }

    @Test
    public void checkoutFailsAndChangesNothingWhenStockIsTooLow() throws Exception {
        cartDAO.addOrUpdateCartItem(USER_ID, 1, "accessory"); // plenty of stock
        for (int i = 0; i < 5; i++) {
            cartDAO.addOrUpdateCartItem(USER_ID, 1, "book"); // only 4 in stock
        }

        CheckoutException e = assertThrows(CheckoutException.class, () -> orderDAO.checkout(USER_ID));
        assertTrue(e.getMessage().contains("stock"));

        assertTrue(orderDAO.getUserOrders(USER_ID).isEmpty(), "no order should be saved");
        assertEquals(4, bookDAO.getBookById(1).getStock());
        assertEquals(46, accessoryDAO.getAccessoryById(1).getStock(), "earlier stock updates are rolled back");
        assertEquals(2, cartDAO.getCartItems(USER_ID).size(), "cart is kept");
    }

    @Test
    public void checkoutWithEmptyCartIsRejected() {
        assertThrows(CheckoutException.class, () -> orderDAO.checkout(USER_ID));
    }
}
