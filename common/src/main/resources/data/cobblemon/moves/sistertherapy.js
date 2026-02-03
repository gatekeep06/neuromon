{
    accuracy: true,
    basePower: 0,
    category: "Status",
    name: "Sister Therapy",
    pp: 5,
    priority: 0,
    heal: [1, 2],
    flags: {snatch: 1, heal: 1, bypasssub: 1},
    secondary: null,
    target: "adjacentAlly",
    onPrepareHit(target, source, move) {
        if (!['Neurosama', "Evilneuro", 'Neuroplushie', 'Evilplushie'].includes(target.baseSpecies.baseSpecies)) return false;
        if (target.baseSpecies.baseSpecies === source.baseSpecies.baseSpecies) return false;
        return true;
    },
    type: "Psychic",
    contestType: "Clever"
}