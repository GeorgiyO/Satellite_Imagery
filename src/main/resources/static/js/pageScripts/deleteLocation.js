import {Ajax} from "/js/modules/Ajax.js";
import {redirect} from "/js/modules/jumper/Linker.js";

let ajax = new Ajax();

init();

window.PageDeleteLocation = deleteRequest;

function init() {
    ajax.setMethod("POST")
        .setUrl("/moderator/image/delete")
        .setOnError(onError)
        .setOnSucceed(onSucceed);
}

function deleteRequest() {
    let type = document.querySelector("meta[name=type]").content;
    let name = document.querySelector("meta[name=name").content;

    let formData = new FormData();

    formData.append("type", type);
    formData.append("name", name);

    ajax.setMessage(formData)
        .execute();
}

function onSucceed(response) {
    if (response.startsWith("redirect")) {
        redirect("");
    }
}

function onError(code) {
    console.log(code);
}