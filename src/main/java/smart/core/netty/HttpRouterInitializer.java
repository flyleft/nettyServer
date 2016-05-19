package smart.core.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by jcala on 2016/4/21
 *
 * @author jcala (jcala.me:9000/blog/jcalaz)
 */
@Component
@Qualifier("httpRouterInitializer")
public class HttpRouterInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    @Qualifier("nettyHttpHandler")
    private HttpHandler handler;
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("respDecoder-reqEncoder", new HttpServerCodec())
                .addLast("http-aggregator", new HttpObjectAggregator(65536))
                .addLast(new ChunkedWriteHandler())
                .addLast("action-handler", handler);
    }
}
