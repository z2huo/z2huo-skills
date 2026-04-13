
# 后置内容参考

后置内容，是指在文章的末尾，放置一些与文章内容相关的参考信息。不一定所有文章都有后置内容。默认有后置内容。

后置内容为 3 个二级标题，三个二级标题分别为：

- 相关链接
- OB links
- OB tags

示例如下：

```markdown
## 相关链接


## OB links


## OB tags

```

## 相关链接

相关链接二级标题下面放置该文章的参考网络文献 URL，比如，`[参考网络文献](https://example.com)`，这些参考文献不只是博客文章，还包括 Github 仓库、Stack Overflow、官方文档等。

示例如下：

```markdown
## 相关链接

[参考网络文献](https://example.com)

[Github 仓库](https://example.com)
```

## OB links

OB links 这个二级标题下面放置的是该文章与该 Obsidian 仓库中其他文件的链接。

比如有文章 `Java 各版本新特性.md` 文章，引用了 `JDK 21 新特性.md` 的文章，则在 `Java 各版本新特性.md` 文章的 `OB links` 这个二级标题下面放置 `JDK 21 新特性.md` 文章的链接。

示例如下：

```markdown
## OB links

[[JDK 21 新特性]]
```

## OB tags

OB tags 这个二级标题下面放置的是该文章的标签，比如，`#java`、`#jdk` 等。

示例如下：

```markdown
## OB tags

#java #jdk

```
