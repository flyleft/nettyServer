package smart.utils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import smart.entity.Sub;

/**
 * Created by jcala on 2016/5/4
 */
public class RespTools {
    private static final Sub.ResponseType PARA_ERROR=new Sub.ResponseType(Sub.PARA_ERROR,Sub.INVALIDS,Sub.INVALID);
    private static final Sub.ResponseType OPERATION_ERROR=new Sub.ResponseType(Sub.OPERATION_ERROR,Sub.INVALIDS,Sub.INVALID);
    private static final Sub.ResponseType INVALID_NAME=new Sub.ResponseType(Sub.INVALID_NAME,Sub.INVALIDS,Sub.INVALID);
    private static final Sub.ResponseType OFFLONE=new Sub.ResponseType(Sub.OFFLONE,Sub.INVALIDS,Sub.INVALID);
    private static final Sub.ResponseType SUCCESS=new Sub.ResponseType(Sub.SUCCESS,Sub.INVALIDS,Sub.INVALID);
    private static final Sub.ResponseType NEED_LOGIN=new Sub.ResponseType(Sub.NEED_LOGIN,Sub.INVALIDS,Sub.INVALID);
    public static void paraErrorBack(ChannelHandlerContext ctx, FullHttpRequest req,Object get){
        HttpTools.sendCorrectResp(ctx, req, PARA_ERROR);
        get=null;
    }
    public static void operateErr(ChannelHandlerContext ctx, FullHttpRequest req,Object get){
        HttpTools.sendCorrectResp(ctx, req, OPERATION_ERROR);
        get=null;
    }
    public static void invalidName(ChannelHandlerContext ctx, FullHttpRequest req,Object get){
        HttpTools.sendCorrectResp(ctx, req, INVALID_NAME);
        get=null;
    }
    public static void offLone(ChannelHandlerContext ctx, FullHttpRequest req,Object get){
        HttpTools.sendCorrectResp(ctx, req, OFFLONE);
        get=null;
    }
    public static void success(ChannelHandlerContext ctx, FullHttpRequest req,Object get){
        HttpTools.sendCorrectResp(ctx, req, SUCCESS);
        get=null;
    }
    public static void needLogin(ChannelHandlerContext ctx, FullHttpRequest req,Object get){
        HttpTools.sendCorrectResp(ctx, req, NEED_LOGIN);
        get=null;
    }
}
