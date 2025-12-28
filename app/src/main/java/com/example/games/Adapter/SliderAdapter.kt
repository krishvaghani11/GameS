package com.example.games.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.example.games.Model.SliderModel
import com.example.games.databinding.SliderItemContainerBinding

class SliderAdapter(
    private var sliderItems: List<SliderModel>,
    private val viewPager2: ViewPager2
) : RecyclerView.Adapter<SliderAdapter.SliderViewholder>() {

    private lateinit var context: Context
    private val runnable = Runnable {
        sliderItems = sliderItems
        notifyDataSetChanged()
    }

    class SliderViewholder(private val binding: SliderItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setImage(sliderItems: SliderModel, context: Context) {
            Glide.with(context)
                .load(sliderItems.url)
                .transform(CenterInside())
                .into(binding.imageSlider)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderViewholder {
        context = parent.context
        val binding =
            SliderItemContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderViewholder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewholder, position: Int) {
        holder.setImage(sliderItems[position], context)
        if (position == sliderItems.lastIndex - 1) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int = sliderItems.size
}
