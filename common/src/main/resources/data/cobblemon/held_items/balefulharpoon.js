{
    name: "Baleful Harpoon",
    fling: {
        basePower: 80
    },
    onBasePowerPriority: 15,
    onBasePower(basePower, source, target, move) {
        if (target && target.types.includes('Water')) {
            return this.chainModify([4915, 4096]);
        }
    },
    gen: 21
}