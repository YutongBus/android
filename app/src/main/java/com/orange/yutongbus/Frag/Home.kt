package com.orange.yutongbus.Frag


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orange.blelibrary.blelibrary.RootFragement
import com.orange.yutongbus.MainActivity

import com.orange.yutongbus.R
import com.orange.yutongbus.YounUart.Command
import com.orange.yutongbus.YounUart.Command.StringHexToByte
import com.orange.yutongbus.lib.hardware.HardwareApp
import kotlinx.android.synthetic.main.fragment_blank.view.*
import kotlinx.android.synthetic.main.fragment_blank.view.i1
import kotlinx.android.synthetic.main.fragment_blank.view.t1
import kotlinx.android.synthetic.main.fragment_home.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Home : RootFragement() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview=inflater.inflate(R.layout.fragment_home, container, false)
        rootview.i1.setOnClickListener {
        act.ChangePage(SpareSelect(),R.id.frage,"SpareSelect",true)
        }
        rootview.t1.setOnClickListener {
            act.ChangePage(SpareSelect(),R.id.frage,"SpareSelect",true)
        }
        rootview.imageView4.setOnClickListener {
            Command.send("A388FFFFFFFFFFFF3A11")
//            HardwareApp.send(StringHexToByte("0A0000030009F5"))
//            HardwareApp.send(StringHexToByte("A388FFFFFFFFFFFF3A11"))
//            (activity!! as MainActivity).a.readData()
        }
        super.onCreateView(inflater, container, savedInstanceState)
        return rootview
    }


}
