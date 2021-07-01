function render() {
    setInterval(() => {
        removeRepeatedNodes(); //removes the cp-for nodes each time we start a new render

        restoreDeleteIfNodes();//remove the deleted if nodes from the list and re adds them to their place

        //render the changes of the cp-model (input=>variable) binding
        for (var i = 0; i < changes.length; i++) {
            // console.log(changes[i]);
            changes[i]();
        }
        changes = [];

        //(variable => input) binding
        for (let index = 0; index < inits.length; index++) {
            inits[index]();
        }

        //render the fors
        for (let index = 0; index < forRenders.length; index++) {
            forRenders[index]();
        }

        replaceAllVariableUsages()
        renderCpShowConditionally();
        renderCpHideConditionally();
        renderCpIfConditionalBindings();
        renderCpSwitchBindinds();

    }, 200);
}
render();