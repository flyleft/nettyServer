package smart.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * The type Convert.
 */
public class Convert {
    private static final Logger logger = LoggerFactory.getLogger(Convert.class);
    /**
     * Buf 2 str string.
     *
     * @param buf the buf
     * @return the string
     */
    public static String buf2Str(ByteBuf buf) {
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        try {
            //buf.release();
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Obj buf byte buf.
     *
     * @param object the object
     * @return the byte buf
     */
    public static ByteBuf Obj2Buf(Object object) {
        if (object==null){return null;}
        logger.debug(JsonTools.writeToOrg(object));
        return Unpooled.copiedBuffer(JsonTools.writeToOrg(object),
                CharsetUtil.UTF_8);
    }
}
