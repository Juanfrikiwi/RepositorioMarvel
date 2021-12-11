package com.example.marvelcharacters.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.marvelcharacters.R
import com.example.marvelcharacters.databinding.DialogGenericAlertBinding

open class GenericDialog : DialogFragment() {
    companion object {
        fun open(
            onAccept: (() -> Unit)? = {},
        ): GenericDialog {
            return GenericDialog().apply {
                onAccept?.let { onDialogAccept = it }
            }
        }
    }

    private lateinit var binding: DialogGenericAlertBinding

    private var onDialogAccept: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogGenericAlertBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListeners()
        dialog!!.window!!.setLayout(100,100)

    }

    override fun onResume() {
        super.onResume()
        if (showsDialog){
            dialog!!.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
        }
    }

    protected open fun initView() {
        binding.run {
            tvName.text = getString(R.string.check_delete)
        }
    }

    private fun initListeners() {
        binding.run {
            btnAccept.setOnClickListener {
                onDialogAccept()
                dismiss()
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
        }
    }
}