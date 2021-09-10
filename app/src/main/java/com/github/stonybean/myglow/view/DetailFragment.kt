package com.github.stonybean.myglow.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.whenResumed
import com.github.stonybean.myglow.R
import com.github.stonybean.myglow.databinding.FragmentDetailBinding
import com.github.stonybean.myglow.model.Product
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


        val product = arguments?.getSerializable("product") as Product

        Picasso.get()
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_no_image)
            .into(binding.ivProductImage)

        binding.tvProductTitle.text = product.productTitle
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}