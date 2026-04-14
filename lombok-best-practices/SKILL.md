---
name: lombok-best-practices
description: "Java / Spring Boot 项目的 Lombok 规范化实践。用户提到 Lombok、`@Data`、`@NoArgsConstructor`、`@AllArgsConstructor`、`@Accessors(chain = true)`、`@Slf4j`，或提出用 `@RequiredArgsConstructor` 替代 `@Autowired` 时，必须启用本 skill。即使用户未显式提及 Lombok，只要在 pom.xml 中识别到 Lombok 依赖，且任务涉及创建/修改 BO/DO/DTO/VO/Req/Resp/Param 等 Pojo 类，也应主动启用。适用于新建类、代码生成、重构、代码评审与批量规范化。"
---

# Lombok Best Practices

## 一、Skill 目标

在 Java / Spring Boot 项目中统一 Lombok 使用方式，重点覆盖：

- 数据模型类：BO/DO/DTO/VO/Req/Resp/Param 等 Pojo 类
- 组件类：Controller/Service/DAO/Listener
- 注入方式：优先构造器注入（`@RequiredArgsConstructor` + `final` 字段）
- 对象风格：涉及 getter/setter 的对象默认使用 `@Accessors(chain = true)`，采用链式调用
- 评审方式：发现并替换低可维护性的字段注入 `@Autowired`

## 二、自动触发规则

- 显式触发：用户明确提到 Lombok、`@Data`、`@NoArgsConstructor`、`@AllArgsConstructor`、`@RequiredArgsConstructor`、`@Autowired` 迁移。
- 隐式触发：用户要求生成功能代码，即使未提及 Lombok，只要 `pom.xml` 中存在 Lombok 依赖，且任务包含创建或修改 BO/DO/DTO/VO/Req/Resp/Param 等 Pojo 类，就应主动应用本 skill。
- 优先动作：在开始生成对象代码前，先检查 `pom.xml` 是否包含 Lombok 依赖，再决定对象注解和注入方式。

## 三、执行步骤

1. 先识别类角色（BO/DO/DTO/VO/Req/Resp/Param 等 Pojo 类，或 Service/DAO/Controller 等组件类）。
2. 根据"注解矩阵"给出推荐注解。
3. 若是 Spring 组件，优先改为构造器注入模式。
4. 输出变更时必须给出"改动建议 + 风险说明 + 示例代码"。
5. 如果存在争议场景（多实现注入、非 Spring 管理类），明确给出例外处理策略。

## 四、注解矩阵

| 类别 | 推荐注解 | 说明 |
| --- | --- | --- |
| DO（数据库实体映射） | `@Data` + `@NoArgsConstructor` + `@Accessors(chain = true)` | 与 ORM/序列化工具配合稳定，保持无参构造器并支持链式 set |
| DO（链式 set 场景） | `@Data` + `@NoArgsConstructor` + `@Accessors(chain = true)` | 适合构建测试数据和 fluent 风格赋值 |
| DTO/VO/Req/Resp | `@Data` + `@Accessors(chain = true)` | 简单承载数据时首选，同时统一链式 set 风格 |
| DTO（明确需要全参/无参构造） | `@Data` + `@NoArgsConstructor` + `@AllArgsConstructor` + `@Accessors(chain = true)` | 常见于 CSV 映射、反序列化和快速构造 |
| Spring 组件（Controller/Service/DAO） | `@RequiredArgsConstructor` + `private final` 依赖字段 | 替代字段注入 `@Autowired` |
| 业务组件类（Controller/Service/DAO/Listener） | `@Slf4j` | 需要记录业务流程、异常、外部调用时使用 |
| Pojo 类（BO/DO/DTO/VO/Req/Resp/Param） | 不使用 `@Slf4j` | Pojo 仅承载数据，不承担日志职责 |

## 五、链式访问器规则

