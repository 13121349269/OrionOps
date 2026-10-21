package com.orion.visor.module.container.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Docker 容器操作枚举
 *
 * @author ops-platform
 */
@Getter
@AllArgsConstructor
public enum DockerActionEnum {

    START("start", "启动"),
    STOP("stop", "停止"),
    RESTART("restart", "重启"),
    PAUSE("pause", "暂停"),
    UNPAUSE("unpause", "恢复"),
    REMOVE("remove", "删除容器"),
    EXEC("exec", "执行命令"),
    LOGS("logs", "查看日志");

    private final String code;
    private final String name;

    public static boolean isValid(String code) {
        if (code == null) return false;
        for (DockerActionEnum e : values()) {
            if (e.code.equals(code)) return true;
        }
        return false;
    }
}
