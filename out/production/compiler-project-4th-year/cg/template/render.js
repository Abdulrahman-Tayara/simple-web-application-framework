function render() {
    setInterval(() => {

        for (var i = 0; i < changes.length; i++) {
            changes[i]();
        }
        changes = [];

        for (let index = 0; index < inits.length; index++) {
            inits[index]();
        }

        for (let index = 0; index < forRenders.length; index++) {
            forRenders[index]();
        }

        replaceAllVariableUsages();

    }, 16);
}
render();