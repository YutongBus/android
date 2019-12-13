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
import kotlinx.android.synthetic.main.trigger_success.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TriggerSuccess : RootFragement() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview=inflater.inflate(R.layout.trigger_success, container, false)

        rootview.textView30.text = TriggerWriting.Main_or_Auxiliary + "\n标定完成"

        rootview.exit.setOnClickListener {
            act.supportFragmentManager.popBackStack(null,1)
        }
        rootview.keepgoing.setOnClickListener {
            act.ShowDaiLog(R.layout.keep_trigger,false,false, DaiSetUp {
                it.findViewById<TextView>(R.id.cancel).setOnClickListener {
                    act.DaiLogDismiss()
                    act.supportFragmentManager.popBackStack(null,1)
                }
                it.findViewById<TextView>(R.id.Yes).setOnClickListener {
                    act.DaiLogDismiss()
                    act.GoBack("WheelTagUp")
                } 
            })
         
        }
        return rootview
        }
    }



