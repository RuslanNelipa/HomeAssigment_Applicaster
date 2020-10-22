package com.nelipa.homeassigment.applicaster.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.nelipa.homeassigment.applicaster.ext.autoNotifyAll
import kotlin.reflect.KClass

/**
 * @param itemLayouts is the map of list item data object and layout res id (which serves as type)
 * @param itemId is the data binding item id. Should be the same ID for all types of layouts
 * */
class MultiTypeListAdapter(
    private val itemLayouts: Map<KClass<*>, Int>,
    private val itemId: Int
) : RecyclerView.Adapter<ListHolder>() {

    private val items = mutableListOf<Any>()
    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ListHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return ListHolder(
            DataBindingUtil.inflate(layoutInflater!!, type, parent, false)
        )
    }

    override fun getItemViewType(position: Int): Int =
        items[position].let {
            itemLayouts[it::class] ?: error("Cannot find layout for ${it::class}")
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.binding.setVariable(itemId, items[position])
    }

    fun setItems(items: List<Any>) {
        autoNotifyAll(this.items, items)
        this.items.clear()
        this.items.addAll(items)
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }
}

class ListHolder(
    val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root)