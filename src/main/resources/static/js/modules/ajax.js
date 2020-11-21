export class Ajax {
    method;
    url;
    onError;
    onSucceed;
    
    async = true;
    message = "";

    headers = [];

    constructor() {
        let token = document.querySelector("meta[name='_csrf']").content;
        let header = document.querySelector("meta[name='_csrf_header']").content;
        this.addHeader({
            name: header,
            value: token
        })
    }

    setMethod(method) {
        this.method = method;
        return this;
    }
    setUrl(url) {
        this.url = url;
        return this;
    }
    setOnError(onError) {
        this.onError = onError;
        return this;
    }
    setOnSucceed(onSucceed) {
        this.onSucceed = onSucceed;
        return this;
    }

    
    setAsync(async) {
        this.async = async;
        return this;
    }
    setMessage(message) {
        this.message = message;
        return this;
    }

    addHeader(header) {
        this.headers.push(header);
        return this;
    }

    execute() {
        let xhr = new XMLHttpRequest();
        xhr.open(this.method, this.url, this.async);
        this.headers.forEach((h) => {
            xhr.setRequestHeader(h.name, h.value);
        });
        xhr.onreadystatechange = () => {
            if (xhr.readyState != 4) 
                return;
            if (xhr.status != 200) {
                this.onError(xhr.status);
            }
            else {
                this.onSucceed(xhr.responseText);
            }
        }
        xhr.send(this.message);
    }
}