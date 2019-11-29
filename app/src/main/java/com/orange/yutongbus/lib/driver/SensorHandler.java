package com.orange.yutongbus.lib.driver;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import bean.hardware.SensorDataBean;
import bean.hardware.SensorVersionBean;
import com.orange.yutongbus.lib.driver.channel.UARTChannel;
import com.orange.yutongbus.lib.driver.protocol.SensorProtocol;
import com.orange.yutongbus.lib.driver.source.SensorSource;
import com.orange.yutongbus.lib.hardware.HardwareApp;
import com.orange.yutongbus.lib.utils.StringUtils;
import cz.msebera.android.httpclient.util.EncodingUtils;
import cz.msebera.android.httpclient.util.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

import static android.media.AudioRecord.MetricsConstants.ENCODING;

/**
 * 传感器控制类
 */
public class SensorHandler extends BApi {

    public static String TAG = SensorHandler.class.getName();

    private static SensorHandler instance = null;

    SensorSource sensorSource;
    HardwareApp hardwareApp;

    public SensorHandler(HardwareApp hardwareApp) {
        this.sensorSource = new SensorSource(hardwareApp);
        this.hardwareApp = hardwareApp;
        this.sensorSource.setIChannel(new UARTChannel(hardwareApp));
    }

    public SensorHandler() {
    }

    public void init (HardwareApp hardwareApp) {
        this.sensorSource = new SensorSource(hardwareApp);
        this.sensorSource.setIChannel(new UARTChannel(hardwareApp));
    }

    public static SensorHandler getInstance (HardwareApp hardwareApp) {
        if (hardwareApp == null) {
            return instance;
        }
        if (instance == null) {
            instance = new SensorHandler(hardwareApp);
        } else {
        }
        return instance;
    }

    public static SensorHandler getInstance () {
        if (instance == null) {
            instance = new SensorHandler();
        } else {
        }
        return instance;
    }

    /**
     * 确认与模块正常联机 (用来做心跳, 发现异常则重新连接)
     * @return
     */
    @Deprecated
    public int ping () {
        int ret = this.sensorSource.handShake(new SensorSource.SensorRespondCB () {

            @Override
            public void receive(int ret) {

            }
        });
        return ret;
    }

    /**
     * 确认与模块正常联机 (用来做心跳, 发现异常则重新连接)
     * @return
     */
    public int ping (SensorSource.SensorRespondCB sensorRespondCB) {
        int ret = this.sensorSource.handShake(sensorRespondCB);
        return ret;
    }

    /**
     * 擦除PDA Flash
     * @return
     */
    @Deprecated
    public int eraseFlash () {
        int ret = this.sensorSource.eraseFlash(new SensorSource.SensorRespondCB () {

            @Override
            public void receive(int ret) {

            }
        });
        return ret;
    }

    public int eraseFlash (SensorSource.SensorRespondCB sensorRespondCB) {
        int ret = this.sensorSource.eraseFlash(sensorRespondCB);
        return ret;
    }

    /**
     * 读取所有传感器信息
     * num 目前只支持1
     * @return
     */
    public SensorDataBean[] readSensor (int num, String hex, SensorSource.SensorDataBeansCb sensorDataBeansCb) {
        return this.sensorSource.readSensor(num, hex, sensorDataBeansCb);
    }

    @Deprecated
    public SensorDataBean[] readSensor (int num, String hex) {
        return null;
    }

    /**
     * 读取软件版本
     * @return
     */
    public SensorVersionBean getSoftwareVersion (SensorSource.SensorVersionCb sensorVersionCb) {
        return this.sensorSource.getVersion(sensorVersionCb);
    }

    /**
     * 读取硬件版本
     * @return
     */
    public SensorVersionBean getHardwareVersion (SensorSource.SensorVersionCb sensorVersionCb) {
        return this.sensorSource.getHardwareVersion(sensorVersionCb);
    }

    /**
     * 写入Flash
     */
    private int writeFlash (byte[] inputData, SensorSource.SensorRespondCB sensorRespondCB) {
        return this.sensorSource.writeFlash (inputData, sensorRespondCB);
    }



