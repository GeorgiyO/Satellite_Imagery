import {Ajax} from "/js/modules/Ajax.js";
import {redirect, setUpLinks} from "/js/modules/jumper/Linker.js";
import {getParentType} from "/js/modules/ladder.js";

console.log("add location js init");

let type = () => {return document.querySelector("meta[name=locationType]").content};
let name = () => {return document.querySelector("meta[name=oldName]").content};
let parentName = () => {return document.getElementById("parentName").value};
let newName = () => {return document.getElementById("newName").value};
let description = () => {return document.getElementById("description").value};
let image = () => {return document.getElementById("image").files[0]};

let ajax = new Ajax();

init();

window.PageUpdateLocation = updateRequest;
window.PageOnLoadUpdateLocation = () => {
    setHeader();
    addBackButton();
}

function init() {
    ajax.setMethod("POST")
        .setUrl("/moderator/image/update")
        .setOnError(onError)
        .setOnSucceed(onSucceed);
    setHeader();
    addBackButton();
}

function updateRequest() {

    let formData = new FormData();

    formData.append("type", type());
    formData.append("name", name());
    formData.append("parentName", parentName());
    formData.append("newName", newName());
    formData.append("description", description());

    let _image = image();
    
    if (_image != undefined) {
        formData.append("image", _image);
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
        document.getElementById("parentName-error").innerText = "Локации типа " + getParentType(type()) + " с таким именем не существует"
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


function setHeader() {
    let headerText = "Редактировать локацию типа " + type() + " с именем " + name();
    document.getElementById("form-header").innerText = headerText;
}

function addBackButton() {
    let a = document.createElement("a");
    let _type = type();
    a.classList.add("ajaxLink");
    setUpLinks([a]);
    a.href = _type == null ? 
            "/" :
            "/photo/" + _type + "/" + name();

    let button = document.createElement("button");
    button.classList.add("fill");
    button.innerText = "Отмена";
    a.appendChild(button);
    
    document.getElementsByClassName("form")[0].appendChild(a);
}


