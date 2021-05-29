package com.rsschool.android2021

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.lang.RuntimeException

class FirstFragment : Fragment() {

    interface ClickButton {
        fun onFirstFragmentButtonClick(min: Int?, max: Int?)
    }

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minEditText: TextView? = null
    private var maxEditText: TextView? = null
    private var listener: ClickButton? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(context is ClickButton){
            listener = context as ClickButton
        } else {
            throw RuntimeException(context.toString()
                    + " must implement ItemClickEventListener")
        }
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        minEditText = view.findViewById(R.id.min_value)
        maxEditText = view.findViewById(R.id.max_value)

        generateButton?.setOnClickListener {
            listener?.onFirstFragmentButtonClick(minEditText?.text.toString().toIntOrNull(),maxEditText?.text.toString().toIntOrNull())
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}