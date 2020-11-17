import {Ajax} from "/js/modules/Ajax.js";
import {redirect} from "/js/modules/jumper/Linker.js";

console.log("add location js init");

let ajax = new Ajax();

init();

window.PageUpdateLocation = updateRequest;

function init() {
    ajax.setMethod("POST")
        .setUrl("/moderator/image/update")
        .setOnError(onError)
        .setOnSucceed(onSucceed);
}

function updateRequest() {
    let type = document.querySelector("meta[name=locationType]").content;
    let name = document.querySelector("meta[name=oldName").content;
    let parentName = document.getElementById("parentName").value;
    let newName = document.getElementById("newName").value;
    let description = document.getElementById("description").value;;
    let image = document.getElementById("image").files[0];

    let formData = new FormData();

    formData.append("type", type);
    formData.append("name", name);
    formData.append("parentName", parentName);
    formData.append("newName", newName);
    formData.append("description", description);
    
    if (image != undefined) {
        formData.append("image", image);
    } else {
        formData.append("image", new Blob());
    }
    ajax.setMessage(formData)
        .execute();
}

function onSucceed(response) {
    if (response.startsWith("redirect")) {
        redirect(response.split(":")[1]);
    } else {
        //парсим на json и обрабатываем ошибки
    }
}

function onError(code) {
    console.log(code);
}