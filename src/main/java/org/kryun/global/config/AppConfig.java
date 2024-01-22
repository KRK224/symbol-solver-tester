package org.kryun.global.config;

public class AppConfig {
    // application
    public static final String APP_NAME = "super-px";
    public static final String APP_VERSION = "0.2.0.hotfix1";
    // project type
    public static final int NORMAL_PROJECT = 0;
    public static final int DEPENDENCY_PROJECT = 1;
    public static final int FROM_DX_FALSE = 0;
    public static final int FROM_DX_TRUE = 1;

    // db
    public static final String DB_POOL_ID = "tibero";
    public static final String DB_USER = "";
    public static final String DB_PASS = "";
    public static final String DB_URL = "jdbc:tibero:thin:@IP:PORT:8629:tibero";

    public static final int DB_MAX_IDLE = 50;
    public static final int DB_MIN_IDLE = 10;
    public static final int DB_MAX_TOTAL = 50;

    // Central Repo URL
    public static final String DEFAULT_MAVEN_REPOSITORY = "https://repo1.maven.org/maven2";

    // Local Nexus Metadata
    public static String LOCAL_MAVEN_REPOSITORY_URL = System.getProperty("LOCAL_MAVEN_REPOSITORY_URL");
    public static String LOCAL_MAVEN_SETTING_SERVER_ID = System.getProperty("LOCAL_MAVEN_SETTING_SERVER_ID");
    public static String LOCAL_MAVEN_SETTING_USERNAME = System.getProperty("LOCAL_MAVEN_SETTING_USERNAME");
    public static String LOCAL_MAVEN_SETTING_PASSWORD = System.getProperty("LOCAL_MAVEN_SETTING_PASSWORD");

    // SAS dependency
    public static final String DEFAULT_SUPER_APP_REPOSITORY = "super-app-release:release:http://192.168.9.12:8081/repository/maven-releases";
    public static final String[] DEFAULT_SUPER_APP_DEPENDENCIES = {
            "com.tmax:super-app-server:0.3.1.hotfix3",
            "com.google.code.gson:gson:2.9.0",
            "org.mybatis:mybatis:3.5.9",
            "org.slf4j:slf4j-api:1.7.32",
            "org.apache.logging.log4j:log4j-core:2.16.0",
            "io.netty:netty-all:4.1.78.Final",
            "com.github.erosb:everit-json-schema:1.14.2",
            "org.junit.jupiter:junit-jupiter:5.8.1:test",
            "org.junit.jupiter:junit-jupiter-engine:5.8.1:runtime"
    };

    // maven
    public static final String MAVEN_NAME = "apache-maven";
    public static final String MAVEN_VERSION = "3.6.3";

    // gradle
    public static final String GRADLE_NAME = "gradle";
    public static final String GRADLE_VERSION = "7.4";

    // file workspace
    public static final String WORKSPACE_PATH = System.getProperty("user.dir") + "/workspace";

    // sasctl
    public static final String SASCTL = "sasctl-0.3.1.hotfix.jar";

    // cicd
    public static final int CICD_PENDING = 0;
    public static final int CICD_PROCESSING = 1;
    public static final int CICD_SUCCESS = 2;
    public static final int CICD_FAILED = 3;

}
