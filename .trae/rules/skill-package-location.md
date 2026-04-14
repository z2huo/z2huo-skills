---
alwaysApply: false
description: 打包 Skill 时此规则生效
---

# Skill 打包规则

打包 Skill 时此规则生效

## 打包 Skill

打包时使用 `skill-creator` 这个 Skill。位置位于：

- 项目 Skill 目录：`.trae/skills/`
- 全局 Skill 目录：`~/.trae/skills/`

不能使用其他位置的 Skill。

未经允许，不可以使用 `anthropic/skill-creator/` 目录下的 Skill 打包其他 Skill 和自己。但是打包 `anthropic/skill-creator/` 是可以的，即不能自己打包自己。

## Skill 输出目录

当需要打包 Skill 到 `.skill` 文件时，输出文件到当前项目下的 `release/` 目录。

- 如果 `release/` 目录不存在，自动创建。
- 如果 `release/` 目录中已经存在相同名称的 `.skill` 文件，自动覆盖。

如果在打包过程中使用了打包脚本：

- 打包脚本中不支持指定输出目录，请使用脚本输出 skill 之后，将输出的 skill 文件移动或复制到该项目下的 `release/` 目录中。
- 打包脚本支持指定输出目录，请在脚本中指定输出目录为 `release/`。

