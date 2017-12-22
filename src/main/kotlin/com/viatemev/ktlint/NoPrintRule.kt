package com.viatemev.ktlint

import com.github.shyiko.ktlint.core.Rule
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil.getNonStrictParentOfType
import org.jetbrains.kotlin.psi.KtStringTemplateEntry

class NoPrintRule : Rule("no-print") {

    override fun visit(node: ASTNode, autoCorrect: Boolean, emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit) {
        if (node is LeafPsiElement && isPrintExpression(node) && getNonStrictParentOfType(node, KtStringTemplateEntry::class.java) == null) {
            emit(node.startOffset, "Do not use System.out.println in code, use logger instead", false)
        }
    }

    private fun isPrintExpression(node: LeafPsiElement): Boolean {
        return node.textMatches("println")
    }
}
