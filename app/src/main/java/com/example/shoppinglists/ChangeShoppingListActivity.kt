package com.example.shoppinglists

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_shopping_list.*

class ChangeShoppingListActivity : AppCompatActivity() {

    lateinit var shoppingList : ShoppingList

    val changeShoppingListAdapter = ChangeShoppingListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_shopping_list)

        shoppingList = intent.getSerializableExtra("changeShoppingList") as ShoppingList

        supportActionBar!!.title = shoppingList.name
        Toast.makeText(this, "Make your changes", Toast.LENGTH_LONG).show()

        listViewListEntries.adapter = changeShoppingListAdapter

        buttonSave.setOnClickListener {
            var resultIntent = Intent()
            resultIntent.putExtra("changedShoppingList", shoppingList)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    inner class ChangeShoppingListAdapter : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parente: ViewGroup?): View {
            val v = layoutInflater.inflate(R.layout.row_change_item_entry, parente, false)
            val imageViewItem = v.findViewById<ImageView>(R.id.imageViewItem)
            val textViewItemName = v.findViewById<TextView>(R.id.textViewItemName)
            val textViewItemQuantity = v.findViewById<TextView>(R.id.textViewItemQuantity)
            val buttonIncreaseItemQuantity = v.findViewById<Button>(R.id.buttonIncreaseItemQuantity)
            val buttonDecreaseItemQuantity = v.findViewById<Button>(R.id.buttonDecreaseItemQuantity)

            var itemEntry = getItem(position) as ItemEntry

            imageViewItem.setImageResource(itemEntry.item.image)
            textViewItemName.text = itemEntry.item.name
            textViewItemQuantity.text = itemEntry.quantity.toString()

            v.setOnClickListener {
                val intent = Intent(this@ChangeShoppingListActivity, ItemActivity::class.java)
                intent.putExtra("item_name", itemEntry.item.name)
                intent.putExtra("item_price", itemEntry.item.price)
                intent.putExtra("item_image", itemEntry.item.image)

                startActivity(intent)
            }

            buttonIncreaseItemQuantity.setOnClickListener {
                itemEntry.quantity++
                changeShoppingListAdapter.notifyDataSetChanged()
            }

            buttonDecreaseItemQuantity.setOnClickListener {
                if(itemEntry.quantity > 0 ) {
                    itemEntry.quantity--
                    changeShoppingListAdapter.notifyDataSetChanged()
                }
            }

            return  v
        }

        override fun getItem(position: Int): Any {
            return shoppingList.itemEntries[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return shoppingList.itemEntries.size
        }

    }
}