package com.metacontent.client.animation

import com.cobblemon.mod.common.client.render.models.blockbench.PosableModel
import com.cobblemon.mod.common.client.render.models.blockbench.PosableState
import com.cobblemon.mod.common.client.render.models.blockbench.addPosition
import com.cobblemon.mod.common.client.render.models.blockbench.addRotation
import com.cobblemon.mod.common.client.render.models.blockbench.animation.PitchTiltAnimation.Companion.CORRECTED_ANGLE
import com.cobblemon.mod.common.client.render.models.blockbench.animation.PitchTiltAnimation.Companion.PITCHED_TILT
import com.cobblemon.mod.common.client.render.models.blockbench.animation.PitchTiltAnimation.Companion.PREVIOUS_ANGLE
import com.cobblemon.mod.common.client.render.models.blockbench.animation.PoseAnimation
import com.cobblemon.mod.common.client.render.models.blockbench.pose.Bone
import com.cobblemon.mod.common.client.render.models.blockbench.pose.ModelPartTransformation.Companion.X_AXIS
import com.cobblemon.mod.common.client.render.models.blockbench.pose.ModelPartTransformation.Companion.Y_AXIS
import com.cobblemon.mod.common.client.render.models.blockbench.repository.RenderContext
import com.cobblemon.mod.common.util.math.geometry.toRadians
import com.metacontent.Neuromon
import net.minecraft.client.model.geom.ModelPart
import kotlin.math.atan
import kotlin.math.tan

class EyesLookAnimation(
    val head: Bone?,
    val eyes: List<ModelPart>,
    val maxPitch: Float,
    val minPitch: Float,
    val maxYaw: Float,
    val minYaw: Float,
    val eyeRadius: Float,
    val maxXShift: Float,
    val minXShift: Float,
    val maxYShift: Float,
    val minYShift: Float
) : PoseAnimation() {
    override fun setupAnim(
        context: RenderContext,
        model: PosableModel,
        state: PosableState,
        limbSwing: Float,
        limbSwingAmount: Float,
        ageInTicks: Float,
        headYaw: Float,
        headPitch: Float,
        intensity: Float
    ) {
        if (head == null || eyes.isEmpty()) return

        var counterTiltDegrees = if (PITCHED_TILT in state.renderMarkers) { state.numbers[PREVIOUS_ANGLE] ?: 0F } else 0F
        if (CORRECTED_ANGLE in state.numbers && PITCHED_TILT in state.renderMarkers) {
            counterTiltDegrees -= state.numbers[CORRECTED_ANGLE] ?: 0F
        }
        state.numbers[CORRECTED_ANGLE] = counterTiltDegrees
        val pitch = headPitch.coerceIn(minPitch, maxPitch).toRadians() * intensity - counterTiltDegrees.toRadians()
        val yaw = headYaw.coerceIn(minYaw, maxYaw).toRadians() * intensity

        val xShift = (eyeRadius * tan(yaw)).coerceIn(minXShift, maxXShift)
        val yShift = (eyeRadius * tan(pitch)).coerceIn(minYShift, maxYShift)

        eyes.forEach { eye ->
            eye.addPosition(X_AXIS, -xShift)
            eye.addPosition(Y_AXIS, -yShift)
        }

        head.addRotation(Y_AXIS, yaw - atan(xShift / eyeRadius))
        head.addRotation(X_AXIS, pitch - atan(yShift / eyeRadius))
    }
}