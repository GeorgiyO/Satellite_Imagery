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

    Array.prototype.forEach.call(document.getElementsByClassName("error"), (errorField) => {
        errorField.innerText = "";
    });

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
    } else if (response == "imageFormatError") {
        document.getElementById("image-error").innerText = "Файл должен быть png формата";
    } else if (response == "typeError") {
        console.log("Заданного типа данных не сщуествует");
    } else if (response == "parentNameError") {
        console.log("Данного родителя не сщуествует");
    } else {
        response = JSON.parse(response);
        console.log(response);
        for (const [key, value] of Object.entries(response)) {
            document.getElementById(key + "-error").innerText = value;
        }
    }
}

function onError(code) {
    if (code == 400) {
        document.getElementById("image-error").innerText = "Файл не выбран";
    }
}