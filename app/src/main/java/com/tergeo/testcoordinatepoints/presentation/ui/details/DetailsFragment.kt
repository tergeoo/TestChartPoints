package com.tergeo.testcoordinatepoints.presentation.ui.details

import android.os.Bundle
import android.view.View
import com.tergeo.domain.entity.model.Point
import com.tergeo.testcoordinatepoints.databinding.FragmentDetailsBinding
import com.tergeo.testcoordinatepoints.presentation.ui.base.BaseFragment
import com.tergeo.testcoordinatepoints.presentation.ui.details.adapter.DetailsPointAdapter
import com.tergeo.testcoordinatepoints.presentation.ui.views.chart.ChartPoint

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(
    FragmentDetailsBinding::inflate
) {

    private val adapter = DetailsPointAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val points = arguments?.getParcelableArray(POINTS, ChartPoint::class.java)
        val list = points!!.sortedBy { it.x }

        binding.recycler.adapter = adapter
        adapter.submitList(list)

        binding.lineChart.setPoints(list)
    }

    companion object {

        private const val POINTS = "points"

        fun newInstance(points: Array<ChartPoint>) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelableArray(POINTS, points)
            }
        }
    }
}