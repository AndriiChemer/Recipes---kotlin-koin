package com.artatech.inkbook.recipes.ui.category.presentation

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.api.response.models.category.CategoryModel
import kotlinx.android.synthetic.main.category_item.view.*
import java.util.*

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private lateinit var onCategoryClicked: (category: CategoryModel) -> Unit
    private val categories by lazy { mutableListOf<CategoryModel>() }

    fun setCategories(categories: List<CategoryModel>) {
        if (categories.isNotEmpty()) {
            this.categories.clear()
        }

        this.categories.addAll(categories)
        notifyDataSetChanged()
    }

    fun setClickListener(onCategoryClicked: (category: CategoryModel) -> Unit) {
        this.onCategoryClicked = onCategoryClicked
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.category_item, parent, false)

        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category, onCategoryClicked)
    }

    override fun getItemCount() = categories.size

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(category: CategoryModel, onCategoryClicked: (category: CategoryModel) -> Unit) {
            val color = getRandomColor()
            itemView.container.setBackgroundColor(color)

            itemView.categoryTitle.text = category.name

            val subcategoriesCount = category.subcategories.size
            if (subcategoriesCount > 0) {
                itemView.subcategoriesCount.text = subcategoriesCount.toString()
            } else {
                itemView.subcategoriesCount.visibility = View.INVISIBLE
            }

            itemView.container.setOnClickListener {
                onCategoryClicked.invoke(category)
            }
        }

        private fun getRandomColor(): Int {
            val random = Random()
            return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
        }
    }
}