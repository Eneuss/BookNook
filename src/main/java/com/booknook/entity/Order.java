package com.booknook.entity;

import java.util.List;

public class Order {
    private int id;
    private int userId;
    private String orderDate;
    private double totalPrice;
    private List<OrderItem> items; //added for order history display

    public Order(int id, int userId, String orderDate, double totalPrice, List<OrderItem> items) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    // Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getOrderDate() { return orderDate; }
    public double getTotalPrice() { return totalPrice; }
    public List<OrderItem> getItems() { return items; }
}