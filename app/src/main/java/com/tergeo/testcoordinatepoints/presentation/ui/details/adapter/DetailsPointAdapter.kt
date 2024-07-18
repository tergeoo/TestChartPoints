package com.tergeo.testcoordinatepoints.presentation.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tergeo.domain.entity.model.Point
import com.tergeo.testcoordinatepoints.databinding.ItemPointBinding
import com.tergeo.testcoordinatepoints.presentation.ui.views.chart.ChartPoint

class DetailsPointAdapter(): RecyclerView.Adapter<DetailsPointViewHolder>() {

    private val list = arrayListOf<ChartPoint>()

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsPointViewHolder {
        val inflater = if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.context)
            layoutInflater
        } else {
            layoutInflater
        }

        return DetailsPointViewHolder(ItemPointBinding.inflate(inflater!!, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DetailsPointViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun submitList(newList: List<ChartPoint>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}

class DetailsPointViewHolder(private val binding: ItemPointBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(data: ChartPoint) {
        binding.pointXValueText.text = data.x.toString()
        binding.pointYValueText.text = data.y.toString()
    }
}


