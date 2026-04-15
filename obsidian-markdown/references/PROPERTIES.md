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
publishFlag: no
---
```

## 属性一览

| 属性 | 示例 |
|------|---------|
| 标题 | `title: 我的标题` |
| 创建日期 | `date: 2024-01-15 14:08:11` |
| 更新日期 | `updated: 2024-01-15 14:08:11` |
| 标签 | YAML 列表 |
| 关键词 | YAML 列表 |
| 发布标志 | `publishFlag: no` |

## 属性含义

- `tags` - 笔记标签
- `keywords` - 笔记的备选名称
- `publishFlag` - 该笔记是否已发布（默认 `no`）
  - `no` - 未发布笔记
  - `yes` - 已发布笔记
  - `wait` - 等待发布
- `date` - 笔记创建日期，创建了笔记之后该日期就不再改变
- `updated` - 笔记最后更新日期，修改笔记内容时更新该日期为修改时间

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

### `date` 和 `updated` 时间

属性中的 `date` 和 `updated` 为笔记的创建日期和更新日期。创建了笔记之后 `date` 就不再改变，而 `updated` 会根据文件的修改时间更新。

格式使用 `YYYY-MM-DD HH:MM:SS`（包括年月日时分秒）。

如果无法获取当前具体时间（只能得到日期），请不要把时间补成 `00:00:00`。

可以用命令行在生成阶段获取当前时间字符串，再写入 frontmatter，请根据系统选择合适的命令：

```bash
date "+%Y-%m-%d %H:%M:%S"
```

下面这个命令使用于 Windows：

```powershell
Get-Date -Format "yyyy-MM-dd HH:mm:ss"
```