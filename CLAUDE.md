# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 一、项目概述

个人 Skills 集合，专注于 AI Coding 场景下的代码规范与最佳实践。用于 Trae IDE 等支持 Skill 机制的开发工具。

## 二、打包规则

打包 Skill 时此规则生效

### 打包 Skill

打包时使用 `skill-creator` 这个 Skill。

### Skill 输出目录

当需要打包 Skill 到 `.skill` 文件时，输出文件到当前项目下的 `release/` 目录。

- 如果 `release/` 目录不存在，自动创建。
- 如果 `release/` 目录中已经存在相同名称的 `.skill` 文件，自动覆盖。

如果在打包过程中使用了打包脚本：

- 打包脚本中不支持指定输出目录，请使用脚本输出 skill 之后，将输出的 skill 文件移动或复制到该项目下的 `release/` 目录中。
- 打包脚本支持指定输出目录，请在脚本中指定输出目录为 `release/`。
