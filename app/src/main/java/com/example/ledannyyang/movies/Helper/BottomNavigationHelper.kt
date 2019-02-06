package com.example.ledannyyang.movies.Helper

import android.annotation.SuppressLint
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView

class BottomNavigationHelper {
    companion object {
        @SuppressLint("RestrictedApi")
        @JvmStatic
        fun disableShiftMode(view: BottomNavigationView) {
            val menuView = view.getChildAt(0) as BottomNavigationMenuView
            try {
                val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
                shiftingMode.isAccessible = true
                shiftingMode.setBoolean(menuView, false)
                shiftingMode.isAccessible = false
                for (i in 0 until menuView.childCount) {
                    val item : BottomNavigationItemView = menuView.getChildAt(i) as BottomNavigationItemView
                    item.setShifting(false)
                    // set once again checked value, so view will be updated
                    item.setChecked(item.itemData.isChecked)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}