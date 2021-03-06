package com.orange.yutongbus.Frag

import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.yutongbus.MainActivity

import com.orange.yutongbus.R
import com.orange.yutongbus.YounUart.Command
import kotlinx.android.synthetic.main.fragment_wheel_tag_up.*
import kotlinx.android.synthetic.main.fragment_wheel_tag_up.view.*
import kotlinx.android.synthetic.main.fragment_wheel_tag_up.view.T1
import java.lang.Thread.sleep
import java.util.ArrayList

class WheelTagUp : JzFragement(R.layout.fragment_wheel_tag_up) {

    companion object{
        var type=0;
        var 二轮配置=1;
        var 四轮配置=2;
        var 六轮配置=3;
        var 八轮配置中=4;
        var 八轮配置後=5;
        var 十轮配置=6;
    }
    var tirecount=0;
    var focus = 1

    var Triggerid=ArrayList<String>()
    var IDtext=ArrayList<TextView>()

    override fun viewInit() {

        UpdateUi()
        //Triggerid.remove("123456")
        rootview.SetUp.setOnClickListener {
            if(Triggerid.size==tirecount){
                (activity as MainActivity).Memory.Triggerid=Triggerid
                JzActivity.getControlInstance().changeFrag(CoolPressure(),R.id.frage,"CoolPressure",true)
            }else{Trigger()}

        }

        rootview.nsbt.setOnClickListener {

            if(SpareSelect.havespare){
                rootview.spbt.setBackgroundResource(R.color.white)
                rootview.nsbt.setBackgroundResource(R.color.blue)
                rootview.spbt.setTextColor(resources.getColor(R.color.black))
                rootview.nsbt.setTextColor(resources.getColor(R.color.white))
                rootview.t11.text=""
                rootview.spare.visibility=View.GONE

                //此為備胎模式時，已有偵測到備胎之判斷
                if(Triggerid.size == tirecount)
                {
                    Triggerid.remove(rootview.id11.text)
                    rootview.id11.text=""
                    //rootview.SetUp.text="下一步"
                }

                SpareSelect.havespare=false
                SpareSelect.ShowDialog=false

                //備胎與無備胎轉換
                TrunUi()

                //此從備胎轉換到無備胎時，已都偵測完輪胎之判斷
                if(Triggerid.size == tirecount)
                {
                    rootview.SetUp.text="下一步"
                }

            }

        }

        rootview.spbt.setOnClickListener {

            if(type != 二轮配置)
            {
                //從無備胎偵測完輪胎後，轉換到有備胎的判斷
                if (Triggerid.size == tirecount && id11.text == "")
                {
                    rootview.SetUp.text = "触发传感器"
                    rootview.spare.setImageResource(R.mipmap.img_tire_red)
                }

                SpareSelect.ShowDialog = false
                SpareSelect.havespare = true

                rootview.spbt.setBackgroundResource(R.color.blue)
                rootview.nsbt.setBackgroundResource(R.color.white)
                rootview.spbt.setTextColor(resources.getColor(R.color.white))
                rootview.nsbt.setTextColor(resources.getColor(R.color.black))

                rootview.t11.text = "备胎"
                rootview.spare.visibility = View.VISIBLE

                TrunUi()
            }
            else
            {
                JzActivity.getControlInstance().showDiaLog(R.layout.trigger_two_tire_error,true,false)
            }
        }

        IDtext.clear()
        Triggerid.clear()
        IDtext.add(rootview.id1)
        IDtext.add(rootview.id2)
        IDtext.add(rootview.id3)
        IDtext.add(rootview.id4)
        IDtext.add(rootview.id5)
        IDtext.add(rootview.id6)
        IDtext.add(rootview.id7)
        IDtext.add(rootview.id8)
        IDtext.add(rootview.id9)
        IDtext.add(rootview.id10)
        IDtext.add(rootview.id11)
        JzActivity.getControlInstance().setPro("tirecount", type)
        JzActivity.getControlInstance().setPro("havespare", SpareSelect.havespare)
    }

//    override fun onKeyTrigger() {
//        super.onKeyTrigger()
//        act.DaiLogDismiss()
//        Trigger()
//    }
    fun Trigger(){
        JzActivity.getControlInstance().showDiaLog(R.layout.loading_dialog,false,true)

        Thread{
val a=Command.Trigger()
            sleep(1000)
            handler.post {
                JzActivity.getControlInstance().closeDiaLog()
                if(!a.equals("false") && !Triggerid.contains(a) && Triggerid.size<tirecount){
                    Triggerid.add(a)
                    Set_Green(Triggerid.size-1)
                    if(Triggerid.size==tirecount&&SpareSelect.havespare)
                    {
                        IDtext.get(IDtext.size-1).text=a
                    }
                    else
                    {
                        IDtext.get(Triggerid.size-1).text=a
                        //rootview.T1.setBackgroundResource(R.mipmap.img_tire_green)
                    }
                    if(Triggerid.size==tirecount)
                    {
                        rootview.SetUp.text="下一步"
                    }
                }else{
if(a.equals("false")){
    JzActivity.getControlInstance().showDiaLog(R.layout.triggererror,true,false)
}else if(Triggerid.size>=tirecount){
    JzActivity.getControlInstance().showDiaLog(R.layout.trigerrexceed,true,false)
}else if(Triggerid.contains(a)){
    JzActivity.getControlInstance().showDiaLog(R.layout.triggeragain,true,false)
}
                }
            }
        }.start()

    }

