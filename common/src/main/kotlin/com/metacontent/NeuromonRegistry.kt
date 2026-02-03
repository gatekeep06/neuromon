package com.metacontent

import com.metacontent.utils.neuromonResource
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation

abstract class NeuromonRegistry<R : Registry<T>, K : ResourceKey<R>, T> {
    abstract val registry: R

    abstract val resourceKey: K

    private val queue = mutableMapOf<ResourceLocation, T>()

    fun add(name: String, entry: T): T {
        queue[neuromonResource(name)] = entry
        return entry
    }

    fun register(consumer: (ResourceLocation, T) -> Unit) {
        queue.forEach(consumer)
    }
}