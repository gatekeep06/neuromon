package com.metacontent.utils

import com.metacontent.Neuromon
import net.minecraft.resources.ResourceLocation

fun neuromonResource(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(Neuromon.ID, path)

fun log(message: String) {
    Neuromon.LOGGER.info(message)
}

fun error(message: String) {
    Neuromon.LOGGER.error(message)
}

fun error(throwable: Throwable) {
    Neuromon.LOGGER.error(throwable.message ?: "", throwable)
}