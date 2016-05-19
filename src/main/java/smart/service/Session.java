package smart.service;

import smart.core.cache.Cache;
import smart.entity.Logic;

/**
 * Created by jcala on 2016/5/1
 */
public class Session {
    //new Sub.Register(Sub.NEED_LOGIN, Sub.INVALID, Sub.INVALID);
    public static boolean needLogin(long userID, String key) {
        return needLogin(userID+"",key);
    }
    public static boolean needLogin(String userID, String key) {
        Logic.DeviceSession session = Cache.get(userID, Logic.DeviceSession.class);
        if (session == null) {
            return true;
        }
        if (!session.getEncryptionkey().equals(key)) {
            return true;
        }
        return false;
    }
    public static Logic.DeviceSession getSessionByTel(String tel){
        return Cache.get(tel, Logic.DeviceSession.class);
    }
}
