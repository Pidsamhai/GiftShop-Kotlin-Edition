package com.mengxyz.giftshopkolinedition.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mengxyz.giftshopkolinedition.R
import com.mengxyz.giftshopkolinedition.db.adapter.ProductRecycleItems
import com.mengxyz.giftshopkolinedition.extentions.toFutureWeatherItems
import com.mengxyz.giftshopkolinedition.ui.scope.FragmentScope
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : FragmentScope() {
    private lateinit var viewModel: HomeFragmentViewModel
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.appbar_title).text = " Home "
        refreshing_layout.isRefreshing = true
        viewModel = ViewModelProviders.of(this).get(HomeFragmentViewModel::class.java)
        viewModel.getAllProduct().observe(this, Observer {
            if (it == null)
                return@Observer
            Toast.makeText(this@HomeFragment.context, "Loading complete", Toast.LENGTH_SHORT).show()
            refreshing_layout.isRefreshing = false
            initRecycleView(it.toFutureWeatherItems())
        })

        refreshing_layout.setOnRefreshListener {
            refreshing_layout.isRefreshing = true
            viewModel.refreshAllProduct()
        }
    }

    private fun initRecycleView(items: List<ProductRecycleItems>) = launch(Dispatchers.Main) {
        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(items)
        }
        product_recycleView.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.context)
            adapter = groupAdapter
        }
        groupAdapter.notifyDataSetChanged()
        Log.e(TAG, "initRecycleView:Error Called ")
    }

}