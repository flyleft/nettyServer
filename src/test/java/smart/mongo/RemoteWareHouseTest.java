package smart.mongo;

import com.mongodb.MongoClient;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import smart.entity.RemoteWareHouse;

import java.util.List;

/**
 * Created by jcala on 2016/5/13
 */
public class RemoteWareHouseTest {
    @Test
    public void testMapping() {
        MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), "smart_home"));
        List<RemoteWareHouse> houseList = mongoOps.findAll(RemoteWareHouse.class);
        System.out.println("Number of RemoteWareHouse = : " + houseList.size());
        for (RemoteWareHouse house:houseList){
            System.out.println(house);
        }
    }
}
