import {Ajax} from "/js/modules/Ajax.js";
import {redirect} from "/js/modules/jumper/Linker.js";
import {getParentType} from "/js/modules/ladder.js";

console.log("add location js init");

let ajax = new Ajax();

init();

window.PageUpdateLocation = updateRequest;
window.PageOnLoadUpdateLocation = setHeader();

function init() {
    ajax.setMethod("POST")
        .setUrl("/moderator/image/update")
        .setOnError(onError)
        .setOnSucceed(onSucceed);
    setHeader();
}

function setHeader() {
    let headerText = "Редактировать локацию типа " + document.querySelector("meta[name=locationType]").content + " с именем " + document.querySelector("meta[name=oldName").content;
    document.getElementById("form-header").innerText = headerText;
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
    } else if (response == "imageFormatError") {
        document.getElementById("image-error").innerText = "Файл должен быть png формата";
    } else if (response == "typeError") {
        console.log("Заданного типа данных не сщуествует");
    } else if (response == "uncheckedFieldsError") {
        document.getElementById("parentName-error").innerText = "Локации типа " + getParentType(document.querySelector("meta[name=locationType]").content) + " с таким именем не существует"
    } else {
        response = JSON.parse(response);
        console.log(response);
        for (const [key, value] of Object.entries(response)) {
            document.getElementById(key + "-error").innerText = value;
        }
    }
}

function onError(code) {
    console.log(error);
}