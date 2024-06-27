package com.example.zenfirelite.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.datamodels.RadioButtonItem

class AdapterForRadioButtonItem(private val itemList: List<RadioButtonItem> ,
                                private val isRadioMode: Boolean = true) :
    RecyclerView.Adapter<AdapterForRadioButtonItem.ViewHolder>() {

    private var selectedItemPosition: Int = RecyclerView.NO_POSITION
    private val selectedItems = mutableSetOf<Int>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val radioButton: RadioButton? = if (isRadioMode) itemView.findViewById(R.id.radioButton) else null
        val checkBox: CheckBox? = if (!isRadioMode) itemView.findViewById(R.id.checkBox) else null
        val itemName: TextView = itemView.findViewById(R.id.itemName)

        init {
            if (isRadioMode) {
                radioButton?.visibility = View.VISIBLE
                checkBox?.visibility = View.GONE
            } else {
                radioButton?.visibility = View.GONE
                checkBox?.visibility = View.VISIBLE
            }

            radioButton?.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                updateSelection(adapterPosition)
            }

            checkBox?.setOnCheckedChangeListener { _, isChecked ->
                if (adapterPosition == RecyclerView.NO_POSITION) return@setOnCheckedChangeListener
                itemList[adapterPosition].isSelected = isChecked
                if (isChecked) {
                    selectedItems.add(adapterPosition)
                } else {
                    selectedItems.remove(adapterPosition)
                }
            }

            itemView.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener

                if (isRadioMode) {
                    updateSelection(adapterPosition)
                } else {
                    checkBox?.let {
                        it.isChecked = !it.isChecked
                        itemList[adapterPosition].isSelected = it.isChecked
                        if (it.isChecked) {
                            selectedItems.add(adapterPosition)
                        } else {
                            selectedItems.remove(adapterPosition)
                        }
                    }
                }
            }
        }
    }

    private fun updateSelection(position: Int) {
        if (isRadioMode) {
            if (selectedItemPosition != position) {
                itemList.getOrNull(selectedItemPosition)?.isSelected = false
                notifyItemChanged(selectedItemPosition)

                itemList[position].isSelected = true
                selectedItemPosition = position
                notifyItemChanged(selectedItemPosition)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.radiobutton_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemName.text = item.itemName
        if (isRadioMode) {
            holder.radioButton?.isChecked = item.isSelected
        } else {
            holder.checkBox?.isChecked = item.isSelected
        }

    }

    override fun getItemCount(): Int = itemList.size

    fun getSelectedItems(): List<RadioButtonItem> {
        return if (isRadioMode) {
            listOf(itemList.getOrNull(selectedItemPosition)).filterNotNull()
        } else {
            selectedItems.mapNotNull { itemList.getOrNull(it) }
        }
    }
}
