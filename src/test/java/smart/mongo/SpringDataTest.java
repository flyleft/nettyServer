package smart.mongo;

import com.mongodb.MongoClient;
import org.junit.Test;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by jcala on 2016/5/12
 */
public class SpringDataTest {
    @Test
    public void testData() {
        MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), "jcala"));
        Person p = new Person("Joe", 34);
        Person p1 = new Person("jcala", 25);

        // Insert is used to initially store the object into the database.
        mongoOps.insert(p);
        mongoOps.insert(p1);
        System.out.println("Insert: " + p);

        // Find
        p = mongoOps.findById(p.getId(), Person.class);
        System.out.println("Found: " + p);

        // Update
        mongoOps.updateFirst(query(where("name").is("Joe")), update("age", 35), Person.class);
        p = mongoOps.findOne(query(where("name").is("Joe")), Person.class);
        System.out.println("Updated: " + p);

        // Delete
        //mongoOps.remove(p);

        // Check that deletion worked
        List<Person> people = mongoOps.findAll(Person.class);
        System.out.println("Number of people = : " + people.size());


        //mongoOps.dropCollection(Person.class);

        Person qp = mongoOps.findOne(query(where("age").is(34)), Person.class);
        System.out.println(qp.toString());

        List<Person> persons=mongoOps.findAll(Person.class);
        for (Person pp:persons){
            System.out.println(pp);
        }
    }
}

class Person {

    @Id private String id;
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
    }
}