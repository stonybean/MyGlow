package com.github.stonybean.myglow.view

import android.annotation.SuppressLint
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
import com.github.stonybean.myglow.model.Recommend
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

    // 추천 제품 목록 관리 HashMap
    private val recommendList: HashMap<Int, ArrayList<Recommend>> = HashMap()

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

        // 리스트 어댑터 초기화
        initAdapter()

        // 뷰모델
        viewModelFactory = GlowViewModelFactory(GlowRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(GlowViewModel::class.java)

        // 제품 목록 LiveData
        viewModel.productData.observe(viewLifecycleOwner, Observer {
            updateProductList(it.products)
        })

        // 추천 제품 목록 LiveData
        viewModel.recommendData.observe(viewLifecycleOwner, Observer {
            recommendList[1] = it.recommend1 as ArrayList<Recommend>
            recommendList[2] = it.recommend2 as ArrayList<Recommend>
            recommendList[3] = it.recommend3 as ArrayList<Recommend>
        })

        // 제품 목록 요청
        viewModel.getProducts(currentPage.toString())
        // 추천 제품 목록 요청
        viewModel.getRecommend()

        // 프로그레스바 표시
        binding.rlProgress.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {
        productListAdapter = ProductListAdapter(requireContext())
        binding.rvProduct.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProduct.adapter = productListAdapter
        binding.rvProduct.setHasFixedSize(true)
        binding.rvProduct.addOnScrollListener(ScrollListener())
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateProductList(productList: List<Product>) {
        binding.rlProgress.visibility = View.GONE
//        this.productList.addAll(productList)

        // 추천 목록 리스트
        productListAdapter.recommendList = recommendList[currentPage] as ArrayList<Recommend>
        productListAdapter.productList = productList as ArrayList<Product>
        productListAdapter.notifyDataSetChanged()
    }

    // 리스트 마지막 스크롤 감지
    private fun isLastPosition(): Boolean {
        val layoutManager = binding.rvProduct.layoutManager as LinearLayoutManager
        val pos = layoutManager.findLastCompletelyVisibleItemPosition()
        val numItems = productListAdapter.itemCount
        return (pos >= numItems - 1)
    }

    // 스크롤 리스너
    inner class ScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (dy > 0) {
                if (isLastPosition()) {
                    if (currentPage == 3) { // API 3개 밖에 없으므로
                        return
                    }

                    currentPage += 1    // 다음 페이지 검색
                    viewModel.getProducts(currentPage.toString())
                    binding.rlProgress.visibility = View.VISIBLE
                }
            }
        }
    }
}