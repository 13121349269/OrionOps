package com.orion.visor.module.product.controller;

import com.orion.visor.framework.common.pojo.CommonResult;
import com.orion.visor.framework.common.pojo.PageResult;
import com.orion.visor.module.product.model.req.ProductLineCreateReq;
import com.orion.visor.module.product.model.req.ProductLinePageReq;
import com.orion.visor.module.product.model.req.ProductLineUpdateReq;
import com.orion.visor.module.product.model.resp.ProductLineResp;
import com.orion.visor.module.product.service.ProductLineService;
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
 * 产品线 Controller
 *
 * @author ops-platform
 */
@Tag(name = "产品线管理")
@RestController
@RequestMapping("/product-line")
@Validated
public class ProductLineController {

    @Resource
    private ProductLineService productLineService;

    @PostMapping("/create")
    @Operation(summary = "创建产品线")
    @PreAuthorize("@ss.hasPermission('ops:product-line:create')")
    public CommonResult<Long> createProductLine(@Valid @RequestBody ProductLineCreateReq req) {
        return success(productLineService.createProductLine(req));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品线")
    @PreAuthorize("@ss.hasPermission('ops:product-line:update')")
    public CommonResult<Boolean> updateProductLine(@Valid @RequestBody ProductLineUpdateReq req) {
        productLineService.updateProductLine(req);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除产品线")
    @Parameter(name = "id", description = "产品线ID", required = true)
    @PreAuthorize("@ss.hasPermission('ops:product-line:delete')")
    public CommonResult<Boolean> deleteProductLine(@RequestParam("id") Long id) {
        productLineService.deleteProductLine(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取产品线详情")
    @Parameter(name = "id", description = "产品线ID", required = true)
    @PreAuthorize("@ss.hasPermission('ops:product-line:query')")
    public CommonResult<ProductLineResp> getProductLine(@RequestParam("id") Long id) {
        return success(productLineService.getProductLine(id));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询产品线列表")
    @PreAuthorize("@ss.hasPermission('ops:product-line:query')")
    public CommonResult<PageResult<ProductLineResp>> getProductLinePage(ProductLinePageReq req) {
        List<ProductLineResp> list = productLineService.getProductLinePage(req);
        // TODO: 接入分页插件后返回 PageResult
        return success(new PageResult<>(list, (long) list.size()));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获取启用的产品线列表 (下拉框用)")
    public CommonResult<List<ProductLineResp>> getEnabledProductLineList() {
        return success(productLineService.getEnabledProductLineList());
    }
}
