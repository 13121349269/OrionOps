package com.orion.visor.module.javaagent.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * JVM指标快照响应 VO
 *
 * @author ops-platform
 */
@Data
@Schema(description = "JVM指标快照")
public class JvmSnapshotResp {

    @Schema(description = "应用ID")
    private Long appId;

    @Schema(description = "堆内存使用(字节)")
    private Long heapUsed;

    @Schema(description = "堆内存最大(字节)")
    private Long heapMax;

    @Schema(description = "堆使用率(%)")
    private Double heapUsagePercent;

    @Schema(description = "非堆内存使用(字节)")
    private Long nonHeapUsed;

    @Schema(description = "线程总数")
    private Integer threadCount;

    @Schema(description = "守护线程数")
    private Integer threadDaemon;

    @Schema(description = "线程峰值")
    private Integer threadPeak;

    @Schema(description = "已加载类数")
    private Integer classCount;

    @Schema(description = "Minor GC次数")
    private Integer gcMinorCount;

    @Schema(description = "Minor GC耗时(ms)")
    private Long gcMinorTime;

    @Schema(description = "Major GC次数")
    private Integer gcMajorCount;

    @Schema(description = "Major GC耗时(ms)")
    private Long gcMajorTime;

    @Schema(description = "CPU使用率(%)")
    private Double cpuUsage;

    @Schema(description = "运行时长(ms)")
    private Long uptime;

    @Schema(description = "采集时间")
    private LocalDateTime createTime;
}
