var ws, log, msg, username, to;

function connect() {
    username = document.getElementById("username").value;
    ws = new WebSocket("ws://localhost:8080/chat-websocket-application/chat/" + username);


    ws.onmessage = function(event) {
    log = document.getElementById("log");
        console.log(event.data);
        log.innerHTML += event.data + "\n";
    };
}

function send() {
    msg = document.getElementById("msg").value;
    to = document.getElementById("to").value;
    ws.send(to + "~"+ msg);
    log.innerHTML += "Me : " + msg + "\n";
}