package com.nelipa.homeassigment.applicaster.ext

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

fun <T> RecyclerView.Adapter<*>.autoNotifyAll(items: MutableList<T>, newList: List<T>) {
    val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = items[oldItemPosition]
            val new = newList[newItemPosition]
            return if (old is Id && new is Id) {
                old.id == new.id
            } else {
                false
            }
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return items[oldItemPosition] == newList[newItemPosition]
        }

        override fun getOldListSize() = items.size

        override fun getNewListSize() = newList.size
    })

    items.clear()
    items.addAll(newList)

    diff.dispatchUpdatesTo(this)
}

interface Id {
    val id: String
}