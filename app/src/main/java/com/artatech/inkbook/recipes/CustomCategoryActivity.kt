package com.artatech.inkbook.recipes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CustomCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_category)
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, CustomCategoryActivity::class.java)
            context.startActivity(intent)
        }
    }
}