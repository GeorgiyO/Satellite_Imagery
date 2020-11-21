import {Ajax} from "/js/modules/Ajax.js";
import {loadHTML} from "/js/modules/jumper/Linker.js";

console.log("execute index init script");

let ajax = new Ajax();

init();

window.PageRestoreDatabase = restoreDatabase;

function init() {
    ajax.setMethod("POST")
        .setUrl("/administrator/backup/restore")
        .setOnError(onError)
        .setOnSucceed(onSucceed);
}

function restoreDatabase() {
    console.log("restore");
    ajax.execute();
}

function onError(code) {
    console.log(code);
}

function onSucceed(response) {
    loadHTML(response);
}