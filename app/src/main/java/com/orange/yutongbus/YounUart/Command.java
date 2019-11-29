package com.orange.yutongbus.YounUart;

import android.util.Log;
import com.orange.yutongbus.lib.hardware.HardwareApp;
import com.orange.yutongbus.lib.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static com.orange.yutongbus.lib.hardware.HardwareApp.hexStringToBytes;
import static com.orange.yutongbus.lib.hardware.HardwareApp.str_tobyte;

public class Command {
    public  static String Rx="";
    public static void send(String a){
        Rx="";
        byte [] data=GetCrc(a);
        HardwareApp.send(new byte[]{0x1B, 0x23, 0x23, 0x55, 0x54, 0x54, 0x32});
        HardwareApp.send(new byte[]{(byte) data.length});
        HardwareApp.send(data);
    }
    public static void ReOpen(){
        try{
            Log.e("DATA:","重新上電");
            HardwareApp.getInstance().open5V(false);
            Thread.sleep(500);
            HardwareApp.getInstance().open5V(false);
            Thread.sleep(500);
            HardwareApp.getInstance().open5V(false);
            Thread.sleep(500);
            HardwareApp.getInstance().open5V(true);
            Thread.sleep(500);
            HardwareApp.getInstance().open5V(true);
            Thread.sleep(500);
            HardwareApp.getInstance().open5V(true);
            Thread.sleep(500);
        }catch (Exception e){e.printStackTrace();}
    }
    public static byte[] GetCrc(String a){
        byte command[]=StringHexToByte(a);
        int xor=0;
        for (int i=0;i<command.length-1;i++){
            xor=xor + command[i];
        }
        command[command.length-1]=(byte)xor;
        return command;
    }
    public static Version GetVersion(){
        Version version=new Version();
        try{
            send("A18AFFFFFFFFFFFF1A31");
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            Date past=sdf.parse(sdf.format(new Date()));
            while(true){
                Date now=sdf.parse(sdf.format(new Date()));
                double time=getDatePoor(now,past);
                if(time>15||Rx.equals("F51C000301EB0A")){
                    if(time > 15){ReOpen();}
                    version.issuccess=false;
                    return  version;
                }
                if(Rx.length()==20){
                    version.VersionData=Rx.substring(14,16);
                    break;
                }
                Thread.currentThread().sleep(100);
            }
            send("A189FFFFFFFFFFFF1A32");
            past=sdf.parse(sdf.format(new Date()));
            while(true){
                Date now=sdf.parse(sdf.format(new Date()));
                double time=getDatePoor(now,past);
                if(time>15||Rx.equals("F51C000301EB0A")){
                    version.issuccess=false;
                    if(time > 15){ReOpen();}
                    return version;
                }
                if(Rx.length()==20){
                    version.VersionData=version.VersionData+Rx.substring(2,16);
                    version.issuccess=true;
                    version.SetVerion();
                    return version;
                }
                Thread.currentThread().sleep(100);
            }
        }catch (Exception e){  version.issuccess=false;
            return  version;}
    }
    public static boolean WriteId(String place,String ID){
        try{
            String insert=place+ID;
            send("A284IDFFFF2A6A".replace("ID",insert));
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            Date past=sdf.parse(sdf.format(new Date()));
            while(true){
                Date now=sdf.parse(sdf.format(new Date()));
                double time=getDatePoor(now,past);
                if(time>15){
                    ReOpen();
                    return false;}
                 if(Rx.length()==20){
                     return Rx.substring(4,12).equals(insert);
                 }
                Thread.currentThread().sleep(100);
            }
        }catch (Exception e){return false;}
    }
    public static boolean WriteSpId(String place,String ID){
        try{
            String insert=place+ID;
            send("A244IDFFFF2A6A".replace("ID",insert));
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            Date past=sdf.parse(sdf.format(new Date()));
            while(true){
                Date now=sdf.parse(sdf.format(new Date()));
                double time=getDatePoor(now,past);
                if(time>15){
                    ReOpen();
                    return false;}
                if(Rx.length()==20){
                    return Rx.substring(4,12).equals(insert);
                }
                Thread.currentThread().sleep(100);
            }
        }catch (Exception e){return false;}
    }
    public static String ReadMainId(String place){
        try{
            send("A288PFFFFFFFFFF2AFE".replace("P",place));
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            Date past=sdf.parse(sdf.format(new Date()));
            while(true){
                Date now=sdf.parse(sdf.format(new Date()));
                double time=getDatePoor(now,past);
                if(time>15||Rx.equals("F51C000301EB0A")){ if(time>15){ReOpen();}return "false";}
                if(Rx.length()==20){
                    return Rx.substring(6,12);
                }
            }
        }catch (Exception e){return "false";}
    }
    public static boolean WritePressure(int pressure){
        try {
            pressure=pressure/5;
            String pr=Integer.toHexString(pressure).toUpperCase();
            while(pr.length()<2){pr="0"+pr;}
            send("A384PFFFFFFFFFF3A86".replace("P",pr));
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            Date past=sdf.parse(sdf.format(new Date()));
            while(true){
                Date now=sdf.parse(sdf.format(new Date()));
                double time=getDatePoor(now,past);
                if(time>15||Rx.equals("F51C000301EB0A")){ if(time > 15){ReOpen();}return false;}
                if(Rx.length()==20){
                    return Rx.substring(4,6).equals(pr);
                }
                Thread.currentThread().sleep(100);
            }
        }catch (Exception e){e.printStackTrace();return false;}
    }
    public static int ReadPressure(){
        try {
            send("A388FFFFFFFFFFFF3A11");
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            Date past=sdf.parse(sdf.format(new Date()));
            while(true){
                Date now=sdf.parse(sdf.format(new Date()));
                double time=getDatePoor(now,past);
                if(time>15||Rx.equals("F51C000301EB0A")){ if(time>15){ReOpen();}return -1;}
                if(Rx.length()==20){
                    return StringHexToByte(Rx.substring(4,6))[0]*5;
                }
                Thread.currentThread().sleep(100);
            }
        }catch (Exception e){e.printStackTrace();return -1;}
    }
    public static String Trigger(){
        try{
            send("A4FFFFFFFFFFFFFF4A11");
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            Date past=sdf.parse(sdf.format(new Date()));
            while(true){
                Date now=sdf.parse(sdf.format(new Date()));
                double time=getDatePoor(now,past);
                if(time>15||Rx.equals("F51C000301EB0A")||Rx.equals("B4FFFFFFFFFFFFFFC16E")){ if(time>15){ReOpen();}return "false";}
                if(Rx.length()==20&&Rx.substring(16,18).equals("4B")){
                    return Rx.substring(2,8);
                }
                Thread.currentThread().sleep(100);
            }
        }catch (Exception e){Log.d("error",e.getMessage());return "false";}
    }
    public static String ReadSecId(String place){
        try{
            send("A248PFFFFFFFFFF2A3E".replace("P",place));
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            Date past=sdf.parse(sdf.format(new Date()));
            while(true){
                Date now=sdf.parse(sdf.format(new Date()));
                double time=getDatePoor(now,past);
                if(time>15||Rx.equals("F51C000301EB0A")){ if(time>15){ReOpen();}return "false";}
                if(Rx.length()==20){
                    return Rx.substring(6,12);
                }
            }
        }catch (Exception e){return "false";}
    }

    public static byte[] StringHexToByte(CharSequence cs){
        byte[] bytes = new byte[cs.length()/2];
        for (int i=0;i<(cs.length()/2);i++)
            bytes[i] = (byte) Integer.parseInt(cs.toString().substring(2*i,2*i+2),16);
        return bytes;
    }
    public static String bytesToHex(byte[] hashInBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
    public  static double getDatePoor(Date endDate, Date nowDate) {
        long diff = endDate.getTime() - nowDate.getTime();
        long sec = diff/1000;
        return sec;
    }
}
