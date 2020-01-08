package com.example.shoppinglists

import java.io.Serializable

class ShoppingList : Serializable {
    var id : Int = 0
    var name : String? = null
    var itemEntries : MutableList<ItemEntry> = ArrayList<ItemEntry>()

    constructor(id : Int, name: String?) {
        this.id = id
        this.name = name
    }

    fun cost() : Float {
        var cost : Float = 0.00F

        for (itemEntry in itemEntries) {
            cost += itemEntry.item.price * itemEntry.quantity;
        }

        return cost
    }
}