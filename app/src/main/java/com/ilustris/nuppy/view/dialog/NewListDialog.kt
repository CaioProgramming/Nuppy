package com.ilustris.nuppy.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ilustris.nuppy.R
import com.ilustris.nuppy.bean.ListType
import com.ilustris.nuppy.bean.WeddingList
import com.ilustris.nuppy.databinding.NewListLayoutBinding


class NewListDialog: BottomSheetDialogFragment() {
    var onSaveItem: ((WeddingList) -> Unit)? = null

    var listType = ListType.GRID_LIST
   lateinit var newListLayoutBinding : NewListLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.new_list_layout,container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newListLayoutBinding = NewListLayoutBinding.bind(view).apply {
            gridListCard.setOnClickListener {
                updateListType(ListType.GRID_LIST)
            }
            textListCard.setOnClickListener {
                updateListType(ListType.TEXT_LIST)
            }
            saveItemButton.setOnClickListener {
                onSaveItem?.invoke(WeddingList(
                    title = this.itemName.text.toString(),
                    listType = listType))
                dismiss()
            }
        }
    }

    private fun updateListType(listType: ListType) {
        this.listType = listType

        if (listType == ListType.GRID_LIST) {
            newListLayoutBinding.gridListCard.apply {
                setCardBackgroundColor(ContextCompat.getColor(requireContext(),R.color.material_red400))
                cardElevation = 8f
            }
            newListLayoutBinding.textListCard.apply {
                setCardBackgroundColor(ContextCompat.getColor(requireContext(),R.color.material_grey400))
                cardElevation = 0f
            }
        } else {
            newListLayoutBinding.textListCard.apply {
                setCardBackgroundColor(ContextCompat.getColor(requireContext(),R.color.material_red400))
                cardElevation = 8f
            }
            newListLayoutBinding.gridListCard.apply {
                setCardBackgroundColor(ContextCompat.getColor(requireContext(),R.color.material_grey400))
                cardElevation = 0f
            }
        }
    }



    companion object {
        fun newInstance(onSaveItem: (WeddingList) -> Unit): NewListDialog {
            return NewListDialog().apply {
                this.onSaveItem = onSaveItem
            }
        }
    }

}