{
    accuracy: true,
    basePower: 0,
    category: "Status",
    name: "Restart",
    pp: 5,
    priority: 0,
    flags: {snatch: 1, heal: 1},
    onHit(target, source, move) {
        let factor = target.maxhp / 2;
        if (target.status) {
            const statusName = target.status;
            if (target.cureStatus()) {
                factor = factor / 2;
            }
        }

        const success = !!target.heal(factor);

        if (!success) {
            this.add('-fail', target, 'heal');
            return this.NOT_FAIL;
        }

        return success;
    },
    secondary: null,
    target: "self",
    type: "Normal",
    zMove: {effect: 'clearnegativeboost'},
    contestType: "Clever"
}