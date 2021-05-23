package com.example.petproject.moxymvp.lists

import androidx.recyclerview.widget.RecyclerView
import android.view.View


abstract class AListAdapter<D, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    internal var onDataClickListener: ((item: D) -> Unit)? = null

    private var filtered: List<D>? = null
    private val dataSet: MutableList<D> = mutableListOf()

    var isNeedShowEmptyView: Boolean = false
        get() = data().isEmpty()
        private set

    var filter: ((D) -> Boolean)? = null
        set(value) {
            field = value
            filtered = if (value != null) dataSet.filter { value(it) } else null
            notifyDataSetChanged()
        }

    override fun getItemCount() = data().size

    fun data(): List<D> = filtered ?: dataSet
    fun data(data: List<D>) {
        clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    fun add(data: List<D>) = add(data, dataSet.size)

    fun add(data: List<D>, index: Int) {
        var itemCount = 0
        data.forEach {
            if (!contains(it)) {
                dataSet.add(index + itemCount, it)
                itemCount++
            }
        }

        if (itemCount == 0)
            return

        if (filter != null) filter = filter
        else notifyItemRangeInserted(index, itemCount)
    }

    @JvmOverloads
    fun clear(notify: Boolean = false) {

        dataSet.clear()
        filtered = null

        if (notify)
            notifyDataSetChanged()
    }

    @Synchronized
    fun remove(item: D) = removeAt(getPosition(item))

    fun removeAt(position: Int, notify: Boolean = true) {
        if (position > -1) {
            dataSet.removeAt(position)
            filter = filter
            if (notify) notifyItemRemoved(position)
        }
    }

    fun addItem(item: D) {
        if (!contains(item) && dataSet.add(item))
            notifyItemInserted(dataSet.size - 1)
    }

    fun addItem(item: D, index: Int) {
        if (!contains(item)) {
            dataSet.add(index, item)
            notifyItemInserted(index)
        }
    }

    fun getItem(index: Int) = data()[index]

    fun contains(item: D) = data().contains(item)

    fun isEmpty() = data().isEmpty()
    fun isNotEmpty() = !isEmpty()

    @JvmOverloads
    open fun update(item: D, payload: String? = null) { update(item, payload, true) }

    fun update(item: List<D>) {
        for (index in item.indices)
            update(item[index], null, true)
    }

    private fun update(item: D, payload: String?, notify: Boolean): Boolean {
        val position = getPosition(item)
        if (position < 0)
            return false

        if (notify) {
            if (payload.isNullOrEmpty()) notifyItemChanged(position)
            else notifyItemChanged(position, payload)
        }

        return true
    }

    fun getPosition(item: D) = dataSet.indexOf(item)

    override fun onBindViewHolder(holder: VH, position: Int) {
        bindDefaultViewHolder(holder as DefaultViewHolder<D>, position)
    }


    private fun bindDefaultViewHolder(holder: DefaultViewHolder<D>, position: Int) {
        if (position >= dataSet.size)
            return
        val d = dataSet[position]
        holder.view?.bind(d, View.OnClickListener {
            onDataClickListener?.invoke(d)
        })
    }

    fun onDestroy() { onDataClickListener = null }

    class DefaultViewHolder<D>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var view: IListItemView<D>? = if (itemView is IListItemView<*>) itemView as IListItemView<D> else null
    }
}
