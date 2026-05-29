package com.smartcopy

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import java.awt.datatransfer.StringSelection

class SmartCopyAction : AnAction() {

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        val hasSelection = editor?.selectionModel?.hasSelection() == true
        e.presentation.isEnabledAndVisible = hasSelection
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val psiFile = e.getData(CommonDataKeys.PSI_FILE) ?: return

        val selectionModel = editor.selectionModel
        val document = editor.document

        val selectedText = selectionModel.selectedText ?: return
        val startLine = document.getLineNumber(selectionModel.selectionStart) + 1
        val endLine = document.getLineNumber(selectionModel.selectionEnd) + 1

        val relativePath = getRelativePath(project, psiFile)
        val language = psiFile.language.displayName

        val result = formatOutput(relativePath, startLine, endLine, language, selectedText)

        CopyPasteManager.getInstance().setContents(StringSelection(result))
    }

    private fun getRelativePath(project: Project, psiFile: PsiFile): String {
        val basePath = project.basePath ?: return psiFile.name
        val fullPath = psiFile.virtualFile.path.replace('\\', '/')
        val base = basePath.replace('\\', '/')
        return if (fullPath.startsWith(base)) fullPath.substring(base.length).removePrefix("/") else psiFile.name
    }

    private fun formatOutput(
        path: String,
        startLine: Int,
        endLine: Int,
        language: String,
        code: String
    ): String {
        val lineRange = if (startLine == endLine) "Line $startLine" else "Lines $startLine-$endLine"

        return buildString {
            appendLine("File: $path")
            appendLine(lineRange)
            appendLine()
            appendLine("```$language")
            appendLine(code)
            appendLine("```")
        }
    }
}
