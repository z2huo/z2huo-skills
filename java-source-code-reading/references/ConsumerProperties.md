# ConsumerProperties 源码阅读

## 一、源代码

`ConsumerProperties` 是 Spring Cloud Stream 中的通用消费者配置对象，用于承载 `spring.cloud.stream.bindings.[destinationName].consumer` 这一层级下的配置项。

从提供的源码片段来看，这个类本质上是一个 Pojo 配置类。它通过大量字段保存消费者的启动方式、并发度、分区实例信息、重试策略、请求头处理方式、本地解码、批量消费等参数，并通过标准访问器或框架辅助方法向外暴露访问能力。

当前文档基于提供的源码片段进行分析，不代表 `ConsumerProperties` 的完整源码。

```java
package org.springframework.cloud.stream.binder;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ConsumerProperties {

    private String bindingName;

    private boolean autoStartup = true;

    private int concurrency = 1;

    private boolean partitioned;

    private int instanceCount = -1;

    private int instanceIndex = -1;

    private List<Integer> instanceIndexList;

    private int maxAttempts = 3;

    private int backOffInitialInterval = 1000;

    private int backOffMaxInterval = 10000;

    private double backOffMultiplier = 2.0;

    private boolean defaultRetryable = true;

    private String retryTemplateName;

    private Map<Class<? extends Throwable>, Boolean> retryableExceptions = new LinkedHashMap<>();

    private HeaderMode headerMode;

    private boolean useNativeDecoding;

    private boolean multiplex;

    private boolean batchMode;

    public void populateBindingName(String bindingName) {
        this.bindingName = bindingName;
    }
}
```

## 二、对象职责

这个对象的职责不是承载业务数据，而是承载“消息消费者如何消费消息”的配置。

从字段设计可以看出，它主要负责描述以下几类配置：

- 消费者实例的启动与并发行为
- 分区消费时的实例数量与索引
- 消费失败后的重试策略
- Header 处理与编解码策略
- 多目标绑定、批量消费等 Binder 扩展能力

因此它更适合被理解为“消费者配置模型”或“绑定消费端配置对象”。

## 三、属性

### 1、属性概览

当前片段中的字段可以按职责分为五组：

- 基础运行参数
- 分区与实例参数
- 重试参数
- Header 与解码参数
- Binder 扩展参数

### 2、基础运行参数

#### 2.1 `bindingName`

当前消费者绑定的名称。这个值通常由框架回填，不一定由业务方直接手工设置。

#### 2.2 `autoStartup`

表示当前消费者是否需要自动启动，默认值为 `true`。如果关闭，消费者不会随应用启动自动拉起。

#### 2.3 `concurrency`

消费者并发度，默认值为 `1`。该值越大，通常表示可以启动更多并发消费线程或并发消费单元，但最终是否生效仍取决于具体 Binder 实现。

### 3、分区与实例参数

#### 3.1 `partitioned`

表示消费者是否接收来自分区生产者的数据，默认值为 `false`。如果上游生产者使用分区机制，下游往往需要结合实例数量与索引进行正确消费。

#### 3.2 `instanceCount`

允许单独指定当前消费者实例总数。默认值为 `-1`，表示不单独配置，而是回退到 `spring.cloud.stream.instanceCount`。如果设为非负值，则会覆盖全局实例总数配置。

#### 3.3 `instanceIndex`

允许单独指定当前消费者实例索引。默认值为 `-1`，表示回退到 `spring.cloud.stream.instanceIndex`。如果设为非负值，则会覆盖全局实例索引配置。

#### 3.4 `instanceIndexList`

允许为列表中的每个索引生成对应消费者，负数索引会被丢弃。这个配置一旦启用，会禁用 `instanceIndex` 的单值模式，因此它和 `instanceIndex` 存在替代关系。

### 4、重试参数

#### 4.1 `maxAttempts`

消息处理失败后的最大处理尝试次数，默认值为 `3`，包含第一次执行。设为 `1` 表示关闭重试。

#### 4.2 `backOffInitialInterval`

