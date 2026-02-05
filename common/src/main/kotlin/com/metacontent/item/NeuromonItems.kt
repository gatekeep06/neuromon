package com.metacontent.item

import com.cobblemon.mod.common.pokemon.helditem.CobblemonHeldItemManager
import com.metacontent.NeuromonRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.ItemLike

object NeuromonItems : NeuromonRegistry<Registry<Item>, ResourceKey<Registry<Item>>, Item>() {
    override val registry: Registry<Item> = BuiltInRegistries.ITEM
    override val resourceKey: ResourceKey<Registry<Item>> = Registries.ITEM

    val HEART_TRANSMITTER = heldItem("heart_transmitter")
    val BALEFUL_HARPOON = heldItem("baleful_harpoon")
    val OMINOUS_BAT = heldItem("ominous_bat")
    val SINISTER_PIPE = heldItem("sinister_pipe")
    val EERIE_GUITAR = heldItem("eerie_guitar")

    fun simpleItem(name: String) = add(name, Item(Item.Properties()))

    fun heldItem(name: String) = simpleItem(name).also { item ->
        CobblemonHeldItemManager.registerRemap(item, name.replace("_", ""))
    }

    fun injectHeldItems(consumer: (ItemLike) -> Unit) {
        consumer(HEART_TRANSMITTER)
        consumer(BALEFUL_HARPOON)
        consumer(OMINOUS_BAT)
        consumer(SINISTER_PIPE)
    }
}