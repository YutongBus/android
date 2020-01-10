package com.orange.yutongbus.Frag

import android.util.Log
import android.view.KeyEvent
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.yutongbus.R
import com.orange.yutongbus.YounUart.Command
import kotlinx.android.synthetic.main.fragment_blank.view.i1
import kotlinx.android.synthetic.main.fragment_blank.view.t1
import kotlinx.android.synthetic.main.fragment_home.view.*



class Home : JzFragement(R.layout.fragment_home) {

    var focus=0

    override fun viewInit() {
        trun_color()

        rootview.i1.setOnClickListener {
            SpareSelect.ShowDialog = true
            JzActivity.getControlInstance().changeFrag(SpareSelect(),R.id.frage,"SpareSelect",true)
        }
        rootview.t1.setOnClickListener {
            SpareSelect.ShowDialog = true
            JzActivity.getControlInstance().changeFrag(SpareSelect(),R.id.frage,"SpareSelect",true)
        }
        rootview.imageView4.setOnClickListener {
            Command.send("A388FFFFFFFFFFFF3A11")
        }
    }

    fun trun_color()
    {
        if(focus == 0)
        {
            rootview.i1.setImageResource((R.mipmap.orange_dsbuffer))
            rootview.t1.setTextColor(resources.getColor(R.color.button_orange))
            rootview.imageView4.setImageResource((R.mipmap.bar_code))
            rootview.textView3.setTextColor(resources.getColor(R.color.black))
        }
        else
        {
            rootview.i1.setImageResource((R.mipmap.frequency))
            rootview.t1.setTextColor(resources.getColor(R.color.black))
            rootview.imageView4.setImageResource((R.mipmap.orange_btn_bar_code))
            rootview.textView3.setTextColor(resources.getColor(R.color.button_orange))
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent) {

        Log.e("key", "" + event.keyCode)
        if (event.action == KeyEvent.ACTION_UP) {
            if (event.keyCode == 19) {
                focus = 0
                trun_color()
            }
            if (event.keyCode == 20) {
                focus = 1
                trun_color()
            }
            if (event.keyCode == 66) {
                if (focus == 0) {
                    rootview.i1.performClick()
                } else {
                    rootview.imageView4.performClick()
                }
            }
        }
    }
}
