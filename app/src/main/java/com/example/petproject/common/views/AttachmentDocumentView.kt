package com.example.petproject.common.views

import android.content.Context

import android.util.AttributeSet
import com.example.petproject.R
import com.example.petproject.common.Utils
import com.example.petproject.data.model.rest.Attachment
import com.example.petproject.moxymvp.ABaseView
import kotlinx.android.synthetic.main.item_question_response_document.view.*

class AttachmentDocumentView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ABaseView(R.layout.item_question_response_document, context, attrs, defStyleAttr) {

    private var onClickListener: OnClickListener? = null

    fun bind(attachment: Attachment) {
//        tvTitle.text = /*Uri.parse(attachment.original).lastPathSegment ?:*/ "Прикрепленный файл"
        tvTitle.text = attachment.filename ?: "Прикрепленный файл"
        llRoot.setOnClickListener {
            onClickListener?.onClick(this)
            Utils.openUrlInBrowser(it.context, attachment.original)
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        onClickListener = l
    }
}