package com.orion.visor.module.javaagent.controller;

import com.orion.visor.framework.common.pojo.CommonResult;
import com.orion.visor.module.javaagent.model.req.JavaAppCreateReq;
import com.orion.visor.module.javaagent.model.req.JavaAppPageReq;
import com.orion.visor.module.javaagent.model.req.JavaAppUpdateReq;
import com.orion.visor.module.javaagent.model.resp.JavaAppResp;
import com.orion.visor.module.javaagent.model.resp.JvmSnapshotResp;
import com.orion.visor.module.javaagent.service.JavaAgentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.orion.visor.framework.common.pojo.CommonResult.success;

/**
 * Java JVM 诊断 Controller
 *
 * @author ops-platform
 */
@Tag(name = "Java JVM诊断")
@RestController
@RequestMapping("/javaagent")
@Validated
public class JavaAgentController {

    @Resource
    private JavaAgentService javaAgentService;

    @PostMapping("/app/create")
    @Operation(summary = "创建Java应用监控")
    @PreAuthorize("@ss.hasPermission('ops:javaagent:app:create')")
    public CommonResult<Long> createJavaApp(@Valid @RequestBody JavaAppCreateReq req) {
        return success(javaAgentService.createJavaApp(req));
    }

    @PutMapping("/app/update")
    @Operation(summary = "更新Java应用")
    @PreAuthorize("@ss.hasPermission('ops:javaagent:app:update')")
    public CommonResult<Boolean> updateJavaApp(@Valid @RequestBody JavaAppUpdateReq req) {
        javaAgentService.updateJavaApp(req);
        return success(true);
    }

    @DeleteMapping("/app/delete")
    @Operation(summary = "删除Java应用")
    @Parameter(name = "id", required = true)
    @PreAuthorize("@ss.hasPermission('ops:javaagent:app:delete')")
    public CommonResult<Boolean> deleteJavaApp(@RequestParam("id") Long id) {
        javaAgentService.deleteJavaApp(id);
        return success(true);
    }

    @PostMapping("/app/test-connection")
    @Operation(summary = "测试JMX连接")
    @Parameter(name = "id", required = true)
    @PreAuthorize("@ss.hasPermission('ops:javaagent:app:update')")
    public CommonResult<Boolean> testConnection(@RequestParam("id") Long id) {
        return success(javaAgentService.testConnection(id));
    }

    @GetMapping("/app/page")
    @Operation(summary = "分页查询Java应用")
    @PreAuthorize("@ss.hasPermission('ops:javaagent:app:query')")
    public CommonResult<List<JavaAppResp>> getJavaAppPage(JavaAppPageReq req) {
        return success(javaAgentService.getJavaAppPage(req));
    }

    @GetMapping("/app/get")
    @Operation(summary = "获取Java应用详情")
    @Parameter(name = "id", required = true)
    @PreAuthorize("@ss.hasPermission('ops:javaagent:app:query')")
    public CommonResult<JavaAppResp> getJavaApp(@RequestParam("id") Long id) {
        return success(javaAgentService.getJavaApp(id));
    }

    @PostMapping("/snapshot/collect")
    @Operation(summary = "采集JVM指标快照")
    @Parameter(name = "appId", required = true)
    @PreAuthorize("@ss.hasPermission('ops:javaagent:snapshot:collect')")
    public CommonResult<JvmSnapshotResp> collectSnapshot(@RequestParam("appId") Long appId) {
        return success(javaAgentService.collectSnapshot(appId));
    }

    @GetMapping("/snapshot/history")
    @Operation(summary = "查询JVM指标历史")
    @Parameter(name = "appId", required = true)
    @Parameter(name = "limit", description = "记录条数, 默认50")
    @PreAuthorize("@ss.hasPermission('ops:javaagent:snapshot:query')")
    public CommonResult<List<JvmSnapshotResp>> getSnapshotHistory(@RequestParam("appId") Long appId,
                                                                   @RequestParam(value = "limit", defaultValue = "50") int limit) {
        return success(javaAgentService.getSnapshotHistory(appId, limit));
    }
}
