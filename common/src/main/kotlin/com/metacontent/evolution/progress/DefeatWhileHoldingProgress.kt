package com.metacontent.evolution.progress

import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.api.pokemon.evolution.progress.EvolutionProgress
import com.cobblemon.mod.common.api.pokemon.evolution.progress.EvolutionProgressType
import com.cobblemon.mod.common.pokemon.Pokemon
import com.metacontent.evolution.NeuromonEvolutions
import com.metacontent.evolution.requirement.DefeatWhileHoldingRequirement
import com.metacontent.utils.neuromonResource
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items

class DefeatWhileHoldingProgress : EvolutionProgress<DefeatWhileHoldingProgress.Progress> {
    companion object {
        val ID = neuromonResource("defeat_while_holding")

        val CODEC: MapCodec<DefeatWhileHoldingProgress> = RecordCodecBuilder.mapCodec { instance ->
            instance.group(
                ItemPredicate.CODEC.fieldOf("heldItem").forGetter { it.progress.heldItem },
                PokemonProperties.CODEC.fieldOf("target").forGetter { it.progress.target },
                Codec.INT.fieldOf("amount").forGetter { it.progress.amount }
            ).apply(instance) { heldItem, target, amount ->
                DefeatWhileHoldingProgress().apply { updateProgress(Progress(heldItem, target, amount)) }
            }
        }
    }

    private var progress = Progress()

    override fun id(): ResourceLocation = ID

    override fun currentProgress(): Progress = progress

    override fun updateProgress(progress: Progress) {
        this.progress = progress
    }

    override fun reset() {
        progress = Progress()
    }

    override fun shouldKeep(pokemon: Pokemon): Boolean {
        return pokemon.form.evolutions.any { evolution ->
            evolution.requirements.any { requirement ->
                requirement is DefeatWhileHoldingRequirement
                        && requirement.heldItem == progress.heldItem
                        && requirement.target.originalString.equals(progress.target.originalString, true)
            }
        }
    }

    override fun type(): EvolutionProgressType<*> = NeuromonEvolutions.DEFEAT_WHILE_HOLDING

    data class Progress(
        val heldItem: ItemPredicate = ItemPredicate.Builder.item().of(Items.EGG).build(),
        val target: PokemonProperties = PokemonProperties(),
        val amount: Int = 0
    )
}