package com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.core.ui.adapter.BaseViewHolder
import com.artatech.inkbook.recipes.core.ui.adapter.ClickableAdapter
import kotlinx.android.synthetic.main.item_subcategory_without_rec_cat.view.*

class SubcategoryWithoutRecCatAdapter: ClickableAdapter
       <EmptySubcategoryItem,
        SubcategoryWithoutRecCatAdapter.SubcategoryWithoutRecCatViewHolder,
               SubcategoryWithoutRecCatAdapter.Listener>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubcategoryWithoutRecCatViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_subcategory_without_rec_cat, parent, false)

        return SubcategoryWithoutRecCatViewHolder(itemView, listener as Listener)
    }

    class SubcategoryWithoutRecCatViewHolder(itemView: View, val listener: Listener): BaseViewHolder<EmptySubcategoryItem>(itemView) {
        override fun bind(item: EmptySubcategoryItem, position: Int) {
            itemView.title.text = item.name
            itemView.setOnClickListener {
                listener.onSubcategoryClicked(item.categoryId, item.id, item.name)
            }
        }

    }

    interface Listener {
        fun onSubcategoryClicked(categoryId: Int, subcategoryId: Int, title: String)
    }
}