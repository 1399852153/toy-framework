# mapper代理实现

## 版本号：A-A

~~~
思路：通过session获取mapper时,通过JDK动态代理生成代理对象（传入接口）

通过代理类，可以在代理类对象中写入其他逻辑
如：PresonMapper 中有一个findByID方法。
在获取PresonMapper时，生成代理对象并返回。客户端使用代理对象PresonMapper
调用findByID方法），进而调用数据源connection执行具体的sql语句。
通过实现代理对象，可以会执行具体的sql语句前后执行我们自己的逻辑。

~~~

## 实现方式
    session 添加getmapper接口
    getMapper的实现里，通过传入的接口参数，生成代理对象返回。

    JDK代理 需要实现一个 invocatiionHadler 重写invoke方法，用于执行被代理对象原有的逻辑
    这部分逻辑需要通过connection去执行sql。因此定义一个mapperProxy类，持有connetion属性
    为不同mapper生成代理类时不需要每次都创建 invocatiionHadler

    mapperProxy类定义：
    session session内持有connection 和其他执行sql所必须的
    接口的cass：因为 mapper.xml中指明mapper 类的全限定名，通过这样才把mapper 类与xml关联起来。
    mapperProxy中持有一个mapper接口class，可以定位到该mapper类对应的mapper xml

    
## 执行原理：
    session获取mapper，获取mapper时创建该mapper的一个代理对象
    代理对象的内部实现：执行sql
    
    
    
## 遗留问题
InvocationHandler实现MapperProxy invoke 方法中的args参数会丢失原始参数类型信息，获取到的是Object类。
后面解析mapper xml时无法通过反射拿到类中的方法。
一种实现思路：通过method获取参数，获取类型信息，将args转换为参数原有类型。




## 2019-07-28 22:16:45
## 版本号：B-1
* 实现通过sqlsesson 获取mapper，通过jdk生成代理代理对象。
* 实现mapper的spring集成：通过factoryBean注册mapper接口，可让spring容器中管理mapper的bean。

### 适配包与demo
* [简易版Mybatis-Spring适配包](https://github.com/nothingax/micro-mybatis-spring)
* [集成micro-mybatis-spring 与toy-mybatis 的demo](https://github.com/nothingax/micro-mybatis-spring-demo)












