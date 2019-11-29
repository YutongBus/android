package com.orange.yutongbus

import java.util.ArrayList

class Memory{
    companion object{
        internal var 兩輪順序 = arrayOf(1,0,2)
        internal var 四輪順序 = arrayOf(1,3,2,0,4)
        internal var 新八輪順序 = arrayOf(1,3,2,0,4)
        internal var 六輪順序 = arrayOf(1,5,4,3,2,0,6)
        internal var 八輪順序 = arrayOf(1,5,4,7,6,3,2,0,8)
        internal var 十輪順序 = arrayOf(1,5,4,9,8,7,6,3,2,0,10)
        internal var 兩輪HEX = arrayOf("01","02","80")
        internal var 四輪HEX = arrayOf("01","02","03","04","80")
        internal var 新八輪HEX = arrayOf("01","04","05","08","80")
        internal var 六輪HEX = arrayOf("01","02","03","04","05","06","80")
        internal var 八輪HEX = arrayOf("01","02","03","04","05","06","07","08","80")
        internal var 十輪HEX = arrayOf("01","02","03","04","05","06","07","08","09","0A","80")
    }
    var Triggerid= ArrayList<String>() //儲存Trigger Id變數值
    var Pressure=0
}