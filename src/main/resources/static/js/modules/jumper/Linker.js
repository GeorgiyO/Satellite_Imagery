/* 
 * uses window.CurrentPage object, that must init with every page-loading by page-init-script
 */
import {Ajax} from "/js/modules/Ajax.js";
import * as handler from "./Handler.js";
import {getErrorPage} from "/js/modules/errorPage.js";

export {setUpLinks, redirect, loadHTML}

let content = document.getElementById("content");
handler.setDiv(content);

let ajax = new Ajax();

ajax.setMethod("Get")
    .addHeader({
        name: "Content-Type",
        value: "application/x-www-form-urlencoded"
    })
    .setOnError(onError)

function setUpLinks(links) {
    console.log(links);
    Array.prototype.forEach.call(links, (link) => {
        link.onclick = (e) => {
            replace(e, link.href);
        }
    });
}

function redirect(href) {
    load(href);
}

function loadHTML(HTML) {
    if (!handler.isBlocked()) {

        handler.start();
        let onClose = window.CurrentPage.onClose;
        if (onClose != undefined) onClose();
        handler.setData(HTML, "/");
    }
}

function replace(e, href) {
    e.preventDefault();
    load(href);
}

function load(href) {
    if (!handler.isBlocked()) {

        handler.start();
        let onClose = window.CurrentPage.onClose;
        if (onClose != undefined) onClose();

        // костыль для работы моих ажаксных ссылок вместе с секьюрити, иначе не будет пускать на //ajax (для загрузки главной страницы)
        let _href = href == window.location.origin + "/" ?
                    window.location.origin + "/ajax" :
                    href + "/ajax";

        console.log(href + " " + _href);

        ajax.setUrl(_href)
            .setOnSucceed((response) => {
                onSucceed(response, href);
            })
            .execute();
    }
}

function onError(code) {
    handler.setData(getErrorPage(code));
}

function onSucceed(response, href) {
    handler.setData(response, href);
}
