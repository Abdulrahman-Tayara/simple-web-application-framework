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

            if (isIndexed(expr)) {
                striped = expr.substr(0, striped.indexOf('['));
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
    return expr.indexOf(".") != -1;
}

function isIndexed(expr) {
    return expr.indexOf("[") != -1;
}

function assigneInitalVariables(id, variableName) {
    document.getElementById(id).value = eval(parse(variableName));
}

function changeValue(id, variableName) {
    eval(`${parse(variableName)} = document.getElementById(${id}).value`);
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


function bindVariableUsage(id, rawCurly, expression) {


    let text = document.getElementById(id).innerHTML;

    if (defaultInnerHTML[rawCurly] === undefined) {
        defaultInnerHTML[rawCurly] = [];
        defaultInnerHTML[rawCurly].push({id: id, text: text, expression: expression});
    } else {
        defaultInnerHTML[rawCurly].push({id: id, text: text, expression: expression});
    }
}

function renderVariableUsages() {

    Object.keys(defaultInnerHTML).forEach(rawCurly => {
        for (let index = 0; index < defaultInnerHTML[rawCurly].length; index++) {

            let obj = defaultInnerHTML[rawCurly][index];

            let newText = obj.text.replace(rawCurly, eval(parse(obj.expression)));

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


function bindCpEvent(id, eventName, cb, params = []) {


    document.getElementById(id).addEventListener(eventName, () => {

        let cbString = cb;

        cbString += "(" + params.join(",") + ")";
        eval(parse(cbString));
    });

}


function bindCpFor(id, expression, counter1, counter2 = null) {

    forRenders.push(() => {
        let element = document.getElementById(id);

        let evaluatedExpression = eval(parse(expression));

        for (let index in evaluatedExpression) {
            let clone = element.cloneNode(true);
            clone.hidden = false;
            clone.id = clone.id + "-" + index;
            element.insertAdjacentElement('beforeBegin', clone);
            repeatedNodes.push(clone);
        }
        element.hidden = true;
    })
}

function removeRepeatedNodes() {
    for (let index = 0; index < repeatedNodes.length; index++) {
        repeatedNodes[index].remove();
    }
    repeatedNodes = [];
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
        if (!eval(parse(cond.expression))) {


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

function renderCpSwitch() {
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
        if (!matched && (eval(parse(lastChild.case)) == null)) {
            document.getElementById(lastChild.childId).hidden = false;
        }

    }
}