    fun TrunUi(){
        when(type){
            二轮配置->{ rootview.carim.setImageResource(R.mipmap.img_two_tires)
                tirecount=2
                rootview.hintext.text=if(SpareSelect.havespare) "二轮配置" else "二轮配置"
            }
            四轮配置->{rootview.carim.setImageResource(if(SpareSelect.havespare) R.mipmap.img_four_tires_spare else R.mipmap.img_four_tires)
                tirecount=4
                rootview.hintext.text=if(SpareSelect.havespare) "四轮配置+备胎" else "四轮配置"}
            六轮配置->{rootview.carim.setImageResource(if(SpareSelect.havespare) R.mipmap.img_six_tires_spare else R.mipmap.img_six_tires)
                tirecount=6
                rootview.hintext.text=if(SpareSelect.havespare) "六轮配置+备胎" else "六轮配置"}
            八轮配置中->{rootview.carim.setImageResource(if(SpareSelect.havespare) R.mipmap.img_ei_tires_spare_center else R.mipmap.img_ei_tires_center)
                tirecount=4
                rootview.hintext.text=if(SpareSelect.havespare) "八轮配置+备胎" else "八轮配置"}
            八轮配置後->{rootview.carim.setImageResource(if(SpareSelect.havespare) R.mipmap.img_ei_tires_spare_back else R.mipmap.img_ei_tires_back)
                tirecount=8
                rootview.hintext.text=if(SpareSelect.havespare) "八轮配置+备胎" else "八轮配置"}
            十轮配置->{rootview.carim.setImageResource(if(SpareSelect.havespare) R.mipmap.img_ten_tires else R.mipmap.img_time_tires)
                tirecount=10
                rootview.hintext.text=if(SpareSelect.havespare) "十轮配置+备胎" else "十轮配置"}
        }
        if(SpareSelect.havespare){tirecount++}
        JzActivity.getControlInstance().setPro("havespare",SpareSelect.havespare)
    }

    fun UpdateUi(){

        SpareSelect.ShowDialog=false

        if(SpareSelect.havespare){
            rootview.spbt.setBackgroundResource(R.color.blue)
            rootview.nsbt.setBackgroundResource(R.color.white)
            rootview.spbt.setTextColor(resources.getColor(R.color.white))
            rootview.nsbt.setTextColor(resources.getColor(R.color.black))

        }else{
            rootview.spbt.setBackgroundResource(R.color.white)
            rootview.nsbt.setBackgroundResource(R.color.blue)
            rootview.spbt.setTextColor(resources.getColor(R.color.black))
            rootview.nsbt.setTextColor(resources.getColor(R.color.white))

        }

        TrunUi()
        ResetView()
    }