    /**
     * 复制传感器id
     * @return
     */
    public int copySensorId (String srcId, String descId, SensorSource.SensorRespondCB sensorRespondCB) {
        Log.d(TAG, "srcId:"+srcId+",descId:"+descId);
        byte[] srcId_bytes = StringUtils.hexStrToByteArray(srcId);
        byte[] descId_bytes = StringUtils.hexStrToByteArray(descId);

        if (srcId.length() < 6 || srcId.length() > 8) {
            // 返回错误
            return -1;
        }
        if (srcId.length() < 6 || srcId.length() > 8) {
            return -1;
        }
        byte[] inputData = new byte[12];
        int j=0;
        for (int i=0;i<srcId_bytes.length;i++) {
            inputData[j++] = srcId_bytes[i];
        }
        inputData[j++] = (byte)srcId.length();
        for (int i=0;i<descId_bytes.length;i++) {
            inputData[j++] = descId_bytes[i];
        }
        inputData[j++] = (byte)descId.length();
        inputData[j++] = 0x00;
        inputData[j++] = 0x00;
        Log.d(TAG, "copySensorId: "+StringUtils.byteArrayToHexStr(inputData));
        return this.sensorSource.copySensorId(inputData, sensorRespondCB);
    }

    //通过反射获取ro.serialno
    private String getSerialNumber(){
        String serial = "unknown";
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            String barcode = (String) get.invoke(c, "gsm.serial");
            String[] code = barcode.split(" ");
            serial = code[0];
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

    /**
     * 获取硬件信息
     * @return
     */
    public HardwareInfo getHardwareInfo () {
        HardwareInfo info = new HardwareInfo();
        info.serianNum = getSerialNumber();
        info.model = Build.MODEL;
        info.hardwareVersion = Build.VERSION.RELEASE;
        return info;
    }

    /**
     * 烧录Flash, 自动reboot
     * @return
     */
    public int wirteFlashWithReboot (FlashWriteProgress flashWriteProgress) {
        SensorHandler sensorHandler = this;
        byte[] buffer = sensorHandler.getAssetsFileStream(hardwareApp.app, "OGPDA_V1.2_190827.x2");

        sensorHandler.ping(ret -> {
            if (ret == 1) {
                // bootloader模式, 直接烧录Flash
                sensorHandler.writeFlash (buffer, 0, flashWriteProgress);
            } else {
                sensorHandler.reboot(tmp_ret -> {
                    if (tmp_ret == 0x01) {
                        // 成功清理Flash, 准备烧录
                        sensorHandler.writeFlash(buffer, 0, flashWriteProgress);
                    } else {
                        flashWriteProgress.fail(tmp_ret, 0, 0);
                        sensorHandler.ping(tmp2_ret -> {
                            if (tmp2_ret == 1) {
                                // bootloader模式, 直接烧录Flash
                                sensorHandler.writeFlash (buffer, 0, flashWriteProgress);
                            }
                        });
                    }
                });
            }
        });
        return 0;
    }

    /**
     * 烧录Flash, 自动reboot
     * @return
     */
    public int wirteFlashWithReboot (String filePath,FlashWriteProgress flashWriteProgress) {
        SensorHandler sensorHandler = this;
        byte[] buffer = sensorHandler.getExternalFileStream(hardwareApp.app, filePath);

        sensorHandler.ping(ret -> {
            if (ret == 1) {
                // bootloader模式, 直接烧录Flash
                sensorHandler.writeFlash (buffer, 0, flashWriteProgress);
            } else {
                sensorHandler.reboot(tmp_ret -> {
                    if (tmp_ret == 0x01) {
                        // 成功清理Flash, 准备烧录
                        sensorHandler.writeFlash(buffer, 0, flashWriteProgress);
                    } else {
                        flashWriteProgress.fail(tmp_ret, 0, 0);
                    }
                });
            }
        });
        return 0;
    }

    /**
     * 写入Flash bytes
     * 可加入线程同步把递归改成非递归方式
     * @return
     */
    private int write_buf_len = 0;
    private int writeFlash (byte[] buffer, int start, FlashWriteProgress flashWriteProgress) {
        int j_data = 0;      // 输入数据下标
        int max_write_buf_len = 132;
        byte[] data = new byte[1];

        // 重启reboot
        write_buf_len = max_write_buf_len;
        for (int i=start;i<buffer.length;i++) {
            if (i%max_write_buf_len==0) {
                // 创建新data buf
                int i_lack_byte = buffer.length-i;  // 剩余buf字节
                if ((i_lack_byte) >= max_write_buf_len) {
                    write_buf_len = max_write_buf_len;
                    data = new byte[write_buf_len];
                } else {
                    write_buf_len = i_lack_byte;
                    data = new byte[write_buf_len];
                }
                j_data = 0;
                Log.d(TAG, String.format("write %s", data.length));
                Log.d(TAG, String.format("write from %s", start));

                if (start == 0) {
                    flashWriteProgress.start(buffer.length);
                }
                // 写入
                /* try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
            data[j_data++] = buffer[i];
            if (j_data == write_buf_len) {
                // 存储到二维数组内
                sensorSource.writeFlash(data, ret -> {
                    // 收到成功的回调后继续下一条消息
                    Log.d(TAG, String.format("ret:%s", ret));
                    if (ret == 0) {
                        // 得到烧录成功的回调
                        flashWriteProgress.progress(start+write_buf_len, buffer.length);
                        writeFlash (buffer, start+write_buf_len, flashWriteProgress);
                    } else if (ret == 0x0b) {
                        // 烧录完成的响应
                        flashWriteProgress.finish(buffer.length);
                    } else {
                        flashWriteProgress.fail(ret, start, buffer.length);
                    }
                });
                break;
            }
        }
        return 0;
        // 二维数组逐行发送
        // sensorHandler.ping(ret -> Log.d(TAG, "ret:"+ret));
        // sensorHandler.writeFlash(data, ret -> Log.d(TAG, String.format("ret:%s", ret)));
    }

    /**
     * 重启
     * @return
     */
    public int reboot (SensorSource.SensorRespondCB sensorRespondCB) {
        return this.sensorSource.reboot(sensorRespondCB);
    }

    public int programCheck (SensorSource.SensorRespondCB sensorRespondCB) {
        return this.sensorSource.programCheck(sensorRespondCB);
    }

    /**
     * 获取烧录传感器配置
     * @包括: 需要烧录多少颗传感器、需要烧录的总大小
     */
    public int writeSensorFirmwareSetting (byte[] buffer, SensorSource.SensorWriteInfo sensorWriteInfo) {
        // 烧录传感器
        byte[] data = new byte[12];
        data[0] = 0x02;
        data[1] = 0x01;
        data[2] = 0x00;
        data[3] = 0x2C;
        data[4] = buffer[7];
        data[5] = buffer[8];
        data[6] = buffer[11];
        data[7] = buffer[12];
        data[8] = 0x00;
        data[9] = 0x00;
        data[10] = 0x00;
        data[11] = 0x00;
        return this.sensorSource.writeSensorFirmwareSetting (sensorWriteInfo, data);
    }

    /**
     * 烧录传感器返回进度
     * @return
     */
    public int writeSensorFirmwareWithProgress (FlashWriteProgress flashWriteProgress) {
        String content = getSensorByteFromStrStream(hardwareApp.app, "2C_V0.3.s19");
        byte[] buffer = StringUtils.hexStrToByteArray(content);
        Log.d(TAG, "buffer len: "+buffer.length);

        flashWriteProgress.start(buffer.length);
        // 烧录传感器
        writeSensorFirmwareSetting(buffer, (ret, sensorWriteInfoBean) -> {
            // 打印
            if (ret == 0) {
                Log.d(TAG, String.format("writeSensorFirmareSetting => byteNum%s, num:%s", sensorWriteInfoBean.getByteNum(),
                        1));
                writeSensorFirmware(buffer, 0, sensorWriteInfoBean.getByteNum(),
                        flashWriteProgress);
                return ;
            }
        });
        return 0;
    }

    /**
     * 烧录传感器返回进度
     * @return
     */
    public int writeSensorFirmwareWithProgress (String filePath,FlashWriteProgress flashWriteProgress) {
        String content = null;
        if(TextUtils.isEmpty(filePath)){
            content = getSensorByteFromStrStream(hardwareApp.app, "2C_V0.3.s19");
        }else{
            content = getSensorByteFromStrStreamWithPath(hardwareApp.app, filePath);
        }
        byte[] buffer = StringUtils.hexStrToByteArray(content);
        Log.d(TAG, "buffer len: "+buffer.length);

        flashWriteProgress.start(buffer.length);
        // 烧录传感器
        writeSensorFirmwareSetting(buffer, (ret, sensorWriteInfoBean) -> {
            // 打印
            if (ret == 0) {
                Log.d(TAG, String.format("writeSensorFirmareSetting => byteNum%s, num:%s", 2048,
                        1));
                writeSensorFirmware(buffer, 0, sensorWriteInfoBean.getByteNum(),
                        flashWriteProgress);
                return ;
            }
        });
        return 0;
    }

    /**
     * 烧录传感器
     */
    private int writeSensorFirmware (byte[] buffer, int start, int end, FlashWriteProgress flashWriteProgress) {
        int j_data = 0;      // 输入数据下标
        int max_write_buf_len = 200;
        byte[] data = new byte[1];

        // 获取第几个200byte
        int index = (int) (Math.ceil((double)(start/max_write_buf_len))+1);

        // 重启reboot
        if ((end-start) >= max_write_buf_len) {
            write_buf_len = max_write_buf_len;
        } else {
            write_buf_len = end-start;
        }

        // 写入进度
        flashWriteProgress.progress(start, end);

        // 完成
        if (start >= end) {
            flashWriteProgress.finish(end);
            return 1;
        }

        for (int i=start;i<end;i++) {
            if (i%max_write_buf_len==0) {
                // 创建新data buf
                int i_lack_byte = end-i;  // 剩余buf字节
                if ((i_lack_byte) >= max_write_buf_len) {
                    write_buf_len = max_write_buf_len;
                } else {
                    write_buf_len = i_lack_byte;
                }
                data = new byte[write_buf_len];
                j_data = 0;
                Log.d(TAG, String.format("write %s", data.length));
            }
            data[j_data++] = buffer[i];
            if (j_data == write_buf_len) {
                // 存储到二维数组内
                sensorSource.writeSensorFireware(ret -> {
                    Log.d(TAG, String.format("ret:%s", ret));
                    if ((start+write_buf_len)==end) {
                        flashWriteProgress.finish(end);       // 完成
                    }
                    if (ret >= 0 && ret != SensorProtocol.CMDCODE.TIMEOUT_OR_ERR) {
                        // 格式符合
                        writeSensorFirmware(buffer, start + write_buf_len, end, flashWriteProgress);
                    } else {
                        Log.d(TAG, String.format("ret error:%s", ret));
                    }
                }, data, index);
                break;
            }
        }
        return 0;
    }

    /**
     * 更新模组固件
     */
    public void updatePDAFirmware () {

    }

    /**
     * 获取Flash固件字节
     * @return
     */
    public byte[] getAssetsFileStream (Context context, String fileName) {
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            //获取文件的字节数
            int length = in.available();
            //创建byte数组
            byte[] buffer = new byte[length];
            //将文件中的数据读到byte数组中
            in.read(buffer);
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Flash固件字节
     * @return
     */
    public byte[] getExternalFileStream (Context context, String fileName) {
        try {
            InputStream in = new FileInputStream(new File(fileName));
            // InputStream in = context.getResources().getAssets().open(fileName);
            //获取文件的字节数
            int length = in.available();
            //创建byte数组
            byte[] buffer = new byte[length];
            //将文件中的数据读到byte数组中
            in.read(buffer);
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符字节文件
     * @return
     */
    public String getSensorByteFromAssetsStrStream (Context context, String fileName) {
        byte[] buffer = this.getAssetsFileStream(context, fileName);
        String result = EncodingUtils.getString(buffer, ENCODING);
        result = StringUtils.replaceBlank(result);
        return result;
    }

    /**
     * 字符字节文件
     * @return
     */
    private String getSensorByteFromStrStream (Context context, String fileName) {
        byte[] buffer = this.getAssetsFileStream(context, fileName);
        String result = EncodingUtils.getString(buffer, ENCODING);
        result = StringUtils.replaceBlank(result);
        return result;
    }

    /**
     * 字符字节文件
     * @return
     */
    private String getSensorByteFromStrStreamWithPath (Context context, String filePath) {
        byte[] buffer = this.getExternalFileStream(context, filePath);
        String result = EncodingUtils.getString(buffer, ENCODING);
        result = StringUtils.replaceBlank(result);
        return result;
    }

    public static class HardwareInfo {

        public String serianNum = "";       // 序列号
        public String model = "";           // 机型型号
        public String hardwareVersion = "";     // 硬件版本

    }

    /**
     * Flash烧录进度
     */
    public static interface FlashWriteProgress {

        public void start(int total);        // 开始烧录
        public void progress(int progress, int total);     // 烧录进度
        public void finish(int total);      // 烧录完成
        public void fail(int errcode, int start, int total);     // 烧录失败, start:失败的烧录起始点
    }
}
