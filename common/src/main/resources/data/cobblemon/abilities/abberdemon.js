{
    onHit(target, source, move) {
        if (target !== source) return;
        if (!move || move.category !== 'Status' || move.target !== 'self') return;
        if (!this.randomChance(333, 1000)) return;
        const moves = [];
        for (const moveSlot of source.moveSlots) {
            const moveid = moveSlot.id;
            if (!moveid) continue;
            const move = this.dex.moves.get(moveid);
            if (move.category === 'Status' || move.flags['charge'] || (move.isZ && move.basePower !== 1) || move.isMax) {
                continue;
            }
            moves.push(move);
        }
        let randomMove = null;
        if (moves.length) randomMove = this.sample(moves);
        if (!randomMove) return;

        const modifiedMove = this.dex.deepClone(randomMove);
        const basePower = randomMove.basePower || 0;
        modifiedMove.basePower = randomMove.id === 'boom' ? basePower : Math.floor(basePower * 0.666);
        this.actions.useMove(modifiedMove, source);
    },
    flags: {failroleplay: 1, noreceiver: 1, noentrain: 1, notrace: 1, failskillswap: 1, cantsuppress: 1, notransform: 1},
    name: "Abber Demon",
    rating: 5
}