package smart.entity;

import java.sql.Date;
import java.util.List;

public class Sub {
    public static final int SUCCESS = 0; //操作成功
    public static final int OFFLONE = 1;//设备不在线
    public static final int PARA_ERROR = 2;//参数错误
    public static final int PARA_JSON_ERROR = 3;//参数JSON序列化错误
    public static final int OPERATION_ERROR = 4;//系统操作失败
    public static final int OPERATION_CHECK = 5;//数据转为重查
    public static final int INVALID_NAME = 6;//帐号出错
    public static final int INVALID_PW = 7;//密码出错
    public static final int EXIST_NAME = 8;//注册时，该手机号已经被注册
    public static final int RES_REGISTER = 9;//设备未向服务器发起注册，或者设备以掉线，设备重连
    public static final int TEL_NULL = 10;//电话号码为空
    public static final int MAX_MSG = 11;//该手机号已经超过最大的验证次数
    public static final int NOT_TEL = 12;//效的手机号
    public static final int VERIFY_CODE_ERR = 13;//验证码错误
    public static final int NEED_LOGIN = 14;//需要重新登录
    public static final int NO_NEW_VERSION = 15;
    public static final int INVALID = 0; //该位无效
    public static final String INVALIDS = "0";
    public static final int SET_DEVICE_STATUS = 0; //改变开关的状态
    public static final int SET_REMOTE_STATUS = 2; //控制红外遥控器
    public static final Register RELOGIN = new Register(NEED_LOGIN, INVALID, INVALID);
    public static final Register PARAERROR = new Register(PARA_ERROR, INVALID, INVALID);

    public static class Register {
        private int RespCode;
        private long UserID;
        private long GroupID;

        public Register(int respCode, long userID, long groupID) {
            RespCode = respCode;
            UserID = userID;
            GroupID = groupID;
        }

        @Override
        public String toString() {
            return "Register{" +
                    "RespCode=" + RespCode +
                    ", UserID=" + UserID +
                    ", GroupID=" + GroupID +
                    '}';
        }

        public Register() {
        }

        public void setRespCode(int respCode) {
            RespCode = respCode;
        }

        public void setUserID(long userID) {
            UserID = userID;
        }

        public void setGroupID(long groupID) {
            GroupID = groupID;
        }
    }

    public static class Sync {
        private int RespCode;
        private List<Sub.SyncData> Data;

        public Sync(int respCode, List<Sub.SyncData> data) {
            RespCode = respCode;
            Data = data;
        }
    }

    public static class SyncData {
        private Integer Id;
        private long UserID;
        private String FilePath;
        private String FileName;

        public SyncData(Integer id, long userID, String filePath, String fileName) {
            Id = id;
            UserID = userID;
            FilePath = filePath;
            FileName = fileName;
        }

        public SyncData() {
        }

        public void setId(Integer id) {
            Id = id;
        }

        public void setUserID(long userID) {
            UserID = userID;
        }

        public void setFileName(String fileName) {
            FileName = fileName;
        }

        public void setFilePath(String filePath) {
            FilePath = filePath;
        }
    }

    public static class ResponseType {
        private int RespCode;
        private String Mac;
        private long Status;

        public ResponseType(int respCode, String mac, long status) {
            RespCode = respCode;
            Mac = mac;
            Status = status;
        }

        public ResponseType() {
        }
    }

    public static class UpdateSwitchStatus {//和Sync相同
        private int RespCode;
        private SyncData Data;
    }

    /* public static class UpdateUser {//和CheckSwitch相同
         private int RespCode;
         private String Mac;
         private long Status;
     }

     public static class SetSwitch {//和CheckSwitch相同
         private int RespCode;
         private String Mac;
         private long Status;
     }
    */
    public static class SearchRemote {
        private int RespCode;
        private List<Remote.ShareRemoteMongo> Data;

        public SearchRemote(int respCode, List<Remote.ShareRemoteMongo> data) {
            RespCode = respCode;
            Data = data;
        }
    }

    public static class DownloadShareRemote {
        private int RespCode;
        private Remote.ShareRemoteWareHouse Data;

        public DownloadShareRemote(Remote.ShareRemoteWareHouse data, int respCode) {
            Data = data;
            RespCode = respCode;
        }
    }

   /* public static class GetTemperature {//和CheckSwitch相同
        private int RespCode;
        private String Mac;
        private long Status;
    }*/

    public static class CheckVerifyCode {
        private int RespCode;
        private String Tel;
        private String Status;

        public CheckVerifyCode(int respCode, String tel, String status) {
            RespCode = respCode;
            Tel = tel;
            Status = status;
        }
    }

    public static class UpdateRemote {
        private int RespCode;
        private List<RemoteWareHouse> Data;
        private int Num;

        public void setRespCode(int respCode) {
            RespCode = respCode;
        }

        public void setData(List<RemoteWareHouse> data) {
            this.Data = data;
        }

        public void setNum(int num) {
            Num = num;
        }
    }

    /*public static class UpdateRemoteData {
        private String ObjectId;
        private String RemoteName;
        private String No;
        private String Version;
        private int DeviceType;
        private String Descript;
        private String SaveUrl;
        private int User;
        private Date UpdateTime;
        private int TimeScale;
        private long IRFRQ;
        private String BootCode;
        private String PoweroffCode;
        private UpdateRemoteAcCode AcCode;
        private HashMap<String, String> CommonCode;
    }*/
/*
    public static class UpdateRemoteAcCode {
        private List<String> TemperatureData;
        private String WindAutoModel;
        private UpdateRemoteModel WindHighModel;
        private UpdateRemoteModel WindMidModel;
        private UpdateRemoteModel WindLowModel;
        private UpdateRemoteModel HotModel;
        private UpdateRemoteModel CodeModel;
        private UpdateRemoteModel VentilateModel;
        private UpdateRemoteModel DehumidifyModel;
        private String DefaultCode;
    }*/

  /*  public static class UpdateRemoteModel {
        private int StartAddr;
        private int Code;
        private int BitLength;
    }*/

    public static class GetData {
        private int Id;
        private long UserID;
        private String FilePath;
        private String FileName;
        private String FileData;
        private String UploadDate;
        private String Size;

        public GetData(int id, String fileData, String fileName, String filePath, long userID, String uploadDate, String size) {
            Id = id;
            FileData = fileData;
            FileName = fileName;
            FilePath = filePath;
            UserID = userID;
            UploadDate = uploadDate;
            Size = size;
        }
    }

    public static class ShareRemote {
        private int RespCode;
        private String Mac;
        private int Status;
    }

    public static class HeartBeat {
        private String Mac;
        private String OpType;
        private int Model;
        private int RespCode;
    }

    public static class GetVerifyCode {
        final int RespCode;
        final String Tel;
        final String Status;

        public GetVerifyCode(int respCode, String tel, String status) {
            RespCode = respCode;
            Tel = tel;
            Status = status;
        }
    }
}
