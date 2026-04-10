---
alwaysApply: false
description: 需要使用 skill-creator 这个 skill 打包 Skill 为 .skill 文件时。或者不限制 skill-creator 这个 skill，只要是打包 Skill 的操作，并且也无论打包的 Skill 是否为 .skill 格式。
---

# Skill 打包规则

当需要打包 Skill 到 `.skill` 文件时，输出目录固定为当前项目下的 `release` 目录。

**打包命令示例**：

```bash
# 在该项目根目录下执行打包命令
python -m scripts.package_skill <skill-path> ./release
```

**注意**：

- `scripts` 目录位于 `~/.trae/skills/skill-creator/scripts/`
- 打包后的 `.skill` 文件名自动采用 Skill 目录的名称

**Skill 目录示例**：

本项目的两个 Skill 存放在以下目录：

| Skill 名称 | 路径 |
| --- | --- |
| markdown-generate-format | `markdown-generate-format/` |
| lombok-best-practices | `lombok-best-practices/` |
