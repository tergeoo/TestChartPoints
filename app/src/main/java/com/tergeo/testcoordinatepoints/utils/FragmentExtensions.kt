package com.tergeo.testcoordinatepoints.utils

import androidx.fragment.app.Fragment
import com.tergeo.testcoordinatepoints.R

fun Fragment.replace(
    fragment: Fragment,
    addToBackStack: Boolean = true,
    tag: String? = null
) {
    val transaction = requireActivity().supportFragmentManager.beginTransaction()
        .replace(R.id.container, fragment, tag)

    if (addToBackStack)
        transaction.addToBackStack(null)

    transaction.commit()
}
