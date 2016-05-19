package smart;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import smart.cfg.MainConfig;

/**
 * Created by jcala on 2016/4/18
 *
 * @author jcala (jcala.me:9000/blog/jcalaz)
 */
public class Main {
    public static void main(String args[]) throws Exception{
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(
                MainConfig.class);
        ctx.registerShutdownHook();//在JVM注册一个关闭钩子，确保IOC容器最终会被正确关闭
        ctx.start();
    }
}
