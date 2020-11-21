import {Ajax} from "/js/modules/Ajax.js";
import {loadHTML} from "/js/modules/jumper/Linker.js";

console.log("execute index init script");

let ajax = new Ajax();

init();

window.PageCreateBackup = createBackup;

function init() {
    ajax.setMethod("POST")
        .setUrl("/administrator/backup/create")
        .setOnError(onError)
        .setOnSucceed(onSucceed);
}

function createBackup() {
    console.log("backup");
    ajax.execute();
}

function onError(code) {
    console.log(code);
}

function onSucceed(response) {
    loadHTML(response);
}