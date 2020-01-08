package com.example.shoppinglists

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_shopping_list.*

class MainActivity : AppCompatActivity() {

    var shoppingLists : MutableList<ShoppingList> = ArrayList<ShoppingList>()

    val shoppingListsAdapter = ShoppingListsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.title = "Shopping Lists"
        Toast.makeText(this, "Welcome!", Toast.LENGTH_LONG).show()

        listViewShoppingLists.adapter = shoppingListsAdapter
    }

    inner class ShoppingListsAdapter : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parente: ViewGroup?): View {
            val v = layoutInflater.inflate(R.layout.row_shopping_list, parente, false)
            val imageViewShoppingList = v.findViewById<ImageView>(R.id.imageViewShoppingList)
            val textViewShoppingListName = v.findViewById<TextView>(R.id.textViewShoppingListName)
            val textViewShoppingListNumEntries = v.findViewById<TextView>(R.id.textViewShoppingListNumEntries)
            val textViewShoppingListCost = v.findViewById<TextView>(R.id.textViewShoppingListCost)
            val buttonRemoveShoppingList = v.findViewById<TextView>(R.id.buttonRemoveShoppingList)

            var currentShoppingList = getItem(position) as ShoppingList

            imageViewShoppingList.setImageResource(R.drawable.shopping_lists_icon)
            textViewShoppingListName.text = currentShoppingList.name
            textViewShoppingListNumEntries.text = currentShoppingList.itemEntries.size.toString() + " item entries"
            textViewShoppingListCost.text = currentShoppingList.cost().toString() + "€"

            v.setOnClickListener {
                val intent = Intent(this@MainActivity, ShoppingListActivity::class.java)
                intent.putExtra("currentShoppingList", currentShoppingList)

                startActivityForResult(intent, 2)
            }

            buttonRemoveShoppingList.setOnClickListener {
                shoppingLists.removeAt(position)
                shoppingListsAdapter.notifyDataSetChanged()
            }

            return  v
        }

        override fun getItem(position: Int): Any {
            return shoppingLists[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return shoppingLists.size
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                var newShoppingListId = shoppingLists.count()
                val newShoppingListName = data?.getStringExtra("newShoppingList_name")
                shoppingLists.add(ShoppingList(newShoppingListId, newShoppingListName))
                shoppingListsAdapter.notifyDataSetChanged()

                Toast.makeText(this, newShoppingListName + " created", Toast.LENGTH_LONG).show()
            }
            if(resultCode == Activity.RESULT_CANCELED){

            }
        }

        if(requestCode == 2) {
            if(resultCode == Activity.RESULT_OK){
                var changedShoppingList = data?.getSerializableExtra( "savedShoppingList") as ShoppingList
                shoppingLists[changedShoppingList.id] = changedShoppingList
                shoppingListsAdapter.notifyDataSetChanged()

                Toast.makeText(this, changedShoppingList.name + " saved", Toast.LENGTH_LONG).show()
            }
            if(resultCode == Activity.RESULT_CANCELED){

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemAdd -> {

                val intent = Intent(this@MainActivity, NewShoppingListNameActivity::class.java)

                startActivityForResult(intent,1)

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}