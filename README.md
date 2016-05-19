```groovy
 //---------------------单元测试----------------------------
    testCompile group: 'junit', name: 'junit', version: '4.11'
    //--------------------数据库驱动----------------------------
    compile 'org.mongodb:mongodb-driver:3.2.2'
    compile 'mysql:mysql-connector-java:5.1.38'
    //-------------------数据库连接池---------------------------
    compile 'com.zaxxer:HikariCP:2.4.5'
    //----------------------ORM------------------------------
    compile group: 'org.mybatis', name: 'mybatis', version:mybatisVersion
    compile group: 'org.mybatis', name: 'mybatis-spring', version:mybatisSpringVersion
    //-----------------------缓存----------------------------
    compile group: 'net.sf.ehcache', name: 'ehcache', version:ehcacheVersion
    //----------------------工具包----------------------------
    compile 'commons-httpclient:commons-httpclient:3.1-rc1'
    compile 'org.javassist:javassist:3.20.0-GA'
    //---------------------日志处理----------------------------
    compile 'org.slf4j:slf4j-api:1.7.21'
    compile 'ch.qos.logback:logback-core:1.1.7'
    compile 'ch.qos.logback:logback-classic:1.1.7'
    //---------------------json处理---------------------------
    compile 'com.google.code.gson:gson:2.6.2'
    //---------------------netty-----------------------------
    compile group: 'io.netty', name: 'netty-all', version:nettyVersion
    //---------------------spring----------------------------
    compile group: 'org.springframework', name: 'spring-test', version:springVersion
    compile group: 'org.springframework', name: 'spring-jdbc', version:springVersion
    compile(group: 'org.springframework', name: 'spring-context', version:springVersion) {
        exclude(module: 'commons-logging')
    }
```