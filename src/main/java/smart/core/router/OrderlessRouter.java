package smart.core.router;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by jcala on 2016/4/19
 *
 * @author jcala (jcala.me:9000/blog/jcalaz)
 */
final public class OrderlessRouter<T> {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(OrderlessRouter.class);
    private final Map<Path, T> routes = new HashMap<>();
    private final Map<T, Set<Path>> reverseRoutes = new HashMap<>();

    /**
     * @return 返回routes的一个只可读的视图
     */
    protected Map<Path, T> routes() {
        return Collections.unmodifiableMap(routes);
    }

    protected OrderlessRouter<T> addRoute(String path, T target) {
        Path p = new Path(path);
        if (routes.containsKey(path)) {
            return this;
        }

        routes.put(p, target);
        addReverseRoute(target, p);
        return this;
    }
    private void addReverseRoute(T target, Path path) {
        Set<Path> paths = reverseRoutes.get(target);
        if (paths == null) {
            paths = new HashSet<Path>();
            paths.add(path);
            reverseRoutes.put(target, paths);
        } else {
            paths.add(path);
        }
    }
    protected void removePath(String path) {
        Path p      = new Path(path);
        T    target = routes.remove(p);
        if (target == null) {
            return;
        }

        Set<Path> paths = reverseRoutes.remove(target);
        paths.remove(p);
    }
    protected void removeTarget(T target) {
        Set<Path> paths = reverseRoutes.remove(ObjectUtil.checkNotNull(target, "target"));
        if (paths == null) {
            return;
        }
        // A path can only point to one target.
        // A target can have multiple paths.
        // Remove all paths leading to this target.
        for (Path path : paths) {
            routes.remove(path);
        }
    }
    public RouteResult<T> route(String path) {
        return route(StringUtil.split(Path.removeSlashesAtBothEnds(path), '/'));
    }
    protected RouteResult<T> route(String[] requestPathTokens) {
        // Optimization note:
        // - Reuse tokens and pathParams in the loop
        // - decoder doesn't decode anything if decoder.parameters is not called
        Map<String, String> pathParams = new HashMap<String, String>();
        for (Map.Entry<Path, T> entry : routes.entrySet()) {
            Path path = entry.getKey();
            if (path.match(requestPathTokens, pathParams)) {
                T target = entry.getValue();
                return new RouteResult(target, pathParams, Collections.emptyMap());
            }

            // Reset for the next loop
            pathParams.clear();
        }

        return null;
    }
    protected boolean anyMatched(String[] requestPathTokens) {
        Map<String, String> pathParams = new HashMap<String, String>();
        for (Path path : routes.keySet()) {
            if (path.match(requestPathTokens, pathParams)) {
                return true;
            }

            // Reset for the next loop
            pathParams.clear();
        }

        return false;
    }
    @SuppressWarnings("unchecked")
    protected String path(T target, Object... params) {
        if (params.length == 0) {
            return path(target, Collections.emptyMap());
        }

        if (params.length == 1 && params[0] instanceof Map<?, ?>) {
            return pathMap(target, (Map<Object, Object>) params[0]);
        }

        if (params.length % 2 == 1) {
            throw new IllegalArgumentException("Missing value for param: " + params[params.length - 1]);
        }

        Map<Object, Object> map = new HashMap<Object, Object>(params.length / 2);
        for (int i = 0; i < params.length; i += 2) {
            String key   = params[i].toString();
            String value = params[i + 1].toString();
            map.put(key, value);
        }
        return pathMap(target, map);
    }
    private String pathMap(T target, Map<Object, Object> params) {
        Set<Path> paths = reverseRoutes.get(target);
        if (paths == null) {
            return null;
        }

        try {
            // The best one is the one with minimum number of params in the query
            String bestCandidate  = null;
            int    minQueryParams = Integer.MAX_VALUE;

            boolean     matched  = true;
            Set<String> usedKeys = new HashSet<String>();

            for (Path path : paths) {
                matched = true;
                usedKeys.clear();

                // "+ 16": Just in case the part befor that is 0
                int           initialCapacity = path.path().length() + 20 * params.size() + 16;
                StringBuilder b               = new StringBuilder(initialCapacity);

                for (String token : path.tokens()) {
                    b.append('/');

                    if (token.length() > 0 && token.charAt(0) == ':') {
                        String key   = token.substring(1);
                        Object value = params.get(key);
                        if (value == null) {
                            matched = false;
                            break;
                        }

                        usedKeys.add(key);
                        b.append(value.toString());
                    } else {
                        b.append(token);
                    }
                }

                if (matched) {
                    int numQueryParams = params.size() - usedKeys.size();
                    if (numQueryParams < minQueryParams) {
                        if (numQueryParams > 0) {
                            boolean firstQueryParam = true;

                            for (Map.Entry<Object, Object> entry : params.entrySet()) {
                                String key = entry.getKey().toString();
                                if (!usedKeys.contains(key)) {
                                    if (firstQueryParam) {
                                        b.append('?');
                                        firstQueryParam = false;
                                    } else {
                                        b.append('&');
                                    }

                                    String value = entry.getValue().toString();

                                    // May throw UnsupportedEncodingException
                                    b.append(URLEncoder.encode(key, "UTF-8"));

                                    b.append('=');

                                    // May throw UnsupportedEncodingException
                                    b.append(URLEncoder.encode(value, "UTF-8"));
                                }
                            }
                        }

                        bestCandidate  = b.toString();
                        minQueryParams = numQueryParams;
                    }
                }
            }

            return bestCandidate;
        } catch (UnsupportedEncodingException e) {
            log.warn("Params can't be UTF-8 encoded: " + params);
            return null;
        }
    }

}
