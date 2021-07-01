
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