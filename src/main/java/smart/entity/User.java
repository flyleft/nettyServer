package smart.entity;

import java.sql.Date;

/**
 * Created by jcala on 2016/4/24
 * @author jcala (jcala.me:9000/blog/jcalaz)
 */
public class User {
    private long id;
    private String tel;
    private String userPw;
    private String productID;
    private  String familyMemId;
    private Date createtime;
    private Date lastUpdateTime;
    private String username;
    public long getId() {
        return id;
    }

    public String getUserPw() {
        return userPw;
    }

    public String getFamilyMemId() {
        return familyMemId;
    }

    public User(String tel,String userPw) {
        this.tel = tel;
        this.userPw = userPw;
        this.createtime=new Date(System.currentTimeMillis());
        this.lastUpdateTime=this.createtime;
        this.username="";
    }

    public User() {
    }

    public User(long id, String tel, String userPw, String productID,
                String familyMemId, Date lastUpdateTime, Date createtime) {
        this.id = id;
        this.tel = tel;
        this.userPw = userPw;
        this.productID = productID;
        this.familyMemId = familyMemId;
        this.lastUpdateTime = lastUpdateTime;
        this.createtime = createtime;
    }

    public User(long id, String userPw, String familyMemId) {
        this.id = id;
        this.userPw = userPw;
        this.familyMemId = familyMemId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", UserPw='" + userPw + '\'' +
                ", familyMemId='" + familyMemId + '\'' +
                '}';
    }
}