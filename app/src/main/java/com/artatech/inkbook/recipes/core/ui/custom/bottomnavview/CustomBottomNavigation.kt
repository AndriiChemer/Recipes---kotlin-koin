package com.artatech.inkbook.recipes.core.ui.custom.bottomnavview

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.core.view.iterator
import com.artatech.inkbook.recipes.R
import kotlinx.android.synthetic.main.activity_main.*

class CustomBottomNavigation @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {


    private val customBottomBar: CustomBottomNavView
    private val circle: RelativeLayout
    private val fab: ImageView
    init {
        val view = View.inflate(context, R.layout.custom_bottom_navigation, this)

        customBottomBar = view.findViewById(R.id.customBottomBar)
        circle = view.findViewById(R.id.circle)
        fab = view.findViewById(R.id.fab)
    }

    fun setOnNavigationItemListener(listener: ItemClickListener) {
        customBottomBar.setOnNavigationItemSelectedListener { item ->
            //index position in menu item
            var index = 0

            for (it: MenuItem in customBottomBar.menu) {
                if (it.itemId != item.itemId) {
                    index++
                } else {
                    break
                }
            }
            customBottomBar.loadByPosition(index)
            fab.setImageDrawable(item.icon)
            circle.x = customBottomBar.mFirstCurveControlPoint1.x.toFloat()



            listener.onItemClick(item)
            true
        }
    }

    interface ItemClickListener {
        fun onItemClick(@NonNull item: MenuItem)
    }
}
