package com.github.stonybean.myglow.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.stonybean.myglow.R
import com.github.stonybean.myglow.databinding.ItemNormalBinding
import com.github.stonybean.myglow.databinding.ItemRecommendBinding
import com.github.stonybean.myglow.model.Product
import com.github.stonybean.myglow.model.Recommend
import com.squareup.picasso.Picasso

/**
 * Created by Joo on 2021/09/10
 */
class RecommendListAdapater(private val recommendList: ArrayList<Recommend>): RecyclerView.Adapter<RecommendListAdapater.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemRecommendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun onBind(data: Recommend) {
            Picasso.get()
                .load(data.imageUrl)
                .placeholder(R.drawable.ic_no_image)
                .resize(100, 100)
                .into(binding.ivThumbnail)

            binding.tvProductTitle.text = data.productTitle
            binding.tvReview.text = "${data.ratingAvg} 리뷰(${data.reviewCount})"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecommendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = recommendList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(recommendList[position])
    }
}