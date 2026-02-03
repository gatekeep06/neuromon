package com.metacontent.evolution

import com.cobblemon.mod.common.api.pokemon.evolution.progress.EvolutionProgressType
import com.cobblemon.mod.common.api.pokemon.evolution.progress.EvolutionProgressTypes
import com.cobblemon.mod.common.pokemon.adapters.CobblemonRequirementAdapter
import com.metacontent.evolution.progress.DefeatWhileHoldingProgress
import com.metacontent.evolution.progress.UseMoveWhileHoldingProgress
import com.metacontent.evolution.requirement.DefeatWhileHoldingRequirement
import com.metacontent.evolution.requirement.UseMoveWhileHoldingRequirement

object NeuromonEvolutions {
    val USE_MOVE_WHILE_HODLING = EvolutionProgressTypes.registerType(
        id = UseMoveWhileHoldingProgress.ID,
        type = EvolutionProgressType(UseMoveWhileHoldingProgress.CODEC)
    )

    val DEFEAT_WHILE_HOLDING = EvolutionProgressTypes.registerType(
        id = DefeatWhileHoldingProgress.ID,
        type = EvolutionProgressType(DefeatWhileHoldingProgress.CODEC)
    )

    @JvmStatic
    fun registerRequirements() {
        CobblemonRequirementAdapter.registerType(UseMoveWhileHoldingRequirement.VARIANT, UseMoveWhileHoldingRequirement::class)
        CobblemonRequirementAdapter.registerType(DefeatWhileHoldingRequirement.VARIANT, DefeatWhileHoldingRequirement::class)
    }
}