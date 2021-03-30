import {Ajax} from "/js/modules/Ajax.js";
import {loadHTML} from "/js/modules/jumper/Linker.js";
import {setUpLinks} from "/js/modules/jumper/Linker.js";
import * as ladder from "/js/modules/ladder.js";
import {getErrorPage} from "/js/modules/errorPage.js";

console.log("execute location template init script");

let ajax = new Ajax();
let locationSection;

let type;
let name;

let parentHrefDiv;
let location;
let childrenDiv;

init();

window.PageOnLoadLocationTemplate = locationRequest;
locationRequest();

function init() {
    ajax.setMethod("GET")
        .setOnError(onError)
        .addHeader({
            name: "Content-Type",
            value: "application/x-www-form-urlencoded"
        });
}

function locationRequest() {

    type = document.querySelector("meta[name=type]").content.toLowerCase();
    name = document.querySelector("meta[name=name]").content.toLowerCase();

    ajax.setUrl("/location/" + type + "/" + name)
        .setOnSucceed((response) => {
            setLocation(JSON.parse(response));
        })
        .execute();
}

function setLocation(_location) {
    
    location = _location;
    locationSection = document.getElementById("location");
    childrenDiv = document.createElement("div");

    console.log(location);

    appendParent();
    appendHeader();
    appendPhoto();
    appendChildHrefs();
    appendAddChildLink();
}

function appendParent() {
    let parentType = ladder.getParentType(type);

    parentHrefDiv = document.createElement("div");
    parentHrefDiv.id = "parentHref";

    let a = document.createElement("a");
    a.classList.add("ajaxLink");

    let button = document.createElement("button");
    button.classList.add("fill");

    if (parentType != null) {
        a.setAttribute("href", "/photo/" + parentType + "/" + location[parentType].name);
        button.innerText = location[parentType].name;
    } else {
        a.setAttribute("href", "/");
        button.innerText = "Вернуться на главную";
    }

    a.appendChild(button);

    locationSection.appendChild(parentHrefDiv);
    parentHrefDiv.appendChild(a);
    
    setUpLinks(document.querySelectorAll("#parentHref .ajaxLink"));
}

function appendHeader() {
    let p = document.createElement("p");
    p.innerText = location.description;
    locationSection.appendChild(p);
}

function appendPhoto() {
    let img = document.createElement("img");
    img.src = "/image/" + location.locationId;
    locationSection.appendChild(img);
}

function appendChildHrefs() {
    let childType = ladder.getChildType(type);

    console.log(childType);

    if (childType != null) {

        childrenDiv = document.createElement("div");
        childrenDiv.id = "childrenHref";
        locationSection.appendChild(childrenDiv);

        ajax.setUrl("/list/" + childType + "/" + location.name)
            .setOnSucceed((res) => {pushChildList(JSON.parse(res))})
            .execute();
    }
}

function pushChildList(children) {
    if (children.length != 0) {
        let h3 = document.createElement("h3");
        h3.innerText = "Подобласти:";
        childrenDiv.appendChild(h3);

        children.forEach(pushChild);
        setUpLinks(document.querySelectorAll("#childrenHref .ajaxLink"));
    }
}

function pushChild(child) {
    let a = document.createElement("a");
    a.setAttribute("href", "/photo/" + ladder.getChildType(type) + "/" + child.name);
    a.classList.add("ajaxLink");
    a.innerText = child.name;
    childrenDiv.appendChild(a);
}

function appendAddChildLink() {
    let editLinksDiv = document.getElementById("editLinks");
    let childType = ladder.getChildType(type);

    if (editLinksDiv != null && childType != null) {
        let a = document.createElement("a");
        a.setAttribute("href", "/moderator/image/add/" + childType + "/" + location.name);
        a.classList.add("ajaxLink");

        let button = document.createElement("button");
        button.classList.add("fill");
        button.innerText = "Добавить подобласть";

        a.appendChild(button);

        editLinksDiv.appendChild(a);
        setUpLinks([a]);
    }
}

function onError(code) {
    if (code == 500) {
        loadHTML(getErrorPage(code + ": Страница не найдена"));
    }
}