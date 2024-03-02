## code-scaffold-project

### 介绍
一个代码脚手架工程，提供了各种常用的代码操作示例！主要为springboot集成各种组件的示例代码工程；</br>
其中除基础微服务提供基本的公共属性外，其他微服务应都可以独立运行，且并不相互通信；</br>
因为本工程的初衷只是为了说明各技术点及其中间件的使用示例，并不做架构层面的设计；</br>
如需某个微服务为单独的公用服务，自行抽取暴露出来应用到你们自己工程中即可；</br>

### 软件架构
jdk1.8 + springboot2.4.5 + mysql8.0 + flyway插件作为基础</br>
其中最外层的pom文件即为依赖管理文件，各子工程中无须再单独引入依赖；

### 开发说明
1. 若你也是此项目的贡献者，在开始之前建议你参照此博客：
[idea配置代码注释模板](https://www.sysfzy.site/2021/0522/idea-template-comment.html)
好的代码书写习惯，是一个coder的真实写照！
2. 关于flyway的使用方法可参考博客
[Spring Boot 集成 Flyway，数据库也能做版本控制，太牛了](https://www.51cto.com/article/668861.html)
3. 提交代码前请注明，提交代码的改动点及其原因，并且在对应工程的时间线上列明

### 工程说明
| 工程名                       |  端口  |         工程说明          |备注|
|:--------------------------|:----:|:---------------------:| :---|
| code-scaffold-project    |  无   |          父工程          |一个包含各种中间件及工具类的微服务集合|
| base-common              |  无   |     基础子工程             |所有微服务的基础工程，封装了一些基本的工具类，常量，全局异常处理|
| dynamicdb-server-sample  | 8081 | dynamic-datasource子工程 |动态数据源 示例工程|
| es-server-sample         | 8082 |         es子工程         |es搜索引擎 示例工程|
| kafka-server-sample      | 8083 |       kafka子工程        |kafka  中间件示例工程|
| redis-server-sample      | 8084 |       redis子工程        |redis  缓存示例工程|
| seata-server-sample      | 8085 |       seata子工程        |seata  分布式事务示例工程|
| tess4j-server-sample     | 8086 |       tess4j子工程       |tess4j 图片识别示例工程|

### 参与贡献者
- Jay/viEcho

### 时间线
- 2022.6.6  初始化基本工程
- 2022.6.14 提交缺失的pom文件
- 2023.3.16 模块重新命名及新增seata和dynamic-datasource模块
- 2023.3.30 完成图片识别示例工程
- 2024.1.27</br>
  1.重新调整依赖管理，所有基础依赖移到base-common中；</br>
  2.全局异常失效处理，由未扫到baseCommon中处理类导致</br>
  3.redis-server-sample工程完善，新增启动脚本；在启动类运行时优先启动redis,无需手动启动redis</br>
  4.es-server-sample工程完善，索引及文档crud示例接口;后面抽时间也新增启动脚本</br>