- 默认规则：只要 Pojo 类需要 Lombok 生成 setter/getter（典型如 BO/DO/DTO/VO/Req/Resp/Param），默认加 `@Accessors(chain = true)`。
- 搭配建议：若使用 `@Data`、`@Getter/@Setter` 生成访问器，优先同时声明 `@Accessors(chain = true)`，保持风格一致。
- 非对象类：`@Service`、`@Controller`、`@Repository` 等组件类通常不需要 `@Accessors(chain = true)`。

## 六、`@Slf4j` 使用规则

- 适用范围：默认用于 Spring 业务组件类，如 `@Controller`、`@Service`、`@Repository`、消息监听器、任务处理器等。
- 不适用范围：默认不用于 Pojo 类（BO/DO/DTO/VO/Req/Resp/Param），避免数据对象承担日志职责。
- 使用目的：记录业务链路、关键状态、异常堆栈、外部依赖调用结果。
- 审查原则：如果一个类仅包含字段和访问器（数据承载），移除 `@Slf4j`；如果负责业务流程或集成逻辑，可保留或新增 `@Slf4j`。

### 1、`@Slf4j` 推荐示例（业务组件）

```java
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void createOrder(CreateOrderReq req) {
        log.info("create order start, orderNo={}", req.getOrderNo());
        try {
            orderRepository.save(req);
            log.info("create order success, orderNo={}", req.getOrderNo());
        } catch (Exception e) {
            log.error("create order fail, orderNo={}", req.getOrderNo(), e);
            throw e;
        }
    }
}
```

### 2、`@Slf4j` 反例（Pojo）

```java
@Data
@Accessors(chain = true)
public class UserDTO {
    private Long id;
    private String userName;
}
```

## 七、组件注入规范

### 1、推荐模式

```java
@Service
@RequiredArgsConstructor
public class DemoService {

    private final UserMapper userMapper;
}
```

### 2、需要避免

```java
@Service
public class DemoService {

    @Autowired
    private UserMapper userMapper;
}
```

## 八、例外与边界

- 当依赖字段不是 `final`（例如确有延迟设置需求）时，可临时保留非构造器注入，但要说明原因。
- 当同一接口存在多个实现且需按名称注入时，优先在构造器参数或字段上搭配 `@Qualifier`，不要回退到字段注入。
- 测试类中可视情况使用 `@Autowired`，但生产代码默认执行构造器注入策略。

### 1、多实现注入示例（`@Qualifier` + 构造器注入）

```java
@Service
public class QualifierWireService {

    private final QualifierService qualifierService;

    public QualifierWireService(@Qualifier("qualifierServiceImpl") QualifierService qualifierService) {
        this.qualifierService = qualifierService;
    }
}
```

## 九、通用示例（与具体项目无关）

### 1、DO 示例

```java
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserDO {
    private Long id;
    private String userCode;
}
```

### 2、DTO 示例

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserCreateDTO {
    private String userName;
    private Integer age;
}
```

### 3、请求对象示例

```java
@Data
@Accessors(chain = true)
public class UserQueryReq {
    private String keyword;
    private Integer pageNo;
    private Integer pageSize;
}
```

## 十、使用此 Skill 时的输出要求

每次输出建议时，使用以下结构：

1. 类别判断：当前类属于 BO/DO/DTO/VO/Req/Resp/Param 等 Pojo 类，还是 Service/DAO/Controller 等组件类。
2. 推荐注解：应添加、保留、删除哪些 Lombok/Spring 注解。
3. 注入建议：是否需要从 `@Autowired` 迁移到 `@RequiredArgsConstructor`。
4. 改造结果：提供最小改动代码片段。
5. 风险提示：序列化、框架反射、测试注入等注意事项。

## 十一、JavaDoc 约定

如果在改造中新增或修改 `/** */` 注释（类注释、成员变量注释、方法注释），遵循以下格式：

- 每个段落使用 `<p>` 开头。
- 若注释中出现列表，使用 `<pre></pre>` 包裹列表内容。
