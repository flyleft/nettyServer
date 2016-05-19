package smart.mapping;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;
import smart.entity.User;

/**
 * The interface User mapper.
 */
@Service
public interface UserMapper {
    @Select("select count(*) from user where tel = #{tel}")
    @ResultType(Integer.class)
    int getNumByTel(@Param("tel") String tel);

    @Select("select id from user where tel = #{tel} limit 1")
    long getIdbyTel(@Param("tel") String tel);


    @Select("select id,user_pw,family_mem_i_d from user where tel = #{tel}")
    @Results(
            {
                    @Result(column = "id", property = "id"),
                    @Result(column = "user_pw", property = "userPw"),
                    @Result(column = "family_mem_i_d", property = "familyMemId")
            })
    User getUserByTel(@Param("tel") String tel);

   /* @Select("select id,user_pw,family_mem_i_d from user where id = #{id}")
    @Results(
            {
                    @Result(column = "id", property = "id"),
                    @Result(column = "user_pw", property = "userPw"),
                    @Result(column = "family_mem_i_d", property = "familyMemId")
            })
    User getUserById(@Param("id") long userId);*/

    String insert = "insert into user (tel,user_pw,createtime,last_update_time)" +
            "values(#{user.tel},#{user.userPw},#{user.createtime},#{user.lastUpdateTime})";
    @Insert(insert)
    void addUer(@Param("user") User user);

    @Select("update user set user_pw =#{userPw} where tel = #{tel}")
    void changePw(@Param("tel") String tel, @Param("userPw") String userPw);

    @Select("select count(*) from user where id = #{id}")
    @ResultType(Integer.class)
    int getNumById(@Param("id") long id);
}
