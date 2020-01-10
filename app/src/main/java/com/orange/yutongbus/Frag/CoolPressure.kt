package com.orange.yutongbus.Frag


import android.util.Log
import android.view.KeyEvent
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.yutongbus.MainActivity
import com.orange.yutongbus.R
import com.orange.yutongbus.util.KeyboardUtil.hideEditTextKeyboard
import kotlinx.android.synthetic.main.fragment_cool_pressure.view.*

class CoolPressure : JzFragement(R.layout.fragment_cool_pressure) {
    override fun viewInit() {
        hideEditTextKeyboard(rootview.editText)
        rootview.button2.setOnClickListener {
            (activity as MainActivity).Memory.Pressure=Integer.valueOf(rootview.editText.text.toString())
            JzActivity.getControlInstance().changeFrag(TriggerInsert(),R.id.frage,"TriggerInsert",true)
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent) {

        Log.e("key", "" + event.keyCode)
        if (event.action == KeyEvent.ACTION_UP) {
            if(event.keyCode == 66)
            {
                rootview.button2.performClick()
            }
        }
    }

}
