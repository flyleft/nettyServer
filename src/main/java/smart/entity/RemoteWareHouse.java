package smart.entity;

import org.springframework.data.annotation.Id;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by jcala on 2016/5/12
 */
public class RemoteWareHouse {
    @Id String id;
    String remotename;
    String no;
    String version;
    String devicetype;
    String descript;
    String saveurl;
    long user;
    Date updatetime;
    int timescale;
    int irfrq;
    String one;
    String zero;
    String s;
    String a;
    String b;
    String c;
    String d;
    String e;
    String bootcode;
    String poweroffcode;
    String matchcode;
    AcType accode;
    CommKeyValue[] commoncode;

    public Date getUpdatetime() {
        return updatetime;
    }

    @Override
    public String toString() {
        return "RemoteWareHouse{" +
                "id='" + id + '\'' +
                ", remotename='" + remotename + '\'' +
                ", no='" + no + '\'' +
                ", version='" + version + '\'' +
                ", devicetype='" + devicetype + '\'' +
                ", descript='" + descript + '\'' +
                ", saveurl='" + saveurl + '\'' +
                ", user=" + user +
                ", updatetime=" + updatetime +
                ", timescale=" + timescale +
                ", irfrq=" + irfrq +
                ", one='" + one + '\'' +
                ", zero='" + zero + '\'' +
                ", s='" + s + '\'' +
                ", a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c='" + c + '\'' +
                ", d='" + d + '\'' +
                ", e='" + e + '\'' +
                ", bootcode='" + bootcode + '\'' +
                ", poweroffcode='" + poweroffcode + '\'' +
                ", matchcode='" + matchcode + '\'' +
                ", accode=" + accode +
                ", commoncode=" + Arrays.toString(commoncode) +
                '}';
    }
}

class RemoteControlModel {
    int[] startaddr;
    String[] code;
    int[] bitLength;

    public RemoteControlModel(int[] startaddr, String[] code, int[] bitLength) {
        this.startaddr = startaddr;
        this.code = code;
        this.bitLength = bitLength;
    }

    @Override
    public String toString() {
        return "RemoteControlModel{" +
                "startaddr=" + Arrays.toString(startaddr) +
                ", code=" + Arrays.toString(code) +
                ", bitLength=" + Arrays.toString(bitLength) +
                '}';
    }
}

class AcType {
    String[][] temperaturedata;
    int[][] tempsart;
    int[][] tempbitLen;
    RemoteControlModel poweroffmodel;
    RemoteControlModel bootmodel;
    RemoteControlModel windsweepleftright;
    RemoteControlModel windsweepupdown;
    RemoteControlModel windautomodel;
    RemoteControlModel windhighmodel;
    RemoteControlModel windmidmodel;
    RemoteControlModel windlowmodel;
    RemoteControlModel automodel;
    RemoteControlModel hotmodel;
    RemoteControlModel codemodel;
    RemoteControlModel ventilatemodel;
    RemoteControlModel dehumidifymodel;
    String defaultcode;

    @Override
    public String toString() {
        return "AcType{" +
                "temperaturedata=" + Arrays.toString(temperaturedata) +
                ", tempsart=" + Arrays.toString(tempsart) +
                ", tempbitLen=" + Arrays.toString(tempbitLen) +
                ", poweroffmodel=" + poweroffmodel +
                ", bootmodel=" + bootmodel +
                ", windsweepleftright=" + windsweepleftright +
                ", windsweepupdown=" + windsweepupdown +
                ", windautomodel=" + windautomodel +
                ", windhighmodel=" + windhighmodel +
                ", windmidmodel=" + windmidmodel +
                ", windlowmodel=" + windlowmodel +
                ", automodel=" + automodel +
                ", hotmodel=" + hotmodel +
                ", codemodel=" + codemodel +
                ", ventilatemodel=" + ventilatemodel +
                ", dehumidifymodel=" + dehumidifymodel +
                ", defaultcode='" + defaultcode + '\'' +
                '}';
    }
}

//键值对结构体
class CommKeyValue{
    String key;
    String value;

    public CommKeyValue(String value, String key) {
        this.value = value;
        this.key = key;
    }

    @Override
    public String toString() {
        return "CommKeyValue{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}