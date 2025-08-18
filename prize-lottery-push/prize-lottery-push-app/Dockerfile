#java8基础镜像
FROM openjdk:21

#维护者
MAINTAINER zhoujiaqi

#设置编码时区环境变量
ENV LANG C.UTF-8
ENV LAC_ALL C.UTF-8
ENV TZ=Asia/Shanghai
# 注册到注册中心的端口
ENV DUBBO_PORT_TO_REGISTRY 21580
# 监听端口
ENV DUBBO_PORT_TO_BIND 21580

#声明挂载点
VOLUME /tmp

#将jar包复制到镜像内
ADD target/prize-lottery-push.jar prize-lottery-push.jar

#声明暴露端口
EXPOSE 9091

#设置jvm参数变量
ENV JAVA_OPTS="-server -Xmx4096m -Xms4096m -Xmn128m -Xss512k -XX:SurvivorRatio=8 -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m -XX:ParallelGCThreads=4"

#启动容器进程
ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /prize-lottery-push.jar
