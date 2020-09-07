package com.artatech.inkbook.recipes.core.ui.adapter

abstract class ClickableAdapter<T : ListRecyclerItem, VH: BaseViewHolder<T>, L>: BaseAdapter<T, VH>() {

    var listener: L? = null

    open fun setItems(items: List<T>, listener: L) {
        this.listener = listener
        this.items.clear()
        this.items.addAll(items)

        notifyDataSetChanged()
    }
}