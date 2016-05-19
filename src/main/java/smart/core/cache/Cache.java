package smart.core.cache;

import smart.utils.Time;

import java.util.Map;

/**
 * 依靠Ehcache实现的缓存
 */
public class Cache {
    /**
     * 隐形的缓存实现类
     */
    public static CacheInter cacheInter;
    
    /**
     * 用于改变缓存的实现类
     */
    public static CacheInter forcedCacheInter;

    /**
     * 按指定的存活期增加一个不存在的元素
     * @param key  key
     * @param value  value
     * @param expiration Ex: 10s, 3mn, 8h
     */
    public static void add(String key, Object value, String expiration) {
        checkSerializable(value);
        cacheInter.add(key, value, Time.parseDuration(expiration));
    }

    /**
     * 按指定的存活期增加一个不存在的元素, 并且只在有效的缓存之后返回
     * @param key  key
     * @param value  value
     * @param expiration Ex: 10s, 3mn, 8h
     * @return 如果元素确实被加入缓存
     */
    public static boolean safeAdd(String key, Object value, String expiration) {
        checkSerializable(value);
        return cacheInter.safeAdd(key, value, Time.parseDuration(expiration));
    }

    /**
     * 增加一个存活期无限的元素
     * @param key  key
     * @param value  value
     */
    public static void add(String key, Object value) {
        checkSerializable(value);
        cacheInter.add(key, value, Time.parseDuration(null));
    }

    /**
     * 按指定的存活期设置一个元素
     * @param key  key
     * @param value  value
     * @param expiration Ex: 10s, 3mn, 8h
     */
    public static void set(String key, Object value, String expiration) {
        checkSerializable(value);
        cacheInter.set(key, value, Time.parseDuration(expiration));
    }

    /**
     * 按指定的存活期设置一个元素, 并且只在有效的缓存之后返回
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     * @return 如果元素确实被加入缓存
     */
    public static boolean safeSet(String key, Object value, String expiration) {
        checkSerializable(value);
        return cacheInter.safeSet(key, value, Time.parseDuration(expiration));
    }

    /**
     * 设置一个有无限存活期的元素
     * @param key Element key
     * @param value Element value
     */
    public static void set(String key, Object value) {
        checkSerializable(value);
        cacheInter.set(key, value, Time.parseDuration(null));
    }

    /**
     * 按指定的存活期替换一个已经存在的元素
     * @param key  key
     * @param value  value
     * @param expiration Ex: 10s, 3mn, 8h
     */
    public static void replace(String key, Object value, String expiration) {
        checkSerializable(value);
        cacheInter.replace(key, value, Time.parseDuration(expiration));
    }

    /**
     * 替换一个已经存在的元素并且只在确实写入缓存之后返回
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     * @return 如果元素确实被加入缓存生效
     */
    public static boolean safeReplace(String key, Object value, String expiration) {
        checkSerializable(value);
        return cacheInter.safeReplace(key, value, Time.parseDuration(expiration));
    }

    /**
     * 替换一个已经存在的元素，并且使它的存活期无限
     * @param key  key
     * @param value  value
     */
    public static void replace(String key, Object value) {
        checkSerializable(value);
        cacheInter.replace(key, value, Time.parseDuration(null));
    }

    /**
     * 增加value的值（value必须为一个数字）
     * @param key Element key 
     * @param by The incr value
     * @return 新的value值
     */
    public static long incr(String key, int by) {
        return cacheInter.incr(key, by);
    }

    /**
     * value的值增加1（value必须为一个数字）
     * @param key Element key 
     * @return 新的value值
     */
    public static long incr(String key) {
        return cacheInter.incr(key, 1);
    }

    /**
     * 减小value的值（value必须为一个数字）
     * @param key Element key 
     * @param by The decr value
     * @return 新的value值
     */
    public static long decr(String key, int by) {
        return cacheInter.decr(key, by);
    }

    /**
     * 使value的值减小1（value必须为一个数字）
     * @param key key
     * @return 新的value值
     */
    public static long decr(String key) {
        return cacheInter.decr(key, 1);
    }

    /**
     * 根据key取回对应的object值
     * @param key key
     * @return 元素值
     */
    public static Object get(String key) {
        return cacheInter.get(key);
    }

    /**
     * 批量取值
     * @param key key集合，可变参数
     * @return map<key,value>
     */
    public static Map<String, Object> get(String... key) {
        return cacheInter.get(key);
    }

    /**
     * 从缓存中删除某个元素
     * @param key 要删除元素的key
     */
    public static void delete(String key) {
        cacheInter.delete(key);
    }

    /**
     * 从缓存中删除某个元素,只有确实从缓存中删除才返回
     * @param key key
     * @return 如果元素确实被从缓存中删除则返回true
     */
    public static boolean safeDelete(String key) {
        return cacheInter.safeDelete(key);
    }

    /**
     * 从缓存中删除所有元素
     */
    public static void clear() {
        if (cacheInter != null) {
            cacheInter.clear();
        }
    }

    /**
     * 泛型取值
     * @param <T> 需要的元素类型
     * @param key key
     * @param clazz 元素类型
     * @return 如果存在则返回该元素，不存在就返回null
     */
    @SuppressWarnings("unchecked")
	public static <T> T get(String key, Class<T> clazz) {
        return (T) cacheInter.get(key);
    }

    /**
     * 初始化缓存系统
     */
    public static void init() {
        if(forcedCacheInter != null) {
            cacheInter = forcedCacheInter;
            return;
        }
            cacheInter = EhCacheImpl.newInstance();
    }

    /**
     * 停止缓存系统
     */
    public static void stop() {
        cacheInter.stop();
    }
    
    /**
     * 检查要存入的元素是否为空
     * 减产加入缓存的元素是否序列化，因为本项目只需要将Logic.DeviceSession加入缓存
     * 且已经实现序列化接口，就不每次检查是否序列化，以提高性能
     */
    static void checkSerializable(Object value) {
        if(value==null){
            throw new RuntimeException("要加入缓存的元素不可以为空!");
        }
       /* else if(!(value instanceof Serializable)){
            throw new CacheException("不可以缓存没有实现序列化的类型:" + value.getClass().getName(),
                    new NotSerializableException(value.getClass().getName()));
        }*/
    }
}

