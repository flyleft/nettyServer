package smart.entity;

import org.springframework.data.annotation.Id;

import java.sql.Date;

/**
 * Created by jcala on 2016/5/13
 */
public class Remote {
    public static class ShareRemoteWareHouse {
        @Id private String id;
        private String storagecode;

        public String getId() {
            return id;
        }

        public String getStoragecode() {
            return storagecode;
        }

        public void setStoragecode(String storagecode) {
            this.storagecode = storagecode;
        }
    }

    public static class ShareRemoteMongo {
        private long Id;
        private String Factory;
        private String DeviceModel;
        private String DeviceType;
        private String Descript;
        private String ObjectId;
        private long UserId;
        private Date ShareTime;

        public void setObjectId(String objectId) {
            this.ObjectId = objectId;
        }

        public void setUserId(long userId) {
            this.UserId = userId;
        }

        public ShareRemoteMongo(String factory, String deviceModel, String deviceType, String descript) {
            this.Factory = factory;
            this.DeviceModel = deviceModel;
            this.DeviceType = deviceType;
            this.Descript = descript;
            this.ShareTime=new Date(System.currentTimeMillis());
        }
    }
}
