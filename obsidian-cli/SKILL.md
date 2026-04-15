---
name: obsidian-cli
description: 使用 Obsidian CLI 与 Obsidian 仓库交互，可用于读取、创建、搜索和管理笔记、任务、属性等。同时支持插件和主题开发，提供重新加载插件、运行 JavaScript、捕获错误、截取屏幕截图和检查 DOM 等命令。当用户需要与 Obsidian 仓库交互、管理笔记、搜索仓库内容、从命令行执行仓库操作，或开发和调试 Obsidian 插件和主题时使用此技能。
---

# Obsidian CLI

使用 `obsidian` CLI 与正在运行的 Obsidian 实例交互。需要在 Obsidian 打开的状态下使用。

## 命令参考

运行 `obsidian help` 查看所有可用命令，内容始终保持最新。完整文档：https://help.obsidian.md/cli

## 语法

**参数**使用 `=` 赋值，包含空格的值需要加引号：

```bash
obsidian create name="My Note" content="Hello world"
```

**标志**是布尔开关，无需赋值：

```bash
obsidian create name="My Note" silent overwrite
```

对于多行内容，使用 `\n` 表示换行，`\t` 表示制表符。

## 文件定位

许多命令接受 `file` 或 `path` 参数来指定文件。如果两者都未指定，则使用当前活动文件。

- `file=<名称>` — 像 wikilink 一样解析（仅需文件名，无需路径或扩展名）
- `path=<路径>` — 从仓库根目录开始的完整路径，例如 `folder/note.md`

## 仓库定位

命令默认作用于最近聚焦的仓库。使用 `vault=<名称>` 作为第一个参数来指定特定仓库：

```bash
obsidian vault="My Vault" search query="test"
```

## 常用模式

```bash
obsidian read file="My Note"
obsidian create name="New Note" content="# Hello" template="Template" silent
obsidian append file="My Note" content="New line"
obsidian search query="search term" limit=10
obsidian daily:read
obsidian daily:append content="- [ ] New task"
obsidian property:set name="status" value="done" file="My Note"
obsidian tasks daily todo
obsidian tags sort=count counts
obsidian backlinks file="My Note"
```

在任意命令上使用 `--copy` 可将输出复制到剪贴板。使用 `silent` 可防止文件打开。在列表命令上使用 `total` 可获取计数。

## 插件开发

### 开发/测试周期

对插件或主题进行代码修改后，遵循以下工作流程：

1. **重新加载**插件以应用更改：
   ```bash
   obsidian plugin:reload id=my-plugin
   ```
2. **检查错误** — 如果出现错误，修复后从步骤 1 重复：
   ```bash
   obsidian dev:errors
   ```
3. **视觉验证**通过截图或 DOM 检查：
   ```bash
   obsidian dev:screenshot path=screenshot.png
   obsidian dev:dom selector=".workspace-leaf" text
   ```
4. **检查控制台输出**中的警告或意外日志：
   ```bash
   obsidian dev:console level=error
   ```

### 额外的开发者命令

在应用上下文中运行 JavaScript：

```bash
obsidian eval code="app.vault.getFiles().length"
```

检查 CSS 值：

```bash
obsidian dev:css selector=".workspace-leaf" prop=background-color
```

切换移动设备模拟：

```bash
obsidian dev:mobile on
```

运行 `obsidian help` 查看更多开发者命令，包括 CDP 和调试器控制。
