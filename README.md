## Simple One-On-One Chat with WebSocket Application

### Overview
Modern web applications require more interactivity than ever before for client/server communications. HTTP, however, 
wasn't built to deliver the kind of interactivity needed today. "Push" or Comet techniques, such as long-polling, 
emerged as a way to allow a server to push data to a browser. Because these techniques usually rely on HTTP, 
they present some disadvantages for client/server communications, such as HTTP overhead. These disadvantages result in 
less efficient communication between the server and the web browser, especially for real-time applications.

WebSocket provides an alternative to this limitation by providing bi-directional, full-duplex, real-time, 
client/server communications. The server can send data to the client at any time. Because WebSocket runs over TCP, 
it also provides a low-latency, low-level communication and reduces the overhead of each message. 
WebSocket also provides greater scalability for message-intensive applications because only one connection per client 
is used (whereas HTTP creates one request per message). 

The life cycle of a WebSocket is easy to understand as well:

* Client sends the Server a handshake request in the form of a HTTP upgrade header with data about the WebSocket 
it’s attempting to connect to.
* The Server responds to the request with another HTTP header, this is the last time a HTTP header gets used in 
the WebSocket connection. If the handshake was successful, they server sends a HTTP header telling the client 
it’s switching to the WebSocket protocol.
* Now a constant connection is opened and the client and server can send any number of messages to each other 
until the connection is closed. These messages only have about 2 bytes of overhead.

### Build and Run :

* Run application :

    	mvn clean tomcat7:run

* Call URL :

		http://localhost:8080/chat-websocket-application/

### References
* [Java EE7 : Building Web Applications with WebSocket, JavaScript and HTML5]
(http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/HomeWebsocket/WebsocketHome.html)
* [WebSockets – A Quick Introduction and a Sample Application] 
(https://blog.idrsolutions.com/2013/12/websockets-an-introduction/)