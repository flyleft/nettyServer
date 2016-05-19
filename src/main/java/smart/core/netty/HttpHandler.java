package smart.core.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import smart.action.inter.Action;
import smart.cfg.RouterSetting;
import smart.core.router.RouteResult;
import smart.entity.Logic;
import smart.entity.Sub;
import smart.utils.HttpTools;
import smart.utils.JsonTools;
import smart.utils.RespTools;
import smart.utils.StringTools;

import static io.netty.handler.codec.http.HttpHeaderNames.HOST;

/**
 * The type Http handler.
 */
@Component
@Qualifier("nettyHttpHandler")
@ChannelHandler.Sharable
public class HttpHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);
    private WebSocketServerHandshaker handshaker;
    @Autowired
    @Qualifier("routerSetting")
    private RouterSetting rs;

    private String mac;

    @Override
    public void messageReceived(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest) {//如果是HTTP请求，进行HTTP操作
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {//如果是Websocket请求，则进行websocket操作
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    //处理HTTP的代码
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        logger.warn("uri:" + req.uri());
        if (req.uri().startsWith("/ws/join")) {//如果urL开头为/ws/join则升级为websocket
            mac = wsBeforeHandler(ctx, req);
            if (mac == null || mac.length() < 1) {
                RespTools.paraErrorBack(ctx, req, null);
                return;
            }
            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                    getWebSocketLocation(req), null, true);
            handshaker = wsFactory.newHandshaker(req);
            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                handshaker.handshake(ctx.channel(), req);
            }
        } else {
            RouteResult<Action> routeResult = rs.getRouter().route(req.method(), req.uri());
            Action action = routeResult.target();
            action.act(ctx, req);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {

        // Check for closing frame
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        if (frame instanceof PingWebSocketFrame) {
            ctx.write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if (frame instanceof TextWebSocketFrame) {
            String json = ((TextWebSocketFrame) frame).text();
            Logic.ReqRespType data = JsonTools.read(json, Logic.ReqRespType.class);
            return;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private static String getWebSocketLocation(FullHttpRequest req) {
        String location = req.headers().get(HOST) + "/ws/join";
        logger.info(location);
        if (NettyServer.SSL) {
            return "wss://" + location;
        } else {
            return "ws://" + location;
        }
    }

    private String wsBeforeHandler(ChannelHandlerContext ctx, FullHttpRequest req) {
        String mac = StringTools.getMac(req.uri());
        if (mac == null) {
            HttpTools.sendCorrectResp(ctx, req, Sub.PARAERROR);
            return null;
        } else {
            return mac;
        }
    }
}
