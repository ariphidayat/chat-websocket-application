var ws;
var log;
var msg;

function connect() {
    ws = new WebSocket("ws://localhost:8080/chat-websocket-application/chat");


    ws.onmessage = function(event) {
    log = document.getElementById("log");
        console.log(event.data);
        log.innerHTML += event.data + "\n";
    };
}

function send() {
    msg = document.getElementById("msg").value;
    ws.send(msg);
}