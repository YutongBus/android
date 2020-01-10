package com.orange.yutongbus.Frag

import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.yutongbus.MainActivity
import com.orange.yutongbus.Memory
import com.orange.yutongbus.R
import com.orange.yutongbus.YounUart.Command

class TriggerWriting : JzFragement(R.layout.fragment_trigger_writing) {

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

    override fun viewInit() {
        refresh=true
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
                    handler.post { JzActivity.getControlInstance().changeFrag(TriggerFalse(), R.id.frage, "TriggerFalse", true) }
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
                        JzActivity.getControlInstance().changeFrag(
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
                    handler.post { JzActivity.getControlInstance().changeFrag(TriggerFalse(), R.id.frage, "TriggerFalse", true) }
                }

                if (ceckversion == 1 ) {
                    handler.post { JzActivity.getControlInstance().changeFrag(TriggerFalse(), R.id.frage, "TriggerFalse", true) }
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
                                    handler.post { JzActivity.getControlInstance().changeFrag(TriggerFalse(), R.id.frage, "TriggerFalse", true) }
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
                                        handler.post { JzActivity.getControlInstance().changeFrag(TriggerFalse(), R.id.frage, "TriggerFalse", true) }
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
                        JzActivity.getControlInstance().changeFrag(
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
                    handler.post { JzActivity.getControlInstance().changeFrag(TriggerFalse(), R.id.frage, "TriggerFalse", true) }
                }
                if(ceckversion == 1)
                {
                    handler.post { JzActivity.getControlInstance().changeFrag(TriggerFalse(), R.id.frage, "TriggerFalse", true) }
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
                        JzActivity.getControlInstance().changeFrag(
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
                        JzActivity.getControlInstance().changeFrag(
                            if (success) TriggerSuccess() else TriggerFalse(),
                            R.id.frage,
                            if (success) {"TriggerSuccess"} else {"TriggerFalse"} ,
                            true
                        )
                    }
                }
            }
        }.start()
    }
}
