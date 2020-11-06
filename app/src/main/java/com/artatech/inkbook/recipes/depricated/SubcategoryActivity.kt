package com.artatech.inkbook.recipes.depricated

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.api.response.models.category.CategoryModel
import com.artatech.inkbook.recipes.api.response.models.category.RecipeCategoryModel
import com.artatech.inkbook.recipes.api.response.models.category.SubcategoryModel
import com.artatech.inkbook.recipes.core.ui.adapter.ListRecyclerItem
import com.artatech.inkbook.recipes.ui.recipeslist.presentation.RecipesActivity
import com.artatech.inkbook.recipes.ui.subcategory.presentation.CategoryIntentModel
import com.artatech.inkbook.recipes.ui.subcategory.presentation.SubcategoryViewModel
import com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.model.EmptySubcategoryItem
import com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.model.RecipeCategoryItem
import com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.model.SubcategoryItem
import kotlinx.android.synthetic.main.subcategory_activity.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.NullPointerException
@Deprecated("Remove this class")
class SubcategoryActivity : AppCompatActivity() {

    private val ANIMATION_DURATION = 250L
    private lateinit var categoryModel: CategoryModel
    
    private val viewModel: SubcategoryViewModel by viewModel()
    private val subcategoryAdapter: SubcategoryAdapter by inject()
    private val linearLayoutManager: RecyclerView.LayoutManager by inject()

    private val adapterListener = object : SubcategoryAdapter.SubcategoryListener {
        override fun scrollTo(y: Int, delay: Int) {
            val runnable = Runnable {
                nestedScrollView.smoothScrollTo(0, y)
            }

            Handler().postDelayed(runnable, delay.toLong())
        }

        override fun onRecipeCategoryClick(subcategoryId: Int, recipeCategoryId: Int, title: String) {
            openRecipesScreen(subcategoryId, recipeCategoryId, title)
        }

        override fun onSubcategoryClick(subcategoryId: Int, title: String) {
            openRecipesScreen(subcategoryId, null, title)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.subcategory_activity)

        val category = getCategoryFromExtra()
        categoryName.text = category.name

        initRecycler()
        setItemAdapter(category.subcategories)
    }

    private fun setItemAdapter(subcategories: List<SubcategoryModel>) {
        val items = mutableListOf<ListRecyclerItem>()

        subcategories.forEach { subcategory -> run {
            if (subcategory.recipeCategories.isNotEmpty()) {

                val list = convertListRecipeCategory(subcategory.recipeCategories)
                val subcategoryItem = SubcategoryItem(subcategory.id, subcategory.name, subcategory.categoryId, list, false)
                items.add(subcategoryItem)
            } else {
                val subcategoryItem = EmptySubcategoryItem(subcategory.id, subcategory.name, subcategory.categoryId, subcategory.imageName)
                items.add(subcategoryItem)
            }

        } }

        subcategoryAdapter.setItems(items, adapterListener)
    }

    private fun convertListRecipeCategory(recipeCategories: List<RecipeCategoryModel>): MutableList<RecipeCategoryItem> {
        val recipeCategoryRecyclerList = mutableListOf<RecipeCategoryItem>()

        recipeCategories.forEach { recipeCategory -> run {
            val recipeCategoryItem = RecipeCategoryItem(recipeCategory.id, recipeCategory.name, recipeCategory.subcategoryId, recipeCategory.imageName)
            recipeCategoryRecyclerList.add(recipeCategoryItem)
        } }

        return recipeCategoryRecyclerList
    }

    private fun initRecycler() {
        val animation = DefaultItemAnimator()
        animation.supportsChangeAnimations = false
        animation.removeDuration = ANIMATION_DURATION

        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = subcategoryAdapter
            itemAnimator = animation
            isNestedScrollingEnabled = false
        }

        nestedScrollView.isNestedScrollingEnabled = false
        nestedScrollView.post {nestedScrollView.scrollTo(0, 0)}
    }

    private fun getCategoryFromExtra(): CategoryModel {
        val extras = intent.extras

        if (extras == null) {
            throw NullPointerException("$intent should not be empty!")
        } else {

            val categoryModel = extras.getSerializable(CATEGORY_KEY) as CategoryModel
            this.categoryModel = categoryModel
            return categoryModel
        }
    }

    private fun openRecipesScreen(subcategoryId: Int, recipeCategoryId: Int?, title: String) {
        val categoryModel = getCategoryFromExtra()
        val categoryIntent = CategoryIntentModel(categoryModel.id, subcategoryId, recipeCategoryId, title)
        RecipesActivity.start(this, categoryIntent)
    }

    companion object {
        private val CATEGORY_KEY = "category_key"

        fun start(context: Context, category: CategoryModel) {
            val intent = Intent(context, SubcategoryActivity::class.java)
            intent.putExtra(CATEGORY_KEY, category)
            context.startActivity(intent)
        }
    }
}
