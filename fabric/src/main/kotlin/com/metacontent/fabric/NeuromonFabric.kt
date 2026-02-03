package com.metacontent.fabric

import com.cobblemon.mod.common.item.group.CobblemonItemGroups
import com.metacontent.Neuromon
import com.metacontent.item.NeuromonItems
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.core.Registry

class NeuromonFabric : ModInitializer {
    override fun onInitialize() {
        Neuromon.init()

        NeuromonItems.register { id, item ->
            Registry.register(NeuromonItems.registry, id, item)
        }
        ItemGroupEvents.modifyEntriesEvent(CobblemonItemGroups.HELD_ITEMS_KEY).register { content ->
            NeuromonItems.injectHeldItems(content::accept)
        }
    }
}