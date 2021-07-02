var globalObjectString = "forthyear";

function parse(expressionString) {


    let type = typeof expressionString;

    if (type != "string") {
        return `${expressionString}`;
    }

    let exprArr = expressionString.split(" ");


    let finalString = "";
    for (let index = 0; index < exprArr.length; index++) {


        let expr = exprArr[index];
        if (isLetter(expr.charAt(0))) {


            let striped = expr;

            if (isFunction(expr)) {
                return parseAsFunction(expr);
            }

            if (isConcatObj(expr)) {
                striped = expr.substr(0, striped.indexOf("."));
            }

            if (forthyear[striped] != undefined) {
                let newExpr = globalObjectString + "." + expr;
                expr = newExpr;
            }
        }
        finalString += expr;
    }


    return finalString;
}

function parseAsFunction(expr) {


    let part1 = expr.substr(0, expr.indexOf("("));


    let len = expr.lastIndexOf(")") - 1 - expr.indexOf("(");
    let part2 = expr.substr(expr.indexOf("(") + 1, len);


    let parsedPart1 = parse(part1);

    let part2Seperated = part2.split(",");
    for (let index = 0; index < part2Seperated.length; index++) {

        part2Seperated[index] = parse(part2Seperated[index]);
    }

    let parsedPart2 = part2Seperated.join(',');

    let finalExpr = parse(part1) + "(" + parsedPart2 + ")";

    return finalExpr;

}

function isFunction(expr) {
    return expr.indexOf("(") != -1;
}

function isLetter(char) {
    return char.toUpperCase() != char.toLowerCase();
}

function isConcatObj(expr) {
    return expr.indexOf(".") != -1
}

function assigneInitalVariables(id, variableName) {
    document.getElementById(id).value = forthyear[variableName];
}

function changeValue(id, variableName) {
    forthyear[variableName] = document.getElementById(id).value;
}


function bindCpModel(id, variableName) {


    inits.push(() => {
        assigneInitalVariables(id, variableName);
    });


    document.getElementById(id).addEventListener("input", (event) => {

        changes.push(() => {
            changeValue(id, variableName);
        });

    });
}

function bindVariableUsageWithVariable(id, variableName, formatters = []) {


    let text = document.getElementById(id).innerHTML;

    if (defaultInnerHTML[variableName] === undefined) {
        defaultInnerHTML[variableName] = [];
        defaultInnerHTML[variableName].push({id: id, text: text, formatter: formatters});
    } else {
        defaultInnerHTML[variableName].push({id: id, text: text, formatter: formatters});
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

function bindCpShow(id, expression) {
    if (cpShowIdBindings[expression] === undefined) {
        cpShowIdBindings[expression] = [];
        cpShowIdBindings[expression].push(id);
    } else {
        cpShowIdBindings[expression].push(id);
    }
}


function renderCpShowConditionally() {
    Object.keys(cpShowIdBindings).forEach(expression => {
        for (let index = 0; index < cpShowIdBindings[expression].length; index++) {

            let id = cpShowIdBindings[expression][index];
            if (eval(parse(expression)) == true) {
                document.getElementById(id).hidden = false;
            } else {
                document.getElementById(id).hidden = true;
            }

        }
    });
}

function bindCpHide(id, expression) {
    if (cpHideIdBindings[expression] === undefined) {
        cpHideIdBindings[expression] = [];
        cpHideIdBindings[expression].push(id);
    } else {
        cpHideIdBindings[expression].push(id);
    }
}

function renderCpHideConditionally() {
    Object.keys(cpHideIdBindings).forEach(expression => {
        for (let index = 0; index < cpHideIdBindings[expression].length; index++) {

            let id = cpHideIdBindings[expression][index];
            if (eval(parse(expression)) == true) {
                document.getElementById(id).hidden = true;
            } else {
                document.getElementById(id).hidden = false;
            }

        }
    });
}

function bindCpEvent(id, eventName, cb) {
    console.log(id);
    console.log(eventName);
    document.getElementById(id).addEventListener(eventName, () => {
        cb();
    })
}

function bindCpIf(id, expression) {
    cpIfConditionalBindings.push({
        id: id,
        expression: expression,
    });
}

function renderCpIf() {
    for (let index = 0; index < cpIfConditionalBindings.length; index++) {

        let cond = cpIfConditionalBindings[index];

        let doc = document.getElementById(cond.id);
        if (eval(parse(cond.expression)) == false) {

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

function bindCpSwitch(expression, childIdsWithCases) {
    cpSwitchBindings.push({
        expression: expression,
        childrenBindings: childIdsWithCases
    });
}

function renderCpSwitchBindinds() {
    for (let index = 0; index < cpSwitchBindings.length; index++) {

        let binding = cpSwitchBindings[index];

        let parentExpr = eval(parse(binding.expression));
        let children = binding.childrenBindings;

        let matched = false;
        for (let index1 = 0; index1 < children.length; index1++) {

            let childId = children[index1].childId;
            let caseExpr = eval(parse(children[index1].case));

            let childDoc = document.getElementById(childId);

            if ((parentExpr == caseExpr) && !matched) {
                childDoc.hidden = false;
                matched = true;
            } else {
                childDoc.hidden = true;
            }


        }


        let lastChild = children[children.length - 1];
        if (!matched && (lastChild.case == null)) {
            document.getElementById(lastChild.childId).hidden = false;
        }

    }
}