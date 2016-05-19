package smart.service;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import smart.entity.Remote;
import smart.mapping.UserMapper;
import smart.service.inter.ShareRemoteInter;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Created by jcala on 2016/5/13
 */
@Service
@Qualifier("shareRemoteImpl")
public class ShareRemoteImpl implements ShareRemoteInter {
    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;
    @Autowired
    @Qualifier("mongoClient")
    private MongoClient mongoClient;
    @Override
    public void addShareRemote(Remote.ShareRemoteMongo remote, long userId,
                               Remote.ShareRemoteWareHouse house) {
        MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(mongoClient, "smart_home"));
        mongoOps.insert(house);
        int num = userMapper.getNumById(userId);
        if (num < 1) {
            return;
        }
        remote.setUserId(userId);
        remote.setObjectId(house.getId());
    }

    @Override
    public Remote.ShareRemoteWareHouse getMgoRemoteByID(String id) {
        MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(mongoClient, "smart_home"));
        return mongoOps.findById(id, Remote.ShareRemoteWareHouse.class);
    }

    @Override
    public void UpdateMgoRemote(String id, Remote.ShareRemoteWareHouse house) {
        MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(mongoClient, "smart_home"));
        mongoOps.updateFirst(Query.query(where("").is("")),
                Update.update("", house.getStoragecode()), Remote.ShareRemoteWareHouse.class);
    }

    @Override
    public List<Remote.ShareRemoteMongo> searchShareRemote(String SearchText) {
        return null;
    }
}
