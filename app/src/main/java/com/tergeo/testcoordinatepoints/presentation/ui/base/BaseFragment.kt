package com.tergeo.testcoordinatepoints.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


typealias InflateType<V> = (LayoutInflater, ViewGroup?, Boolean) -> V

abstract class BaseFragment<B : ViewBinding>(
    private val inflate: InflateType<B>,
) : Fragment() {

    private var _binding: B? = null
    val binding get() = _binding
            ?: throw java.lang.IllegalStateException("binding is null")

    private var toast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    protected fun showToast(stringId: Int) {
        showToast(getString(stringId))
    }

    protected fun showToast(text: String) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), text, Toast.LENGTH_LONG)
        toast?.show()
    }
}