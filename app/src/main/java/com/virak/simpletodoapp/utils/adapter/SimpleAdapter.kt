package com.virak.simpletodoapp.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class SimpleAdapter<T>(
    private var items: List<T>,
    private val layoutId: Int,
    private val bindVariableId: Int, // BR.item
    private val onItemClick: ((T, Int) -> Unit)? = null
) : RecyclerView.Adapter<SimpleAdapter.GenericViewHolder>() {

    class GenericViewHolder(
        val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = androidx.databinding.DataBindingUtil.inflate<ViewDataBinding>(
            inflater, layoutId, parent, false
        )
        return GenericViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        val item = items[position]
        holder.binding.setVariable(bindVariableId, item)
        holder.binding.executePendingBindings()
        holder.binding.root.setOnClickListener {
            onItemClick?.invoke(item, position)
        }
    }

    override fun getItemCount(): Int = items.size
}