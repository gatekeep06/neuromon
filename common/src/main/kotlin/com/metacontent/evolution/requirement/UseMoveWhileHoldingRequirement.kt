package com.metacontent.evolution.requirement

import com.cobblemon.mod.common.api.moves.MoveTemplate
import com.cobblemon.mod.common.api.pokemon.requirement.Requirement
import com.cobblemon.mod.common.pokemon.Pokemon
import com.metacontent.evolution.progress.UseMoveWhileHoldingProgress
import com.metacontent.utils.error
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

class UseMoveWhileHoldingRequirement(
    val heldItem: ItemPredicate,
    val move: MoveTemplate,
    val amount: Int
) : Requirement {
    companion object {
        const val VARIANT = "use_move_while_holding"

        @JvmStatic
        fun getRequirement(pokemon: Pokemon, heldItem: ItemStack, move: MoveTemplate): UseMoveWhileHoldingRequirement? {
            return pokemon.form.evolutions.firstNotNullOfOrNull { evolution ->
                evolution.requirements.firstOrNull { requirement ->
                    requirement is UseMoveWhileHoldingRequirement
                            && requirement.heldItem.test(heldItem)
                            && requirement.move == move
                } as? UseMoveWhileHoldingRequirement?
            }
        }
    }

    constructor() : this(ItemPredicate.Builder.item().of(Items.EGG).build(), MoveTemplate.dummy(""), 0)

    override fun check(pokemon: Pokemon): Boolean {
        return pokemon.evolutionProxy.current()
            .progress()
            .filterIsInstance<UseMoveWhileHoldingProgress>()
            .any { progress ->
                progress.currentProgress().heldItem == heldItem
                        && progress.currentProgress().move == move
                        && progress.currentProgress().amount >= amount
            }
    }
}