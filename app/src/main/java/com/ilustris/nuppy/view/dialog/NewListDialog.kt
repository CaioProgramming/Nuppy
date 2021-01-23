package com.ilustris.nuppy.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ilustris.nuppy.R
import com.ilustris.nuppy.databinding.NewListLayoutBinding
import com.vanniktech.emoji.EmojiPopup

class NewListDialog: BottomSheetDialogFragment() {
    var onSaveItem: ((String, String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.new_list_layout,container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NewListLayoutBinding.bind(view).run {
            emojiEditText.forceSingleEmoji()
            emojiEditText.onFocusChangeListener = object : View.OnFocusChangeListener {
                override fun onFocusChange(view: View, focused: Boolean) {
                    if (focused) {
                        EmojiPopup.Builder.fromRootView(view).build(emojiEditText).toggle()
                    }
                }

            }
            saveItemButton.setOnClickListener {
                onSaveItem?.invoke(this.itemName.text.toString(), this.emojiEditText.text.toString())
                dismiss()
            }
        }
    }

    companion object {
        fun newInstance(onSaveItem: (String, String) -> Unit): NewListDialog {
            return NewListDialog().apply {
                this.onSaveItem = onSaveItem
            }
        }
    }

}