    fun Set_Green(position:Int)
    {
        val Tireimage = ArrayList<ImageView>()
        when(type)
        {
            二轮配置->{
            Tireimage.add(rootview.T1)
            Tireimage.add(rootview.T2)
                Tireimage.add(rootview.spare)
            }
            四轮配置->{
            Tireimage.add(rootview.T1)
            Tireimage.add(rootview.T2)
            Tireimage.add(rootview.T7)
            Tireimage.add(rootview.T10)
                Tireimage.add(rootview.spare)

            }
            六轮配置->{
            Tireimage.add(rootview.T1)
            Tireimage.add(rootview.T2)
            Tireimage.add(rootview.T7)
            Tireimage.add(rootview.T8)
            Tireimage.add(rootview.T9)
            Tireimage.add(rootview.T10)

                Tireimage.add(rootview.spare)
            }
            八轮配置中->{
            Tireimage.add(rootview.T1)
            Tireimage.add(rootview.T2)
            Tireimage.add(rootview.T7)
            Tireimage.add(rootview.T10)

                Tireimage.add(rootview.spare)
            }
            八轮配置後->{
            Tireimage.add(rootview.T1)
            Tireimage.add(rootview.T2)
            Tireimage.add(rootview.T3)
            Tireimage.add(rootview.T6)
            Tireimage.add(rootview.T7)
            Tireimage.add(rootview.T8)
            Tireimage.add(rootview.T9)
            Tireimage.add(rootview.T10)

                Tireimage.add(rootview.spare)
            }
            十轮配置->{
            Tireimage.add(rootview.T1)
            Tireimage.add(rootview.T2)
            Tireimage.add(rootview.T3)
            Tireimage.add(rootview.T4)
            Tireimage.add(rootview.T5)
            Tireimage.add(rootview.T6)
            Tireimage.add(rootview.T7)
            Tireimage.add(rootview.T8)
            Tireimage.add(rootview.T9)
            Tireimage.add(rootview.T10)

                Tireimage.add(rootview.spare)
            }
        }
        Tireimage[position].setImageResource(R.mipmap.img_tire_green)
    }

fun ResetView(){
    Triggerid.clear()
    rootview.SetUp.text="触发传感器"
    rootview.T1.setImageResource(R.mipmap.img_tire_red)
    rootview.T2.setImageResource(R.mipmap.img_tire_red)
    rootview.T3.setImageResource(R.mipmap.img_tire_red)
    rootview.T4.setImageResource(R.mipmap.img_tire_red)
    rootview.T5.setImageResource(R.mipmap.img_tire_red)
    rootview.T6.setImageResource(R.mipmap.img_tire_red)
    rootview.T7.setImageResource(R.mipmap.img_tire_red)
    rootview.T8.setImageResource(R.mipmap.img_tire_red)
    rootview.T9.setImageResource(R.mipmap.img_tire_red)
    rootview.T10.setImageResource(R.mipmap.img_tire_red)
    rootview.spare.setImageResource(R.mipmap.img_tire_red)

    rootview.t1.text=""
    rootview.t2.text=""
    rootview.t3.text=""
    rootview.t4.text=""
    rootview.t5.text=""
    rootview.t6.text=""
    rootview.t7.text=""
    rootview.t8.text=""
    rootview.t9.text=""
    rootview.t10.text=""
    rootview.t11.text=""
    rootview.id1.text=""
    rootview.id2.text=""
    rootview.id3.text=""
    rootview.id4.text=""
    rootview.id5.text=""
    rootview.id6.text=""
    rootview.id7.text=""
    rootview.id8.text=""
    rootview.id9.text=""
    rootview.id10.text=""
    rootview.id11.text=""
    rootview.T1.visibility=View.GONE
    rootview.T2.visibility=View.GONE
    rootview.T3.visibility=View.GONE
    rootview.T4.visibility=View.GONE
    rootview.T5.visibility=View.GONE
    rootview.T6.visibility=View.GONE
    rootview.T7.visibility=View.GONE
    rootview.T8.visibility=View.GONE
    rootview.T9.visibility=View.GONE
    rootview.T10.visibility=View.GONE

    if(SpareSelect.havespare){
        rootview.t11.text="备胎"
        rootview.spare.visibility=View.VISIBLE
        when(type){
            二轮配置->{
                rootview.T1.visibility=View.VISIBLE
                rootview.T2.visibility=View.VISIBLE
                rootview.T1.translationY= -12F
                rootview.T2.translationY= -12F
                rootview.number1.translationY= -12F
                rootview.number2.translationY= -12F
                rootview.t1.text="1"
                rootview.t2.text="2"
            }
            四轮配置->{
                rootview.T1.visibility=View.VISIBLE
                rootview.T2.visibility=View.VISIBLE
                rootview.T7.visibility=View.VISIBLE
                rootview.T10.visibility=View.VISIBLE
                rootview.T1.translationY= -12F
                rootview.T2.translationY= -12F
                rootview.T7.translationY= -15F
                rootview.T10.translationY= -15F
                rootview.number1.translationY= -12F
                rootview.number2.translationY= -12F
                rootview.number7.translationY= -15F
                rootview.number10.translationY= -15F
                rootview.t1.text="1"
                rootview.t2.text="2"
                rootview.t3.text="3"
                rootview.t4.text="4"
                rootview.number7.setImageResource(R.mipmap.trree)
                rootview.number10.setImageResource(R.mipmap.four)
            }
            六轮配置->{
                rootview.T1.visibility=View.VISIBLE
                rootview.T2.visibility=View.VISIBLE
                rootview.T7.visibility=View.VISIBLE
                rootview.T8.visibility=View.VISIBLE
                rootview.T9.visibility=View.VISIBLE
                rootview.T10.visibility=View.VISIBLE
                rootview.T1.translationY=-17F
                rootview.T2.translationY=-17F
                rootview.T7.translationY=-18F
                rootview.T8.translationY=-18F
                rootview.T9.translationY=-18F
                rootview.T10.translationY=-18F
                rootview.number1.translationY=-17F
                rootview.number2.translationY=-17F
                rootview.number7.translationY=-18F
                rootview.number8.translationY=-18F
                rootview.number9.translationY=-18F
                rootview.number10.translationY=-18F
                rootview.t1.text="1"
                rootview.t2.text="2"
                rootview.t3.text="3"
                rootview.t4.text="4"
                rootview.t5.text="5"
                rootview.t6.text="6"
                rootview.number7.setImageResource(R.mipmap.trree)
                rootview.number8.setImageResource(R.mipmap.four)
                rootview.number9.setImageResource(R.mipmap.five)
                rootview.number10.setImageResource(R.mipmap.six)
            }
            八轮配置中->{
                rootview.T1.visibility=View.VISIBLE
                rootview.T2.visibility=View.VISIBLE
                rootview.T7.visibility=View.VISIBLE
                rootview.T10.visibility=View.VISIBLE
                rootview.T1.translationY=-18F
                rootview.T2.translationY=-18F
                rootview.T7.translationY=-18F
                rootview.T10.translationY=-18F
                rootview.number1.translationY=-18F
                rootview.number2.translationY=-18F
                rootview.number7.translationY=-18F
                rootview.number10.translationY=-18F
                rootview.t1.text="1"
                rootview.t2.text="2"
                rootview.t3.text="3"
                rootview.t4.text="4"
                //rootview.T3.setImageResource(R.mipmap.img_tire_red)
                //rootview.T6.visibility=View.VISIBLE
                //rootview.T8.visibility=View.VISIBLE
                //rootview.T9.visibility=View.VISIBLE
                rootview.number7.setImageResource(R.mipmap.trree)
                rootview.number10.setImageResource(R.mipmap.four)
            }
            八轮配置後->{
                rootview.T1.visibility=View.VISIBLE
                rootview.T2.visibility=View.VISIBLE
                rootview.T3.visibility=View.VISIBLE
                rootview.T6.visibility=View.VISIBLE
                rootview.T7.visibility=View.VISIBLE
                rootview.T8.visibility=View.VISIBLE
                rootview.T9.visibility=View.VISIBLE
                rootview.T10.visibility=View.VISIBLE
                rootview.T1.translationY=-18F
                rootview.T2.translationY=-18F
                rootview.T3.translationY=-18F
                rootview.T6.translationY=-18F
                rootview.T7.translationY=-18F
                rootview.T8.translationY=-18F
                rootview.T9.translationY=-18F
                rootview.T10.translationY=-18F
                rootview.number1.translationY=-18F
                rootview.number2.translationY=-18F
                rootview.number3.translationY=-18F
                rootview.number6.translationY=-18F
                rootview.number7.translationY=-18F
                rootview.number8.translationY=-18F
                rootview.number9.translationY=-18F
                rootview.number10.translationY=-18F
                rootview.t1.text="1"
                rootview.t2.text="2"
                rootview.t3.text="3"
                rootview.t4.text="4"
                rootview.t5.text="5"
                rootview.t6.text="6"
                rootview.t7.text="7"
                rootview.t8.text="8"
                rootview.number6.setImageResource(R.mipmap.four)
                rootview.number7.setImageResource(R.mipmap.five)
                rootview.number8.setImageResource(R.mipmap.six)
                rootview.number9.setImageResource(R.mipmap.seven)
                rootview.number10.setImageResource(R.mipmap.eight)
            }
            十轮配置->{
                rootview.T1.visibility=View.VISIBLE
                rootview.T2.visibility=View.VISIBLE
                rootview.T3.visibility=View.VISIBLE
                rootview.T4.visibility=View.VISIBLE
                rootview.T5.visibility=View.VISIBLE
                rootview.T6.visibility=View.VISIBLE
                rootview.T7.visibility=View.VISIBLE
                rootview.T8.visibility=View.VISIBLE
                rootview.T9.visibility=View.VISIBLE
                rootview.T10.visibility=View.VISIBLE
                rootview.t1.text="1"
                rootview.t2.text="2"
                rootview.t3.text="3"
                rootview.t4.text="4"
                rootview.t5.text="5"
                rootview.t6.text="6"
                rootview.t7.text="7"
                rootview.t8.text="8"
                rootview.t9.text="9"
                rootview.t10.text="10"
                rootview.T1.translationY=-13F
                rootview.T2.translationY=-13F
                rootview.T3.translationY=-13F
                rootview.T4.translationY=-13F
                rootview.T5.translationY=-13F
                rootview.T6.translationY=-13F
                rootview.T7.translationY=-13F
                rootview.T8.translationY=-13F
                rootview.T9.translationY=-13F
                rootview.T10.translationY=-13F
                rootview.number1.translationY=-13F
                rootview.number2.translationY=-13F
                rootview.number3.translationY=-13F
                rootview.number4.translationY=-13F
                rootview.number5.translationY=-13F
                rootview.number6.translationY=-13F
                rootview.number7.translationY=-13F
                rootview.number8.translationY=-13F
                rootview.number9.translationY=-13F
                rootview.number10.translationY=-13F
            }
        }
    }else{
        rootview.spare.visibility=View.GONE
        when(type){
            二轮配置->{
                rootview.T1.visibility=View.VISIBLE
                rootview.T2.visibility=View.VISIBLE
                rootview.T1.translationY= -12F
                rootview.T2.translationY= -12F
                rootview.number1.translationY= -12F
                rootview.number2.translationY= -12F
                rootview.t1.text="1"
                rootview.t2.text="2"
            }
            四轮配置->{
                rootview.T1.visibility=View.VISIBLE
                rootview.T2.visibility=View.VISIBLE
                rootview.T7.visibility=View.VISIBLE
                rootview.T10.visibility=View.VISIBLE
                rootview.T1.translationY= -12F
                rootview.T2.translationY= -12F
                rootview.T7.translationY= -15F
                rootview.T10.translationY= -15F
                rootview.number1.translationY= -12F
                rootview.number2.translationY= -12F
                rootview.number7.translationY= -15F
                rootview.number10.translationY= -15F
                rootview.t1.text="1"
                rootview.t2.text="2"
                rootview.t3.text="3"
                rootview.t4.text="4"
                rootview.number7.setImageResource(R.mipmap.trree)
                rootview.number10.setImageResource(R.mipmap.four)
            }
            六轮配置->{
                rootview.T1.visibility=View.VISIBLE
                rootview.T2.visibility=View.VISIBLE
                rootview.T7.visibility=View.VISIBLE
                rootview.T8.visibility=View.VISIBLE
                rootview.T9.visibility=View.VISIBLE
                rootview.T10.visibility=View.VISIBLE
                rootview.T1.translationY=-22F
                rootview.T2.translationY=-22F
                rootview.T7.translationY=-23F
                rootview.T8.translationY=-23F
                rootview.T9.translationY=-23F
                rootview.T10.translationY=-23F
                rootview.number1.translationY=-22F
                rootview.number2.translationY=-22F
                rootview.number7.translationY=-23F
                rootview.number8.translationY=-23F
                rootview.number9.translationY=-23F
                rootview.number10.translationY=-23F
                rootview.t1.text="1"
                rootview.t2.text="2"
                rootview.t3.text="3"
                rootview.t4.text="4"
                rootview.t5.text="5"
                rootview.t6.text="6"
                rootview.number7.setImageResource(R.mipmap.trree)
                rootview.number8.setImageResource(R.mipmap.four)
                rootview.number9.setImageResource(R.mipmap.five)
                rootview.number10.setImageResource(R.mipmap.six)
            }
            八轮配置中->{
                rootview.T1.visibility=View.VISIBLE
                rootview.T2.visibility=View.VISIBLE
                rootview.T7.visibility=View.VISIBLE
                rootview.T10.visibility=View.VISIBLE
                rootview.T1.translationY=-27F
                rootview.T2.translationY=-27F
                rootview.T7.translationY=-29F
                rootview.T10.translationY=-29F
                rootview.number1.translationY=-27F
                rootview.number2.translationY=-27F
                rootview.number7.translationY=-29F
                rootview.number10.translationY=-29F
                rootview.t1.text="1"
                rootview.t2.text="2"
                rootview.t3.text="3"
                rootview.t4.text="4"
                rootview.number7.setImageResource(R.mipmap.trree)
                rootview.number10.setImageResource(R.mipmap.four)
            }
            八轮配置後->{
                rootview.T1.visibility=View.VISIBLE
                rootview.T2.visibility=View.VISIBLE
                rootview.T3.visibility=View.VISIBLE
                rootview.T6.visibility=View.VISIBLE
                rootview.T7.visibility=View.VISIBLE
                rootview.T8.visibility=View.VISIBLE
                rootview.T9.visibility=View.VISIBLE
                rootview.T10.visibility=View.VISIBLE
                rootview.T1.translationY=-27F
                rootview.T2.translationY=-27F
                rootview.T3.translationY=-27F
                rootview.T6.translationY=-27F
                rootview.T7.translationY=-27F
                rootview.T8.translationY=-27F
                rootview.T9.translationY=-27F
                rootview.T10.translationY=-27F
                rootview.number1.translationY=-27F
                rootview.number2.translationY=-27F
                rootview.number3.translationY=-27F
                rootview.number6.translationY=-27F
                rootview.number7.translationY=-27F
                rootview.number8.translationY=-27F
                rootview.number9.translationY=-27F
                rootview.number10.translationY=-27F
                rootview.t1.text="1"
                rootview.t2.text="2"
                rootview.t3.text="3"
                rootview.t4.text="4"
                rootview.t5.text="5"
                rootview.t6.text="6"
                rootview.t7.text="7"
                rootview.t8.text="8"
                rootview.number6.setImageResource(R.mipmap.four)
                rootview.number7.setImageResource(R.mipmap.five)
                rootview.number8.setImageResource(R.mipmap.six)
                rootview.number9.setImageResource(R.mipmap.seven)
                rootview.number10.setImageResource(R.mipmap.eight)
            }
            十轮配置->{
                rootview.T1.visibility=View.VISIBLE
                rootview.T2.visibility=View.VISIBLE
                rootview.T3.visibility=View.VISIBLE
                rootview.T4.visibility=View.VISIBLE
                rootview.T5.visibility=View.VISIBLE
                rootview.T6.visibility=View.VISIBLE
                rootview.T7.visibility=View.VISIBLE
                rootview.T8.visibility=View.VISIBLE
                rootview.T9.visibility=View.VISIBLE
                rootview.T10.visibility=View.VISIBLE
                rootview.t1.text="1"
                rootview.t2.text="2"
                rootview.t3.text="3"
                rootview.t4.text="4"
                rootview.t5.text="5"
                rootview.t6.text="6"
                rootview.t7.text="7"
                rootview.t8.text="8"
                rootview.t9.text="9"
                rootview.t10.text="10"
            }
        }
    }

}

    override fun dispatchKeyEvent(event: KeyEvent) {

        Log.e("key", "" + event.keyCode)
        if (event.action == KeyEvent.ACTION_UP) {
            if(event.keyCode == 21)
            {
                //focus=1
                spbt.performClick()
            }

            if(event.keyCode == 22)
            {
                //focus= 2
                nsbt.performClick()
            }

            if(event.keyCode == 66)
            {
                rootview.SetUp.performClick()
            }

            if(event.keyCode == 131)
            {
                //rootview.SetUp.performClick()
            }
        }
    }

}
