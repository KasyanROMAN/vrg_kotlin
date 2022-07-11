package com.example.vrg.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.vrg.R
import com.example.vrg.commons.adapter.ViewType
import com.example.vrg.commons.adapter.ViewTypeDelegateAdapter
import com.example.vrg.commons.inflate

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup) = TurnViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class TurnViewHolder(parent: ViewGroup): RecyclerView.ViewHolder (
        parent.inflate(R.layout.new_item_loading))

}