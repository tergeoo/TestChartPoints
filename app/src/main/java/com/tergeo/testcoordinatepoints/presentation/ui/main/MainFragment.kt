package com.tergeo.testcoordinatepoints.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.tergeo.testcoordinatepoints.databinding.FragmentMainBinding
import com.tergeo.testcoordinatepoints.presentation.ui.base.BaseEffect
import com.tergeo.testcoordinatepoints.presentation.ui.base.BaseFragment
import com.tergeo.testcoordinatepoints.presentation.ui.details.DetailsFragment
import com.tergeo.testcoordinatepoints.utils.replace
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel by inject<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.getPointsButton.setOnClickListener {
            viewModel.getPoints()
        }
        binding.pointCountEditText.addTextChangedListener {
            viewModel.changeCount(it?.toString())
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    render(state)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.effects.collect { effect ->
                    when (effect) {
                        is BaseEffect.MessageEffect -> showToast(effect.message)
                        is MainEffect.Next -> replace(DetailsFragment.newInstance(effect.points))
                    }
                }
            }
        }
    }

    private fun render(state: MainState) {
        with(binding) {
            progress.visibility = if (state.isLoading) View.VISIBLE else View.GONE
            pointCountInputLayout.error = state.error
            getPointsButton.isEnabled = state.error == null
        }
    }

    companion object {

        fun newInstance() = MainFragment()
    }
}