重试的初始退避间隔，默认值为 `1000` 毫秒。

#### 4.3 `backOffMaxInterval`

重试退避的最大间隔，默认值为 `10000` 毫秒。

#### 4.4 `backOffMultiplier`

重试退避倍数，默认值为 `2.0`。它通常与初始间隔和最大间隔一起决定指数退避行为。

#### 4.5 `defaultRetryable`

表示没有在 `retryableExceptions` 中显式列出的异常，是否默认也参与重试。默认值为 `true`。

#### 4.6 `retryTemplateName`

用于指定当前消费者绑定应使用哪个 `RetryTemplate`。这让单个消费者可以绑定到特定的重试模板实现。

#### 4.7 `retryableExceptions`

键是异常类型，值是布尔值，用于定义某类异常及其子类是否应该重试。它适合做更细粒度的异常重试策略控制。

### 5、Header 与解码参数

#### 5.1 `headerMode`

控制输入消息的 Header 解析方式。

根据注释，典型语义包括：

- `none`：关闭 Header 解析
- `headers`：使用中间件原生 Header
- `embeddedHeaders`：把 Header 嵌入到消息体

这个配置主要用于那些不原生支持消息头的中间件场景。

#### 5.2 `useNativeDecoding`

表示是否由底层客户端库直接完成入站消息反序列化。默认值为 `false`。这是 Binder 相关能力，不是所有 Binder 都支持，当前注释中明确指出 Kafka Binder 支持这一特性。

### 6、Binder 扩展参数

#### 6.1 `multiplex`

表示底层 Binder 是否原生支持在同一个输入绑定上复用多个目标地址。默认值是 `false`。如果开启，核心框架会把多目标绑定责任交给 Binder 自身处理。

#### 6.2 `batchMode`

表示 Binder 是否以批量方式投递消息。如果启用且 Binder 支持，消息载荷会以 `List` 形式交付，函数式编程模型中也可以直接接收对象列表或 `Message` 列表。

## 四、访问方法

### 1、标准访问器

该类的大多数方法是标准 Getter 和 Setter，用于读取和设置对应配置属性。这类方法本身没有复杂业务逻辑，重点仍然在字段语义和默认值。

### 2、`populateBindingName(String bindingName)`

这是一个特殊方法。根据源码注释，它并不是给应用配置使用的常规 Setter，而是供框架回填绑定名称。

这意味着：

- 业务方通常不应该把它当作普通配置入口
- 该方法的存在主要是为了让框架在组装配置对象时写入绑定名

### 3、`getRetryTemplateName()` / `setRetryTemplateName(String retryTemplateName)`

这组访问器用于读取和设置当前绑定所使用的重试模板名称。

### 4、`getConcurrency()` / `setConcurrency(int concurrency)`

这组访问器用于读取和设置消费者并发度。虽然片段里只展示了这两个属性的访问器，但可以推断其余字段也有对应的 Getter、Setter 或类似访问方式。

## 五、使用要点

### 1、优先级与覆盖关系

`instanceCount` 和 `instanceIndex` 在设置为非负值时，会覆盖全局配置。使用时要特别注意“局部绑定配置”是否会意外覆盖“应用级默认配置”。

### 2、互斥与联动关系

`instanceIndexList` 启用后会禁用单值的 `instanceIndex`。因此这两个配置不应按相同语义同时理解，而应视为单实例索引模式与多索引列表模式两种方案。

### 3、重试语义

是否重试不仅取决于 `maxAttempts`，还取决于 `defaultRetryable`、`retryableExceptions` 以及具体 `RetryTemplate` 的配置。实际行为通常是这些参数共同作用的结果。

### 4、Binder 相关能力

`useNativeDecoding`、`multiplex`、`batchMode` 等配置都带有明显的 Binder 特性，不同消息中间件实现的支持程度可能不同，不能假设所有 Binder 都完全支持。

### 5、框架专用方法

`populateBindingName` 明确是框架内部回填用途，不建议业务代码手工把它作为普通配置 API 使用。
