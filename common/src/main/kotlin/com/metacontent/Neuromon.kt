package com.metacontent

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.metacontent.evolution.NeuromonEvolutions
import com.metacontent.evolution.progress.DefeatWhileHoldingProgress
import com.metacontent.evolution.requirement.DefeatWhileHoldingRequirement
import com.metacontent.utils.EvilneuroEvolutionFix
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Neuromon {
    const val ID = "neuromon"
    val LOGGER: Logger = LoggerFactory.getLogger(ID)

    fun init() {
        NeuromonEvolutions.registerRequirements()

        CobblemonEvents.EVOLUTION_COMPLETE.subscribe { (result, _, _) ->
            EvilneuroEvolutionFix.teachEvolutionMove(result)
        }

        CobblemonEvents.BATTLE_VICTORY.subscribe { (_, winners, losers, _) ->
            val actors = winners + losers
            actors.forEach { actor ->
                val faintedPokemon = actor.pokemonList.filter { it.health <= 0 }
                actor.getSide().getOppositeSide().actors.forEach { opponent ->
                    val opponentNonFaintedPokemon = opponent.pokemonList.filter { it.health > 0 }
                    faintedPokemon.forEach { faintedPokemon ->
                        opponentNonFaintedPokemon.forEach { opponentPokemon ->
                            val facedFainted = opponentPokemon.facedOpponents.contains(faintedPokemon)
                            val pokemon = opponentPokemon.effectedPokemon
                            if (facedFainted) {
                                DefeatWhileHoldingRequirement.getRequirement(
                                    pokemon = pokemon,
                                    heldItem = pokemon.heldItem(),
                                    target = faintedPokemon.effectedPokemon
                                )?.let { requirement ->
                                    val progress = pokemon.evolutionProxy.current().progressFirstOrCreate(
                                        predicate = {
                                            it is DefeatWhileHoldingProgress
                                                    && it.currentProgress().heldItem == requirement.heldItem
                                                    && it.currentProgress().target.originalString.equals(
                                                requirement.target.originalString,
                                                true
                                            )
                                        },
                                        progressFactory = { DefeatWhileHoldingProgress() }
                                    )
                                    progress.updateProgress(
                                        DefeatWhileHoldingProgress.Progress(
                                            requirement.heldItem,
                                            requirement.target,
                                            progress.currentProgress().amount + 1
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}