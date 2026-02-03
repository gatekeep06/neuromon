{
    onStart(pokemon) {
        if (pokemon.baseSpecies.baseSpecies !== 'Vedal' || pokemon.species.forme === 'Giga') return;
        const fainted = pokemon.side.faintedLastTurn ? pokemon.side.faintedLastTurn : pokemon.side.faintedThisTurn;
        if (fainted && ['Neurosama', "Evilneuro", 'Neuroplushie', 'Evilplushie', 'Studysama'].includes(fainted.baseSpecies.baseSpecies)) {
            this.add('-activate', pokemon, 'ability: Father of the Year');
            pokemon.formeChange('Vedal-Giga', this.effect, true);
        }
    },
    flags: {failroleplay: 1, noreceiver: 1, noentrain: 1, notrace: 1, failskillswap: 1, cantsuppress: 1, notransform: 1},
    name: "Father of the Year",
    rating: 5
}