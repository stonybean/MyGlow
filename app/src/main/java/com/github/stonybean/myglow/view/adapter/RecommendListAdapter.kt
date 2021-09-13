package com.github.stonybean.myglow.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.stonybean.myglow.R
import com.github.stonybean.myglow.databinding.ItemRecommendBinding
import com.github.stonybean.myglow.model.Recommend
import com.squareup.picasso.Picasso

/**
 * Created by Joo on 2021/09/10
 */
class RecommendListAdapter (private val recommendList: ArrayList<Recommend>): RecyclerView.Adapter<RecommendListAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemRecommendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // 추천 아이템 표시
        @SuppressLint("SetTextI18n")
        fun onBind(data: Recommend) {
            binding.apply {
                Picasso.get()
                    .load(data.imageUrl)
                    .placeholder(R.drawable.ic_no_image)
                    .into(ivThumbnail)

                tvProductTitle.text = data.productTitle
                tvReview.text = "${data.ratingAvg} ${root.context.getString(R.string.text_review, data.reviewCount)}"

                clRecommend.setOnClickListener {
                    val bundle = bundleOf("recommend" to data)
                    root.findNavController().navigate(R.id.navigation_detail, bundle)
                }
            }
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