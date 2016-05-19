package smart.core.router;

import io.netty.util.internal.ObjectUtil;

import java.util.*;

/**
 * @author jcala (jcala.me:9000/blog/jcalaz)
 */
final public class RouteResult<T>{
    private final T  target;
    private final Map<String, String> pathParams;
    private final Map<String, List<String>> queryParams;
    public RouteResult(T target, Map<String, String> pathParams, Map<String, List<String>> queryParams) {
        this.target      = ObjectUtil.checkNotNull(target,      "target");
        this.pathParams  = Collections.unmodifiableMap(ObjectUtil.checkNotNull(pathParams,  "pathParams"));
        this.queryParams = Collections.unmodifiableMap(ObjectUtil.checkNotNull(queryParams, "queryParams"));
    }
    public T target() {
        return target;
    }
    public Map<String, String> pathParams() {
        return pathParams;
    }
    public Map<String, List<String>> queryParams() {
        return queryParams;
    }
    public String queryParam(String name) {
        List<String> values = queryParams.get(name);
        return (values == null)? null : values.get(0);
    }
    public String param(String name) {
        String pathValue = pathParams.get(name);
        return (pathValue == null)? queryParam(name) : pathValue;
    }
    public List<String> params(String name) {
        List<String> values = queryParams.get(name);
        String       value  = pathParams.get(name);

        if (values == null) {
            return (value == null)? Collections.<String>emptyList() : Arrays.asList(value);
        }

        if (value == null) {
            return Collections.unmodifiableList(values);
        } else {
            List<String> aggregated = new ArrayList(values.size() + 1);
            aggregated.addAll(values);
            aggregated.add(value);
            return Collections.unmodifiableList(aggregated);
        }
    }
}
