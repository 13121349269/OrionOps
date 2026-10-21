package com.orion.visor.module.asset.controller;

import com.orion.visor.framework.common.pojo.CommonResult;
import com.orion.visor.module.asset.model.req.HostProductBindReq;
import com.orion.visor.module.asset.model.req.HostProductPageReq;
import com.orion.visor.module.asset.model.resp.HostProductResp;
import com.orion.visor.module.asset.service.HostProductService;
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
 * 主机产品线关联 Controller
 *
 * @author ops-platform
 */
@Tag(name = "资产扩展-主机产品线关联")
@RestController
@RequestMapping("/asset/host-product")
@Validated
public class HostProductController {

    @Resource
    private HostProductService hostProductService;

    @PostMapping("/bind")
    @Operation(summary = "绑定主机到产品线/项目")
    @PreAuthorize("@ss.hasPermission('ops:asset:host-product:bind')")
    public CommonResult<Long> bindHostProduct(@Valid @RequestBody HostProductBindReq req) {
        return success(hostProductService.bindHostProduct(req));
    }

    @PutMapping("/update")
    @Operation(summary = "更新主机关联")
    @PreAuthorize("@ss.hasPermission('ops:asset:host-product:update')")
    public CommonResult<Boolean> updateBindHostProduct(@Valid @RequestBody HostProductBindReq req) {
        hostProductService.updateBindHostProduct(req);
        return success(true);
    }

    @DeleteMapping("/unbind")
    @Operation(summary = "解绑主机")
    @Parameter(name = "hostId", description = "主机ID", required = true)
    @PreAuthorize("@ss.hasPermission('ops:asset:host-product:unbind')")
    public CommonResult<Boolean> unbindHostProduct(@RequestParam("hostId") Long hostId) {
        hostProductService.unbindHostProduct(hostId);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "根据主机ID查询关联")
    @Parameter(name = "hostId", description = "主机ID", required = true)
    @PreAuthorize("@ss.hasPermission('ops:asset:host-product:query')")
    public CommonResult<HostProductResp> getByHostId(@RequestParam("hostId") Long hostId) {
        return success(hostProductService.getByHostId(hostId));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询关联列表")
    @PreAuthorize("@ss.hasPermission('ops:asset:host-product:query')")
    public CommonResult<List<HostProductResp>> getHostProductPage(HostProductPageReq req) {
        return success(hostProductService.getHostProductPage(req));
    }

    @GetMapping("/list-by-product-line")
    @Operation(summary = "按产品线查询主机列表")
    @Parameter(name = "productLineId", description = "产品线ID", required = true)
    @PreAuthorize("@ss.hasPermission('ops:asset:host-product:query')")
    public CommonResult<List<HostProductResp>> listByProductLineId(@RequestParam("productLineId") Long productLineId) {
        return success(hostProductService.listByProductLineId(productLineId));
    }

    @GetMapping("/list-by-project")
    @Operation(summary = "按项目查询主机列表")
    @Parameter(name = "projectId", description = "项目ID", required = true)
    @PreAuthorize("@ss.hasPermission('ops:asset:host-product:query')")
    public CommonResult<List<HostProductResp>> listByProjectId(@RequestParam("projectId") Long projectId) {
        return success(hostProductService.listByProjectId(projectId));
    }
}
