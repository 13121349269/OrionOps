package com.orion.visor.module.javaagent.dal.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * JVM 指标快照 DO
 *
 * @author ops-platform
 */
@Data
@TableName("ops_jvm_snapshot")
public class JvmSnapshotDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long appId;

    private Long heapUsed;

    private Long heapMax;

    private Long nonHeapUsed;

    private Integer threadCount;

    private Integer threadDaemon;

    private Integer threadPeak;

    private Integer classCount;

    private Integer gcMinorCount;

    private Long gcMinorTime;

    private Integer gcMajorCount;

    private Long gcMajorTime;

    private Double cpuUsage;

    private Long uptime;

    private LocalDateTime createTime;
}
