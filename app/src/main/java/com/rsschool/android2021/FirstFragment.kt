package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.lang.RuntimeException

class FirstFragment : Fragment() {

    interface ClickGenerateButton {
        fun onFirstFragmentButtonClick(min: Int, max: Int)
        fun onFirstFragmentExceptionClick(message: String)
    }

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minEditText: TextView? = null
    private var maxEditText: TextView? = null
    private var listener: ClickGenerateButton? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(context is ClickGenerateButton){
            listener = context as ClickGenerateButton
        } else {
            throw RuntimeException(context.toString()
                    + " must implement ClickGenerateButton")
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
            checkInputData()
        }
    }

    private fun checkInputData(){
        var message: String? = null
        var minInt: Int = 0
        var maxInt: Int = 0
        if (minEditText?.text.isNullOrEmpty()){
            message = "no min value"
        } else if (maxEditText?.text.isNullOrEmpty()){
            message = "no max value"
        } else {
            try {
                minInt = minEditText?.text.toString().toInt()
                maxInt = maxEditText?.text.toString().toInt()
            } catch (exc: Exception) {
                message = "invalid data"
            }
            if (maxInt < minInt){
                message = "the max value must be greater than the min"
            }
        }

        if (message != null){
            listener?.onFirstFragmentExceptionClick(message)
        } else {
            listener?.onFirstFragmentButtonClick(minInt, maxInt)
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