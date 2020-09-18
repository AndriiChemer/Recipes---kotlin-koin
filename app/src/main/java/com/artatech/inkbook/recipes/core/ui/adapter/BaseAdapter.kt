package com.artatech.inkbook.recipes.core.ui.adapter

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : ListRecyclerItem, VH: BaseViewHolder<T>>: RecyclerView.Adapter<VH>() {

    open val items by lazy { mutableListOf<T>() }

    fun setItems(items: List<T>) {
        if (items.isNotEmpty()) {
            this.items.clear()
        }

        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return itemAt(position).getViewType()
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item: T = itemAt(holder.adapterPosition)
        holder.bind(item, holder.adapterPosition)
    }

    override fun getItemCount() = items.size

    fun itemAt(position: Int): T {
        return items[position]
    }

    fun addItems(items: List<T>) {
        val firstIndex = this.items.size
        this.items.addAll(items)

        notifyItemRangeInserted(firstIndex, items.size)
    }

    fun addItems(index: Int, items: List<T>) {
        this.items.addAll(index, items);

        notifyItemRangeInserted(index, items.size)
    }

    fun removeItems(startPosition: Int, items: List<T>) {
        removeItems(startPosition, startPosition + items.size)
    }

    fun removeItems(startPosition: Int, endPosition: Int) {
        this.items.subList(startPosition, endPosition).clear();
        notifyItemRangeRemoved(startPosition, endPosition - startPosition);
    }

    fun removeItem(startPosition: Int, item: T) {
        val list = arrayListOf(item)
        removeItems(startPosition, list)
    }
}