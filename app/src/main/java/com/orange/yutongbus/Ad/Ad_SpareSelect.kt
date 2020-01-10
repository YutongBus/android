package com.orange.yutongbus.Ad

import bean.Make_SpareSelect
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzAdapter
import com.orange.yutongbus.Frag.SpareSelect
import com.orange.yutongbus.Frag.WheelTagUp
import com.orange.yutongbus.R
import kotlinx.android.synthetic.main.fragment_ad__spare_select.view.*

class Ad_SpareSelect (val myitem: Make_SpareSelect) : JzAdapter(R.layout.fragment_ad__spare_select) {

    var focus=0
    var click=-1
    fun setfocus(a:Int){
        focus=a
        this.notifyDataSetChanged()
    }
    fun performClick(a:Int){
        click=a
        this.notifyDataSetChanged()
    }

    override fun sizeInit(): Int {
        return myitem.item.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position==focus){
            holder.mView.r.setBackgroundResource((R.color.button_orange))
        }else{
            holder.mView.r.setBackgroundResource((R.color.button_blue))
        }

        holder.mView.t1.text = myitem.item[position]

        holder.mView.ManyTireSetImage.setBackgroundResource(myitem.item2[position])

        if(SpareSelect.havespare)
        {holder.mView.s1.text = "+备胎"}
        else
        {holder.mView.s1.text = ""}

        holder.mView.r.setOnClickListener {
            when (myitem.item[position]) {
                "二轮配置" -> {
                    WheelTagUp.type = WheelTagUp.二轮配置
                }
                "四轮配置" -> {
                    WheelTagUp.type = WheelTagUp.四轮配置
                }
                "六轮配置" -> {
                    WheelTagUp.type = WheelTagUp.六轮配置
                }
                "八轮配置" -> {
                    WheelTagUp.type = WheelTagUp.八轮配置中
                }
                "八轮配置 " -> {
                    WheelTagUp.type = WheelTagUp.八轮配置後
                }
                "十轮配置" -> {
                    WheelTagUp.type = WheelTagUp.十轮配置
                }
            }

            //holder.mView.r.setBackgroundResource(R.color.button_orange)

            JzActivity.getControlInstance().changeFrag(
                WheelTagUp(),
                R.id.frage, "WheelTagUp", false
            )
        }

        if(position==click){
            holder.mView.r.performClick()
            click=-1}
    }

}