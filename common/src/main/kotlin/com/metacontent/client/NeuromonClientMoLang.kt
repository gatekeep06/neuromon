package com.metacontent.client

import com.bedrockk.molang.runtime.MoParams
import com.cobblemon.mod.common.api.molang.ObjectValue
import com.cobblemon.mod.common.client.ClientMoLangFunctions
import com.cobblemon.mod.common.client.render.models.blockbench.PosableModel
import com.cobblemon.mod.common.util.getDoubleOrNull
import com.cobblemon.mod.common.util.getIntOrNull
import com.metacontent.client.animation.EyesLookAnimation
import java.util.function.Function

object NeuromonClientMoLang {
    private val animationFunctions = hashMapOf<String, Function<PosableModel, Function<MoParams, Any>>>(
        "look_with_eyes" to Function { model ->
            Function { params ->
                val headBoneName = params.getString(0)
                val eyeCount = params.getIntOrNull(1) ?: 0
                val eyeBoneNames = (0..<eyeCount).map { params.getString(2 + it) }
                val maxPitch = params.getDoubleOrNull(2 + eyeCount) ?: 70f
                val minPitch = params.getDoubleOrNull(3 + eyeCount) ?: -45f
                val maxYaw = params.getDoubleOrNull(4 + eyeCount) ?: 45F
                val minYaw = params.getDoubleOrNull(5 + eyeCount) ?: -45F
                val eyeRadius = params.getDoubleOrNull(6 + eyeCount) ?: 1f
                val maxXShift = params.getDoubleOrNull(7 + eyeCount) ?: 1f
                val minXShift = params.getDoubleOrNull(8 + eyeCount) ?: -1f
                val maxYShift = params.getDoubleOrNull(9 + eyeCount) ?: 1f
                val minYShift = params.getDoubleOrNull(10 + eyeCount) ?: -1f

                ObjectValue(
                    EyesLookAnimation(
                        head = model.getPart(headBoneName),
                        eyes = eyeBoneNames.map { model.getPart(it) },
                        maxPitch = maxPitch.toFloat(),
                        minPitch = minPitch.toFloat(),
                        maxYaw = maxYaw.toFloat(),
                        minYaw = minYaw.toFloat(),
                        eyeRadius = eyeRadius.toFloat(),
                        maxXShift = maxXShift.toFloat(),
                        minXShift = minXShift.toFloat(),
                        maxYShift = maxYShift.toFloat(),
                        minYShift = minYShift.toFloat(),
                    )
                )
            }
        }
    )

    fun init() {
        ClientMoLangFunctions.animationFunctions.putAll(animationFunctions)
    }
}