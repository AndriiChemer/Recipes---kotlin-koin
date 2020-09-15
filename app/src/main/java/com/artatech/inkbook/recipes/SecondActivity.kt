package com.artatech.inkbook.recipes

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.artatech.inkbook.recipes.core.ui.custom.bottomnavview.CustomBottomNavigation
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        bottomNavMenu.setOnNavigationItemListener(object : CustomBottomNavigation.ItemClickListener {
            override fun onItemClick(item: MenuItem) {
                Toast.makeText(this@SecondActivity, "${item.title} clicked!", Toast.LENGTH_SHORT).show()
            }

        })
    }
}