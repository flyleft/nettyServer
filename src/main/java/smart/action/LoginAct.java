package smart.action;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import smart.action.inter.Action;
import smart.core.cache.Cache;
import smart.entity.Get;
import smart.entity.Logic;
import smart.entity.Sub;
import smart.entity.User;
import smart.mapping.UserMapper;
import smart.utils.*;


/**
 * 手机登录
 * The type Login act.
 */
@Controller
public class LoginAct implements Action {
    private static final Logger logger = LoggerFactory.getLogger(LoginAct.class);
    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    /**
     * netty的handler在路由后，到此方法开始执行
     *
     * @param ctx ChannelHandlerContext
     * @param req 接收到的HTTP请求
     */
    @Override
    public void act(ChannelHandlerContext ctx, FullHttpRequest req) {
        String ip = HttpTools.getIp(req);
        String body = Convert.buf2Str(req.content());
        Get.Login get = JsonTools.read(body, Get.Login.class);//1.得到HTTP传来的json数据解析为javabean
        Sub.Register back;//构建返回给客户端的javabean的实例
        User user;
        try {
            user = userMapper.getUserByTel(get.getTel());
            logger.debug(user.toString());
            if (user != null) {
                if (user.getUserPw().equals(Md5Tools.MD5(get.getUserPw()))) {//登陆成功
                    back = new Sub.Register(Sub.SUCCESS, user.getId(), Sub.INVALID);
                    addSession(user.getId(), ip);
                } else {//Tel存在，但是密码错误
                    back = new Sub.Register(Sub.INVALID_PW, Sub.INVALID, Sub.INVALID);
                }
            } else {//user为空，说明账号还没有被注册，返回参数错误的code
                back = new Sub.Register(Sub.PARA_ERROR, Sub.INVALID, Sub.INVALID);
            }
        } catch (Exception e) {//出现异常，返回账号出错的code
            back = new Sub.Register(Sub.INVALID_NAME, Sub.INVALID, Sub.INVALID);
            logger.error(e.getMessage());
        }
        logger.debug(back.toString());
        HttpTools.sendCorrectResp(ctx, req, back);//返回给客户端HTTP Response
        get = null;//手动清理没用的对象，加快垃圾回收
        user = null;
        back = null;
    }

    private void addSession(long userId, String ip) {
        Logic.DeviceSession session = new Logic.DeviceSession(ip, "");
        Cache.add(userId + "", session, "6mn");//设置session的缓存时间为6分钟
        //debugSession(userId);
    }

    /*private void debugSession(long userId) {
        Logic.DeviceSession session = Cache.get(userId+"", Logic.DeviceSession.class);
        if (session==null){
            logger.warn("缓存中Logic.DeviceSession is null");
        }else {
            logger.debug(userId+"对应的缓存为:"+session.toString());
        }
        if (Session.needLogin(userId,"")){
            logger.debug(userId+"需要重新登陆");
        }else {
            logger.debug(userId+"不需要重新登陆");
        }
    }*/
}
