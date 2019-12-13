package com.orange.yutongbus.Frag


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.orange.blelibrary.blelibrary.Callback.DaiSetUp
import com.orange.blelibrary.blelibrary.RootFragement

import com.orange.yutongbus.R
import kotlinx.android.synthetic.main.fragment_trigger_insert.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TriggerInsert : RootFragement() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview = inflater.inflate(R.layout.fragment_trigger_insert, container, false)
        rootview.reset.setOnClickListener {
            act.GoBack()
        }
        rootview.insert.setOnClickListener {
            act.ShowDaiLog(R.layout.insertobd, true, false, DaiSetUp {
            it.findViewById<TextView>(R.id.cancel).setOnClickListener {
                act.DaiLogDismiss()
            }
            it.findViewById<TextView>(R.id.Yes).setOnClickListener {
                act.DaiLogDismiss()
                act.ChangePage(TriggerWriting(),R.id.frage,"TriggerWriting",true)
            }
        })
          
        }
        return rootview
    }



}
