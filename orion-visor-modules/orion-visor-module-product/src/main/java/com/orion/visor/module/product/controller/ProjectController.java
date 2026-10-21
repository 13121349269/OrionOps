package com.orion.visor.module.product.controller;

import com.orion.visor.framework.common.pojo.CommonResult;
import com.orion.visor.framework.common.pojo.PageResult;
import com.orion.visor.module.product.model.req.ProjectCreateReq;
import com.orion.visor.module.product.model.req.ProjectPageReq;
import com.orion.visor.module.product.model.req.ProjectUpdateReq;
import com.orion.visor.module.product.model.resp.ProjectResp;
import com.orion.visor.module.product.service.ProjectService;
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
 * 项目 Controller
 *
 * @author ops-platform
 */
@Tag(name = "项目管理")
@RestController
@RequestMapping("/project")
@Validated
public class ProjectController {

    @Resource
    private ProjectService projectService;

    @PostMapping("/create")
    @Operation(summary = "创建项目")
    @PreAuthorize("@ss.hasPermission('ops:project:create')")
    public CommonResult<Long> createProject(@Valid @RequestBody ProjectCreateReq req) {
        return success(projectService.createProject(req));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目")
    @PreAuthorize("@ss.hasPermission('ops:project:update')")
    public CommonResult<Boolean> updateProject(@Valid @RequestBody ProjectUpdateReq req) {
        projectService.updateProject(req);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目")
    @Parameter(name = "id", description = "项目ID", required = true)
    @PreAuthorize("@ss.hasPermission('ops:project:delete')")
    public CommonResult<Boolean> deleteProject(@RequestParam("id") Long id) {
        projectService.deleteProject(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取项目详情")
    @Parameter(name = "id", description = "项目ID", required = true)
    @PreAuthorize("@ss.hasPermission('ops:project:query')")
    public CommonResult<ProjectResp> getProject(@RequestParam("id") Long id) {
        return success(projectService.getProject(id));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询项目列表")
    @PreAuthorize("@ss.hasPermission('ops:project:query')")
    public CommonResult<PageResult<ProjectResp>> getProjectPage(ProjectPageReq req) {
        List<ProjectResp> list = projectService.getProjectPage(req);
        return success(new PageResult<>(list, (long) list.size()));
    }

    @GetMapping("/list-by-line")
    @Operation(summary = "根据产品线ID查询项目列表")
    @Parameter(name = "productLineId", description = "产品线ID", required = true)
    public CommonResult<List<ProjectResp>> getProjectListByProductLine(
            @RequestParam("productLineId") Long productLineId) {
        return success(projectService.getProjectListByProductLine(productLineId));
    }
}
