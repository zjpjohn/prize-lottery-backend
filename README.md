# prize-lottery-backend

> 彩票服务端基于jdk21，技术栈采用springboot3.3.2、spring cloud
> alibaba2023.0.3.2、nacos2.5、dubbo3.2.17、mybatis、mysql8.0、redis等等
>
> 本项目采用DDD领域驱动CQE模式开发，整洁架构代码规范清晰，欢迎下载学习交流
>
> app端新版仓库:https://github.com/zjpjohn/prize_lottery_app
>
> 管理端前端仓库:https://github.com/zjpjohn/prize-lottery-front
>
> 如有私有化部署需求或指导请联系(添加微信-zjpcomeon 备注-lottery-app部署)
>
> 项目结构说明：
>
> 1.prize-lottery-gateway: 服务网关
>
> 2.prize-lottery-operate: 运营支撑服务
>
> 3.prize-lottery-payment: 支付服务
>
> 4.prize-lottery-push: 消息服务
>
> 5.prize-lottery-user-app: 用户中心服务
>
> 6.prize-lottery-lotto-app: 彩票服务业务服务
>
> 7.prize-lottery-lotto-compute: 彩票服务计算服务
>
> 8.prize-lottery-utilize: 公共工具包

## 项目构建启动

``` bash
  # 安装必要依赖 
  nacos2.5、jdk21、mysql8.0、redis
  # 创建数据导
  导入dicuments中sql脚本
  # 启动网关
  启动prize-lottery-gateway
  # 启动运营服务
  启动prize-lottery-operate
  # 启动用户服务(后续服务依赖用户服务)
  启动prize-lottery-user-app
  # 后续启动剩余服务(不限顺序)
  启动prize-lottery-lotto-app、prize-lottery-lotto-compute、
  prize-lottery-push-app、prize-lottery-push-app、prize-lottery-payment-app等服务
```

#### 注：后端项目依赖封装的快读开发工具包，暂未开源，后续计划开源