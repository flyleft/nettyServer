package smart.utils;

import java.util.StringTokenizer;

/**
 * Created by jcala on 2016/5/8
 */
public class StringTools {
    public static String getMac(String uri) {
        StringTokenizer token = new StringTokenizer(uri, "?mac=");
        token.nextElement();
        if (token.hasMoreElements()) {
            return (String) token.nextElement();
        }
        return null;
    }
}
