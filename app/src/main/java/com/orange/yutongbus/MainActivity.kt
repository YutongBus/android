package com.orange.yutongbus

import android.content.Intent
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.KeyEvent
import android.view.View
import com.orange.jzchi.jzframework.JzActivity
import com.orange.yutongbus.Frag.Act_Home
import com.orange.yutongbus.Frag.WheelTagUp
import com.orange.yutongbus.YounUart.Command

class MainActivity : JzActivity() {

    var Memory = Memory()

    override fun changePageListener(tag: String, frag: Fragment) {
        if (JzActivity.getControlInstance().findFragByTag("Act_Home") is Act_Home) {
            (JzActivity.getControlInstance().findFragByTag("Act_Home") as Act_Home).changePageListener(
                tag,
                frag
            )
        }
    }

    override fun keyEventListener(event: KeyEvent): Boolean {

        Log.e("key",""+event.keyCode)

        if(event.action==KeyEvent.ACTION_UP){
            if (Fraging is WheelTagUp&&event.keyCode==131){
                (Fraging as WheelTagUp).Trigger()
            }
        }
       return true
    }

    override fun viewInit(rootview: View) {
        SetNaVaGation(true)
        Thread { Command.ReOpen() }.start()
        JzActivity.getControlInstance().setHome(Act_Home(), "Act_Home")
    }

     fun SetNaVaGation(hide:Boolean){
        var intent= Intent("hbyapi.intent.action_hide_navigationbar")
        intent.putExtra("hide",hide)
        sendBroadcast(intent)
        var intent2=Intent("hbyapi.intent.action_lock_panelbar")
        intent2.putExtra("state",hide)
        sendBroadcast(intent2)
    }
}
