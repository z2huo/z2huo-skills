# 嵌入参考

## 嵌入笔记

```markdown
![[Note Name]]
![[Note Name#Heading]]
![[Note Name#^block-id]]
```

## 嵌入图片

```markdown
![[image.png]]
![[image.png|640x480]]    宽度 x 高度
![[image.png|300]]        仅宽度（保持宽高比）
```

## 外部图片

```markdown
![Alt text](https://example.com/image.png)
![Alt text|300](https://example.com/image.png)
```

外部图片默认使用上述 Markdown 图片语法。

以下情况不要将图片在 `<img>` 与 `![]()` 两种写法之间互相替换，保持原有嵌入方式不变：

1. `<img>` 标签使用 `width="...%"` 指定百分比宽度（例如 `width="80%"`）。
2. 图片 URL 的域名包含 `z2huo.cn`，或其任意子域名（例如 `image.z2huo.cn`）。
3. `<img>` 配合外层的 HTML 容器标签（例如 `<div>`、`<p>`）一起使用时，保持整个 HTML 块不变（包括外层 `<div>`/`<p>` 及其属性）。

```html
<img src="https://example.com/image.png" width="80%" />

<div style="text-align: center;"><img src="https://image.z2huo.cn/pictures/" alt="" width="80%" /><p></p></div>
```

## 嵌入音频

```markdown
![[audio.mp3]]
![[audio.ogg]]
```

## 嵌入 PDF

```markdown
![[document.pdf]]
![[document.pdf#page=3]]
![[document.pdf#height=400]]
```

## 嵌入列表

```markdown
![[Note#^list-id]]
```

其中列表具有块 ID：

```markdown
- 项目 1
- 项目 2
- 项目 3

^list-id
```

## 嵌入搜索结果

````markdown
```query
tag:#project status:done
```
````
