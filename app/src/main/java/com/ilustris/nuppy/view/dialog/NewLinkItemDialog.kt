package com.ilustris.nuppy.view.dialog

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ilustris.animations.fadeIn
import com.ilustris.animations.fadeOut
import com.ilustris.animations.popIn
import com.ilustris.nuppy.R
import com.ilustris.nuppy.bean.ListType
import com.ilustris.nuppy.databinding.GridItemLayoutBinding
import com.ilustris.nuppy.databinding.LinkPreviewDialogBinding
import com.mega4tech.linkpreview.GetLinkPreviewListener
import com.mega4tech.linkpreview.LinkPreview
import com.mega4tech.linkpreview.LinkUtil
import com.silent.ilustriscore.core.utilities.gone


class NewLinkItemDialog : BottomSheetDialogFragment(), GetLinkPreviewListener {

    var onSaveItem: ((String) -> Unit)? = null
    var listType = ListType.GRID_LIST
    lateinit var linkPreviewDialogBinding: LinkPreviewDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.link_preview_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linkPreviewDialogBinding =  LinkPreviewDialogBinding.bind(view).apply{
            if (listType == ListType.GRID_LIST) {
                linkEditText.addTextChangedListener {
                    validateLink(it.toString(), view.context)
                }
            } else {
                saveItemButton.setOnClickListener {
                    onSaveItem?.invoke(linkPreviewDialogBinding.linkEditText.text.toString())
                    dismiss()
                }
                linkEditText.hint = "Insira o nome de um item"
                linkPreviewCard.linkCard.gone()
                saveItemButton.isEnabled = true
            }


            closeButton.apply {
                setOnClickListener {
                    dismiss()
                }
            }
        }
    }

    private fun validateLink(link: String, context: Context) {
        if (Patterns.WEB_URL.matcher(link).matches()) {
            linkPreviewDialogBinding.closeButton.isEnabled = false
            LinkUtil.getInstance().getLinkPreview(context, link,this@NewLinkItemDialog)
        } else {
            Toast.makeText(context, "Insira um link válido", Toast.LENGTH_SHORT).show()
        }

    }

    private fun GridItemLayoutBinding.setupLink(preview: LinkPreview){
        requireActivity().runOnUiThread {
            enableSaveButton(true)
            Glide.with(requireContext()).load(preview.imageFile).into(this.linkImage)
            this.preview = preview
            this.loadingLink.fadeOut()
            if (preview.title == null || preview.link == null) {
                this.loadingLink.fadeIn()
            } else {
                this.loadingLink.fadeOut()
                this.linkImage.fadeIn()
                this.itemTitle.popIn()
            }
        }
    }


    override fun onSuccess(linkPreview: LinkPreview?) {
        linkPreview?.let {
            linkPreviewDialogBinding.linkPreviewCard.setupLink(it)
        }
    }

    private fun enableSaveButton(canSave: Boolean) {
        linkPreviewDialogBinding.saveItemButton.isEnabled = true
        linkPreviewDialogBinding.closeButton.isEnabled = true
        if (canSave) {
            linkPreviewDialogBinding.saveItemButton.setOnClickListener {
                onSaveItem?.invoke(linkPreviewDialogBinding.linkEditText.text.toString())
                dismiss()
            }
        } else {
            linkPreviewDialogBinding.saveItemButton.text = "Cancelar"
            dismiss()
        }
    }

    override fun onFailed(e: java.lang.Exception?) {
        e?.printStackTrace()
        if (!this.isDetached) {
            requireActivity().runOnUiThread {
                linkPreviewDialogBinding.linkEditText.error = "Link inválido"
                Toast.makeText(context, "Erro ao obter link", Toast.LENGTH_SHORT).show()
                enableSaveButton(false)
            }
        }
    }

    companion object {

        fun newInstance(listType: ListType,onSaveItem: ((String) -> Unit)): NewLinkItemDialog {
            return  NewLinkItemDialog().apply {
                this.onSaveItem = onSaveItem
                this.listType = listType
                isCancelable = false
            }
        }

    }


}