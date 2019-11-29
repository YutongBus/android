package com.orange.yutongbus


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.KeyEvent
import android.view.View
import com.orange.yutongbus.Frag.Home
import android.widget.ImageView
import com.orange.blelibrary.blelibrary.BleActivity
import com.orange.blelibrary.blelibrary.Callback.DaiSetUp
import com.orange.blelibrary.blelibrary.RootFragement
import com.orange.yutongbus.Frag.CoolPressure
import com.orange.yutongbus.Frag.WheelTagUp
import com.orange.yutongbus.YounUart.Command
import com.orange.yutongbus.lib.hardware.HardwareApp
import com.orange.yutongbus.util.DiaLogSetting


class MainActivity : BleActivity() {
    //處理頁面切換後，toolbar的事件更改
    override fun ChangePageListener(tag:String,frag:Fragment){
        Log.d("switch", tag)
        if (tag == "Home") {
            backim.visibility = View.GONE
        } else {
            backim.visibility = View.VISIBLE
        }
            CanGoBack=tag != "TriggerWriting"
        if (tag == "WheelTagUp" || tag == "Home") {
            rightop.visibility = View.VISIBLE
            when (tag) {
                "Home" -> {
                    rightop.setImageResource(R.mipmap.sign_out)
                    rightop.setOnClickListener { }
                }
                "WheelTagUp" -> {
                    rightop.setImageResource(R.mipmap.select_tire)
                    rightop.setOnClickListener {
                        ShowDaiLog(R.layout.activity_reselect__tire,true,false, DaiSetUp {  })
                        Dialoginit.Reselect_Tire(mDialog!!,(frag as WheelTagUp))
                    }
                }
            }
        } else {
            rightop.visibility = View.GONE
        }
    }
//
//       var a= HardwareApp()
     var Dialoginit= DiaLogSetting()
    var Memory=Memory()
    lateinit var backim: ImageView
    lateinit var rightop: ImageView
//    lateinit var anima
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread{Command.ReOpen()}.start()
        backim = findViewById(R.id.backim)
        rightop = findViewById(R.id.RightTop)
        ChangePage(Home(), R.id.frage, "Home", false)
//    ChangePage(CoolPressure(), R.id.frage, "CoolPressure", false)
    }

    fun onclick(view: View) {
        when (view.id) {
            R.id.backim -> {
                if(CanGoBack){
                    supportFragmentManager.popBackStack(null,1)
                }
            }
        }
    }
    override fun onBackPressed() {
        if(CanGoBack){
            supportFragmentManager.popBackStack(null,1)
        }
    }
    @SuppressLint("RestrictedApi")
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {//只处理按下的动画,抬起的动作忽略
            Log.v("yhd-", "event:$event")
            //按键事件向Fragment分发
            if(Fraging != null){ (Fraging as RootFragement).dispatchKeyEvent(event)}
            //页面在顶层才会分发
        }
        return superDispatchKeyEvent(event)
    }

    override fun onResume() {
        super.onResume()
        SetNaVaGation(false)
    }
}
