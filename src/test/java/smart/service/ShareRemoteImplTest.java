package smart.service;

import com.mongodb.MongoClient;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import smart.entity.Remote;

/**
 * Created by jcala on 2016/5/15
 */
public class ShareRemoteImplTest {
    @Test
    public  void testMongo(){
        Remote.ShareRemoteWareHouse house=new Remote.ShareRemoteWareHouse();
        house.setStoragecode("23343");
        MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), "smart_home"));
        mongoOps.insert(house);
    }
}
