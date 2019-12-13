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
import kotlinx.android.synthetic.main.trigger_success.view.*

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
            TriggerWriting.版本不一樣 ->
            {
                act.ShowDaiLog(R.layout.trigger_version_error,true,false, DaiSetUp { it.findViewById<TextView>(R.id.textView23).text = "主副机版本新旧不一样" })
            }
            TriggerWriting.錯誤 ->
            {
                act.ShowDaiLog(R.layout.trigger_version_error,true,false, DaiSetUp { it.findViewById<TextView>(R.id.textView23).text = "错误 \n  请检查主副机是否有插电" })
            }

            TriggerWriting.皆為舊或新 ->
            {
                act.ShowDaiLog(R.layout.trigger_version_error,true,false, DaiSetUp { it.findViewById<TextView>(R.id.textView23).text = "十轮版本需为新版" })
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
