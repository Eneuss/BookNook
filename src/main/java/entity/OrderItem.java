/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

public class OrderItem {
    private String type; // "book" or "accessory"
    private String name;
    private int quantity;
    private double priceAtPurchase;

    public OrderItem(String type, String name, int quantity, double priceAtPurchase) {
        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    // Getters
    public String getType() { return type; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPriceAtPurchase() { return priceAtPurchase; }
}
