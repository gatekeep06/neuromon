package com.metacontent.mixin;

import com.cobblemon.mod.common.api.battles.interpreter.Effect;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.api.moves.animations.ActionEffectTimeline;
import com.cobblemon.mod.common.battles.dispatch.DispatchResult;
import com.cobblemon.mod.common.battles.interpreter.instructions.MoveInstruction;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.metacontent.evolution.progress.UseMoveWhileHoldingProgress;
import com.metacontent.evolution.requirement.UseMoveWhileHoldingRequirement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MoveInstruction.class)
public abstract class MoveInstructionMixin {
    @Inject(method = "invoke$lambda$1", at = @At("HEAD"), remap = false)
    private static void injectDispatch(MoveInstruction this$0, PokemonBattle $battle, BattlePokemon $targetPokemon, Effect $optionalEffect, ActionEffectTimeline $actionEffect, CallbackInfoReturnable<DispatchResult> cir) {
        if ($targetPokemon == null) return;
        Pokemon pokemon = this$0.userPokemon.getEffectedPokemon();
        MoveTemplate move = this$0.getMove();
        UseMoveWhileHoldingRequirement requirement = UseMoveWhileHoldingRequirement.getRequirement(pokemon, pokemon.heldItem(), move);
        if (requirement != null) {
            UseMoveWhileHoldingProgress progress = pokemon.getEvolutionProxy().current().progressFirstOrCreate(
                    p -> {
                        if (p instanceof UseMoveWhileHoldingProgress whileHoldingProgress) {
                            return whileHoldingProgress.currentProgress().getHeldItem() == requirement.getHeldItem() && whileHoldingProgress.currentProgress().getMove() == requirement.getMove();
                        }
                        return false;
                    },
                    UseMoveWhileHoldingProgress::new
            );
            progress.updateProgress(new UseMoveWhileHoldingProgress.Progress(requirement.getHeldItem(), this$0.getMove(), progress.currentProgress().getAmount() + 1));
        }
    }
}
