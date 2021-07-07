package com.nneka.persistence

import androidx.lifecycle.LiveData

class ShoppingViewModel: ViewModel () {

    fun addShoppingItem (
        shoppingItem: ShoppingModel,
        database: ShoppingDatabase){
       database.ShoppingDAO().addShoppingItem(shoppingItem)
    }
    fun getAllShoppingItems (database: ShoppingDatabase)
    : LiveData<List<ShoppingModel>>{
        return  database.shoppingDao().gatAllShoppingItems ()
    }

}