package smart.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import smart.action.*;
import smart.core.router.Router;
import smart.action.inter.Action;

/**
 * Created by jcala on 2016/4/21
 *
 * @author jcala (jcala.me:9000/blog/jcalaz)
 */
@Component
@Qualifier("routerSetting")
public class RouterSetting {
    @Autowired
    private Router<Action> router;
    @Autowired
    private GetVerifyCodeAct getVerifyCodeAct;//w
    @Autowired
    private LoginAct loginAct;
    @Autowired
    private RegisterAct registerAcc;

    public Router<Action> getRouter() {
        routerConfig(this.router);
        return this.router;
    }

    private void routerConfig(Router<Action> r) {
        r.POST("api/get_verify_code", getVerifyCodeAct);
        r.POST("api/login", loginAct);
        r.POST("api/register", registerAcc);
    }
}
