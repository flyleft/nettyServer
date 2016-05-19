package smart.entity;

import io.netty.channel.Channel;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by jcala on 2016/4/29
 */
public class Logic {
    public static class CodeTimeType {
        private int TimeScale;
        private int IRFRQ;
        private String OpType;

        public void setTimeScale(int timeScale) {
            TimeScale = timeScale;
        }

        public void setIRFRQ(int IRFRQ) {
            this.IRFRQ = IRFRQ;
        }
    }

    public static class DeviceScript {
        private int Mask;
        private long UnixTime;
        private String Mac;
        private int IsBusy;
        private String[] Status=new String[3];
        private int Temperature;
        private CodeTimeType CodeTime;
        private Channel Conn;

        public void setMac(String mac) {
            Mac = mac;
        }

        public long getUnixTime() {
            return UnixTime;
        }

        public int getTemperature() {
            return Temperature;
        }

        public CodeTimeType getCodeTime() {
            return CodeTime;
        }

        public int getMask() {
            return Mask;
        }

        public void setMask(int mask) {
            Mask = mask;
        }

        public void setTemperature(int temperature) {
            Temperature = temperature;
        }

        public void setUnixTime(long unixTime) {
            UnixTime = unixTime;
        }

        public String getMac() {
            return Mac;
        }

        public DeviceScript() {
        }

        public DeviceScript(Channel conn, String mac, int temperature) {
            Conn = conn;
            Mac = mac;
            Temperature = temperature;
        }

        public void setStatus(String[] status) {
            Status = status;
        }

        public String[] getStatus() {
            return Status;
        }

        public Channel getConn() {
            return Conn;
        }

        public DeviceScript(int temperature) {
            Temperature = temperature;
        }
    }
/*
type MsgScript struct {
	Data     []byte
	Mac      string
}
 */
    public static class MsgScript{
    private String Data;
    private String Mac;

    public String getData() {
        return Data;
    }

    public String getMac() {
        return Mac;
    }

    public MsgScript(String data, String mac) {
        Data = data;
        Mac = mac;
    }
}
    public static class DeviceSession implements Serializable {
        private static final long serialVersionUID = 2392083052819025689L;
        private int Count;
        private int identifyCode;//手机验证码
        private String IP;
        private String Encryptionkey;

        public int getIdentifyCode() {
            return identifyCode;
        }

        public String getEncryptionkey() {
            return Encryptionkey;
        }

        public DeviceSession(String IP, String encryptionkey) {
            this.Count = 0;
            this.identifyCode = 0;
            this.IP = IP;
            this.Encryptionkey = encryptionkey;
        }

        public DeviceSession(int count, int identifyCode) {
            Count = count;
            this.identifyCode = identifyCode;
        }

        @Override
        public String toString() {
            return "DeviceSession{" +
                    "Count=" + Count +
                    ", identifyCode=" + identifyCode +
                    ", IP='" + IP + '\'' +
                    ", Encryptionkey='" + Encryptionkey + '\'' +
                    '}';
        }
    }

    public static class DeviceConLog {
        private long Id;
        final String Mac;
        final String OpType;
        private int Model;
        final int DeviceType;
        final Date TimeStamp;
        final long UserID;

        public void setModel(int model) {
            Model = model;
        }

        public DeviceConLog(String mac, String opType, int model, int deviceType, long userID) {
            Mac = mac;
            OpType = opType;
            Model = model;
            DeviceType = deviceType;
            UserID = userID;
            this.TimeStamp = new Date(System.currentTimeMillis());
        }

        @Override
        public String toString() {
            return "DeviceConLog{" +
                    "Id=" + Id +
                    ", Mac='" + Mac + '\'' +
                    ", OpType='" + OpType + '\'' +
                    ", Model=" + Model +
                    ", DeviceType=" + DeviceType +
                    ", TimeStamp=" + TimeStamp +
                    ", UserID=" + UserID +
                    '}';
        }
    }

    public static class ReqRespType {
        private int RespCode;
        private String Mac;
        private int Model;
        private int DeviceType;
        private String IP;
        private int Port;
        private String ProductName;
        private int TimeScale;
        private int IRFRQ;
        private String OpType;
        private int Mask;

        public int getIRFRQ() {
            return IRFRQ;
        }

        public int getTimeScale() {
            return TimeScale;
        }

        public int getModel() {
            return Model;
        }

        public int getDeviceType() {
            return DeviceType;
        }

        public String getMac() {
            return Mac;
        }

        public void setRespCode(int respCode) {
            RespCode = respCode;
        }

        public String getOpType() {
            return OpType;
        }

        public int getMask() {
            return Mask;
        }

        public ReqRespType(String mac, int deviceType, int IRFRQ, String opType, int mask) {
            Mac = mac;
            DeviceType = deviceType;
            this.IRFRQ = IRFRQ;
            OpType = opType;
            Mask = mask;
            this.RespCode = Sub.INVALID;
            this.Model = Sub.SET_REMOTE_STATUS;
            this.IP = Sub.INVALIDS;
            this.Port = Sub.INVALID;
            this.ProductName = Sub.INVALIDS;
            this.TimeScale = Sub.INVALID;
        }

        public void setTimeScale(int timeScale) {
            TimeScale = timeScale;
        }

        public ReqRespType(String mac, int model, int deviceType) {
            Mac = mac;
            Model = model;
            DeviceType = deviceType;
            this.RespCode = Sub.INVALID;
            this.Model = Sub.SET_REMOTE_STATUS;
            this.IP = Sub.INVALIDS;
            this.Port = Sub.INVALID;
            this.ProductName = Sub.INVALIDS;
            this.TimeScale = Sub.INVALID;
            this.IRFRQ=Sub.INVALID;
            this.OpType=Sub.INVALIDS;
            this.Mask=Sub.INVALID;
        }

        @Override
        public String toString() {
            return "ReqRespType{" +
                    "RespCode=" + RespCode +
                    ", Mac='" + Mac + '\'' +
                    ", Model=" + Model +
                    ", DeviceType=" + DeviceType +
                    ", IP='" + IP + '\'' +
                    ", Port=" + Port +
                    ", ProductName='" + ProductName + '\'' +
                    ", TimeScale=" + TimeScale +
                    ", IRFRQ=" + IRFRQ +
                    ", OpType='" + OpType + '\'' +
                    ", Mask=" + Mask +
                    '}';
        }
    }
}
