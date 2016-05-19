package smart.core.netty;

import io.netty.bootstrap.ServerBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import smart.core.cache.Cache;

import javax.annotation.PostConstruct;

/**
 * Created by jcala on 2016/4/23
 * @author jcala (jcala.me:9000/blog/jcalaz)
 */
@Component
public class NettyServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    static final boolean SSL = System.getProperty("ssl") != null;
    @Autowired
    @Qualifier("serverBootstrap")
    private ServerBootstrap b;
    @Autowired
    @Qualifier("port")
    private int port;
    @PostConstruct//会在服务器加载时执行，并且只执行一次
    public void start() throws Exception {
        init();
        logger.info("Starting server at " + port);
        b.bind(port).sync().channel().closeFuture().sync().channel();
    }
    private void init(){
        Cache.init();
    }

}
