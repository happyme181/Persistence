package com.nneka.persistence

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.nneka.persistence.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myShoppingAdapter: ShoppingAdapter
    private lateinit var myShoppingList: MutableList <ShoppingModel>
    private lateinit var  viewModel: ShoppingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,)[ShoppingViewModel::class.java]

        myShoppingList = mutableListOf()

        myShoppingAdapter = ShoppingAdapter(myShoppingList)

        binding.recyclerView.adapter = myShoppingAdapter


        val db = Room.databaseBuilder(
            applicationContext,
            ShoppingDatabase::class.java, "shopping-database"
        ).allowMainThreadQueries().build()

        val shoppingDAO = db.ShoppingDAO()


        viewModel.getAllShoppingItems().observe(this,{
            myShoppingAdapter = ShoppingAdapter(myShoppingList)
            binding.recyclerView.adapter = myShoppingAdapter
            myShoppingAdapter.notifyDataSetChanged()
        })



        binding.button.setOnClickListener {
            val category : String = binding.editText2.text.toString()
            val description : String = binding.editText.text.toString()

            val shoppingItem = ShoppingModel(category, description)
            shoppingDAO.addShoppingItem(shoppingItem)

            myShoppingAdapter.notifyDataSetChanged()
        }
    }
}