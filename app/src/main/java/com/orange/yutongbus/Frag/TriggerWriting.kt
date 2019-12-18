package com.orange.yutongbus.Frag


import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.parser.IntegerParser
import com.orange.blelibrary.blelibrary.Callback.DaiSetUp
import com.orange.blelibrary.blelibrary.RootFragement
import com.orange.yutongbus.MainActivity
import com.orange.yutongbus.Memory

import com.orange.yutongbus.R
import com.orange.yutongbus.YounUart.Command
import com.orange.yutongbus.YounUart.Command.StringHexToByte
import kotlinx.android.synthetic.main.fragment_trigger_writing.view.*
import java.lang.Thread.sleep

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TriggerWriting : RootFragement() {

    companion object{
        var error_type=0;
        val 錯誤=1;
        val TIME_OUT=4;
        val 版本不一樣=2;
        val 皆為舊或新=3;

        var Main_or_Auxiliary ="";
        val 主機="主机";
        val 主副機="主机，副机";
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview=inflater.inflate(R.layout.fragment_trigger_writing, container, false)
        Thread{
                    var success=false
                    val memory=(activity as MainActivity).Memory
                    var wheel=WheelTagUp.type
                    var wheel_length=memory.Triggerid.size
                    var ceckversion=Command.CheckVersion(wheel,wheel_length)

                    if(WheelTagUp.type ==WheelTagUp.二轮配置 ||WheelTagUp.type ==WheelTagUp.四轮配置)
                    {
                        Main_or_Auxiliary = 主機
                        if (ceckversion == 0) {
                            handler.post { act.ChangePage(TriggerFalse(), R.id.frage, "TriggerFalse", true) }
                        }

                        if (ceckversion == 2 || ceckversion == 3) {

                            for (i in 0 until memory.Triggerid.size) {
                                when (WheelTagUp.type) {
                                    WheelTagUp.二轮配置 -> {
                                        success = Command.WriteId(
                                            Memory.兩輪HEX[i],
                                            memory.Triggerid[Memory.兩輪順序[i]]
                                        )
                                    }


                                    WheelTagUp.四轮配置 -> {
                                        success = Command.WriteId(
                                            Memory.四輪HEX[i],
                                            memory.Triggerid[Memory.四輪順序[i]]
                                        )
                                    }

                                }

                                if (!success) {
                                    break
                                }
                            }

                            /*
                        if (success) {
                            for (i in 0 until memory.Triggerid.size) {
                                success = Command.WriteSpId(Memory.兩輪HEX[i], memory.Triggerid[Memory.兩輪順序[i]])
                                if (!success) {
                                    break
                                }
                            }
                        }
                        */
                            if (success) {
                                success = Command.WritePressure(memory.Pressure)
                            }

                            handler.post {
                                act.ChangePage(
                                    if (success) TriggerSuccess()
                                    else TriggerFalse(),
                                    R.id.frage,
                                    if (success) {
                                        "TriggerSuccess"
                                    } else {
                                        "TriggerFalse"
                                    },
                                    true
                                )
                            }
                        }

                    }
            if(WheelTagUp.type ==WheelTagUp.八轮配置中 || WheelTagUp.type ==WheelTagUp.八轮配置後 ||WheelTagUp.type ==WheelTagUp.十轮配置)
            {
                        //var ceckversion=Command.CheckVersion(wheel,wheel_length)
                if (ceckversion == 0) {
                    handler.post { act.ChangePage(TriggerFalse(), R.id.frage, "TriggerFalse", true) }
                }

                if (ceckversion == 1 ) {
                    handler.post { act.ChangePage(TriggerFalse(), R.id.frage, "TriggerFalse", true) }
                }

                if (ceckversion == 2 || ceckversion == 3)
                {
                    Main_or_Auxiliary = 主副機

                     for (i in 0 until memory.Triggerid.size) {
                       when (WheelTagUp.type) {

                           WheelTagUp.十轮配置 -> {
                               if(ceckversion == 3) {
                                   success = Command.WriteId(Memory.十輪HEX[i], memory.Triggerid[Memory.十輪順序[i]])
                               }
                               else
                               {
                                   handler.post { act.ChangePage(TriggerFalse(), R.id.frage, "TriggerFalse", true) }
                               }
                           }
                           WheelTagUp.八轮配置後 -> {
                               success = Command.WriteId(Memory.八輪HEX[i], memory.Triggerid[Memory.八輪順序[i]])
                           }
                           WheelTagUp.八轮配置中 -> {
                               success = Command.WriteId(Memory.新八輪HEX[i], memory.Triggerid[Memory.新八輪順序[i]])
                           }
                           //WheelTagUp.二轮配置 -> {
                           //version_M=StringHexToByte(M_version.substring(0,2))[0]>=13
                           //success =
                           //Command.WriteId(Memory.兩輪HEX[i], memory.Triggerid[Memory.兩輪順序[i]])
                           //}
                           //WheelTagUp.六轮配置 -> {
                               //success = Command.WriteId(Memory.六輪HEX[i], memory.Triggerid[Memory.六輪順序[i]])
                           //}
                           //WheelTagUp.四轮配置 -> {
                               //success = Command.WriteId(Memory.四輪HEX[i], memory.Triggerid[Memory.四輪順序[i]])
                           //}
                       }
                       if (!success) {
                           break
                       }
                   }
                    if (success) {
                        for (i in 0 until memory.Triggerid.size) {
                            when (WheelTagUp.type) {
                                WheelTagUp.十轮配置 -> {
                                    if(ceckversion == 3) {
                                        success = Command.WriteSpId(Memory.十輪HEX[i], memory.Triggerid[Memory.十輪順序[i]])
                                    }
                                    else
                                    {
                                        handler.post { act.ChangePage(TriggerFalse(), R.id.frage, "TriggerFalse", true) }
                                    }
                                }
                                WheelTagUp.八轮配置後 -> {
                                    success = Command.WriteSpId(Memory.八輪HEX[i], memory.Triggerid[Memory.八輪順序[i]])
                                }
                                WheelTagUp.八轮配置中 -> {
                                    success = Command.WriteSpId(Memory.新八輪HEX[i], memory.Triggerid[Memory.新八輪順序[i]])
                                }
                                //WheelTagUp.二轮配置 -> {
                                //success = Command.WriteSpId(
                                //Memory.兩輪HEX[i],
                                //memory.Triggerid[Memory.兩輪順序[i]]
                                //)
                                //}
                                //WheelTagUp.六轮配置 -> {
                                    //success = Command.WriteSpId(Memory.六輪HEX[i], memory.Triggerid[Memory.六輪順序[i]])
                                //}
                                //WheelTagUp.四轮配置 -> {
                                   // success = Command.WriteSpId(Memory.四輪HEX[i], memory.Triggerid[Memory.四輪順序[i]])
                                //}
                            }
                            if (!success) {
                                break
                            }
                        }
                    }
                    if (success) {
                        success = Command.WritePressure(memory.Pressure)
                    }
                    handler.post {
                        //                act.ChangePage(TriggerSuccess(),R.id.frage,"TriggerSuccess",true)
                        act.ChangePage(
                            if (success) TriggerSuccess() else TriggerFalse(),
                            R.id.frage,
                            if (success) "TriggerSuccess" else "TriggerFalse",
                            true
                        )
                    }
                }

            }

            if(WheelTagUp.type ==WheelTagUp.六轮配置)
            {

                if(ceckversion == 0)
                {
                    handler.post { act.ChangePage(TriggerFalse(), R.id.frage, "TriggerFalse", true) }
                }
                if(ceckversion == 1)
                {
                    handler.post { act.ChangePage(TriggerFalse(), R.id.frage, "TriggerFalse", true) }
                }
                if(ceckversion == 2 || ceckversion == 3)
                {
                    Main_or_Auxiliary = 主副機
                    for (i in 0 until memory.Triggerid.size) {
                        when(WheelTagUp.type)
                        {
                            WheelTagUp.六轮配置->
                            {success = Command.WriteId(Memory.六輪HEX[i], memory.Triggerid[Memory.六輪順序[i]])}
                        }

                        if (!success) {
                            break
                        }
                    }

                    if (success) {
                        for (i in 0 until memory.Triggerid.size) {
                            success = Command.WriteSpId(Memory.六輪HEX[i], memory.Triggerid[Memory.六輪順序[i]])
                            if (!success) {
                                break
                            }
                        }
                    }

                    if (success) {
                        success = Command.WritePressure(memory.Pressure)
                    }
                    handler.post {
                        act.ChangePage(
                            if (success) TriggerSuccess() else TriggerFalse(),
                            R.id.frage,
                            if (success) {"TriggerSuccess"} else {"TriggerFalse"} ,
                            true
                        )
                    }
                }
                if(ceckversion == 4)
                {
                    Main_or_Auxiliary = 主機
                    for (i in 0 until memory.Triggerid.size)
                    {
                        success = Command.WriteId(Memory.六輪HEX[i], memory.Triggerid[Memory.六輪順序[i]])

                        if (!success) {
                            break
                        }
                    }
                    if (success) {
                        success = Command.WritePressure(memory.Pressure)
                    }
                    handler.post {
                        act.ChangePage(
                            if (success) TriggerSuccess() else TriggerFalse(),
                            R.id.frage,
                            if (success) {"TriggerSuccess"} else {"TriggerFalse"} ,
                            true
                        )
                    }
                }
            }
        }.start()
        return rootview
    }

    override fun onPause() {
        super.onPause()
    }
    override fun onDestroy() {
        super.onDestroy()
    }
}
