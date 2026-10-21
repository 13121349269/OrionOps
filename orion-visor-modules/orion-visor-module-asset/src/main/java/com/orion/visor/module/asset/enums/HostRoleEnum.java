package com.orion.visor.module.asset.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 主机角色枚举
 *
 * @author ops-platform
 */
@Getter
@AllArgsConstructor
public enum HostRoleEnum {

    WEB("web", "Web服务器"),
    APP("app", "应用服务器"),
    DB("db", "数据库服务器"),
    CACHE("cache", "缓存服务器"),
    MQ("mq", "消息队列"),
    OTHER("other", "其他");

    private final String code;
    private final String name;

    public static boolean isValid(String code) {
        if (code == null) {
            return false;
        }
        for (HostRoleEnum e : values()) {
            if (e.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
