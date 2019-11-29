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
import kotlinx.android.synthetic.main.activity_reselect__tire.view.*
import kotlinx.android.synthetic.main.activity_reselect__tire.view.r
import kotlinx.android.synthetic.main.activity_reselect__tire.view.r1
import kotlinx.android.synthetic.main.activity_reselect__tire.view.r3
import kotlinx.android.synthetic.main.activity_reselect__tire.view.r4
import kotlinx.android.synthetic.main.activity_reselect__tire.view.r5
import kotlinx.android.synthetic.main.activity_reselect__tire.view.r6
import kotlinx.android.synthetic.main.fragment_blank.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SpareSelect : RootFragement() {
    companion object{
        var havespare=true;
        var ShowDialog=true;
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview=inflater.inflate(R.layout.fragment_blank, container, false)
        if(ShowDialog){act.ShowDaiLog(R.layout.activity_check_spare,true,false, DaiSetUp {
            it.findViewById<TextView>(R.id.textView14).setOnClickListener {
                havespare=false
                UpdateUI()
                act.DaiLogDismiss()
            }
            it.findViewById<TextView>(R.id.textView15).setOnClickListener {
                havespare=true
                UpdateUI()
                act.DaiLogDismiss()
            }
        })
         }else{
            ShowDialog=true;
        }
rootview.r.setOnClickListener {
    WheelTagUp.type=WheelTagUp.二轮配置
    act.ChangePage(WheelTagUp(),R.id.frage,"WheelTagUp",true)
}
        rootview.r1.setOnClickListener {
            WheelTagUp.type=WheelTagUp.四轮配置
            act.ChangePage(WheelTagUp(),R.id.frage,"WheelTagUp",true)
        }
        rootview.r3.setOnClickListener {
            WheelTagUp.type=WheelTagUp.六轮配置
            act.ChangePage(WheelTagUp(),R.id.frage,"WheelTagUp",true)
        }
        rootview.r4.setOnClickListener {
            WheelTagUp.type=WheelTagUp.八轮配置中
            act.ChangePage(WheelTagUp(),R.id.frage,"WheelTagUp",true)
        }
        rootview.r5.setOnClickListener {
            WheelTagUp.type=WheelTagUp.八轮配置後
            act.ChangePage(WheelTagUp(),R.id.frage,"WheelTagUp",true)
        }
        rootview.r6.setOnClickListener {
            WheelTagUp.type=WheelTagUp.十轮配置
            act.ChangePage(WheelTagUp(),R.id.frage,"WheelTagUp",true)
        }
        super.onCreateView(inflater, container, savedInstanceState)
        UpdateUI()
        return rootview
    }
    fun UpdateUI(){   if(havespare){
        rootview.r.visibility=View.GONE
        rootview.s1.visibility=View.VISIBLE
        rootview.s2.visibility=View.VISIBLE
        rootview.s3.visibility=View.VISIBLE
        rootview.s4.visibility=View.VISIBLE
        rootview.s5.visibility=View.VISIBLE
        rootview.s6.visibility=View.VISIBLE
    }else{
        rootview.r.visibility=View.VISIBLE
        rootview.s1.visibility=View.GONE
        rootview.s2.visibility=View.GONE
        rootview.s3.visibility=View.GONE
        rootview.s4.visibility=View.GONE
        rootview.s5.visibility=View.GONE
        rootview.s6.visibility=View.GONE
    }}
    override fun onResume() {
        super.onResume()

    }


}
