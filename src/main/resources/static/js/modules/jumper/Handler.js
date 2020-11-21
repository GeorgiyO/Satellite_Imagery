import {setUpLinks} from "/js/modules/jumper/Linker.js";

export {isBlocked, setDiv, setData, start, tryShow};

let DELAY = 300;

let div;
let loadingDiv = document.getElementById("pageLoading");

let data;
let href;
let blocked = false;
let hidden = false;
let ready = false;

function isBlocked() {
    return blocked;
}

function setData(_data, _href) {
    data = _data;
    href = _href;
    ready = true;
    tryShow();
}

function setDiv(_div) {
    div = _div;
}

function start() {
    blocked = true;
    hide();
}

function hide() {
    div.classList.add("hide");
    div.classList.add("fromMiddleToDown");

    setTimeout(() => {
        div.classList.remove("fromMiddleToDown");
        showLoading();
        hidden = true;
        tryShow();
    }, DELAY);
}

function tryShow() {
    if (hidden && ready) {
        show();
        processAjaxTransition(data, href);
    }
}

function show() {
    div.classList.remove("hide");
    hideLoading();

    div.classList.add("fromUpToMiddle");
    setTimeout(()=>{div.classList.remove("fromUpToMiddle");}, DELAY)

    ready = hidden = blocked = false;
}

function showLoading() {
    loadingDiv.style.display = "block";
    loadingDiv.classList.remove("hide");
}

function hideLoading() {
    loadingDiv.classList.add("hide");
    setTimeout(()=>{loadingDiv.style.display="none"}, DELAY);
}

// page carring out:

function processAjaxTransition() {
    content.innerHTML = data;
    executePageScripts();

    let title = document.title;
    window.history.pushState({
        data,
        title 
    }, title, href);

    data = title = null;

    setUpLinks(document.querySelectorAll("#content .ajaxLink"));
}

function executePageScripts() {
    let scripts = document.querySelectorAll("#content script");
    
    Array.prototype.forEach.call(scripts, (script) => {
        let src = script.src;
        if (src != "")
            executeSciptWithSrc(src);
        else
            try {
                eval(script.textContent);
            } catch (e) {
                console.log(e);
            }
    });
}

function executeSciptWithSrc(src) {
    let js = document.createElement("script");
    js.setAttribute("src", src);
    js.setAttribute("type", "module");
    content.appendChild(js);
    content.removeChild(js);
}