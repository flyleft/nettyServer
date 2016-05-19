package smart.service.inter;

import smart.entity.Remote;

import java.util.List;

/**
 * Created by jcala on 2016/5/13
 */
public interface ShareRemoteInter {
    void addShareRemote(Remote.ShareRemoteMongo remote, long userId, Remote.ShareRemoteWareHouse house);
    Remote.ShareRemoteWareHouse getMgoRemoteByID(String id);
    void UpdateMgoRemote(String id, Remote.ShareRemoteWareHouse house);
    List<Remote.ShareRemoteMongo> searchShareRemote(String SearchText);
}
