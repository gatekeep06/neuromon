{
    onStart(pokemon) {
        if (pokemon.side.totalFainted) {
            this.add('-activate', pokemon, 'ability: When You\'re Gone');
            const fallen = Math.min(pokemon.side.totalFainted, 5);
            this.add('-start', pokemon, `fallen${fallen}`, '[silent]');
            this.effectState.fallen = fallen;
        }
    },
    onEnd(pokemon) {
        this.add('-end', pokemon, `fallen${this.effectState.fallen}`, '[silent]');
    },
    onModifySpAPriority: 21,
    onModifySpA(relayVar, target, source, move) {
        if (this.effectState.fallen) {
            const powMod = [4096, 4506, 4915, 5325, 5734, 6144];
            this.debug(`When You\'re Gone SpA boost: ${powMod[this.effectState.fallen]}/4096`);
            return this.chainModify([powMod[this.effectState.fallen], 4096]);
        }
    },
    onModifySpDPriority: 21,
    onModifySpD(relayVar, target, source, move) {
        if (this.effectState.fallen) {
            const powMod = [4096, 4506, 4915, 5325, 5734, 6144];
            this.debug(`When You\'re Gone SpD boost: ${powMod[this.effectState.fallen]}/4096`);
            return this.chainModify([powMod[this.effectState.fallen], 4096]);
        }
    },
    flags: {failroleplay: 1, noreceiver: 1, noentrain: 1, notrace: 1, failskillswap: 1, cantsuppress: 1, notransform: 1},
    name: "When You\'re Gone",
    rating: 5
}