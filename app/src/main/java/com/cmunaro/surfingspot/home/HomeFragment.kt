package com.cmunaro.surfingspot.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmunaro.surfingspot.R
import com.cmunaro.surfingspot.base.BaseFragment
import com.cmunaro.surfingspot.base.Intent
import com.cmunaro.surfingspot.databinding.HomeFragmentBinding
import com.cmunaro.surfingspot.home.HomeFragment.*
import com.cmunaro.surfingspot.home.HomeFragment.HomeIntent.*
import com.cmunaro.surfingspot.home.recycleradapter.MeteoRecyclerAdapter
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
class HomeFragment : BaseFragment<HomeIntent, HomeViewModel, HomeFragmentBinding>() {
    override val layoutResource = R.layout.home_fragment
    override val viewModelClass = HomeViewModel::class.java

    override fun onResume() {
        super.onResume()
        deliverIntent(StartMeteoObserving)
    }

    override fun onPause() {
        super.onPause()
        deliverIntent(StopMeteoObserving)
    }

    override fun setupUI() {
        setupRecycler()
    }

    private fun setupRecycler() {
        binding.citiesRecycler.apply {
            setHasFixedSize(true)
            adapter = MeteoRecyclerAdapter()
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val margin = resources.getDimensionPixelSize(R.dimen.recycler_margin)
                    outRect.top = margin
                    outRect.bottom = margin
                    outRect.left = margin
                    outRect.right = margin
                }
            })
        }
    }

    sealed class HomeIntent : Intent {
        object StartMeteoObserving : HomeIntent()
        object StopMeteoObserving : HomeIntent()
    }
}