# 属性（前置内容）参考

属性使用 YAML 前置内容，位于笔记开头：

```yaml
---
title: 我的笔记标题
date: 2024-01-15 14:08:11
updated: 2024-01-15 14:08:11
tags:
  - project
  - important
keywords:
  - 我的笔记
  - 备选名称
publishFlag: yes
---
```

## 属性一览

| 属性 | 示例 |
|------|---------|
| 标题 | `title: 我的标题` |
| 日期 | `date: 2024-01-15 14:08:11` |
| 更新日期 | `updated: 2024-01-15 14:08:11` |
| 标签 | YAML 列表 |
| 关键词 | YAML 列表 |
| 发布标志 | `publishFlag: yes` |

## 属性含义

- `tags` - 笔记标签
- `keywords` - 笔记的备选名称
- `publishFlag` - 该笔记是否已发布（默认 `yes`）
  - `yes` - 已发布笔记
  - `no` - 未发布笔记
  - `wait` - 等待发布
- `date` - 笔记创建日期，创建了笔记之后该日期就不再改变
- `updated` - 笔记最后更新日期，修改笔记内容时会更新该日期为修改时间

## 示例

### 标签

```markdown
#tag
#nested/tag
#tag-with-dashes
#tag_with_underscores
```

标签可包含：字母（任何语言）、数字（首字符不能是数字）、下划线 `_`、连字符 `-`、正斜杠 `/`（用于嵌套）。

在前置内容中：

```yaml
---
tags:
  - tag1
  - nested/tag2
---
```

