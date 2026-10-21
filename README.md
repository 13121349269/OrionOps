# Orion-Visor-Ops — 运维管理平台 (二次开发)

基于 [Orion-Visor](https://github.com/dromara/orion-visor) 的运维管理平台二次开发项目。

## 技术栈

- SpringBoot 2.7+
- Vue3 + Arco Design
- MySQL 8.0+
- Redis 6.0+
- InfluxDB 2.7+

## 模块结构

```
orion-visor-ops/
├── orion-visor-modules/
│   └── orion-visor-module-product/   # P1: 产品线 + 项目管理
│       ├── controller/               # REST API
│       ├── service/                  # 业务逻辑
│       ├── dal/                       # 数据访问层
│       │   ├── dataobject/           # DO
│       │   └── mysql/               # Mapper
│       ├── model/
│       │   ├── req/                  # 请求 DTO
│       │   └── resp/                 # 响应 VO
│       ├── convert/                  # MapStruct 转换器
│       └── enums/                    # 枚举 + 错误码
├── sql/                              # 数据库迁移脚本
└── docs/                             # 文档
```

## 开发阶段

| 阶段 | 模块 | 状态 |
|------|------|------|
| P1 | 产品线 + 项目管理 | 进行中 |
| P2 | 资产扩展 (关联产品线/项目) | 待开发 |
| P3 | 密钥保险箱 | 待开发 |
| P4 | Docker 容器管理 | 待开发 |
| P5 | Java JVM 诊断 | 待开发 |
| P6 | 监控扩展 | 待开发 |

## 快速开始

```bash
# 1. 克隆 Orion-Visor 基座
git clone --depth=1 https://github.com/dromara/orion-visor
cd orion-visor

# 2. 将本项目的 module-product 复制到 orion-visor-modules/ 下
# 3. 在 orion-visor-modules/pom.xml 中添加子模块引用
# 4. 执行 SQL 脚本 (sql/V1.0.0__product_line_project.sql)
# 5. 启动项目
docker compose up -d
```

## License

Apache-2.0
