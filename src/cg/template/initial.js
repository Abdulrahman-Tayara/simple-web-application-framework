let inits = [];
let changes = [];
let forRenders = [];

// let defaultTexts = {};
let defaultInnerHTML = {};
let cpShowIdBindings = {};
let cpHideIdBindings = {};
let cpIfConditionalBindings = [];
let cpSwitchBindings = []; //[ {id, var, childrenBindings: [{childId, case}]} ]

let deletedNodes = []; //list of ids
let repeatedNodes = []; //list of nodes