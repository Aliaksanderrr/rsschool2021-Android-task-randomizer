package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.lang.RuntimeException
import kotlin.random.Random

class SecondFragment : Fragment(){

    interface ClickBackButton {
        fun onSecondFragmentButtonClick()
        fun previousGenerated(number: Int)
    }

    private var backButton: Button? = null
    private var result: TextView? = null
    private var listener: ClickBackButton? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(context is ClickBackButton){
            listener = context as ClickBackButton
        } else {
            throw RuntimeException(context.toString()
                    + " must implement ClickBackButton")
        }
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        var randomNumber = generate(min, max)
        listener?.previousGenerated(randomNumber)
        result?.text = randomNumber.toString()

        backButton?.setOnClickListener {
            listener?.onSecondFragmentButtonClick()
        }
    }

    private fun generate(min: Int, max: Int): Int {
        return Random.nextInt(min, max + 1 )
    }

    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)
            fragment.arguments = args
            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}