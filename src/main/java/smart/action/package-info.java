/**
 * action的包
 * 所有HTTP请求将会被路由到该包下的类中act方法中处理
 * 该包下的action类要想处理:1.必须实现Action接口，2.必须到RouterSetting中注册
 * 推荐将每个action，尤其频繁使用的action设置为单例，可通过spring IOC实现单利
 */
package smart.action;