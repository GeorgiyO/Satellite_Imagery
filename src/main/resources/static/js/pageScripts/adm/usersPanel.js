import {Ajax} from "/js/modules/Ajax.js";

console.log("execute index init script");

let username = () => {return document.getElementById("username").value};

let ajax = new Ajax();

init();

window.PageFindUsers = findUsers;

function init() {
    ajax.setMethod("POST")
        .setOnError(onError)
}

function findUsers() {
    let form = new FormData();
    form.append("username", username());

    ajax.setUrl("/administrator/users/get-list")
        .setMessage(form)
        .setOnSucceed((response) => {
            setUserList(JSON.parse(response));
        })
        .execute();
}

function onError(code) {
    console.log(code);
}

function setUserList(userList) {
    let userListDiv = document.getElementById("userList");
    userListDiv.innerHTML = "";

    userList.forEach((user) => {
        // div: {nameSpan - buttonsSpan: {mkUser - mkModer - delete}}
        let div = document.createElement("div");
        let nameSpan = document.createElement("div");
        let buttonsSpan = document.createElement("div");

        buttonsSpan.classList.add("buttons");

        let makeUserBtn = createFillButton();
        let makeModerBtn = createFillButton();
        let deleteUserBtn = createFillButton();

        makeUserBtn.onclick = () => {setRole(user.id, "ROLE_USER")};
        makeModerBtn.onclick = () => {setRole(user.id, "ROLE_MODERATOR")};
        deleteUserBtn.onclick = () => {deleteUser(user.id)};

        if (user.roles.includes("ROLE_USER")) {
            makeUserBtn.classList.add("active-effect-button");
        } else {
            makeModerBtn.classList.add("active-effect-button");
        }
        deleteUserBtn.classList.add("delete-button");

        makeUserBtn.innerText = "User"; 
        makeModerBtn.innerText = "Moderator"; 
        deleteUserBtn.innerText = "Удалить пользователя";

        buttonsSpan.appendChild(makeUserBtn);
        buttonsSpan.appendChild(makeModerBtn);
        buttonsSpan.appendChild(deleteUserBtn);

        nameSpan.innerText = user.name;

        div.appendChild(nameSpan);
        div.appendChild(buttonsSpan);

        userListDiv.appendChild(div);
    });

    function createFillButton() {
        let button = document.createElement("button");
        button.classList.add("fill");
        return button;
    }
}

function setRole(userId, userRole) {
    let form = new FormData();
    form.append("userId", userId);
    form.append("userRole", userRole);

    ajax.setUrl("/administrator/users/set-role")
        .setMessage(form)
        .setOnSucceed(onSuceedSetRole)
        .execute();
}

function onSuceedSetRole(response) {
    let div = document.getElementById("info");
    div.innerText = "";
    if (response == "succeed") {
        div.innerText = "Роль изменена";
        findUsers();
    } else if (response == "not-exist") {
        div.innerText = "Ошибка выполнения"
    }
}

function deleteUser(userId) {
    let form = new FormData();
    form.append("userId", userId);

    ajax.setUrl("/administrator/users/delete")
        .setMessage(form)
        .setOnSucceed(onSuceedDelete)
        .execute();
}

function onSuceedDelete(response) {
    let div = document.getElementById("info");
    div.innerText = "";
    if (response == "succeed") {
        div.innerText = "Пользователь удален";
        findUsers();
    } else if (response == "not-exist") {
        div.innerText = "Пользователя не существует"
    }
}