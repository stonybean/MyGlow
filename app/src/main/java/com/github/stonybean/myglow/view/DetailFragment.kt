package com.github.stonybean.myglow.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.github.stonybean.myglow.R
import com.github.stonybean.myglow.databinding.FragmentDetailBinding
import com.github.stonybean.myglow.model.Product
import com.github.stonybean.myglow.model.Recommend
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tbDetail.title = "상세 정보"
        binding.tbDetail.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        binding.tbDetail.setNavigationOnClickListener { _ ->
            binding.root.findNavController().popBackStack()
        }

        // 상세 정보 표시. 일반 목록에서 넘어온 것, 추천 목록에서 넘어온 것 분기 처리
        try {
            // 일반
            val product = arguments?.getSerializable("product") as Product

            Picasso.get()
                .load(product.imageUrl)
                .placeholder(R.drawable.ic_no_image)
                .into(binding.ivProductImage)

            binding.tvProductTitle.text = product.productTitle
        } catch (e: Exception) {
            // 추천
            val recommend = arguments?.getSerializable("recommend") as Recommend

            Picasso.get()
                .load(recommend.imageUrl)
                .placeholder(R.drawable.ic_no_image)
                .into(binding.ivProductImage)

            binding.tvProductTitle.text = recommend.productTitle
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}