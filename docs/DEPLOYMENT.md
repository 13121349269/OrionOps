# OrionOps 部署指南

## 前置条件

- Docker 20.10+
- Docker Compose 2.0+

## 快速部署

```bash
# 1. 克隆项目
git clone https://github.com/13121349269/OrionOps.git
cd OrionOps

# 2. 构建应用 (需要先克隆 Orion-Visor 基座并集成模块)
#    参考 README.md 中的快速开始

# 3. 将构建好的 jar 放入 app/ 目录
mkdir -p app
cp orion-visor-launch/target/orion-visor-launch.jar app/

# 4. 启动全部服务
docker compose up -d

# 5. 查看状态
docker compose ps
docker compose logs -f orionops-app
```

## 服务端口

| 服务 | 端口 | 说明 |
|------|:----:|------|
| OrionOps 应用 | 80 | Web + API |
| MySQL | 3306 | 数据库 |
| Redis | 6379 | 缓存 |
| InfluxDB | 8086 | 时序指标 |

## 默认账号

- 用户名: admin
- 密码: 见 Orion-Visor 初始化脚本

## 环境变量

| 变量 | 默认值 | 说明 |
|------|--------|------|
| SPRING_PROFILES_ACTIVE | prod | Spring 配置文件 |
| SPRING_DATASOURCE_URL | jdbc:mysql://mysql:3306/orion_visor | 数据库连接 |
| SPRING_DATASOURCE_PASSWORD | OrionOps@2026 | 数据库密码 |
| SPRING_REDIS_HOST | redis | Redis 地址 |
| SPRING_REDIS_PASSWORD | OrionOps@2026 | Redis 密码 |
| SPRING_INFLUXDB_URL | http://influxdb:8086 | InfluxDB 地址 |
| SPRING_INFLUXDB_TOKEN | orionops-influxdb-token-2026 | InfluxDB Token |
| ORION_VISOR_VAULT_AES_KEY | (需更换) | 密钥保险箱 AES 加密密钥 |

## 安全注意事项

1. **生产环境必须修改以下密码**:
   - MySQL root 密码
   - Redis 密码
   - InfluxDB admin 密码
   - Vault AES 加密密钥

2. **Docker Socket 挂载**: 应用容器挂载 `/var/run/docker.sock` 用于 Docker 容器管理。
   生产环境建议限制容器权限或使用远程 Docker API + TLS。

3. **密钥保险箱**: 二次验证逻辑需对接用户认证体系后才能正式启用。

## 停止服务

```bash
docker compose down

# 清除数据 (危险!)
# docker compose down -v
```
