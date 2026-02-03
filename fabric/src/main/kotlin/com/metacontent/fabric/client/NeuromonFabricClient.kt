package com.metacontent.fabric.client

import com.metacontent.client.NeuromonClient
import net.fabricmc.api.ClientModInitializer

class NeuromonFabricClient : ClientModInitializer {
    override fun onInitializeClient() {
        NeuromonClient.init()
    }
}