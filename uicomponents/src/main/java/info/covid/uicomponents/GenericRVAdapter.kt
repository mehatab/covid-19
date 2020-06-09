/*
 * MIT License
 *
 * Copyright (c) 2020 Mehatab Shaikh
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package info.covid.uicomponents

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import info.covid.data.DiffItem

class GenericRVAdapter(private val layoutId: Int = 0) : Adapter<RecyclerView.ViewHolder>(),
    OnItemClickListener {

    private lateinit var inflater: LayoutInflater
    lateinit var getLayout: (Int) -> Int
    lateinit var onItemClickListener: (View, Int) -> Unit
    private var maxCount = 0

    private var itemList: List<DiffItem> by autoNotifyDelegate(
        adapter = this,
        initialValue = emptyList()
    )


    fun setList(list: List<DiffItem>, count: Int = 0) {
        itemList = list.toMutableList()
        maxCount = count
    }

    fun <T> getItem(position: Int) = itemList[position] as T

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        if (::inflater.isInitialized.not())
            inflater = LayoutInflater.from(parent.context)

        return ItemViewHolder(
            DataBindingUtil.inflate(
                inflater,
                getLayoutId(position),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = itemList.size

    //Instead of view type returning position so we can get from parent to support multiple view type
    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ItemViewHolder) {
            holder.binding.setVariable(BR.viewModel, itemList[position])
            holder.binding.setVariable(BR.adapterPosition, position + 1)
            holder.binding.setVariable(BR.listener, this)
            holder.binding.setVariable(BR.maxCount, maxCount)
            holder.binding.setVariable(BR.total, itemCount)
        }
    }

    class ItemViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    private fun getLayoutId(position: Int): Int {
        return if (::getLayout.isInitialized) getLayout(position) else layoutId
    }

    override fun onItemClick(v: View, position: Int) {
        if (::onItemClickListener.isInitialized) onItemClickListener(v, position)
    }

}