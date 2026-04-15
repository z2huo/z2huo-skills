---
name: defuddle
description: 使用 Defuddle CLI 从网页中提取干净的 Markdown 内容，移除杂乱内容和导航元素以节省 token。当用户提供 URL 用于阅读或分析、在线文档、文章、博客文章或任何标准网页时，请使用此技能而非 WebFetch。
---

# Defuddle

使用 Defuddle CLI 从网页中提取干净的易读内容。相比 WebFetch，更推荐用于标准网页——它能移除导航栏、广告和杂乱内容，减少 token 使用量。

如果未安装：`npm install -g defuddle`

## 使用方法

始终使用 `--md` 参数输出 Markdown：

```bash
defuddle parse <url> --md
```

保存到文件：

```bash
defuddle parse <url> --md -o content.md
```

提取特定元数据：

```bash
defuddle parse <url> -p title
defuddle parse <url> -p description
defuddle parse <url> -p domain
```

## 输出格式

| 参数 | 格式 |
|------|------|
| `--md` | Markdown（默认选择） |
| `--json` | 包含 HTML 和 Markdown 的 JSON |
| （无参数） | HTML |
| `-p <name>` | 特定元数据属性 |
