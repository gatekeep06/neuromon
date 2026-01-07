{
    accuracy: 100,
    basePower: 100,
    category: "Physical",
    name: "BOOM",
    pp: 10,
    priority: 0,
    onModifyMove(move, pokemon, target) {
        if (pokemon.types.includes('Dark')) {
            move.forceSTAB = true
        }
    },
    flags: {protect: 1, mirror: 1},
    target: "allAdjacentFoes",
    type: "Fire",
    contestType: "Cool"
}