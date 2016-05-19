package smart.entity;

public class Get {
    public static class Register{
        private String Tel;
        private String UserPw;
        private String ProductID;

        public String getTel() {
            return Tel;
        }

        public String getUserPw() {
            return UserPw;
        }

        public String getProductID() {
            return ProductID;
        }

        public boolean isLegal(){
            if(Tel==null||UserPw==null||ProductID==null){
                return false;
            }else {
                return true;
            }
        }

        @Override
        public String toString() {
            return "Register{" +
                    "Tel='" + Tel + '\'' +
                    ", UserPw='" + UserPw + '\'' +
                    ", ProductID='" + ProductID + '\'' +
                    '}';
        }
    }
    public static class Login{
        private String Tel;
        private String UserPw;

        public String getTel() {
            return Tel;
        }

        public String getUserPw() {
            return UserPw;
        }

        public boolean isLegal(){
            if(Tel==null||UserPw==null){
                return false;
            }else {
                return true;
            }
        }
    }
    public  static class AutoControl{
        private long UserID;
        private long GroupID;
        private String Mac;
        private boolean IsOpen;

        public String getMac() {
            return Mac;
        }

        public boolean isOpen() {
            return IsOpen;
        }
    }
    public static class Sync{
        private long UserID;
        private int GroupID;
        private int OpType;
        public long getUserID() {
            return UserID;
        }

        public int getGroupID() {
            return GroupID;
        }

        public int getOpType() {
            return OpType;
        }
    }
    public static class CheckSwitch{
        private long UserID;
        private int GroupID;
        private int DeviceType;
        private String OpType;
        private String Mac;
        private int Mask;

        public String getMac() {
            return Mac;
        }
    }
    public static class UpdateSwitchStatus{
        private String UploadData;
    }
    public static  class SetSwitch {
        private long UserID;
        private int GroupID;
        private int DeviceType;
        private String OpType;
        private String Mac;
        private int Mask;

        public int getMask() {
            return Mask;
        }

        public int getDeviceType() {
            return DeviceType;
        }

        public String getOpType() {
            return OpType;
        }

        public String getMac() {
            return Mac;
        }

        public long getUserID() {
            return UserID;
        }
    }
    public static  class SetRemote{
        private long UserID;
        private int GroupID;
        private int DeviceType;
        private int TimeScale;
        private int IRFRQ;
        private String OpType;
        private String Mac;
        private int Mask;

        public int getIRFRQ() {
            return IRFRQ;
        }

        public int getMask() {
            return Mask;
        }

        public long getUserID() {
            return UserID;
        }

        public int getDeviceType() {
            return DeviceType;
        }

        public String  getOpType() {
            return OpType;
        }

        public String getMac() {
            return Mac;
        }

        public int getTimeScale() {
            return TimeScale;
        }
    }
    public static  class GetVerifyCode{
        private String Tel;

        public String getTel() {
            return Tel;
        }
    }
    public static class UpdateUser{
        private String Tel;
        private String UserPw;
        private String VerifyCode;

        public String getTel() {
            return Tel;
        }

        public String getUserPw() {
            return UserPw;
        }

        public boolean notlegal(){
            if (Tel==null||"".equals(Tel)||Tel.length()<1){
                return true;
            }
            if (UserPw==null||"".equals(UserPw)||UserPw.length()<1){
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return "UpdateUser{" +
                    "Tel='" + Tel + '\'' +
                    ", UserPw='" + UserPw + '\'' +
                    ", VerifyCode='" + VerifyCode + '\'' +
                    '}';
        }
    }
    public static class ShareRemote{
        private int Id;
        private String StorageCode;
        private String Factory;
        private String DeviceModel;
        private String DeviceType;
        private String Descript;

        public String getFactory() {
            return Factory;
        }

        public String getDeviceModel() {
            return DeviceModel;
        }

        public String getDeviceType() {
            return DeviceType;
        }

        public String getDescript() {
            return Descript;
        }

        public int getId() {
            return Id;
        }

        public String getStorageCode() {
            return StorageCode;
        }
    }
    public static class DownloadShareRemote{
        private long UserID;
        private String ObjectId;

        public long getUserID() {
            return UserID;
        }

        public String getObjectId() {
            return ObjectId;
        }
    }
    public static class SearchRemote{
        private long UserID;
        private String SearchText;

        public long getUserID() {
            return UserID;
        }

        public String getSearchText() {
            return SearchText;
        }
    }
    public static class GetTemperature{
        private long UserID;
        private String Mac;

        public long getUserID() {
            return UserID;
        }

        public String getMac() {
            return Mac;
        }
    }
    public static class UpdateRemote{
        private long UserID;
        private int GroupID;

        public long getUserID() {
            return UserID;
        }
    }
    public static class GetData{
        private long UserID;
        private int GroupID;
        private int Id;

        public long getUserID() {
            return UserID;
        }

        public int getId() {
            return Id;
        }
    }
    public static class CheckVerifyCode{
        private String Tel;
        private int VerifyCode;

        public String getTel() {
            return Tel;
        }

        public int getVerifyCode() {
            return VerifyCode;
        }

        public boolean notLegal(){
            return Tel==null?true:false;
        }

    }
    public static class  WebsocketConn{
        private String Mac;
        private int DeviceType;
        private int TimeScale;
        private int IRFRQ;
        private  int OpType;
        private  int Model;
        private int RespCode;
        private int Mask;
    }

}
