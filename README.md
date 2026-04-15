# z2huo-skills

## 介绍

个人 Skills 集合，专注于 AI Coding 场景下的代码规范与最佳实践。

## Skills 列表

| Skill | 描述 |
| --- | --- |
| [defuddle](defuddle/SKILL.md) | 从网页中提取干净的 Markdown 内容 |
| [java-comment-specification](java-comment-specification/SKILL.md) | Java 代码注释（Javadoc）规范 |
| [json-canvas](json-canvas/SKILL.md) | 创建和编辑 JSON Canvas 文件 |
| [lombok-best-practices](lombok-best-practices/SKILL.md) | Java / Spring Boot 项目 Lombok 规范化实践 |
| [markdown-generate-format](markdown-generate-format/SKILL.md) | Markdown 文档格式规范 |
| [obsidian-bases](obsidian-bases/SKILL.md) | Obsidian Bases 文件创建和编辑 |
| [obsidian-cli](obsidian-cli/SKILL.md) | Obsidian CLI 交互工具 |
| [obsidian-markdown](obsidian-markdown/SKILL.md) | Obsidian 风格 Markdown |
| [skill-creator](skill-creator/SKILL.md) | 创建和改进 skills |

## 目录结构

```
z2huo-skills/
├── defuddle/                     # 网页内容提取工具
│   └── SKILL.md
├── java-comment-specification/    # Java 代码注释规范
│   ├── references/
│   └── SKILL.md
├── json-canvas/                  # JSON Canvas 文件
│   ├── references/
│   └── SKILL.md
├── lombok-best-practices/         # Lombok 最佳实践
│   └── SKILL.md
├── markdown-generate-format/      # Markdown 格式规范
│   └── SKILL.md
├── obsidian-bases/                # Obsidian Bases
│   ├── references/
│   └── SKILL.md
├── obsidian-cli/                  # Obsidian CLI
│   └── SKILL.md
├── obsidian-markdown/             # Obsidian Markdown
│   ├── references/
│   └── SKILL.md
├── skill-creator/                # Skill 创建工具
│   ├── agents/
│   ├── assets/
│   ├── eval-viewer/
│   ├── references/
│   ├── scripts/
│   └── SKILL.md
├── release/                       # 打包发布的 skill 文件
├── docs/                          # 文档资源
└── README.md
```

## 使用方式

这些 Skills 适用于 Trae IDE 等支持 Skill 机制的开发工具。当识别到相关场景时，会自动触发对应的 Skill 规范。

## Fork Skills

### Anthropic Skills

Skills 来源自 [Anthropic Skills | Github](https://github.com/anthropics/skills) 项目，在使用过程中发现有些需要更待的地方，所以这里使用到的 Skills 进行了一些修改。

包括：

- skill-creator

### Obsidian Skills

这里是一些 Obsidian 的 Skills，来源：[obsidian-skills/skills at main · kepano/obsidian-skills](https://github.com/kepano/obsidian-skills/tree/main/skills)

包括：

- defuddle
- json-canvas
- obsidian-bases
- obsidian-cli
- obsidian-markdown