{
    name: "Heart Transmitter",
    fling: {
        basePower: 40,
    },
    onBasePowerPriority: 15,
    onBasePower(basePower, user, target, move) {
        if (move && move.flags.sound === 1) {
            return this.chainModify([4915, 4096]);
        }
	},
	gen: 21
}