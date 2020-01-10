package com.orange.yutongbus.Frag


import android.util.Log
import android.view.KeyEvent
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.yutongbus.R
import kotlinx.android.synthetic.main.fragment_trigger_false.view.*
import kotlinx.android.synthetic.main.trigger_success.view.exit
import kotlinx.android.synthetic.main.trigger_success.view.keepgoing

class TriggerFalse : JzFragement(R.layout.fragment_trigger_false) {

    var focus=1

    override fun viewInit() {

        rootview.keepgoing.setBackgroundResource((R.color.button_orange))
        rootview.exit.setBackgroundResource((R.mipmap.btn_rectangle_short))

        Log.e("errortype","${TriggerWriting.error_type}")
        when(TriggerWriting.error_type)
        {
            //六輪(主副機)、八輪、十輪
            TriggerWriting.版本不一樣 ->
            {
                rootview.text.text= "主副机版本新旧不一样"
            }


            TriggerWriting.錯誤 ->
            {
                //rootview.text.text= "错误 \n  请检查主副机是否有插电"
                if(TriggerWriting.Main_or_Auxiliary == TriggerWriting.主機){rootview.text.text= "读取版本错误"}
                if(TriggerWriting.Main_or_Auxiliary == TriggerWriting.主副機){rootview.text.text= "读取版本错误"}

            }

            //八輪、十輪
            TriggerWriting.皆為舊或新 ->
            {
                //if(TriggerWriting.Main_or_Auxiliary == TriggerWriting.主機){rootview.text.text= "主机标定完成\n错误\n  请检查主机是否有插电"}
                //if(TriggerWriting.Main_or_Auxiliary == TriggerWriting.主副機){rootview.text.text= "主机标定完成\n错误\n  请检查主副机是否有插电"}
                when(WheelTagUp.type){
                    WheelTagUp.二轮配置 ->{rootview.text.text="资料写入失败\n错误\n 请检查主机是否有插电"}
                    WheelTagUp.四轮配置 ->{rootview.text.text="资料写入失败\n错误\n 请检查主机是否有插电"}
                    WheelTagUp.六轮配置 ->{if(TriggerWriting.Main_or_Auxiliary == TriggerWriting.主機){rootview.text.text="资料写入失败\n错误\n 请检查主机是否有插电"}else{rootview.text.text="资料写入失败\n错误\n 请检查主副机是否有插电"} }
                    WheelTagUp.八轮配置中 ->{rootview.text.text="资料写入失败\n错误\n 请检查主副机是否有插电" }
                    WheelTagUp.八轮配置後 ->{rootview.text.text="资料写入失败\n错误\n 请检查主副机是否有插电" }
                    WheelTagUp.十轮配置 ->{rootview.text.text="资料写入失败\n错误\n 请检查主副机是否有插电"  }
                }
                //rootview.text.text="十轮版本需为新版"

//                act.ShowDaiLog(R.layout.trigger_version_error,true,false, DaiSetUp { it.findViewById<TextView>(R.id.textView23).text = "错误 \n  请检查主副机是否有插电" })
//                if(WheelTagUp.type== WheelTagUp.十轮配置) {
//                    act.ShowDaiLog(R.layout.trigger_version_error, true, false, DaiSetUp { it.findViewById<TextView>(R.id.textView23).text = "十轮版本需为新版" })
//                }
            }
            TriggerWriting.TIME_OUT->{
                rootview.text.text= "资料写入失败"
            }
        }

        rootview.exit.setOnClickListener {
            //act.supportFragmentManager.popBackStack(null,1)
            JzActivity.getControlInstance().closeDiaLog()
            JzActivity.getControlInstance().goBack("WheelTagUp")
        }
        rootview.keepgoing.setOnClickListener {
            JzActivity.getControlInstance().goBack()

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
