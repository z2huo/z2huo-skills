# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 一、项目概述

个人 Skills 集合，专注于 AI Coding 场景下的代码规范与最佳实践。用于 Trae IDE 等支持 Skill 机制的开发工具。

## 二、Skills 列表

| Skill | 描述 |
| --- | --- |
| `lombok-best-practices/` | Java / Spring Boot 项目 Lombok 规范化实践 |
| `markdown-generate-format/` | Markdown 文档格式规范（标题编号 + 换行规则） |
| `skill-creator/` | 创建、测试和优化 Skills 的完整工作流 |
| `obsidian-*` | Obsidian 相关技能（cli, markdown, bases, defuddle, json-canvas） |

## 三、Skill 架构

每个 Skill 目录结构：

```
skill-name/
├── SKILL.md (必需) - YAML frontmatter + Markdown 指令
├── scripts/       - 可执行脚本（打包、验证等）
├── references/    - 按需加载的参考文档
├── assets/       - 静态资源（模板、图标等）
└── agents/       - 子代理指令（用于 skill-creator）
```

## 四、常用命令

### 1、打包 Skill

```bash
cd anthropic/skill-creator/skill-creator
python -m scripts.package_skill <path/to/skill-folder> <output-directory>
# 示例：python -m scripts.package_skill ../../lombok-best-practices ../../release
```

### 2、验证 Skill

```bash
cd anthropic/skill-creator/skill-creator
python -m scripts.quick_validate <skill-directory>
```

### 3、运行 Benchmark（skill-creator）

```bash
python -m scripts.aggregate_benchmark <workspace>/iteration-N --skill-name <name>
```

## 五、Skill 创建/优化流程

参考 `skill-creator/SKILL.md`：

1. **Capture Intent** - 理解用户需求和触发场景
2. **Write SKILL.md** - 包含 name, description（触发机制）, instructions
3. **创建测试用例** - 保存到 `evals/evals.json`
4. **运行测试** - 使用 subagent 并行执行 with-skill 和 baseline
5. **评估结果** - 使用 `eval-viewer/generate_review.py` 生成可视化报告
6. **迭代优化** - 根据反馈改进 skill
7. **打包发布** - 使用 `package_skill.py` 生成 `.skill` 文件

## 六、SKILL.md Frontmatter 规范

```yaml
---
name: skill-name           # 必需：kebab-case，最大64字符
description: 触发描述       # 必需：最大1024字符，用于触发匹配
compatibility: 依赖说明     # 可选：最大500字符
---
```

## 七、发布流程

打包后的 `.skill` 文件存放于 `release/` 目录，可直接分发给用户安装。

## 八、目录结构

```
z2huo-skills/
├── anthropic/skill-creator/   # Skill 创建工具（基于 Anthropic 原始版本修改）
├── lombok-best-practices/      # Lombok 规范
├── markdown-generate-format/   # Markdown 格式规范
├── obsidian/                   # Obsidian 相关 skills
├── release/                    # 打包发布的 .skill 文件
└── docs/                       # 文档规范参考
```
