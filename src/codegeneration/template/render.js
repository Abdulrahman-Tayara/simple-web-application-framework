function render() {
    setInterval(() => {
        removeRepeatedNodes();
        restoreDeleteIfNodes();
        
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

        renderVariableUsages();
        renderCpShowConditionally();
        renderCpHideConditionally();
        renderCpIf();
        renderCpSwitch();

    }, 200);
}
render();