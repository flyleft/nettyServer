package smart.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by jcala on 2016/4/25
 */
public class HttpTools {
    /**
     * FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);
     * sendHttpResponse(ctx, req, res);
     * <p>
     * if (!req.decoderResult().isSuccess()) {
     * sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
     * return;
     * }
     * <p>
     * sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
     */
    public static void sendCorrectResp(
            ChannelHandlerContext ctx, FullHttpRequest req, Object obj) {
        ByteBuf content = Convert.Obj2Buf(obj);
        if (content == null) {
            return;
        }
        FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);
        res.headers().set(CONTENT_TYPE, "application/json; charset=UTF-8");
        HttpHeaderUtil.setContentLength(res, content.readableBytes());
        execute(ctx, req, res);
    }

    public static void sendWrongResp(
            ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
        res.content().writeBytes(buf);
        HttpHeaderUtil.setContentLength(res, res.content().readableBytes());
        execute(ctx, req, res);
        buf.release();
    }

   public static String getIp(HttpRequest request){
       HttpHeaders headers=request.headers();
       String[] ips=proxyIP(headers);
       if(ips.length>0&&ips[0]!=""){
           return ips[0].split(":")[0];
       }
       CharSequence realIPChar=headers.get("X-Real-IP");
       if (realIPChar!=null){
           String[] realIP=realIPChar.toString().split(":");
           if(realIP.length>0){
               if (realIP[0]!="["){
                   return realIP[0];
               }
           }
       }
       return "127.0.0.1";
   }

    private static String[] proxyIP(HttpHeaders headers){
        CharSequence ip=headers.get("X-Forwarded-For");
        if (ip==null){
            return new String[]{};
        }
        return ip.toString().split(",");
    }
    private static void execute(
            ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        if (HttpHeaderUtil.isKeepAlive(req)) {
            res.headers().set(CONNECTION, KEEP_ALIVE);
            ctx.write(res);
            ctx.flush();
        } else {
            ctx.write(res);
            ctx.flush();
            ctx.channel().writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
