package com.metacontent.evolution.progress

import com.cobblemon.mod.common.api.moves.MoveTemplate
import com.cobblemon.mod.common.api.pokemon.evolution.progress.EvolutionProgress
import com.cobblemon.mod.common.api.pokemon.evolution.progress.EvolutionProgressType
import com.cobblemon.mod.common.pokemon.Pokemon
import com.metacontent.evolution.NeuromonEvolutions
import com.metacontent.evolution.requirement.UseMoveWhileHoldingRequirement
import com.metacontent.utils.neuromonResource
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items

class UseMoveWhileHoldingProgress : EvolutionProgress<UseMoveWhileHoldingProgress.Progress> {
    companion object {
        val ID = neuromonResource("use_move_while_holding")

        val CODEC: MapCodec<UseMoveWhileHoldingProgress> = RecordCodecBuilder.mapCodec { instance ->
            instance.group(
                ItemPredicate.CODEC.fieldOf("heldItem").forGetter { it.progress.heldItem },
                MoveTemplate.BY_STRING_CODEC.fieldOf("move").forGetter { it.progress.move },
                Codec.INT.fieldOf("amount").forGetter { it.progress.amount }
            ).apply(instance) { heldItem, move, amount ->
                UseMoveWhileHoldingProgress().apply { updateProgress(Progress(heldItem, move, amount)) }
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
                requirement is UseMoveWhileHoldingRequirement
                        && requirement.heldItem == progress.heldItem
                        && requirement.move == progress.move
            }
        }
    }

    override fun type(): EvolutionProgressType<*> = NeuromonEvolutions.USE_MOVE_WHILE_HODLING

    data class Progress(
        val heldItem: ItemPredicate = ItemPredicate.Builder.item().of(Items.EGG).build(),
        val move: MoveTemplate = MoveTemplate.dummy(""),
        val amount: Int = 0
    )
}