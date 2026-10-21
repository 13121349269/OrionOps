# OrionOps Dockerfile
# 基于 Eclipse Temurin JRE (比 JDK 更小、攻击面更小)

FROM eclipse-temurin:8-jre-alpine

LABEL maintainer="ops-platform"
LABEL description="OrionOps - 运维管理平台"

# 时区设置 + curl 健康检查
ENV TZ=Asia/Shanghai
RUN apk add --no-cache tzdata curl && \
    cp /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone

# 创建工作目录
WORKDIR /app

# 复制应用 jar (构建时放入)
COPY app/orion-visor-launch.jar /app/orion-visor-launch.jar

# 暴露端口
EXPOSE 80

# JVM 参数
ENV JAVA_OPTS="-Xms512m -Xmx1024m -Duser.timezone=Asia/Shanghai"

# 健康检查
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
    CMD curl -f http://localhost:80/actuator/health || exit 1

# 启动命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/orion-visor-launch.jar"]
