package com.orange.yutongbus.Frag

import android.app.Dialog
import android.util.Log
import android.view.KeyEvent
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bean.Make_SpareSelect
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.jzchi.jzframework.callback.SetupDialog
import com.orange.yutongbus.Ad.Ad_SpareSelect
import com.orange.yutongbus.R
import kotlinx.android.synthetic.main.fragment_spareselect.view.*

class SpareSelect : JzFragement(R.layout.fragment_spareselect) {


    var focus=0
    var dailog_focus=0
    var limit=5
    var r_List = ArrayList<RelativeLayout>()

    var myitem= Make_SpareSelect()
    lateinit var adapter: Ad_SpareSelect

    companion object{
        var havespare=true;
        var ShowDialog=true;
    }

    override fun viewInit() {

        if(ShowDialog){JzActivity.getControlInstance().showDiaLog(R.layout.activity_check_spare,true,false, object : SetupDialog{
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
                            diaroot.findViewById<TextView>(R.id.textView15).performClick()
                        }
                        if(dailog_focus==0)
                        {
                            diaroot.findViewById<TextView>(R.id.textView14).performClick()
                        }
                    }
                }
                return true
            }

            fun trun_color()
            {
                var rootview= JzActivity.getControlInstance().getRootActivity().mDialog

                if(dailog_focus == 1){
                    rootview!!.findViewById<TextView>(R.id.textView15).setBackgroundResource((R.color.button_orange))!!
                    rootview!!.findViewById<TextView>(R.id.textView14).setBackgroundResource((R.drawable.cornerblue))!!
                }else{
                    rootview!!.findViewById<TextView>(R.id.textView15).setBackgroundResource((R.drawable.cornerblue))!!
                    rootview!!.findViewById<TextView>(R.id.textView14).setBackgroundResource((R.color.button_orange))!!
                }
            }

            override fun setup(rootview: Dialog) {
                dailog_focus = 0
                trun_color()

                rootview.findViewById<TextView>(R.id.textView14).setOnClickListener {
                    //adapter.notifyItemRangeRemoved(0,adapter.itemCount)

                    havespare=false
                    UpdateUI()
                    rootview.dismiss()
                }
                rootview.findViewById<TextView>(R.id.textView15).setOnClickListener {
                    //adapter.notifyItemRangeRemoved(0,adapter.itemCount)

                    havespare=true
                    UpdateUI()
                    rootview.dismiss()
                }
            }

        })
        }else{
            ShowDialog=true;
        }

        UpdateUI()
/*
        rootview.r.setOnClickListener {
            WheelTagUp.type=WheelTagUp.二轮配置
            act.ChangePage(WheelTagUp(),R.id.frage,"WheelTagUp",true)
        }
        rootview.r1.setOnClickListener {
            WheelTagUp.type=WheelTagUp.四轮配置
            act.ChangePage(WheelTagUp(),R.id.frage,"WheelTagUp",true)
        }
        rootview.r3.setOnClickListener {
            WheelTagUp.type=WheelTagUp.六轮配置
            act.ChangePage(WheelTagUp(),R.id.frage,"WheelTagUp",true)
        }
        rootview.r4.setOnClickListener {
            WheelTagUp.type=WheelTagUp.八轮配置中
            act.ChangePage(WheelTagUp(),R.id.frage,"WheelTagUp",true)
        }
        rootview.r5.setOnClickListener {
            WheelTagUp.type=WheelTagUp.八轮配置後
            act.ChangePage(WheelTagUp(),R.id.frage,"WheelTagUp",true)
        }
        rootview.r6.setOnClickListener {
            WheelTagUp.type=WheelTagUp.十轮配置
            act.ChangePage(WheelTagUp(),R.id.frage,"WheelTagUp",true)
        }

        UpdateUI()
         */

    }

    fun UpdateUI(){

        myitem=Make_SpareSelect()
        adapter= Ad_SpareSelect(myitem)

        rootview.Set_SpareSelect.layoutManager= GridLayoutManager(
            activity,
            1
        ) as RecyclerView.LayoutManager?
        //rootview.Set_SpareSelect.adapter=adapter

        rootview.Set_SpareSelect.adapter=adapter

        if(havespare == false){
            myitem.add("二轮配置", R.mipmap.twowheel)
        }

        myitem.add("四轮配置", R.mipmap.fourwheel)
        myitem.add("六轮配置", R.mipmap.sixwheel)
        myitem.add("八轮配置", R.mipmap.eight_wheel)
        myitem.add("八轮配置 ", R.mipmap.eiwheel)
        myitem.add("十轮配置", R.mipmap.tenwheel)

        adapter.notifyDataSetChanged()

        adapter.setfocus(0)

        /*
        if(havespare){
            rootview.r.visibility=View.GONE
        rootview.s1.visibility=View.VISIBLE
        rootview.s2.visibility=View.VISIBLE
        rootview.s3.visibility=View.VISIBLE
        rootview.s4.visibility=View.VISIBLE
        rootview.s5.visibility=View.VISIBLE
        rootview.s6.visibility=View.VISIBLE
    }else{
        rootview.r.visibility=View.VISIBLE
        rootview.s1.visibility=View.GONE
        rootview.s2.visibility=View.GONE
        rootview.s3.visibility=View.GONE
        rootview.s4.visibility=View.GONE
        rootview.s5.visibility=View.GONE
        rootview.s6.visibility=View.GONE
    }

        if(havespare){
            limit=4
        }else{
            limit=5
            //r_List.add(rootview.r)

        }
        */
        /*
        r_List.add(rootview.r1)
        //r_List.add(rootview.r2)
        r_List.add(rootview.r3)
        r_List.add(rootview.r4)
        r_List.add(rootview.r5)
        r_List.add(rootview.r6)
         */

        //rootview.Set_SpareSelect.scrollToPosition(2)
        //rootview.Set_SpareSelect.performClick()

        //rootview.Set_SpareSelect.setBackgroundResource(R.drawable.corner)

    }

    override fun dispatchKeyEvent(event: KeyEvent) {

        Log.e("key",""+event.keyCode)
        if(event.action==KeyEvent.ACTION_UP){

            /*
            if(focus == 0 || focus == limit)
            {

            }
            else
            {
                rootview.sc.scrollY = (120 * focus) / 2
            }
*/

            //rootview.sc.scrollY = (120 * focus) / 2

            //adapter.setfocus(focus)

            when(event.keyCode){
                19->{if(focus > 0)
                        {
                            focus--
                        }
                    }
                20->{if(focus < adapter.itemCount-1)
                        {
                            focus++
                        }
                    }
                21->{dailog_focus = 1}
                22->{dailog_focus = 0}
                66->{adapter.performClick(focus)}
            }

            adapter.setfocus(focus)
            rootview.Set_SpareSelect.scrollToPosition(focus)

            //r_List.get(focus).setBackgroundResource(R.drawable.corner)

            //if(focus!=0 || focus!=limit-1)
            //{
                //rootview.sc.scrollY = (120 * focus) / 2
            //}

        }
    }

}
