package com.orion.visor.module.container.controller;

import com.orion.visor.framework.common.pojo.CommonResult;
import com.orion.visor.module.container.model.req.ContainerActionReq;
import com.orion.visor.module.container.model.req.DockerHostCreateReq;
import com.orion.visor.module.container.model.req.DockerHostPageReq;
import com.orion.visor.module.container.model.req.DockerHostUpdateReq;
import com.orion.visor.module.container.model.resp.ContainerResp;
import com.orion.visor.module.container.model.resp.DockerHostResp;
import com.orion.visor.module.container.service.DockerService;
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
 * Docker 容器管理 Controller
 *
 * @author ops-platform
 */
@Tag(name = "Docker容器管理")
@RestController
@RequestMapping("/container")
@Validated
public class DockerController {

    @Resource
    private DockerService dockerService;

    // ========== Docker主机配置 ==========

    @PostMapping("/host/create")
    @Operation(summary = "创建Docker主机配置")
    @PreAuthorize("@ss.hasPermission('ops:container:host:create')")
    public CommonResult<Long> createDockerHost(@Valid @RequestBody DockerHostCreateReq req) {
        return success(dockerService.createDockerHost(req));
    }

    @PutMapping("/host/update")
    @Operation(summary = "更新Docker主机配置")
    @PreAuthorize("@ss.hasPermission('ops:container:host:update')")
    public CommonResult<Boolean> updateDockerHost(@Valid @RequestBody DockerHostUpdateReq req) {
        dockerService.updateDockerHost(req);
        return success(true);
    }

    @DeleteMapping("/host/delete")
    @Operation(summary = "删除Docker主机配置")
    @Parameter(name = "id", required = true)
    @PreAuthorize("@ss.hasPermission('ops:container:host:delete')")
    public CommonResult<Boolean> deleteDockerHost(@RequestParam("id") Long id) {
        dockerService.deleteDockerHost(id);
        return success(true);
    }

    @PostMapping("/host/test-connection")
    @Operation(summary = "测试Docker连接")
    @Parameter(name = "id", required = true)
    @PreAuthorize("@ss.hasPermission('ops:container:host:update')")
    public CommonResult<Boolean> testConnection(@RequestParam("id") Long id) {
        return success(dockerService.testConnection(id));
    }

    @GetMapping("/host/page")
    @Operation(summary = "分页查询Docker主机")
    @PreAuthorize("@ss.hasPermission('ops:container:host:query')")
    public CommonResult<List<DockerHostResp>> getDockerHostPage(DockerHostPageReq req) {
        return success(dockerService.getDockerHostPage(req));
    }

    @GetMapping("/host/get")
    @Operation(summary = "获取Docker主机详情")
    @Parameter(name = "id", required = true)
    @PreAuthorize("@ss.hasPermission('ops:container:host:query')")
    public CommonResult<DockerHostResp> getDockerHost(@RequestParam("id") Long id) {
        return success(dockerService.getDockerHost(id));
    }

    // ========== 容器操作 ==========

    @GetMapping("/container/list")
    @Operation(summary = "列出容器")
    @Parameter(name = "dockerHostId", required = true)
    @Parameter(name = "all", description = "是否包含停止的容器")
    @PreAuthorize("@ss.hasPermission('ops:container:container:query')")
    public CommonResult<List<ContainerResp>> listContainers(@RequestParam("dockerHostId") Long dockerHostId,
                                                            @RequestParam(value = "all", defaultValue = "true") boolean all) {
        return success(dockerService.listContainers(dockerHostId, all));
    }

    @PostMapping("/container/action")
    @Operation(summary = "容器操作 (start/stop/restart/pause/unpause/remove)")
    @PreAuthorize("@ss.hasPermission('ops:container:container:action')")
    public CommonResult<Boolean> containerAction(@Valid @RequestBody ContainerActionReq req) {
        dockerService.containerAction(req);
        return success(true);
    }

    @GetMapping("/container/logs")
    @Operation(summary = "获取容器日志")
    @Parameter(name = "dockerHostId", required = true)
    @Parameter(name = "containerId", required = true)
    @Parameter(name = "tailLines", description = "最后N行")
    @PreAuthorize("@ss.hasPermission('ops:container:container:query')")
    public CommonResult<String> getContainerLogs(@RequestParam("dockerHostId") Long dockerHostId,
                                                  @RequestParam("containerId") String containerId,
                                                  @RequestParam(value = "tailLines", defaultValue = "100") int tailLines) {
        return success(dockerService.getContainerLogs(dockerHostId, containerId, tailLines));
    }
}
