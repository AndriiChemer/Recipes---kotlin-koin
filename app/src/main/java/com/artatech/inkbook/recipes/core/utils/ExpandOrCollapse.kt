package com.artatech.inkbook.recipes.core.utils

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup


class ExpandOrCollapse {
    fun expand(view: View) {
        view.visibility = View.VISIBLE
        val widthSpec = View.MeasureSpec.makeMeasureSpec(
            0,
            View.MeasureSpec.UNSPECIFIED
        )
        val heightSpec = View.MeasureSpec.makeMeasureSpec(
            0,
            View.MeasureSpec.UNSPECIFIED
        )
        view.measure(widthSpec, heightSpec)

        val mAnimator = slideAnimator(view, 1, view.measuredHeight)

        mAnimator!!.start()

    }

    fun collapse(v: View) {
        val finalHeight = v.height

        val mAnimator: ValueAnimator? = slideAnimator(v, finalHeight, 1)

        mAnimator?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
                v.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animator?) {}

            override fun onAnimationCancel(animation: Animator?) {}

            override fun onAnimationStart(animation: Animator?) {}

        })

        mAnimator?.start()
    }

    private fun slideAnimator(view: View, start: Int, end: Int): ValueAnimator? {
        val animator = ValueAnimator.ofInt(start, end)
        animator.addUpdateListener { valueAnimator -> //Update Height
            val value = valueAnimator.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = view.layoutParams
            layoutParams.height = value
            view.layoutParams = layoutParams
        }
        return animator
    }

    interface Listener {
        fun collapse(view: View)
        fun expand(view: View)
    }
}