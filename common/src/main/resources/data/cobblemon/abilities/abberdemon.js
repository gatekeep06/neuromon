{
    onHit(target, source, move) {
        if (!move || move.category !== 'Status' || move.target !== 'self') return;
        if (!this.randomChance(3, 10)) return;
        const moves = [];
        for (const moveSlot of source.moveSlots) {
            const moveid = moveSlot.id;
            if (!moveid) continue;
            const move = this.dex.moves.get(moveid);
            if (move.category === 'Status' || move.flags['charge'] || (move.isZ && move.basePower !== 1) || move.isMax) {
                continue;
            }
            moves.push(moveid);
        }
        let randomMove = '';
        if (moves.length) randomMove = this.sample(moves);
        if (!randomMove) return;
        this.actions.useMove(randomMove, source);
    },
    flags: {failroleplay: 1, noreceiver: 1, noentrain: 1, notrace: 1, failskillswap: 1, cantsuppress: 1, notransform: 1},
    name: "Abber Demon",
    rating: 5
}