package com.viatemev.ktlint

import com.github.shyiko.ktlint.core.Rule
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil.getNonStrictParentOfType
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtStringTemplateEntry

class NoPrintRule : Rule("no-print") {

    override fun visit(node: ASTNode, autoCorrect: Boolean, emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit) {
        if (isPrintExpression(node)) {
            emit(node.startOffset, "Do not use \"${node.text}\" in code, use logger instead", false)
        }
    }

    private fun isPrintExpression(node: ASTNode): Boolean {
        if (node is LeafPsiElement && node.elementType === KtTokens.IDENTIFIER) {
            return node.text == "println" || node.text == "print"
        }
        return false
    }
}
