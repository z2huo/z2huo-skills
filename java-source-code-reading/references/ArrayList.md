# ArrayList 源码阅读

## 一、源代码

`ArrayList` 是 `List` 接口的可变长度数组实现。它支持全部可选的列表操作，允许存放 `null` 元素，并额外提供了控制底层数组容量的方法。这个实现大体上可以看作 `Vector` 的非线程安全版本。

从提供的源码片段来看，`ArrayList` 重点体现了几个设计特征：一是读取、按下标访问和迭代等常见操作具有较低时间开销；二是通过容量与扩容策略在追加元素时取得摊还常数时间复杂度；三是它本身不是线程安全的，多线程下需要由调用方自行同步；四是迭代器采用 fail-fast 机制，主要用于尽早暴露并发修改问题。

当前文档基于提供的源码片段进行分析，不代表 `ArrayList` 的完整源码。

```java
package java.util;

public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{

    @java.io.Serial
    private static final long serialVersionUID = 8683452581122892189L;

    private static final int DEFAULT_CAPACITY = 10;

    private static final Object[] EMPTY_ELEMENTDATA = {};

    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    transient Object[] elementData;

    private int size;

    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public ArrayList(Collection<? extends E> c) {
        Object[] a = c.toArray();
        if ((size = a.length) != 0) {
            if (c.getClass() == ArrayList.class) {
                elementData = a;
            } else {
                elementData = Arrays.copyOf(a, size, Object[].class);
            }
        } else {
            elementData = EMPTY_ELEMENTDATA;
        }
    }

    public void trimToSize() {
        modCount++;
        if (size < elementData.length) {
            elementData = (size == 0) ? EMPTY_ELEMENTDATA : Arrays.copyOf(elementData, size);
        }
    }

    public void ensureCapacity(int minCapacity) {
        if (minCapacity > elementData.length && !(elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA && minCapacity <= DEFAULT_CAPACITY)) {
            modCount++;
            grow(minCapacity);
        }
    }

    private Object[] grow(int minCapacity) {
        int oldCapacity = elementData.length;
        if (oldCapacity > 0 || elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            int newCapacity = ArraysSupport.newLength(oldCapacity, minCapacity - oldCapacity, oldCapacity >> 1);
            return elementData = Arrays.copyOf(elementData, newCapacity);
        } else {
            return elementData = new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
        }
    }
}
```

## 二、类职责

`ArrayList` 的核心职责是使用连续数组来承载线性表结构，并在保持随机访问高性能的同时，提供自动扩容能力。

从这段源码可以看出，这个类主要解决三个问题：

- 用数组保存元素，保证按索引访问的高效性
- 在元素持续增加时自动扩容，降低频繁重分配的成本
- 通过不同构造方法兼顾空列表、指定容量和集合拷贝三种初始化场景

同时，它明确不是线程安全容器。如果多个线程并发访问且至少有一个线程会修改结构，就必须由外部做同步控制。

## 三、属性

### 1、核心属性概览

当前片段中的属性主要分为三类：

- 序列化相关元数据
- 容量与空数组常量
- 底层存储与当前元素个数

### 2、`serialVersionUID`

该字段是 Java 序列化机制使用的版本号，用于保证序列化兼容性。

### 3、`DEFAULT_CAPACITY`

默认初始容量，值为 `10`。当通过无参构造创建列表且第一次真正插入元素时，会以这个值作为默认扩容起点。

### 4、`EMPTY_ELEMENTDATA`

表示真正意义上的空数组实例。它常用于容量明确为 `0` 的空列表场景。

### 5、`DEFAULTCAPACITY_EMPTY_ELEMENTDATA`

这也是一个空数组，但语义与 `EMPTY_ELEMENTDATA` 不同。它专门用于无参构造创建的空列表，目的是在首次添加元素时能够识别出“应该膨胀到默认容量 10”，而不是只扩到最小需要容量。

### 6、`elementData`

底层数组缓冲区，真正用于保存列表元素。`ArrayList` 的容量本质上就是这个数组的长度。

### 7、`size`

当前列表中实际元素个数。它与 `elementData.length` 不同，前者表示已使用数量，后者表示可容纳上限。

## 四、构造方法

### 1、构造方法概览

当前片段中提供了三种构造方式：

- 指定初始容量构造
- 无参构造
- 基于集合拷贝构造

### 2、`ArrayList(int initialCapacity)`

这个构造方法允许调用方显式指定底层数组初始容量。

它的行为分三种：

- `initialCapacity > 0`：直接创建对应长度的数组
- `initialCapacity == 0`：使用共享空数组
- `initialCapacity < 0`：抛出 `IllegalArgumentException`

这个构造方法适合调用方已知数据规模时使用，可以减少后续扩容次数。

### 3、`ArrayList()`

无参构造不会立即分配默认容量为 `10` 的数组，而是先指向 `DEFAULTCAPACITY_EMPTY_ELEMENTDATA`。这是一种延迟分配策略，只有在首次插入元素时才真正初始化底层存储。

### 4、`ArrayList(Collection<? extends E> c)`

该构造方法把已有集合内容复制到 `ArrayList` 中。

它的关键点有两个：

- 如果传入集合本身就是 `ArrayList`，会直接复用 `toArray()` 的结果
- 如果不是 `ArrayList`，则通过 `Arrays.copyOf` 拷贝为 `Object[]`

这样既兼顾了性能，也避免了某些集合 `toArray()` 返回类型不符合预期的问题。

## 五、方法

### 1、方法概览

当前片段展示的方法主要集中在容量管理上，包括：

- 压缩容量
- 预留容量
- 实际扩容

### 2、`trimToSize()`

该方法用于把底层数组容量收缩到当前元素个数。

它适合在确认列表后续不会再显著增长时使用，以减少多余内存占用。方法内部会先增加 `modCount`，然后在 `size < elementData.length` 时重新复制数组。

### 3、`ensureCapacity(int minCapacity)`

该方法用于确保底层数组至少具备指定容量。

它不会盲目扩容，而是先判断：

- 当前容量是否已经足够
- 当前是否是默认空数组且请求容量没有超过默认容量

只有确实需要扩容时才会调用 `grow(minCapacity)`。

### 4、`grow(int minCapacity)`

这是实际执行扩容的内部方法。

它的主要逻辑是：

- 如果当前已经有数组容量，或者当前数组不是“默认空数组”，则基于旧容量按增长策略计算新容量
- 如果当前还是无参构造形成的默认空数组，则至少扩到 `DEFAULT_CAPACITY` 与 `minCapacity` 中较大的那个

这说明 `ArrayList` 既要兼顾节省初始化内存，也要避免每次只增长一点点造成频繁复制。

## 六、使用要点

### 1、线程安全

`ArrayList` 本身不是线程安全的。并发读写时应由外部同步，或改用线程安全容器。

### 2、性能特征

按索引访问通常很快，但中间插入、删除元素可能导致数组搬移。追加元素整体上是摊还常数时间，但扩容时会有一次性复制成本。

### 3、容量管理

如果预估元素规模较大，建议提前使用带容量构造方法或 `ensureCapacity()` 预分配空间，以减少扩容次数。

### 4、片段范围

当前提供的源码片段主要展示了构造和容量管理逻辑，未覆盖增删改查、迭代器、批量操作等完整实现，因此本文档也主要围绕这些已展示部分进行说明。
