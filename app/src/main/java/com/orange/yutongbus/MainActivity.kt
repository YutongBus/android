package com.orange.yutongbus


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.KeyEvent
import android.view.View
import com.orange.yutongbus.Frag.Home
import android.widget.ImageView
import android.widget.TextView
import com.orange.blelibrary.blelibrary.BleActivity
import com.orange.blelibrary.blelibrary.Callback.DaiSetUp
import com.orange.blelibrary.blelibrary.RootFragement
import com.orange.yutongbus.Frag.SpareSelect
import com.orange.yutongbus.Frag.WheelTagUp
import com.orange.yutongbus.YounUart.Command
import com.orange.yutongbus.util.DiaLogSetting
import kotlinx.android.synthetic.main.logout.*
import kotlin.math.floor


class MainActivity : BleActivity() {
    //處理頁面切換後，toolbar的事件更改
    override fun ChangePageListener(tag:String,frag:Fragment){
        Log.d("switch", tag)

        if (tag == "Home") {
            backim.visibility = View.GONE
        } else {
            backim.visibility = View.VISIBLE
        }

        when (tag) {
            "SpareSelect" ->
            {
                Title.text = "轮位标定数量选择"
            }
            "CoolPressure" ->
            {
                Title.text = "胎压设定"
            }
            "TriggerInsert" ->
            {
                Title.text = "标定资料写入"
            }
        }

            CanGoBack=tag != "TriggerWriting"
        if (tag == "WheelTagUp" || tag == "Home") {
            rightop.visibility = View.VISIBLE
            when (tag) {
                "Home" -> {
                    Title.text = "YUTONG"
                    rightop.setImageResource(R.mipmap.sign_out)
                    rightop.setOnClickListener {
                        ShowDaiLog(R.layout.logout,true,false, DaiSetUp {
                            mDialog!!.Yes.setOnClickListener { android.os.Process.killProcess(android.os.Process.myPid()) }
                            mDialog!!.cancel.setOnClickListener {  DaiLogDismiss() }})
                    }
                }
                "WheelTagUp" -> {
                    Title.text = "轮位标定"
                    rightop.setImageResource(R.mipmap.select_tire)
                    rightop.setOnClickListener {
                        ShowDaiLog(R.layout.activity_reselect__tire,true,false, DaiSetUp {  })
                        Dialoginit.Reselect_Tire(mDialog!!,(frag as WheelTagUp))
                        //Title.text = "轮位标定"

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
    lateinit var Title: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread{ Command.ReOpen()}.start()
        backim = findViewById(R.id.backim)
        rightop = findViewById(R.id.RightTop)
        Title= findViewById(R.id.Title)
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
        if(NowFrage=="Home"){
            val profilePreferences = getSharedPreferences("Setting", Context.MODE_PRIVATE)
            val data=profilePreferences.getInt("tirecount",0)
            val havespare=profilePreferences.getBoolean("havespare",false)
            if(data!=0){
                ShowDaiLog(R.layout.keep_trigger,false,false, DaiSetUp {
                    it.findViewById<TextView>(R.id.cancel).setOnClickListener {
                        DaiLogDismiss()
                    }
                    it.findViewById<TextView>(R.id.Yes).setOnClickListener {
                        DaiLogDismiss()
                        WheelTagUp.type=data
                        SpareSelect.havespare=havespare
                        ChangePage(WheelTagUp(),R.id.frage,"WheelTagUp",true)
                    }
                })
            }
        }
        SetNaVaGation(true)
    }
}
