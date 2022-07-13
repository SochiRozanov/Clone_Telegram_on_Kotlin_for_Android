package com.example.telegram.extension

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.telegram.R

fun Fragment.replace(
    target: Fragment,
    @IdRes containerId: Int = R.id.data_container,
    fragmentManager: FragmentManager = requireActivity().supportFragmentManager
) {
    val beginTransaction = fragmentManager.beginTransaction()
    beginTransaction.replace(containerId, target)
    beginTransaction.addToBackStack(null)
    beginTransaction.commit()
}

fun FragmentActivity.replace(
    target: Fragment,
    @IdRes containerId: Int = R.id.data_container,
    fragmentManager: FragmentManager = supportFragmentManager
) {
    val beginTransaction = fragmentManager.beginTransaction()
    beginTransaction.replace(containerId, target)
    beginTransaction.addToBackStack(null)
    beginTransaction.commit()
}
