package com.github.stonybean.myglow.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.stonybean.myglow.R
import com.github.stonybean.myglow.databinding.ItemNormalBinding
import com.github.stonybean.myglow.model.Product
import com.squareup.picasso.Picasso
import androidx.recyclerview.widget.LinearLayoutManager
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

        @SuppressLint("SetTextI18n")
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


//            if (adapterPosition % 10 == 0) {
//                // 추천 상품 리스트 (가로 스크롤)
//                val recommendListAdapter = RecommendListAdapater(recommendList)
//                binding.rlRecommend.layoutManager =
//                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//                binding.rlRecommend.adapter = recommendListAdapter
//                recommendListAdapter.notifyDataSetChanged()
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNormalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return productList[position].idBrand
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(productList[position])
    }

    fun addItem(productList: ArrayList<Product>) {
        this.productList.addAll(productList)
        notifyDataSetChanged()
    }
}