package com.viatemev.ktlint

import com.github.shyiko.ktlint.core.RuleSet
import com.github.shyiko.ktlint.core.RuleSetProvider

class CustomRuleSetProvider : RuleSetProvider {

    override fun get(): RuleSet = RuleSet("no-print", NoPrintRule())
}
