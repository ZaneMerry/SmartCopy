# SmartCopy

IntelliJ IDEA 插件：选中代码复制时，自动附带文件路径和行号上下文，方便直接粘贴给 AI 编程助手（Claude Code、GitHub Copilot 等）。

## 功能

选中代码后，按 `Ctrl+Alt+C` 或右键菜单 **"Copy with Context"**，剪贴板内容格式：

```
File: src/main/java/com/example/UserService.java
Lines 42-58

```java
public User getUser(Long id) {
    return userDao.findById(id);
}
```
```

## 安装

### 方式一：本地安装

1. 下载 [最新 Release](https://github.com/xiao-zhou-study/SmartCopy/releases) 中的 `SmartCopy-*.zip`
2. IDEA → `File` → `Settings` → `Plugins` → ⚙️ → **Install Plugin from Disk...**
3. 选择 zip 文件，重启 IDEA

### 方式二：从源码构建

```bash
./gradlew buildPlugin
# 产物在 build/distributions/ 下
```

## 使用

1. 在编辑器中选中代码
2. 右键菜单 → **Copy with Context**，或按 `Ctrl+Alt+C`
3. 粘贴到 Claude Code CLI 或任意 AI 工具

## 兼容性

IntelliJ IDEA 2024.1+
