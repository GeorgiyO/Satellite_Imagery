import {Ajax} from "/js/modules/Ajax.js";
import {redirect, setUpLinks} from "/js/modules/jumper/Linker.js";
import {getParentType} from "/js/modules/ladder.js";

console.log("add location js init");

let type = () => {return document.querySelector("meta[name=locationType]").content};
let parentName = () => {return document.querySelector("meta[name=parentName]").content};
let name = () => {return document.getElementById("name").value};
let description = () => {return document.getElementById("description").value};
let image = () => {return document.getElementById("image").files[0]};

let ajax = new Ajax();

init();

window.PageAddLocation = addLocation;
window.PageOnLoadAddLocation = () => {
    setHeader();
    addBackButton();
}

function init() {
    ajax.setMethod("POST")
        .setUrl("/moderator/image/add")
        .setOnError(onError)
        .setOnSucceed(onSucceed);
        
    setHeader();
    addBackButton();
}

function addLocation() {
    Array.prototype.forEach.call(document.getElementsByClassName("error"), (errorField) => {
        errorField.innerText = "";
    });

    let formData = new FormData();

    formData.append("type", type())
    formData.append("parentName", parentName())
    formData.append("name", name())
    formData.append("description", description())
    formData.append("image", image())

    ajax.setMessage(formData)
        .execute();
}

function onSucceed(response) {
    if (response.startsWith("redirect")) {
        redirect(response.split(":")[1]);
    } else if (response == "imageFormatError") {
        document.getElementById("image-error").innerText = "Файл должен быть png формата";
    } else if (response == "typeError") {
        console.log("Заданного типа данных не существует");
    } else if (response == "parentNameError") {
        console.log("Заданного родителя не сщуествует");
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

function setHeader() {
    let headerText = "Добавить новую локацию типа " + type();
    if (parentName() != "*") {
        headerText += " к " + parentName();
    }
    document.getElementById("form-header").innerText = headerText;
}

function addBackButton() {
    let a = document.createElement("a");
    let _type = getParentType(type());
    a.classList.add("ajaxLink");
    setUpLinks([a]);
    a.href = _type == null ? 
            "/" :
            "/photo/" + _type + "/" + parentName();

    let button = document.createElement("button");
    button.classList.add("fill");
    button.innerText = "Отмена";
    a.appendChild(button);
    
    document.getElementsByClassName("form")[0].appendChild(a);
}