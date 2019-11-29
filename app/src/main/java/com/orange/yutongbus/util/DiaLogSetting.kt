package com.orange.yutongbus.util

import android.app.Dialog
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.orange.yutongbus.Frag.SpareSelect
import com.orange.yutongbus.Frag.WheelTagUp
import com.orange.yutongbus.R
import org.greenrobot.eventbus.EventBus

class DiaLogSetting{

    fun Reselect_Tire(mDialog:Dialog,tagup:WheelTagUp){
        (mDialog.findViewById(R.id.textView16) as TextView).visibility = if (SpareSelect.havespare) View.VISIBLE else View.GONE
        (mDialog.findViewById(R.id.textView17) as TextView).visibility = if (SpareSelect.havespare) View.VISIBLE else View.GONE
        (mDialog.findViewById(R.id.textView18) as TextView).visibility = if (SpareSelect.havespare) View.VISIBLE else View.GONE
        (mDialog.findViewById(R.id.textView19) as TextView).visibility = if (SpareSelect.havespare) View.VISIBLE else View.GONE
        (mDialog.findViewById(R.id.textView20) as TextView).visibility = if (SpareSelect.havespare) View.VISIBLE else View.GONE
        (mDialog.findViewById(R.id.textView21) as TextView).visibility = if (SpareSelect.havespare) View.VISIBLE else View.GONE
        (mDialog.findViewById(R.id.r1) as RelativeLayout).visibility = if (SpareSelect.havespare) View.GONE else View.VISIBLE
        (mDialog.findViewById(R.id.r1) as RelativeLayout).setOnClickListener {
            WheelTagUp.type = WheelTagUp.二轮配置
            tagup.UpdateUi()
            mDialog.dismiss()
        }
        (mDialog.findViewById(R.id.r2) as RelativeLayout).setOnClickListener {
            WheelTagUp.type = WheelTagUp.四轮配置
            tagup.UpdateUi()
            mDialog.dismiss()
        }
        (mDialog.findViewById(R.id.r3) as RelativeLayout).setOnClickListener {
            WheelTagUp.type = WheelTagUp.六轮配置
            tagup.UpdateUi()
            mDialog.dismiss()
        }
        (mDialog.findViewById(R.id.r4) as RelativeLayout).setOnClickListener {
            WheelTagUp.type = WheelTagUp.八轮配置後
            tagup.UpdateUi()
            mDialog.dismiss()
        }
        (mDialog.findViewById(R.id.r5) as RelativeLayout).setOnClickListener {
            WheelTagUp.type = WheelTagUp.八轮配置中
            tagup.UpdateUi()
            mDialog.dismiss()
        }
        (mDialog.findViewById(R.id.r6) as RelativeLayout).setOnClickListener {
            WheelTagUp.type = WheelTagUp.十轮配置
            tagup.UpdateUi()
            mDialog.dismiss()
        }
    }
}