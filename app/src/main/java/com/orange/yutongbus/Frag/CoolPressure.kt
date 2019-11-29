package com.orange.yutongbus.Frag


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orange.blelibrary.blelibrary.RootFragement
import com.orange.yutongbus.MainActivity

import com.orange.yutongbus.R
import com.orange.yutongbus.util.KeyboardUtil.hideEditTextKeyboard
import kotlinx.android.synthetic.main.fragment_cool_pressure.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CoolPressure : RootFragement() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview=inflater.inflate(R.layout.fragment_cool_pressure, container, false)
        hideEditTextKeyboard(rootview.editText)
        rootview.button2.setOnClickListener {
            (activity as MainActivity).Memory.Pressure=Integer.valueOf(rootview.editText.text.toString())
            act.ChangePage(TriggerInsert(),R.id.frage,"TriggerInsert",true)
        }
        return rootview
    }


}
