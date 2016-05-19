package smart.core.router;

import io.netty.util.internal.StringUtil;

/**
 * Created by jcala on 2016/4/19
 * @author jcala (jcala.me:9000/blog/jcalaz)
 */
final public class MethodlessRouter<T> {
    private final OrderlessRouter<T> first = new OrderlessRouter<>();
    private final OrderlessRouter<T> other = new OrderlessRouter<>();
    private final OrderlessRouter<T> last  = new OrderlessRouter<>();

    protected OrderlessRouter<T> first() {
        return first;
    }

    protected OrderlessRouter<T> other() {
        return other;
    }

    protected OrderlessRouter<T> last() {
        return last;
    }

    protected int size() {
        return first.routes().size() + other.routes().size() + last.routes().size();
    }
    protected MethodlessRouter<T> addRouteFirst(String path, T target) {
        first.addRoute(path, target);
        return this;
    }
    protected MethodlessRouter<T> addRoute(String path, T target) {
        other.addRoute(path, target);
        return this;
    }
    protected MethodlessRouter<T> addRouteLast(String path, T target) {
        last.addRoute(path, target);
        return this;
    }
    protected void removePath(String path) {
        first.removePath(path);
        other.removePath(path);
        last .removePath(path);
    }
    protected void removeTarget(T target) {
        first.removeTarget(target);
        other.removeTarget(target);
        last .removeTarget(target);
    }
    protected RouteResult<T> route(String path) {
        return route(StringUtil.split(Path.removeSlashesAtBothEnds(path), '/'));
    }
    protected RouteResult<T> route(String[] requestPathTokens) {
        RouteResult<T> ret = first.route(requestPathTokens);
        if (ret != null) {
            return ret;
        }

        ret = other.route(requestPathTokens);
        if (ret != null) {
            return ret;
        }

        ret = last.route(requestPathTokens);
        if (ret != null) {
            return ret;
        }

        return null;
    }
    protected boolean anyMatched(String[] requestPathTokens) {
        return first.anyMatched(requestPathTokens) ||
                other.anyMatched(requestPathTokens) ||
                last.anyMatched(requestPathTokens);
    }
    protected String path(T target, Object... params) {
        String ret = first.path(target, params);
        if (ret != null) {
            return ret;
        }

        ret = other.path(target, params);
        if (ret != null) {
            return ret;
        }

        return last.path(target, params);
    }
}
