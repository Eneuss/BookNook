/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

public class Cart {
    private int id;
    private int userId;
    private String itemType; //book or accessory
    private int itemId;
    private int quantity;

    public Cart(int id, int userId, String itemType, int itemId, int quantity) {
        this.id = id;
        this.userId = userId;
        this.itemType = itemType;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    //getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getItemType() { return itemType; }
    public int getItemId() { return itemId; }
    public int getQuantity() { return quantity; }

    //setters
    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setItemType(String itemType) { this.itemType = itemType; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}

