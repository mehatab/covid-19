package info.covid.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import info.covid.BR

class RVAdapter<out T : DiffItem>(
    private val layoutID: Int,
    private val listener: OnItemClickListener? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var inflater: LayoutInflater
    private var maxCount = 0
    private var itemList: List<DiffItem> by autoNotifyDelegate(
        adapter = this,
        initialValue = emptyList()
    )


    fun setList(list: List<DiffItem>, count: Int = 0) {
        itemList = list.toMutableList()
        maxCount = count
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)
        return RVAdapterItemViewHolder(
            DataBindingUtil.inflate(inflater, layoutID, parent, false)
        )
    }

    override fun getItemCount() = itemList.size

    fun getItem(position: Int) = itemList[position]

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RVAdapterItemViewHolder) {
            holder.binding.setVariable(BR.viewModel, itemList[position])
            holder.binding.setVariable(BR.adapterPosition, position + 1)
            holder.binding.setVariable(BR.listener, listener)
            holder.binding.setVariable(BR.maxCount, maxCount)
            holder.binding.setVariable(BR.total, itemCount)
        }
    }

    class RVAdapterItemViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}