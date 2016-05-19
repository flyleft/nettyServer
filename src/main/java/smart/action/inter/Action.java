package smart.action.inter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * Created by jcala on 2016/4/21
 *
 * @author jcala (jcala.me:9000/blog/jcalaz)
 */
public interface Action {
    void act(ChannelHandlerContext ctx, FullHttpRequest req);
}
