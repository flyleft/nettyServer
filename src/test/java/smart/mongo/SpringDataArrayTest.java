package smart.mongo;

import com.mongodb.MongoClient;
import org.junit.Test;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jcala on 2016/5/12
 */
public class SpringDataArrayTest {
    @Test
    public void arrayTest() {
        MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), "jcala"));
        String[][] strs = new String[2][3];
        strs[0][0] = "kas00";
        strs[0][1] = "kas01";
        strs[0][2] = "kas02";
        strs[1][0] = "kas10";
        strs[1][1] = "kas11";
        strs[1][2] = "kas12";
        int[] scores = {34, 56, 67, 89};
//        Student student = new Student(strs, scores);
//        mongoOps.insert(student);
//        System.out.println("Insert: " + student);
        List<Student> persons = mongoOps.findAll(Student.class);
        for (Student pp : persons) {
            System.out.println(pp);
        }
    }
}

class Student {

    @Id
    private String id;
    private String[][] features;
   // private int[] scores;

  /*  public Student(String[][] features, int[] scores) {
        this.features = features;
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", features=" + Arrays.toString(features) +
                ", scores=" + Arrays.toString(scores) +
                '}';
    }*/

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", features=" + Arrays.toString(features) +
                '}';
    }
}