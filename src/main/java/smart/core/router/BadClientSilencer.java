package smart.core.router;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * Created by jcala on 2016/4/19
 *
 * @author jcala (jcala.me:9000/blog/jcalaz)
 */
public class BadClientSilencer extends SimpleChannelInboundHandler<Object> {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(BadClientSilencer.class);

    protected void onUnknownMessage(Object msg) {
        log.warn("Unknown msg: " + msg);
    }

    protected void onBadClient(Throwable e) {
        log.warn("Caught exception (maybe client is bad)", e);
    }

    protected void onBadServer(Throwable e) {
        log.warn("Caught exception (maybe server is bad)", e);
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.close();
        if (msg != LastHttpContent.EMPTY_LAST_CONTENT) {
            onUnknownMessage(msg);
        }
    }
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {

    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
        ctx.close();
        if (e instanceof java.io.IOException                            ||  // Connection reset by peer, Broken pipe
                e instanceof java.nio.channels.ClosedChannelException       ||
                e instanceof io.netty.handler.codec.DecoderException        ||
                e instanceof io.netty.handler.codec.CorruptedFrameException ||  // Bad WebSocket frame
                e instanceof IllegalArgumentException             ||  // Use https://... to connect to HTTP server
                e instanceof javax.net.ssl.SSLException                     ||  // Use http://... to connect to HTTPS server
                e instanceof io.netty.handler.ssl.NotSslRecordException) {
            onBadClient(e);  // Maybe client is bad
        } else {
            onBadServer(e);  // Maybe server is bad
        }
    }
}

