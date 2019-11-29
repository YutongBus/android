package com.orange.yutongbus.YounUart;

import static com.orange.yutongbus.YounUart.Command.StringHexToByte;

public class Version {
    public String version="";
    public String year="";
    public String month="";
    public String day="";
    public String VersionData="";
    public boolean issuccess=false;
    public void SetVerion(){
        String a=new String(StringHexToByte(VersionData));
        version=a.substring(0,2);
        year=a.substring(2,4);
        month=a.substring(4,6);
        day=a.substring(6,8);
    }
}
