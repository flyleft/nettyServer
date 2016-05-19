package smart.core.cache;

import java.util.Map;

public interface CacheInter {

    void add(String key, Object value, int expiration);

    boolean safeAdd(String key, Object value, int expiration);

    void set(String key, Object value, int expiration);

    boolean safeSet(String key, Object value, int expiration);

    void replace(String key, Object value, int expiration);

    boolean safeReplace(String key, Object value, int expiration);

    Object get(String key);

    Map<String, Object> get(String[] keys);

    long incr(String key, int by);

    long decr(String key, int by);

    void clear();

    void delete(String key);

    boolean safeDelete(String key);

    void stop();
}
