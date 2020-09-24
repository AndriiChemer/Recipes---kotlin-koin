package com.artatech.inkbook.recipes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artatech.inkbook.recipes.depricated.CategoriesActivity
import com.artatech.inkbook.recipes.ui.recipeslist.presentation.RecipesActivity
import com.artatech.inkbook.recipes.ui.splash.presentation.SplashActivity
import com.artatech.inkbook.recipes.ui.subcategory.presentation.CategoryIntentModel
import kotlinx.android.synthetic.main.activity_developer.*

class DeveloperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer)

        openCategoriesButton.setOnClickListener {
            val intent = Intent(this, CategoriesActivity::class.java)
            startActivity(intent)
        }

        mainFlow.setOnClickListener {
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
        }

        openRecipeListButton.setOnClickListener {
            RecipesActivity.start(this, CategoryIntentModel(43, 164, 173, "Солянка"))
        }

        customCategory.setOnClickListener {

        }

    }

}
