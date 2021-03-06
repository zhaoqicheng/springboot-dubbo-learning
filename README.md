spring-boot整合dubbo：

引用文章：
https://www.bysocket.com/?p=1681

首先作为入门级小白之前的两个问题：
1.为什么使用dubbo？之前使用httpcilent作为系统之间的交互，采用json数据爽爆了，为什么还要使用dubbo？

Dubbo 不单单只是高性能的 RPC 调用框架，更是 SOA 服务治理的一种方案。
核心：
1. 远程通信，向本地调用一样调用远程方法。
2. 集群容错
3. 服务自动发现和注册，可平滑添加或者删除服务提供者。
我们常常使用 Springboot 暴露 HTTP 服务，并走 JSON 模式。但慢慢量大了，一种 SOA 的治理方案。
这样可以暴露出 Dubbo 服务接口，提供给 Dubbo 消费者进行 RPC 调用。下面我们详解下如何集成 Dubbo。

2.dubbo和http有关系吗？
dubbo运用多种协议来实现两个独立系统之间的通信，http只是其中一种协议。
协议支持
Dubbo支持多种协议，如下所示：
    Dubbo协议
    Hessian协议
    HTTP协议
    RMI协议
    WebService协议
    Thrift协议
    Memcached协议
    Redis协议
    
3.zookeeper在Dubbo中扮演了一个什么角色，起到了什么作用啊？

采用知乎回答：https://www.zhihu.com/question/25070185'
zookeeper实现的是资源的订阅发布基本原理就是，分布式的环境下服务方实际上是资源，每个服务方把自己的服务的节点信息，
注册在zk上，消费者通过zk获取到所需要的服务的相关信息，比如url之类。但是zk有个很重要的功能，
会主动通知消费者所订阅资源的变化信息，比如，同一个服务 某台机器相关进程关闭后，zk会通知消费者，
资源的变化情况，这样，就实现了服务的动态添加减少。这一点在分布式环境下非常重要，设想如下场景某网站在做抢购活动，
突然发现，后台某个服务资源吃紧，需要增加服务器，而又不能影响当前业务，简单来说他的功能类似于注册中心。
dubbo的服务提供者会在zookeeper上面创建一个临时节点，表明自己的IP和端口，当消费者需要使用服务时，
会先在zookeeper上面查询，找到服务提供者，做一些负载的选择（比如随机、轮流），然后按照这些信息，访问服务提供者。

zookeeper负责保存了服务提供方和服务消费方的的URI（dubbo自定义的一种URI），服务消费方找到zookeeper，
向zookeeper要到服务提供方的URI，然后就找到提供方，并调用提供方的服务。解耦，分布式，failover。
dubbo是管理中间层的工具，在业务层到数据仓库间有非常多服务的接入和服务提供者需要调度，dubbo提供一个框架解决这个问题。
注意这里的dubbo只是一个框架，至于你架子上放什么是完全取决于你的，就像一个汽车骨架，你需要配你的轮子引擎。
这个框架中要完成调度必须要有一个分布式的注册中心，储存所有服务的元数据，你可以用zk，也可以用别的，只是大家都用zk。
zookeeper用来注册服务和进行负载均衡，哪一个服务由哪一个机器来提供必需让调用者知道，简单来说就是IP地址和服务名称的对应关系。
当然也可以通过硬编码的方式把这种对应关系在调用方业务代码中实现，但是如果提供服务的机器挂掉调用者无法知晓，如果不更改代码会继续请求挂掉的机器提供服务。
zookeeper通过心跳机制可以检测挂掉的机器并将挂掉机器的IP和服务对应关系从列表中删除。
至于支持高并发，简单来说就是横向扩展，在不更改代码的情况通过添加机器来提高运算能力。
通过添加新的机器向zookeeper注册服务，服务的提供者多了能服务的客户就多了

4.个人理解：
springCloud有类似于dubbo的功能，正在完善的springCloud未来很可能代替dubbo。

注意：
zk只是告诉消费者A生产者B在哪里，然后A和B进行直连，不会参与其中的数据性的交互。
