package com.github.stonybean.myglow.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.stonybean.myglow.databinding.FragmentMainBinding
import com.github.stonybean.myglow.model.Product
import com.github.stonybean.myglow.repository.GlowRepository
import com.github.stonybean.myglow.view.adapter.ProductListAdapter
import com.github.stonybean.myglow.viewmodel.GlowViewModel
import com.github.stonybean.myglow.viewmodel.GlowViewModelFactory

class MainFragment : Fragment() {

    private lateinit var viewModel: GlowViewModel
    private lateinit var viewModelFactory: GlowViewModelFactory

    private var _binding: FragmentMainBinding? = null

    // adapter 관련
    private lateinit var productListAdapter: ProductListAdapter
    private var productList: ArrayList<Product> = ArrayList()
    private var currentPage = 1

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        viewModelFactory = GlowViewModelFactory(GlowRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(GlowViewModel::class.java)

        viewModel.productData.observe(viewLifecycleOwner, Observer {
            updateProductList(it.products)
        })

//        viewModel.recommendData.observe(viewLifecycleOwner, Observer {
//            recommendList.add(it.recommend1)
//            recommendList.add(it.recommend2)
//            recommendList.add(it.recommend3)
//        })

        viewModel.getProducts(currentPage.toString())
//        viewModel.getRecommend()

        binding.rlProgress.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {
        productListAdapter = ProductListAdapter(requireContext())
        binding.rlProduct.layoutManager = LinearLayoutManager(requireContext())
        binding.rlProduct.adapter = productListAdapter
        binding.rlProduct.setHasFixedSize(true)
        binding.rlProduct.addOnScrollListener(ScrollListener())
    }

    private fun updateProductList(productList: List<Product>) {
        binding.rlProgress.visibility = View.GONE
        this.productList.addAll(productList)

        productListAdapter.productList = this.productList
        productListAdapter.notifyDataSetChanged()
    }

    // 리스트 마지막 스크롤 감지
    private fun isLastPosition(): Boolean {
        val layoutManager = binding.rlProduct.layoutManager as LinearLayoutManager
        val pos = layoutManager.findLastCompletelyVisibleItemPosition()
        val numItems = productListAdapter.itemCount
        return (pos >= numItems - 1)
    }

    // 스크롤 리스너
    inner class ScrollListener: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (dy > 0) {
                if (isLastPosition()) {
                    if (currentPage == 3) {
                        return
                    }

                    currentPage += 1    // 다음 페이지 검색
                    binding.rlProgress.visibility = View.VISIBLE
                    viewModel.getProducts(currentPage.toString())
                }
            }
        }
    }
}