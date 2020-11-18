/* 
 * uses window.CurrentPage object, that must init with every page-loading by page-init-script
 */
import {Ajax} from "/js/modules/Ajax.js";
import * as handler from "./Handler.js";
import {getErrorPage} from "/js/modules/errorPage.js";

export {setUpLinks, redirect, loadError}

let content = document.getElementById("content");
handler.setDiv(content);

let ajax = new Ajax();

ajax.setMethod("Get")
    .addHeader({
        name: "Content-Type",
        value: "application/x-www-form-urlencoded"
    })
    .setOnError(onError)

// public:

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

function loadError(errorHTML) {
    if (!handler.isBlocked()) {

        handler.start();
        let onClose = window.CurrentPage.onClose;
        if (onClose != undefined) onClose();
        handler.setData(errorHTML, "/");
    }
}

// private:

function replace(e, href) {
    e.preventDefault();
    load(href);
}

function load(href) {
    if (!handler.isBlocked()) {

        handler.start();
        let onClose = window.CurrentPage.onClose;
        if (onClose != undefined) onClose();

        ajax.setUrl(href + "/ajax")
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
