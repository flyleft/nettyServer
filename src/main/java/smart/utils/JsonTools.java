package smart.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * The type Json tools.
 */
public class JsonTools {
    private static Gson gson=new Gson();//静态创建Gson一次，避免多次创建产生大量对象

    /**
     * Read t.
     *
     * @param <T>        the type parameter
     * @param jsonString the json string
     * @param claz       the claz
     * @return the t
     */
    public static  <T> T read(String jsonString, Class<T> claz) {
        T t = null;
        try {
            t = gson.fromJson(jsonString, claz);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * Write to org string.
     *
     * @param obj the obj
     * @return the string
     */
    public static String writeToOrg(Object obj){
        return (gson.toJson(obj));
    }
}