package com.github.stonybean.myglow.view.adapter

import android.annotation.SuppressLint
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
import javax.inject.Inject

/**
 * Created by Joo on 2021/09/10
 */
class ProductListAdapter @Inject constructor() :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    var productList: ArrayList<Product> = ArrayList()
    var recommendList: HashMap<Int, ArrayList<Recommend>> = HashMap()

    inner class ViewHolder(private val binding: ItemNormalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
        fun onBind(data: Product) {
            binding.apply {
                Picasso.get()
                    .load(data.imageUrl)
                    .placeholder(R.drawable.ic_no_image)
                    .resize(100, 100)
                    .into(ivThumbnail)

                tvSequence.text = (adapterPosition + 1).toString()
                tvCompanyName.text = data.brand.brandTitle
                tvProductTitle.text = data.productTitle
                tvReview.text = "${data.ratingAvg} ${root.context.getString(R.string.text_review, data.reviewCount)}"

                clProduct.setOnClickListener {
                    val bundle = bundleOf("product" to data)
                    root.findNavController().navigate(R.id.navigation_detail, bundle)
                }

                if (adapterPosition == 9 || adapterPosition == 19 || adapterPosition == 29) {
                    var recommendListAdapter: RecommendListAdapter? = null

                    when (adapterPosition) {
                        9 -> {
                            tvRecommendSeq.text = root.context.getString(R.string.title_recommend_1)
                            recommendListAdapter = RecommendListAdapter(recommendList[0] as ArrayList<Recommend>)
                        }
                        19 -> {
                            tvRecommendSeq.text = root.context.getString(R.string.title_recommend_2)
                            recommendListAdapter = RecommendListAdapter(recommendList[1] as ArrayList<Recommend>)
                        }
                        29 -> {
                            tvRecommendSeq.text = root.context.getString(R.string.title_recommend_3)
                            recommendListAdapter = RecommendListAdapter(recommendList[2] as ArrayList<Recommend>)
                        }
                    }

                    rlRecommend.visibility = View.VISIBLE


                    rvRecommend.apply {
                        layoutManager =
                            LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
                        adapter = recommendListAdapter
                        setHasFixedSize(true)
                    }

                    recommendListAdapter!!.notifyDataSetChanged()
                } else {
                    binding.rlRecommend.visibility = View.GONE
                }
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