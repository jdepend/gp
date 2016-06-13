gp-DDD
======

1、采用eclipse\jdk8\maven\springboot构建；
2、将应用划分为platform、domain、application、listener、portal、mobile、rest、test八个工程；
3、包结构以com.rofine.gp开始；
4、view采用springboot推荐的thymeleaf构建；
5、持久化采用springdata；
6、主键生成采用uuid；
7、核心对象关系采用manytoone和onetomany构建（采用lazyload策略）；
8、数据库采用mysql；
9、采用Repository模式，通过在IdEntity中调用平台中的ApplicationContextUtil获取Repo；
10、采用可捕获异常来定义业务异常，并建立异常树来分类业务异常；
11、通过实体关系获取实体将不再通过Repo来获取，只通过实体上的引用获取；
12、当通过定制条件获取实体时使用Repo；
13、除了创建实体外，其他操作尽量在DomainService后通过Repo.fin实例化实体；
14、领域逻辑尽量附着在实体上，其次是DomainService；
15、非领域逻辑不允许被DomainService和Entity依赖；
16、领域事件由DomainService抛出；
17、通过@Async可实现异步监听；
18、领域模型中错误处理代码应为正常代码量的n倍；
19、领域模型需配备n倍场景的单元测试代码；
