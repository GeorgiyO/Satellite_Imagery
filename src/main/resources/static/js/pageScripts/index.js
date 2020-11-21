import {Ajax} from "/js/modules/Ajax.js";
import {setUpLinks} from "/js/modules/jumper/Linker.js";

console.log("execute index init script");

let ajax = new Ajax();
let countriesSection;

init();

window.PageOnLoadIndex = countryListRequest;
countryListRequest();

function init() {
    ajax.setMethod("GET")
        .setOnError(onError)
        .addHeader({
            name: "Content-Type",
            value: "application/x-www-form-urlencoded"
        })
        .setUrl("/list/Country/*")
        .setOnSucceed((response) => {
            setCountryList(JSON.parse(response));
        });
}

function countryListRequest() {
    if (document.getElementById("countries") != null)
    ajax.execute();
}

function setCountryList(countries) {
    countriesSection = document.getElementById("countries");
    countries.forEach(pushCountry);
    setUpLinks(document.querySelectorAll("#content .ajaxLink"));
}

function pushCountry(country) {
    let a = document.createElement("a");
    a.setAttribute("href", "/photo/Country/" + country.name);
    a.classList.add("ajaxLink");
    a.innerText = country.name;
    countriesSection.appendChild(a);
    countriesSection.appendChild(document.createElement("br"));
}

function onError(code) {
    console.log("error: " + code);
}