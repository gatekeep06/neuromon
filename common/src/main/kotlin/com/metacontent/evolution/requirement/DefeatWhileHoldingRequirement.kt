package com.metacontent.evolution.requirement

import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.api.pokemon.requirement.Requirement
import com.cobblemon.mod.common.pokemon.Pokemon
import com.metacontent.evolution.progress.DefeatWhileHoldingProgress
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

class DefeatWhileHoldingRequirement(
    val heldItem: ItemPredicate,
    val target: PokemonProperties,
    val amount: Int
) : Requirement {
    companion object {
        const val VARIANT = "defeat_while_hodling"

        @JvmStatic
        fun getRequirement(pokemon: Pokemon, heldItem: ItemStack, target: Pokemon): DefeatWhileHoldingRequirement? {
            return pokemon.form.evolutions.firstNotNullOfOrNull { evolution ->
                evolution.requirements.firstOrNull { requirement ->
                    requirement is DefeatWhileHoldingRequirement
                            && requirement.heldItem.test(heldItem)
                            && requirement.target.matches(target)
                } as? DefeatWhileHoldingRequirement?
            }
        }
    }

    constructor() : this(ItemPredicate.Builder.item().of(Items.EGG).build(), PokemonProperties(), 0)

    override fun check(pokemon: Pokemon): Boolean {
        return pokemon.evolutionProxy.current()
            .progress()
            .filterIsInstance<DefeatWhileHoldingProgress>()
            .any { progress ->
                progress.currentProgress().heldItem == heldItem
                        && progress.currentProgress().target.originalString.equals(target.originalString, true)
                        && progress.currentProgress().amount >= amount
            }
    }
}