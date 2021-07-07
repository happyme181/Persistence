package com.nneka.persistence

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.nneka.persistence.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myShoppingAdapter: ShoppingAdapter
    private lateinit var myShoppingList: MutableList <ShoppingModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myShoppingList = mutableListOf()

        myShoppingAdapter = ShoppingAdapter(myShoppingList)
        binding.recyclerView.adapter = myShoppingAdapter


        val db = Room.databaseBuilder(
            applicationContext,
            ShoppingDatabase::class.java, "shopping-database"
        ).build()

        val shoppingDAO = db.ShoppingDAO()


        myShoppingList = shoppingDAO.getAllShoppingItems().toMutableList()
        myShoppingAdapter.notifyDataSetChanged()


        binding.button.setOnClickListener {
            val category : String = binding.editText2.text.toString()
            var description : String = binding.editText.text.toString()

            val shoppingItem = ShoppingModel(category, description)

            shoppingDAO.addShoppingItem(shoppingItem)

            myShoppingList.add(shoppingItem)
            myShoppingAdapter.notifyDataSetChanged()
        }
    }
}