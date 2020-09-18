package com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.core.ui.adapter.BaseViewHolder
import com.artatech.inkbook.recipes.core.ui.adapter.ClickableAdapter
import com.artatech.inkbook.recipes.core.ui.adapter.SpacingItemDecoration
import kotlinx.android.synthetic.main.item_subcategory.view.*

class SubcategoriesAdapter: ClickableAdapter<SubcategoryItem, SubcategoriesAdapter.SubcategoryViewHolder, SubcategoriesAdapter.SubcategoryListener>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubcategoryViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_subcategory, parent, false)

        return SubcategoryViewHolder(itemView, listener as SubcategoryListener)
    }

    class SubcategoryViewHolder(itemView: View, val listener: SubcategoryListener): BaseViewHolder<SubcategoryItem>(itemView) {
        override fun bind(item: SubcategoryItem, position: Int) {
            itemView.title.text = item.name

            prepareRecyclerView(item.categoryId, item.id, item.recipeCategories)

        }

        private fun prepareRecyclerView(categoryId: Int, subcategoryId: Int, items: MutableList<RecipeCategoryItem>) {
            val recipeCategoryAdapter = RecipeCategoryAdapter()
            itemView.subRecyclerView.apply {
                layoutManager = LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
                adapter = recipeCategoryAdapter
                addItemDecoration(SpacingItemDecoration(10))
            }

            recipeCategoryAdapter.setItems(items, object : RecipeCategoryAdapter.Listener {
                override fun onRecipeCategoryClicked(recipeCategoryId: Int, title: String) {
                    listener.onClick(categoryId, subcategoryId, recipeCategoryId, title)
                }

            } )
        }

    }

    interface SubcategoryListener {
        fun onClick(categoryId: Int, subcategoryId: Int, recipeCategoryId: Int, title: String)
    }
}