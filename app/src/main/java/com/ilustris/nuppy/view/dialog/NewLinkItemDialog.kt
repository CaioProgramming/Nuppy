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
import com.ilustris.nuppy.R
import com.ilustris.nuppy.databinding.LinkItemsLayoutBinding
import com.ilustris.nuppy.databinding.LinkPreviewDialogBinding
import com.mega4tech.linkpreview.GetLinkPreviewListener
import com.mega4tech.linkpreview.LinkPreview
import com.mega4tech.linkpreview.LinkUtil
import com.silent.ilustriscore.core.utilities.fadeOut


class NewLinkItemDialog : BottomSheetDialogFragment(),GetLinkPreviewListener {

    var onSaveItem: ((String, String) -> Unit)? = null
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
            linkEditText.addTextChangedListener {
                validateLink(it.toString(), view.context)
            }
            saveItemButton.setOnClickListener {
                onSaveItem?.invoke(linkEditText.text.toString(), itemName.text.toString())
            }
      }
    }

    private fun validateLink(link: String, context: Context) {
        if (Patterns.WEB_URL.matcher(link).matches()) {
            LinkUtil.getInstance().getLinkPreview(context, link,this@NewLinkItemDialog)
        }

    }

    private fun LinkItemsLayoutBinding.setupLink(preview: LinkPreview){
        requireActivity().runOnUiThread {
            Glide.with(requireContext()).load(preview.imageFile).into(this.linkImage)
            Toast.makeText(context, "Link sucessed get ${preview.title} \n ${preview.siteName} \n ${preview.link}", Toast.LENGTH_SHORT).show()
            this.preview = preview
            this.loadingLink.fadeOut()
            if (preview.title == null || preview.link == null) {
                this.linkCard.fadeOut()
            }
        }
    }


    override fun onSuccess(linkPreview: LinkPreview?) {
        linkPreview?.let {
            linkPreviewDialogBinding.preview.setupLink(it)
        }
    }

    override fun onFailed(p0: java.lang.Exception?) {
        requireActivity().runOnUiThread {
            linkPreviewDialogBinding.linkEditText.error = "Link inválido"
        }
    }

    companion object {

        fun newInstance(onSaveItem: ((String, String) -> Unit)): NewLinkItemDialog {
            return  NewLinkItemDialog().apply {
                this.onSaveItem = onSaveItem
            }
        }

    }


}