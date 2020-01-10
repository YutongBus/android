package com.orange.yutongbus.Frag


import android.app.Dialog
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.widget.TextView
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.jzchi.jzframework.callback.SetupDialog

import com.orange.yutongbus.R
import kotlinx.android.synthetic.main.fragment_trigger_insert.view.*

class TriggerInsert : JzFragement(R.layout.fragment_trigger_insert) {

    var focus=1

    override fun viewInit() {

        rootview.insert.setBackgroundResource((R.color.button_orange))
        rootview.reset.setBackgroundResource((R.mipmap.btn_rectangle_short))

        rootview.reset.setOnClickListener {
            JzActivity.getControlInstance().goBack()
        }
        rootview.insert.setOnClickListener {
            JzActivity.getControlInstance().showDiaLog(R.layout.insertobd, true, false, object : SetupDialog {
                override fun dismess() {

                }

                override fun keyevent(event: KeyEvent): Boolean {
                    return true
                }

                override fun setup(rootview: Dialog) {
                    rootview.findViewById<TextView>(R.id.cancel).setOnClickListener {
                        rootview.dismiss()
                    }
                    rootview.findViewById<TextView>(R.id.Yes).setOnClickListener {
                        rootview.dismiss()
                        JzActivity.getControlInstance().changeFrag(TriggerWriting(),R.id.frage,"TriggerWriting",true)
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

                rootview.insert.setBackgroundResource((R.mipmap.btn_rectangle_short))
                rootview.reset.setBackgroundResource((R.color.button_orange))
            }
            if(event.keyCode == 22)
            {
                focus=1

                rootview.insert.setBackgroundResource((R.color.button_orange))
                rootview.reset.setBackgroundResource((R.mipmap.btn_rectangle_short))
            }
            if(event.keyCode == 66)
            {
                if(focus==1)
                {
                    rootview.insert.performClick()
                }
                if(focus==0)
                {
                    rootview.reset.performClick()
                }
            }
        }
    }
}
