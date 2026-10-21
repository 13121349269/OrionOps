# OrionOps — 运维管理平台 (二次开发)

基于 [Orion-Visor](https://github.com/dromara/orion-visor) 的运维管理平台二次开发项目。

## 技术栈

- SpringBoot 2.7+
- Vue3 + Arco Design
- MySQL 8.0+
- Redis 6.0+
- InfluxDB 2.7+
- docker-java 3.3.6 (Docker SDK)
- JMX (JVM 远程诊断)

## 模块结构

```
orion-visor-ops/
├── orion-visor-modules/
│   ├── orion-visor-module-product/      # P1: 产品线 + 项目管理
│   ├── orion-visor-module-asset/        # P2: 资产扩展 (主机关联产品线/项目)
│   ├── orion-visor-module-vault/        # P3: 密钥保险箱 (AES-256加密/审计)
│   ├── orion-visor-module-container/   # P4: Docker容器管理
│   ├── orion-visor-module-javaagent/    # P5: Java JVM诊断
│   └── orion-visor-module-monitor/      # P6: 监控告警扩展
├── sql/                                  # 数据库迁移脚本
│   ├── V1.0.0__product_line_project.sql
│   ├── V1.0.1__asset_extend.sql
│   ├── V1.0.2__vault_secret.sql
│   ├── V1.0.3__docker_container.sql
│   ├── V1.0.4__javaagent.sql
│   └── V1.0.5__monitor_alert.sql
└── docs/                                 # 文档
```

## 开发阶段

| 阶段 | 模块 | 状态 | 文件数 |
|------|------|------|:----:|
| P1 | 产品线 + 项目管理 | ✅ 完成 | 20 |
| P2 | 资产扩展 (关联产品线/项目) | ✅ 完成 | 12 |
| P3 | 密钥保险箱 | ✅ 完成 | 16 |
| P4 | Docker 容器管理 | ✅ 完成 | 14 |
| P5 | Java JVM 诊断 | ✅ 完成 | 12 |
| P6 | 监控告警扩展 | ✅ 完成 | 13 |

## API 接口概览

### P1: 产品线 + 项目
```
POST   /product-line/create        PUT    /product-line/update
DELETE /product-line/delete         GET    /product-line/get
GET    /product-line/page           GET    /product-line/simple-list

POST   /project/create             PUT    /project/update
DELETE /project/delete              GET    /project/get
GET    /project/page                GET    /project/list-by-line
```

### P2: 资产扩展
```
POST   /asset/host-product/bind    PUT    /asset/host-product/update
DELETE /asset/host-product/unbind   GET    /asset/host-product/get
GET    /asset/host-product/page     GET    /asset/host-product/list-by-product-line
GET    /asset/host-product/list-by-project
```

### P3: 密钥保险箱
```
POST   /vault/secret/create        PUT    /vault/secret/update
DELETE /vault/secret/delete         GET    /vault/secret/get
GET    /vault/secret/page           POST   /vault/secret/reveal
POST   /vault/secret/rotate         GET    /vault/secret/access-logs
```

### P4: Docker容器管理
```
POST   /container/host/create      PUT    /container/host/update
DELETE /container/host/delete       POST   /container/host/test-connection
GET    /container/host/page         GET    /container/host/get
GET    /container/container/list    POST   /container/container/action
GET    /container/container/logs
```

### P5: Java JVM诊断
```
POST   /javaagent/app/create       PUT    /javaagent/app/update
DELETE /javaagent/app/delete        POST   /javaagent/app/test-connection
GET    /javaagent/app/page          GET    /javaagent/app/get
POST   /javaagent/snapshot/collect  GET    /javaagent/snapshot/history
```

### P6: 监控告警
```
POST   /alert/rule/create           PUT    /alert/rule/update
DELETE /alert/rule/delete           GET    /alert/rule/get
GET    /alert/rule/page             POST   /alert/rule/evaluate
GET    /alert/event/list            GET    /alert/event/list-by-target
POST   /alert/event/resolve
```

## 快速开始

```bash
# 1. 克隆 Orion-Visor 基座
git clone --depth=1 https://github.com/dromara/orion-visor
cd orion-visor

# 2. 将本项目的模块复制到 orion-visor-modules/ 下
# 3. 在 orion-visor-modules/pom.xml 中添加子模块引用
# 4. 执行 SQL 脚本 (sql/V1.0.0 ~ V1.0.5)
# 5. 配置 orion.visor.vault.aes-key (密钥加密密钥)
# 6. 启动项目
docker compose up -d
```

## License

Apache-2.0
