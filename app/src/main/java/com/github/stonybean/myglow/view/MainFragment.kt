package com.github.stonybean.myglow.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.stonybean.myglow.databinding.FragmentMainBinding
import com.github.stonybean.myglow.model.Product
import com.github.stonybean.myglow.model.Recommend
import com.github.stonybean.myglow.model.Recommends
import com.github.stonybean.myglow.view.adapter.ProductListAdapter
import com.github.stonybean.myglow.viewmodel.GlowViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import com.github.stonybean.myglow.R
import javax.inject.Inject

/**
 * Created by Joo on 2021/09/10
 */
@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    val viewModel: GlowViewModel by viewModels()

    @Inject
    lateinit var productListAdapter: ProductListAdapter     // adapter
    private var itemList: ArrayList<Product> = ArrayList()  // list for add adapter
    private var currentPage = 1

    // 추천 제품 목록 관리 HashMap
    private val recommendHashMap: HashMap<Int, ArrayList<Recommend>> = HashMap()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.tbMain.title = getString(R.string.title_main)

        // 리스트 어댑터 초기화
        initAdapter()

        // LiveData Observer
        setObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (currentPage < 3) {     // 3 페이지 이후 요청 안함
            // 제품 목록 요청
            viewModel.getProducts(currentPage)

            if (recommendHashMap.isEmpty()) {
                // 추천 제품 목록 요청
                viewModel.getRecommend()
            }

            // 프로그레스바 표시
            binding.rlProgress.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Set Adapter
    private fun initAdapter() {
        productListAdapter = ProductListAdapter()

        binding.rvProduct.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productListAdapter
            setHasFixedSize(true)
            addOnScrollListener(ScrollListener())
        }
    }

    // LiveData Observer
    private fun setObservers() {
        viewModel.apply {
            // 제품 목록
            productData.observe(viewLifecycleOwner, Observer {
                updateProductList(it.products)
            })

            // 추천 제품 목록
            recommendData.observe(viewLifecycleOwner, Observer {
                updateRecommendList(it)
            })
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateProductList(list: List<Product>) {
        binding.rlProgress.visibility = View.GONE

        if (currentPage == 1) {
            itemList.clear()
        }

        if (itemList.size < 60) {   // 3 페이지까지의 리스트만 표시하기 위해
            itemList.addAll(list)
        }

        // 제품 목록
        productListAdapter.apply {
            productList = itemList
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateRecommendList(recommends: Recommends) {
        recommendHashMap[0] = recommends.recommend1 as ArrayList<Recommend>
        recommendHashMap[1] = recommends.recommend2 as ArrayList<Recommend>
        recommendHashMap[2] = recommends.recommend3 as ArrayList<Recommend>

        // 추천 제품 목록
        productListAdapter.apply {
            recommendList = recommendHashMap
            notifyDataSetChanged()
        }
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
                    viewModel.getProducts(currentPage)
                    binding.rlProgress.visibility = View.VISIBLE
                }
            }
        }
    }
}