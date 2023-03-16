## code-scaffold-project

#### 介绍
一个代码脚手架工程，提供了各种常用的代码操作示例！主要为springboot集成各种组件的示例代码工程；

其中除基础微服务提供基本的公共属性外，其他微服务应都可以独立运行，且并不相互通信；

因为本工程的初衷只是为了说明各技术点及其中间件的使用示例，并不做架构层面的设计；

如需某个微服务为单独的公用服务，自行抽取暴露出来应用到你们自己工程中即可；

#### 软件架构
jdk1.8 + springboot2.4.5 +mysql8.0+flyway作为基础

其中最外层的pom文件即为依赖管理文件，各子工程中无须再单独引入依赖；

#### 开发说明
1. 若你也是此项目的贡献者，在开始之前建议你参照此博客：
[配置代码注释模板](http://imecho.life/index.php/2021/05/22/notes-template/)
好的代码书写习惯，是一个coder的真实写照！
2. 关于flyway的使用方法可参考博客
[Spring Boot 集成 Flyway，数据库也能做版本控制，太牛了](https://www.51cto.com/article/668861.html)

3. 提交代码前请注明，提交代码的改动点及其原因，并且在对应工程的时间线上列明
#### 工程说明
|工程名                      | 工程说明                     |备注|
| :--- | :---:| :---|
|code-scaffold-project      |父工程                        |一个包含各种中间件及工具类的微服务集合|
|base-common                |子工程                        |所有微服务的基础工程，封装了一些基本的工具类，常量，全局异常处理|
|kafka-server-sample        |kafka子工程                   |kafka 示例工程|
|redis-server-sample        |redis子工程                   |redis 示例工程|
|seata-server-sample        |seata子工程                   |seata 示例工程|
|dynamic-datasource-sample  |dynamic-datasource子工程      |动态数据源 示例工程|


#### 参与贡献者
- Jay
- xxx
- xxx

#### 时间线
- 2022.6.6 初始化基本工程
- 2022.6.14 提交缺失的pom文件
- 2023.3.16 模块重新命名及新增seata和dynamic-datasource模块
