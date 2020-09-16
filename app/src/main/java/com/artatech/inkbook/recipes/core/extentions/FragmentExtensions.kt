package com.artatech.inkbook.recipes.core.extentions

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.artatech.inkbook.recipes.R

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

private const val ANIM_ENTER_RES_ID: Int = R.anim.slide_in_right
private const val ANIM_EXIT_RES_ID: Int = R.anim.slide_out_left
private const val ANIM_POP_ENTER_RES_ID: Int = R.anim.slide_in_left
private const val ANIM_POP_EXIT_RES_ID: Int = R.anim.slide_out_right

fun AppCompatActivity.addFragment(
        fragment: Fragment,
        frameId: Int,
        tag: String? = null,
        addToBackStack: Boolean = false,
        enterAnimResId: Int = ANIM_ENTER_RES_ID,
        exitAnimResId: Int = ANIM_EXIT_RES_ID,
        popEnterAnimResId: Int = ANIM_POP_ENTER_RES_ID,
        popExitAnimResId: Int = ANIM_POP_EXIT_RES_ID
) {
    supportFragmentManager.inTransaction {
        setCustomAnimations(enterAnimResId, exitAnimResId, popEnterAnimResId, popExitAnimResId)
        if (addToBackStack) {
            addToBackStack(tag)
        }
        add(frameId, fragment)
    }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction {
        setDefaultAnimation()
        replace(frameId, fragment)
    }
}

fun Fragment.launchActivity(starter: (Context) -> Unit) {
    context?.let { starter(it) }
}

private fun FragmentTransaction.setDefaultAnimation() {
    setCustomAnimations(ANIM_ENTER_RES_ID, ANIM_EXIT_RES_ID, ANIM_POP_ENTER_RES_ID, ANIM_POP_EXIT_RES_ID)
}

fun Fragment.getFragmentTag(): String {
    return this::class.java.name.split('$')[0]
}