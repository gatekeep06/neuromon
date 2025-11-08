{
    onStart(pokemon) {
        if (pokemon.baseSpecies.baseSpecies !== 'neuromon:Vedal' || pokemon.species.forme === 'Giga') return;
        const fainted = pokemon.side.faintedLastTurn ? pokemon.side.faintedLastTurn : pokemon.side.faintedThisTurn;
        if (fainted && ['neuromon:Neurosama', "neuromon:Evilneuro", 'neuromon:Neuroplush', 'neuromon:Evilplush', 'neuromon:Studysama'].includes(fainted.baseSpecies.baseSpecies)) {
            this.add('-activate', pokemon, 'ability: Father of the Year');
            pokemon.formeChange('neuromon:Vedal-Giga', this.effect, true);
        }
    },
    flags: {failroleplay: 1, noreceiver: 1, noentrain: 1, notrace: 1, failskillswap: 1, cantsuppress: 1, notransform: 1},
    name: "Father of the Year",
    rating: 5
}