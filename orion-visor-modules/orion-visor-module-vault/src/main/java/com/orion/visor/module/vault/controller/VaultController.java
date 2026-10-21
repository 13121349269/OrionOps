package com.orion.visor.module.vault.controller;

import com.orion.visor.framework.common.pojo.CommonResult;
import com.orion.visor.module.vault.model.req.VaultRevealReq;
import com.orion.visor.module.vault.model.req.VaultSecretCreateReq;
import com.orion.visor.module.vault.model.req.VaultSecretPageReq;
import com.orion.visor.module.vault.model.req.VaultSecretUpdateReq;
import com.orion.visor.module.vault.model.resp.VaultAccessLogResp;
import com.orion.visor.module.vault.model.resp.VaultSecretRevealResp;
import com.orion.visor.module.vault.model.resp.VaultSecretResp;
import com.orion.visor.module.vault.service.VaultService;
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
 * 密钥保险箱 Controller
 *
 * @author ops-platform
 */
@Tag(name = "密钥保险箱")
@RestController
@RequestMapping("/vault/secret")
@Validated
public class VaultController {

    @Resource
    private VaultService vaultService;

    @PostMapping("/create")
    @Operation(summary = "创建密钥")
    @PreAuthorize("@ss.hasPermission('ops:vault:secret:create')")
    public CommonResult<Long> createSecret(@Valid @RequestBody VaultSecretCreateReq req) {
        return success(vaultService.createSecret(req));
    }

    @PutMapping("/update")
    @Operation(summary = "更新密钥")
    @PreAuthorize("@ss.hasPermission('ops:vault:secret:update')")
    public CommonResult<Boolean> updateSecret(@Valid @RequestBody VaultSecretUpdateReq req) {
        vaultService.updateSecret(req);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除密钥")
    @Parameter(name = "id", description = "密钥ID", required = true)
    @PreAuthorize("@ss.hasPermission('ops:vault:secret:delete')")
    public CommonResult<Boolean> deleteSecret(@RequestParam("id") Long id) {
        vaultService.deleteSecret(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取密钥详情 (不含明文)")
    @Parameter(name = "id", description = "密钥ID", required = true)
    @PreAuthorize("@ss.hasPermission('ops:vault:secret:query')")
    public CommonResult<VaultSecretResp> getSecret(@RequestParam("id") Long id) {
        return success(vaultService.getSecret(id));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询密钥列表")
    @PreAuthorize("@ss.hasPermission('ops:vault:secret:query')")
    public CommonResult<List<VaultSecretResp>> getSecretPage(VaultSecretPageReq req) {
        return success(vaultService.getSecretPage(req));
    }

    @PostMapping("/reveal")
    @Operation(summary = "明文查看密钥 (需二次验证)")
    @PreAuthorize("@ss.hasPermission('ops:vault:secret:reveal')")
    public CommonResult<VaultSecretRevealResp> revealSecret(@Valid @RequestBody VaultRevealReq req) {
        return success(vaultService.revealSecret(req));
    }

    @PostMapping("/rotate")
    @Operation(summary = "轮换密钥")
    @Parameter(name = "id", description = "密钥ID", required = true)
    @Parameter(name = "newValue", description = "新密钥值", required = true)
    @PreAuthorize("@ss.hasPermission('ops:vault:secret:rotate')")
    public CommonResult<Boolean> rotateSecret(@RequestParam("id") Long id,
                                              @RequestParam("newValue") String newValue) {
        vaultService.rotateSecret(id, newValue);
        return success(true);
    }

    @GetMapping("/access-logs")
    @Operation(summary = "查询密钥访问日志")
    @Parameter(name = "secretId", description = "密钥ID", required = true)
    @PreAuthorize("@ss.hasPermission('ops:vault:secret:query')")
    public CommonResult<List<VaultAccessLogResp>> getAccessLogs(@RequestParam("secretId") Long secretId) {
        return success(vaultService.getAccessLogs(secretId));
    }
}
