
function assigneInitalVariables(id, variableName) {
    document.getElementById(id).value = forthyear[variableName];
}

function changeValue(id, variableName) {
    forthyear[variableName] = document.getElementById(id).value;
}

function bindCpModelToVar(id, variableName) {
    inits.push(() => {
        assigneInitalVariables(id, variableName);
    });
    document.getElementById(id).addEventListener("input", (event) => {

        changes.push(() => {
            changeValue(id, variableName);
        });
    });
}


function bindVariableUseageWithVariable(id, variableName) {

    let text = document.getElementById(id).innerHTML;

    if (defaultInnerHTML[variableName] === undefined) {
        defaultInnerHTML[variableName] = [];
        defaultInnerHTML[variableName].push({ id: id, text: text });
    } else {
        defaultInnerHTML[variableName].push({ id: id, text: text });
    }

}

function replaceAllVariableUsages() {
    Object.keys(defaultInnerHTML).forEach(key => {
        for (let index = 0; index < defaultInnerHTML[key].length; index++) {

            let obj = defaultInnerHTML[key][index];
            let newText = obj.text.replace("{{" + key + "}}", forthyear[key]);
            document.getElementById(obj.id).innerHTML = newText;

        }
    });
}
function addCpShowIdBinding(id, variableName) {
    if (cpShowIdBindings[variableName] === undefined) {
        cpShowIdBindings[variableName] = [];
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


function addCpHideIdBinding(id, variableName) {
    if (cpHideIdBindings[variableName] === undefined) {
        cpHideIdBindings[variableName] = [];
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

            
            let rep = document.createElement('div');
            rep.id = cond.id;
            rep.hidden = true;

            
            doc.after(rep);

            
            let cloneDoc = doc.cloneNode(true);

            
            deletedNodes.push(cloneDoc);
            doc.remove();
        }

    }
}

function restoreDeleteIfNodes() {

    

    for (let index = 0; index < deletedNodes.length; index++) {

        let delNode = deletedNodes[index];

        
        let rep = document.getElementById(delNode.id);
        rep.after(delNode);
        rep.remove();


    }
    deletedNodes = [];
}