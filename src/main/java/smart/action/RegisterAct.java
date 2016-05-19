package smart.action;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import smart.action.inter.Action;
import smart.entity.Get;
import smart.entity.Sub;
import smart.entity.User;
import smart.mapping.UserMapper;
import smart.utils.Convert;
import smart.utils.HttpTools;
import smart.utils.JsonTools;
import smart.utils.Md5Tools;

/**
 * 手机注册
 * The type Register act.
 */
@Controller
@Qualifier("registerAct")
public class RegisterAct implements Action{
    private static final Logger logger = LoggerFactory.getLogger(RegisterAct.class);
    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Override
    public void act(ChannelHandlerContext ctx, FullHttpRequest req) {
        String body = Convert.buf2Str(req.content());
        Get.Register get = JsonTools.read(body, Get.Register.class);
        Sub.Register back;
        User user;
        if (get != null && get.isLegal()) {//判断从HTTP请求中获取的数据转换为javabean后是否合法
           int num=userMapper.getNumByTel(get.getTel());
            if (num>0){//如果数据库中已经有该手机号
                back=new Sub.Register(Sub.EXIST_NAME,Sub.INVALID,Sub.INVALID);
            }
            else {
                user=new User(get.getTel(),Md5Tools.MD5(get.getUserPw()));
                try {
                    userMapper.addUer(user);//数据库插入数据
                    long userId=userMapper.getIdbyTel(get.getTel());
                    back = new Sub.Register(Sub.SUCCESS, userId, Sub.INVALID);//获取插入后返回的javabean
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    back = new Sub.Register(Sub.OPERATION_ERROR, Sub.INVALID, Sub.INVALID);
                }
            }
        }else{
            back=new Sub.Register(Sub.PARA_ERROR,Sub.INVALID,Sub.INVALID);
        }
        logger.debug(back.toString());
        HttpTools.sendCorrectResp(ctx, req,back);
        get=null;//手动清理生成的对象，加快垃圾回收
        user=null;
    }
}