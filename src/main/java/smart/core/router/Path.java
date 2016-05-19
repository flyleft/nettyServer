package smart.core.router;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;

import java.util.Map;

/**
 * Created by jcala on 2016/4/19
 * @author jcala (jcala.me:9000/blog/jcalaz)
 */
final public class Path {
    private final String   path;
    private final String[] tokens;
    public Path(String path) {
        this.path   = removeSlashesAtBothEnds(path);
        this.tokens = StringUtil.split(this.path, '/');
    }

    /**
     * 去除url路径两边的url
     * @param path 要去除path两边/的url路径
     * @return 去除两边/后的url
     */
    protected static String removeSlashesAtBothEnds(String path) {
        ObjectUtil.checkNotNull(path, "path");//检查path不为null

        if (path.isEmpty()) {//如果path的值为空直接返回
            return path;
        }

        int beginIndex = 0;
        while (beginIndex < path.length() && path.charAt(beginIndex) == '/') {
            beginIndex++;
        }
        if (beginIndex == path.length()) {
            return "";
        }

        int endIndex = path.length() - 1;
        while (endIndex > beginIndex && path.charAt(endIndex) == '/') {
            endIndex--;
        }

        return path.substring(beginIndex, endIndex + 1);
    }
    protected String path() {
        return this.path;
    }//获取去除两边'/'后的url
    protected String[] tokens() {
        return this.tokens;
    }//获取url由'/'分割后的字符串
    @Override
    public int hashCode() {
        return path.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }else if(o instanceof Path){
            return ((Path) o).path.equals(path);
        }else {
            return false;
        }
    }
    protected boolean match(String[] requestPathTokens, Map<String, String> params) {
        if (tokens.length == requestPathTokens.length) {
            for (int i = 0; i < tokens.length; i++) {
                String key   = tokens[i];
                String value = requestPathTokens[i];

                if (key.length() > 0 && key.charAt(0) == ':') {
                    params.put(key.substring(1), value);
                } else if (!key.equals(value)) {
                    return false;
                }
            }

            return true;
        }

        if (tokens.length > 0 &&
                tokens[tokens.length - 1].equals(":*") &&
                tokens.length <= requestPathTokens.length) {
            // The first part
            for (int i = 0; i < tokens.length - 2; i++) {
                String key   = tokens[i];
                String value = requestPathTokens[i];

                if (key.length() > 0 && key.charAt(0) == ':') {
                    // This is a placeholder
                    params.put(key.substring(1), value);
                } else if (!key.equals(value)) {
                    // This is a constant
                    return false;
                }
            }

            // The last :* part
            StringBuilder b = new StringBuilder(requestPathTokens[tokens.length - 1]);
            for (int i = tokens.length; i < requestPathTokens.length; i++) {
                b.append('/');
                b.append(requestPathTokens[i]);
            }
            params.put("*", b.toString());

            return true;
        }

        return false;
    }
}
