package com.orange.yutongbus.Frag

import android.app.Dialog
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.jzchi.jzframework.callback.SetupDialog
import com.orange.yutongbus.R
import com.orange.yutongbus.util.DiaLogSetting
import kotlinx.android.synthetic.main.activity_main.view.*

class Act_Home : JzFragement(R.layout.activity_main){

    var dailog_focus=0

    override fun viewInit() {
//        (act as MainActivity).backim= rootview.findViewById(R.id.backim)
//        (act as MainActivity).rightop= rootview.findViewById(R.id.RightTop)
//        (act as MainActivity).Title= rootview.findViewById(R.id.Title)

       JzActivity.getControlInstance().changeFrag(Home(),R.id.frage,"Home",false)
    }

    //處理頁面切換後，toolbar的事件更改
    fun changePageListener(tag: String, frag: Fragment){
        Log.d("switch", tag)
        if (tag == "Home") {
            rootview.backim.visibility = View.GONE
        } else {
            rootview.backim.visibility = View.VISIBLE
        }

        when (tag) {
            "SpareSelect" -> {
                rootview.Title.text = "轮位标定数量选择"
            }
            "CoolPressure" -> {
                rootview.Title.text = "胎压设定"
            }
            "TriggerInsert" -> {
                rootview.Title.text = "标定资料写入"
            }
        }
        if (tag == "WheelTagUp" || tag == "Home") {
            rootview.RightTop.visibility = View.VISIBLE
            when (tag) {
                "Home" -> {
                    rootview.Title.text = "YUTONG"
                    rootview.RightTop.setImageResource(R.mipmap.sign_out)
                    rootview.RightTop.setOnClickListener {
                        JzActivity.getControlInstance().showDiaLog(R.layout.logout, true, false, object : SetupDialog {
                            override fun dismess() {

                            }

                            override fun keyevent(event: KeyEvent): Boolean {
                               return true
                            }

                            override fun setup(rootview: Dialog) {
                                rootview.findViewById<TextView>(R.id.Yes).setOnClickListener { android.os.Process.killProcess(android.os.Process.myPid()) }
                                rootview.findViewById<TextView>(R.id.cancel).setOnClickListener { rootview.dismiss() }
                            }

                        })
                    }
                }
                "WheelTagUp" -> {
                    rootview.Title.text = "轮位标定"
                    rootview.RightTop.setImageResource(R.mipmap.select_tire)
                    rootview.RightTop.setOnClickListener {
                        JzActivity.getControlInstance().showDiaLog(
                            R.layout.activity_reselect__tire,
                            true,
                            false,
                            object : SetupDialog {
                                override fun dismess() {

                                }

                                override fun keyevent(event: KeyEvent): Boolean {
                                    return true
                                }

                                override fun setup(rootview: Dialog) {
                                    Dialoginit.Reselect_Tire(rootview!!, (frag as WheelTagUp))
                                }

                            })
                    }
                }
            }
        } else {
            rootview.RightTop.visibility = View.GONE
        }
    }

    //       var a= HardwareApp()
    var Dialoginit = DiaLogSetting()

//    lateinit var backim: ImageView
//    lateinit var rightop: ImageView
//    lateinit var Title: TextView

    fun viewInit(view: View) {
        when (view.id) {
            R.id.backim -> {
                JzActivity.getControlInstance().goMenu()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        if (JzActivity.getControlInstance().getNowPageTag() == "Home") {

            val data = JzActivity.getControlInstance().getPro("tirecount", 0)
            val havespare = JzActivity.getControlInstance().getPro("havespare", false)
            if (data != 0) {
                JzActivity.getControlInstance().showDiaLog(R.layout.keep_trigger, false, false, object : SetupDialog {
                    override fun dismess() {

                    }

                    override fun keyevent(event: KeyEvent): Boolean {

                        //按鈕事件監聽
                        // return true後會繼續執行父類別的dispathKeyevent方法，反之攔截按鈕事件
                        val diaroot=JzActivity.getControlInstance().getRootActivity().mDialog!!

                        if (event.action == KeyEvent.ACTION_UP) {
                            Log.e("key", "" + event.keyCode)
                            if(event.keyCode == 21)
                            {
                                dailog_focus=0

                                trun_color()

                            }
                            if(event.keyCode == 22)
                            {
                                dailog_focus=1

                                trun_color()

                            }
                            if(event.keyCode == 66)
                            {
                                if(dailog_focus==1)
                                {
                                    diaroot.findViewById<TextView>(R.id.Yes).performClick()
                                }
                                if(dailog_focus==0)
                                {
                                    diaroot.findViewById<TextView>(R.id.cancel).performClick()
                                }
                            }
                        }

                        return true
                    }

                    override fun setup(rootview: Dialog) {

                        dailog_focus=1
                        trun_color()

                        rootview.findViewById<TextView>(R.id.cancel).setOnClickListener {
                            rootview.dismiss()
                        }
                        rootview.findViewById<TextView>(R.id.Yes).setOnClickListener {
                            rootview.dismiss()
                            WheelTagUp.type = data
                            SpareSelect.havespare = havespare
                            JzActivity.getControlInstance().changeFrag(WheelTagUp(), R.id.frage, "WheelTagUp", true)
                        }
                    }

                    fun trun_color()
                    {
                       var rootview= JzActivity.getControlInstance().getRootActivity().mDialog

                        if(dailog_focus == 1){
                            rootview!!.findViewById<TextView>(R.id.Yes).setBackgroundResource((R.color.button_orange))!!
                            rootview!!.findViewById<TextView>(R.id.cancel).setBackgroundResource((R.drawable.cornerblue))!!
                        }else{
                            rootview!!.findViewById<TextView>(R.id.Yes).setBackgroundResource((R.drawable.cornerblue))!!
                            rootview!!.findViewById<TextView>(R.id.cancel).setBackgroundResource((R.color.button_orange))!!
                        }
                    }

                })
            }
        }

    }
}