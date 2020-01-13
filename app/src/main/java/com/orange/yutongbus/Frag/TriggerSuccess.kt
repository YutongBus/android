package com.orange.yutongbus.Frag


import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.jzchi.jzframework.callback.SetupDialog
import com.orange.yutongbus.R
import kotlinx.android.synthetic.main.trigger_success.view.*

class TriggerSuccess : JzFragement(R.layout.trigger_success) {

    var focus=1
    var dailog_focus=1

    override fun viewInit() {

        rootview.keepgoing.setBackgroundResource((R.color.button_orange))
        rootview.exit.setBackgroundResource((R.mipmap.btn_rectangle_short))

        rootview.textView30.text = TriggerWriting.Main_or_Auxiliary + "\n标定完成"

        rootview.exit.setOnClickListener {
            act.supportFragmentManager.popBackStack(null,1)
        }
        rootview.keepgoing.setOnClickListener {
            JzActivity.getControlInstance().showDiaLog(R.layout.keep_trigger,false,false, object :
                SetupDialog {
                override fun dismess() {

                }

                override fun keyevent(event: KeyEvent): Boolean {

                    val diaroot=JzActivity.getControlInstance().getRootActivity().mDialog!!

                    if (event.action == KeyEvent.ACTION_UP) {

                        Log.e("key", "" + event.keyCode)
                        if (event.keyCode == 21) {
                            dailog_focus = 0

                            trun_color()

                        }
                        if (event.keyCode == 22) {
                            dailog_focus = 1

                            trun_color()

                        }
                        if (event.keyCode == 66) {
                            if (dailog_focus == 1) {
                                diaroot.findViewById<TextView>(R.id.Yes).performClick()
                            }
                            if (dailog_focus == 0) {
                                diaroot.findViewById<TextView>(R.id.cancel).performClick()
                            }
                        }
                    }

                    return true
                }

                fun trun_color()
                {
                    var rootview= JzActivity.getControlInstance().getRootActivity().mDialog

                    if(dailog_focus == 1){
                        rootview!!.findViewById<TextView>(R.id.Yes).setBackgroundResource((R.color.button_orange))!!
                        rootview!!.findViewById<TextView>(R.id.cancel).setBackgroundResource(R.drawable.cornerblue)!!
                    }else{
                        rootview!!.findViewById<TextView>(R.id.Yes).setBackgroundResource((R.drawable.cornerblue))!!
                        rootview!!.findViewById<TextView>(R.id.cancel).setBackgroundResource((R.color.button_orange))!!
                    }
                }


                override fun setup(rootview: Dialog) {
                    dailog_focus = 1
                    trun_color()

                    it.findViewById<TextView>(R.id.cancel).setOnClickListener {
                        rootview.dismiss()
                        act.supportFragmentManager.popBackStack(null,1)
                    }
                    it.findViewById<TextView>(R.id.Yes).setOnClickListener {
                        rootview.dismiss()
                        JzActivity.getControlInstance().goBack("WheelTagUp")
                    }
                }

            })
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent) {

        Log.e("key", "" + event.keyCode)
        if (event.action == KeyEvent.ACTION_UP) {
            if(event.keyCode == 21)
            {
                focus=0

                rootview.keepgoing.setBackgroundResource((R.mipmap.btn_rectangle_short))
                rootview.exit.setBackgroundResource((R.color.button_orange))
            }
            if(event.keyCode == 22)
            {
                focus=1

                rootview.keepgoing.setBackgroundResource((R.color.button_orange))
                rootview.exit.setBackgroundResource((R.mipmap.btn_rectangle_short))
            }
            if(event.keyCode == 66)
            {
                if(focus==1)
                {
                    rootview.keepgoing.performClick()
                }
                if(focus==0)
                {
                    rootview.exit.performClick()
                }
            }
        }
    }

    }



