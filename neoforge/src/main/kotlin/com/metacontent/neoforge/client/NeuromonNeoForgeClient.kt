package com.metacontent.neoforge.client

import com.metacontent.client.NeuromonClient
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

object NeuromonNeoForgeClient {
    fun init() {
        with(MOD_BUS) {
            NeuromonClient.init()
        }
    }
}