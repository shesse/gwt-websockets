gwt-websockets
==============

A simple GWT wrapper for javascript websockets which enable you to avoid writing native javascript code. This is not a fully featured client/server framework for websockets. Instead it is a simple wrapper for client side code when you need to connect to an existing websocket server. For example, this library was built because I needed to connect a mobile GWT webapp to a Java websocket system that used Apache Camel.

This library has no dependancy outside of the standard GWT distribution.

To use add this dependancy to your project (hosted on maven central):

    <dependency>
        <groupId>com.sksamuel.gwt</groupId>
        <artifactId>gwt-websockets</artifactId>
        <version>1.0.2</version>
    </dependency>

Then update your .gwt.xml files to include this:

    <inherits name="com.sksamuel.gwt.GwtWebsockets" />

Getting Started
===============

To create a websocket in your code, use 

`Websocket socket = new Websocket("ws://hostname:port/path");`

Then attach one or more listeners which are used for the callbacks:

    socket.addListener(new WebsocketListener() {

        @Override
        public void onClose() {
    	    // do something on close
        }

        @Override
        public void onMessage(String msg) {
	        // a message is received
        }

        @Override
        public void onOpen() {
	        // do something on open
        }
    });
    
When the socket is successfully connected, the onOpen() callback will be invoked on each of your listeners. Similarly, for onClose() and onMessage(String). Note: You can add as many listeners as you want, and you can remove/add while the socket is open.

To open a websocket use `socket.open();`

To close a websocket `socket.close();`

To send a message to the websocket server `socket.send("My message");`

Sometimes you might want to check if websockets are available on your target system. For this you can use the static method `Websocket.isSupported()` which returns true if websockets are available.

You can create as many websockets as you want on a single page. The library takes care of assigning each one to a different javascript global.

Finally, you can get the status of the socket using the method `socket.getState()` which returns an integer for the current state of the socket, where `CONNECTING = 0, OPEN = 1, CLOSED = 2`.

Have fun.

Binary support
===============

Version 1.0.1 adds support for byte methods for sending and receiving. To send, simply use send(byte[]) method on Websocket. To receive, you must implement BinaryWebsocketListener instead of WebsocketListener and then you have a new callback method onMessage(byte[]).

Because binary support on websockets isn't yet supported on all browsers, this modules sends binary data using base64.
