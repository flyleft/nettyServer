package smart.cfg;

import com.mongodb.MongoClient;
import com.zaxxer.hikari.HikariDataSource;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import smart.core.netty.HttpRouterInitializer;
import smart.mapping.UserMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * The type Main config.
 */
@Configuration
@ComponentScan("smart")
@PropertySource("classpath:server.properties")
public class MainConfig {
    @Value("${netty.boss.thread.count}")
    private int bossCount;
    @Value("${netty.worker.thread.count}")
    private int workerCount;
    @Value("${port}")
    private int port;
    @Value("${netty.so.keepalive}")
    private boolean keepAlive;
    @Value("${netty.so.backlog}")
    private int backlog;
    @Value("${hikaricp.driver}")

    private String driver;
    @Value("${hikaricp.url}")
    private String url;
    @Value("${hikaricp.username}")
    private String username;
    @Value("${hikaricp.password}")
    private String password;
    @Value("${hikaricp.connectionTimeout}")
    private long connectionTimeout;
    @Value("${hikaricp.maximumPoolSize}")
    private int maximumPoolSize;
    @Value("${hikaricp.minimumIdle}")
    private int minimumIdle;
    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSessionTemplate mainTemplate;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @SuppressWarnings("unchecked")
    @Bean(name = "serverBootstrap")
    @Autowired
    @Qualifier("httpRouterInitializer")
    public ServerBootstrap bootstrap(HttpRouterInitializer httpRouterInitializer) {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(httpRouterInitializer);
        Map<ChannelOption<?>, Object> channelOptions = channelOptions();
        Set<ChannelOption<?>> keySet = channelOptions.keySet();
        for (@SuppressWarnings("rawtypes")
                ChannelOption option : keySet) {
            b.option(option, channelOptions.get(option));
        }
        return b;
    }

    @Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(bossCount);
    }

    @Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(workerCount);
    }

    @Bean(name = "channelOptions")
    public Map<ChannelOption<?>, Object> channelOptions() {
        Map<ChannelOption<?>, Object> options = new HashMap<>();
        options.put(ChannelOption.SO_BACKLOG, 128);
        options.put(ChannelOption.SO_KEEPALIVE, keepAlive);
        return options;
    }

    @Bean(name = "port")
    public int getPort() {
        return this.port;
    }

    @Bean(name = "hikariDataSource")
    public DataSource hikariDataSource() {
        HikariDataSource hds = new HikariDataSource();
        hds.setUsername(username);
        hds.setPassword(password);
        hds.setJdbcUrl(url);
        hds.setDriverClassName(driver);
        hds.setConnectionTimeout(connectionTimeout);
        hds.setMaximumPoolSize(maximumPoolSize);
        hds.setMinimumIdle(minimumIdle);
        return hds;
    }

    @Bean(name = "sqlSessionFactory")
    @Autowired
    @Qualifier("hikariDataSource")
    public SqlSessionFactory sqlSessionFactory(DataSource hikariDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(hikariDataSource);
        bean.setTransactionFactory(new SpringManagedTransactionFactory());
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.addMappers("smart.mapping");
        bean.setConfiguration(config);
        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    @Autowired
    @Qualifier("sqlSessionFactory")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "notFundResponse")
    public FullHttpResponse notFundResponse() {
        return new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);
    }

    @Bean(name = "userMapper")
    @Autowired
    @Qualifier("sqlSessionTemplate")
    public UserMapper userMapper(SqlSessionTemplate sqlSessionTemplate) {
        return sqlSessionTemplate.getMapper(UserMapper.class);
    }

    @Bean(name = "mongoClient")
    public MongoClient mongoClient() {
        return new MongoClient();
    }
}
