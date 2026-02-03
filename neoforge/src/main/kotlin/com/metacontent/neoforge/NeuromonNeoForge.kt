package com.metacontent.neoforge

import com.cobblemon.mod.common.item.group.CobblemonItemGroups
import com.metacontent.Neuromon
import com.metacontent.item.NeuromonItems
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent
import net.neoforged.neoforge.registries.RegisterEvent
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(Neuromon.ID)
class NeuromonNeoForge {
    init {
        with(MOD_BUS) {
            Neuromon.init()
            addListener<RegisterEvent> { event ->
                event.register(NeuromonItems.resourceKey) { helper ->
                    NeuromonItems.register { id, item -> helper.register(id, item) }
                }
            }
            addListener<BuildCreativeModeTabContentsEvent> { event ->
                if (event.tabKey == CobblemonItemGroups.HELD_ITEMS_KEY) {
                    NeuromonItems.injectHeldItems(event::accept)
                }
            }
        }
    }
}