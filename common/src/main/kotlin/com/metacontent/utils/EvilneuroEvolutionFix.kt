package com.metacontent.utils

import com.cobblemon.mod.common.api.moves.BenchedMove
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature
import com.cobblemon.mod.common.pokemon.Pokemon

/**
 * This is a workaround for the issue where a Pokémon, after evolving, for some reason learns moves from ALL its evolution variants
 */
object EvilneuroEvolutionFix {
    const val SPECIES_ID = "evilneuro"
    val moves = mapOf(
        "harpoon" to "liquidation",
        "bat" to "woodhammer",
        "pipe" to "meteormash",
        "guitar" to "supercellslam"
    )

    fun teachEvolutionMove(pokemon: Pokemon) {
        if (pokemon.species.showdownId() != SPECIES_ID) return

        val moveName = moves[pokemon.getFeature<StringSpeciesFeature>("weapon_of_choice")?.value] ?: return
        log(moveName)
        val move = pokemon.species.moves.specialMoves.firstOrNull {
            it.name.equals(moveName, true).also { log(it.toString()) }
        } ?: return

        if (pokemon.moveSet.hasSpace()) {
            pokemon.moveSet.add(move.create())
        }
        else {
            pokemon.benchedMoves.add(BenchedMove(move, 0))
        }
    }
}