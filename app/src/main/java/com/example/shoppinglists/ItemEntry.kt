package com.example.shoppinglists

import java.io.Serializable

class ItemEntry : Serializable {
    var item : Item
    var quantity : Int = 0

    constructor(item: Item, quantity: Int) {
        this.item = item
        this.quantity = quantity
    }
}