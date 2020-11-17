import {Ajax} from "/js/modules/Ajax.js";
import {redirect} from "/js/modules/jumper/Linker.js";

console.log("add location js init");

let ajax = new Ajax();

init();

window.PageAddLocation = addLocation;

function init() {
    ajax.setMethod("POST")
        .setUrl("/moderator/image/add")
        .setOnError(onError)
        .setOnSucceed(onSucceed);
}

function addLocation() {
    let type = document.querySelector("meta[name=locationType]").content;
    let parentName = document.querySelector("meta[name=parentName").content;
    let name = document.getElementById("name").value;
    let description = document.getElementById("description").value;;
    let image = document.getElementById("image").files[0];

    let formData = new FormData();

    formData.append("type", type)
    formData.append("parentName", parentName)
    formData.append("name", name)
    formData.append("description", description)
    formData.append("image", image)

    ajax.setMessage(formData)
        .execute();
}

function onSucceed(response) {
    if (response.startsWith("redirect")) {
        redirect(response.split(":")[1]);
    }
}

function onError(code) {
    console.log(code);
}