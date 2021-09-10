package com.github.stonybean.myglow.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.stonybean.myglow.R
import com.github.stonybean.myglow.databinding.ItemNormalBinding
import com.github.stonybean.myglow.model.Product
import com.squareup.picasso.Picasso
import com.github.stonybean.myglow.model.Recommend


/**
 * Created by Joo on 2021/09/10
 */
class ProductListAdapter(private val context: Context) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    var productList: ArrayList<Product> = ArrayList()
    var recommendList: ArrayList<Recommend> = ArrayList()

    inner class ViewHolder(private val binding: ItemNormalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
        fun onBind(data: Product) {
            Picasso.get()
                .load(data.imageUrl)
                .placeholder(R.drawable.ic_no_image)
                .resize(100, 100)
                .into(binding.ivThumbnail)

            binding.tvSequence.text = (adapterPosition + 1).toString()
            binding.tvCompanyName.text = data.brand.brandTitle
            binding.tvProductTitle.text = data.productTitle
            binding.tvReview.text = "${data.ratingAvg} 리뷰(${data.reviewCount})"

            binding.clProduct.setOnClickListener {
                val bundle = bundleOf("product" to data)
                binding.root.findNavController().navigate(R.id.navigation_detail, bundle)
            }

            if (adapterPosition == 9 || adapterPosition == 19 || adapterPosition == 29) {
                when (adapterPosition) {
                    9 -> binding.tvRecommendSeq.text = "추천 1"
                    19 -> binding.tvRecommendSeq.text = "추천 2"
                    29 -> binding.tvRecommendSeq.text = "추천 3"
                }

                binding.rlRecommend.visibility = View.VISIBLE
                val recommendListAdapter = RecommendListAdapter(recommendList)
                binding.rvRecommend.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.rvRecommend.adapter = recommendListAdapter
                binding.rvRecommend.setHasFixedSize(true)
                recommendListAdapter.notifyDataSetChanged()
            } else {
                binding.rlRecommend.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNormalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(productList[position])
    }
}