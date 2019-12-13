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
import kotlinx.android.synthetic.main.fragment_trigger_false.view.*
import kotlinx.android.synthetic.main.trigger_success.view.*
import kotlinx.android.synthetic.main.trigger_success.view.exit
import kotlinx.android.synthetic.main.trigger_success.view.keepgoing

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TriggerFalse : RootFragement() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       rootview=inflater.inflate(R.layout.fragment_trigger_false, container, false)

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
                if(TriggerWriting.Main_or_Auxiliary == TriggerWriting.主機){rootview.text.text= "主机标定完成\n错误\n  请检查主机是否有插电"}
                if(TriggerWriting.Main_or_Auxiliary == TriggerWriting.主副機){rootview.text.text= "主机标定完成\n错误\n  请检查主副机是否有插电"}

            }

            //八輪、十輪
            TriggerWriting.皆為舊或新 ->
            {
                if(TriggerWriting.Main_or_Auxiliary == TriggerWriting.主機){rootview.text.text= "主机标定完成\n错误\n  请检查主机是否有插电"}
                if(TriggerWriting.Main_or_Auxiliary == TriggerWriting.主副機){rootview.text.text= "主机标定完成\n错误\n  请检查主副机是否有插电"}
                when(WheelTagUp.type){
                    WheelTagUp.二轮配置 ->{rootview.text.text="主机标定完成\n错误\n 请检查主机是否有插电"}
                    WheelTagUp.四轮配置->{rootview.text.text="十轮版本需为新版" }
                    WheelTagUp.六轮配置 ->{rootview.text.text="十轮版本需为新版" }
                    WheelTagUp.八轮配置中->{rootview.text.text="十轮版本需为新版" }
                    WheelTagUp.八轮配置後 ->{rootview.text.text="十轮版本需为新版" }
                    WheelTagUp.十轮配置->{rootview.text.text="十轮版本需为新版" }
                }
                rootview.text.text="十轮版本需为新版"

//                act.ShowDaiLog(R.layout.trigger_version_error,true,false, DaiSetUp { it.findViewById<TextView>(R.id.textView23).text = "错误 \n  请检查主副机是否有插电" })
//                if(WheelTagUp.type== WheelTagUp.十轮配置) {
//                    act.ShowDaiLog(R.layout.trigger_version_error, true, false, DaiSetUp { it.findViewById<TextView>(R.id.textView23).text = "十轮版本需为新版" })
//                }
            }
            TriggerWriting.TIME_OUT->{
                rootview.text.text= "資料寫入失敗"
            }
        }

        rootview.exit.setOnClickListener {
//act.supportFragmentManager.popBackStack(null,1)
            act.DaiLogDismiss()
            act.GoBack("WheelTagUp")
        }
        rootview.keepgoing.setOnClickListener {
            act.GoBack()

        }
        return rootview
    }


}
