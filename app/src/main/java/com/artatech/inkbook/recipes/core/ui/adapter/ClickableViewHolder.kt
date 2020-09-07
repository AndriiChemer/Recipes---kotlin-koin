package com.artatech.inkbook.recipes.core.ui.adapter

import android.view.View

abstract class ClickableViewHolder< T, L>(itemView: View) : BaseViewHolder<T>(itemView) {

    var listener: L? = null
}