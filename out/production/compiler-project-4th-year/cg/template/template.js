
// -------------------------- cp-model -------------------------- //

// this should be used because we are initializing on every render
function assigneInitalVariables(id, variableName) {
    document.getElementById(id).value = forthyear[variableName];
}

function changeValue(id, variableName) {
    forthyear[variableName] = document.getElementById(id).value;
}

function bindCpModelToVar(id, variableName) {

    // here we have 2 way bindings.

    //bind: variable => input
    inits.push(() => {
        assigneInitalVariables(id, variableName);
    });

    //bind : input => variable
    document.getElementById(id).addEventListener("input", (event) => {

        changes.push(() => {
            changeValue(id, variableName);
        });

    });
}


// -------------------------- variable usage -------------------------- //
function bindVariableUseageWithVariable(id, variableName) {

    //this function saves the default innerHTML for the doc with id: @param:id
    //ex: saves : "the value of x is {{x}}"

    let text = document.getElementById(id).innerHTML;

    if (defaultInnerHTML[variableName] === undefined) {
        defaultInnerHTML[variableName] = []
        defaultInnerHTML[variableName].push({ id: id, text: text });
    } else {
        defaultInnerHTML[variableName].push({ id: id, text: text });
    }

}

function replaceAllVariableUsages() {

    //this function repleaces all the {{__}} with the value of the __ variable

    Object.keys(defaultInnerHTML).forEach(key => {
        for (let index = 0; index < defaultInnerHTML[key].length; index++) {

            let obj = defaultInnerHTML[key][index];
            let newText = obj.text.replace("{{" + key + "}}", forthyear[key]);
            // console.log(newText);
            document.getElementById(obj.id).innerHTML = newText;

        }
    });
}

// -------------------------- cp-show -------------------------- //

function addCpShowIdBinding(id, variableName) {
    if (cpShowIdBindings[variableName] === undefined) {
        cpShowIdBindings[variableName] = []
        cpShowIdBindings[variableName].push(id);
    } else {
        cpShowIdBindings[variableName].push(id);
    }
}


function renderCpShowConditionally(id, variableName) {
    Object.keys(cpShowIdBindings).forEach(variableName => {
        for (let index = 0; index < cpShowIdBindings[variableName].length; index++) {

            let id = cpShowIdBindings[variableName][index];
            if (forthyear[variableName] == true) {
                document.getElementById(id).hidden = false;
            } else {
                document.getElementById(id).hidden = true;
            }

        }
    });
}

// -------------------------- cp-hide -------------------------- //

function addCpHideIdBinding(id, variableName) {
    if (cpHideIdBindings[variableName] === undefined) {
        cpHideIdBindings[variableName] = []
        cpHideIdBindings[variableName].push(id);
    } else {
        cpHideIdBindings[variableName].push(id);
    }
}

function renderCpHideConditionally(id, variableName) {
    Object.keys(cpHideIdBindings).forEach(variableName => {
        for (let index = 0; index < cpHideIdBindings[variableName].length; index++) {

            let id = cpHideIdBindings[variableName][index];
            if (forthyear[variableName] == true) {
                document.getElementById(id).hidden = true;
            } else {
                document.getElementById(id).hidden = false;
            }

        }
    });
}

// -------------------------- cp-if -------------------------- //

//here we replace the node we want to delete (if we needed to delete it)
//with a node that has the same id and it is hidden
//and when we need to re-add the old node we add it instead of the newly initialized node

//this is implemented this way to save the place in-wich the node should always be

function addCpIfBinding(id, variableName, expectedValue) {
    cpIfConditionalBindings.push({
        id: id,
        variableName: variableName,
        expectedValue: expectedValue
    });
}

function renderCpIfConditionalBindings() {
    for (let index = 0; index < cpIfConditionalBindings.length; index++) {

        let cond = cpIfConditionalBindings[index];

        let doc = document.getElementById(cond.id);
        if (forthyear[cond.variableName] != cond.expectedValue) {

            //create a node to be put instead of the if node
            let rep = document.createElement('div');
            rep.id = cond.id;
            rep.hidden = true;

            //put it after the if node
            doc.after(rep);

            //clone the if node to keep a reference
            let cloneDoc = doc.cloneNode(true);

            //save the reference and delete the original
            deletedNodes.push(cloneDoc);
            doc.remove();
        }

    }
}

function restoreDeleteIfNodes() {

    //remove the deleted if nodes from the list and re adds them to their place

    for (let index = 0; index < deletedNodes.length; index++) {

        let delNode = deletedNodes[index];

        //find the one we put there
        let rep = document.getElementById(delNode.id);
        rep.after(delNode);
        rep.remove();


    }
    deletedNodes = [];
}