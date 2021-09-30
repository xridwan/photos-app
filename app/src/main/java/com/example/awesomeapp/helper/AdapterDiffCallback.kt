package com.example.awesomeapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.awesomeapp.model.Photo

class AdapterDiffCallback(
    private val mOldDataList: List<Photo>,
    private val mNewDataList: List<Photo>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = mOldDataList.size

    override fun getNewListSize(): Int = mNewDataList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        mOldDataList[oldItemPosition].id == mNewDataList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldDataList[oldItemPosition]
        val newEmployee = mNewDataList[newItemPosition]
        return oldEmployee.photographer == newEmployee.photographer
    }
}