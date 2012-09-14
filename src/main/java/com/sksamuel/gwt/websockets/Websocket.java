package com.sksamuel.gwt.websockets;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephen K Samuel samspade79@gmail.com 14 Sep 2012 08:58:55
 *
 */
public class Websocket {

	private static int	counter	= 1;

	private static native boolean _isWebsocket() /*-{
									return ("WebSocket" in window);
									}-*/;

	public static boolean isSupported() {
		return _isWebsocket();
	}

	private final Set<WebsocketListener>	listeners	= new HashSet();

	private final String				varName;
	private final String				url;

	public Websocket(String url) {
		this.url = url;
		this.varName = "gwtws-" + counter++;
	}

	public native void _close(String s) /*-{
								$wnd.s.close();
								}-*/;

	private native void _open(Websocket ws, String s, String url) /*-{
												$wnd.s = new WebSocket(url);								
												$wnd.s.onopen = function() { ws.@com.sksamuel.gwt.websockets.Websocket::onOpen()(); };
												$wnd.s.onclose = function() { ws.@com.sksamuel.gwt.websockets.Websocket::onClose()(); };
												$wnd.s.onmessage = function(msg) { ws.@com.sksamuel.gwt.websockets.Websocket::onMessage(Ljava/lang/String;)(msg.data); }
												}-*/;

	public native void _send(String s, String msg) /*-{
										$wnd.s.send(msg);
										}-*/;

	private native int _state(String s) /*-{
								return $wnd.s.readyState;
								}-*/;

	public void addListener(WebsocketListener listener) {
		listeners.add(listener);
	}

	public void close() {
		_close(varName);
	}

	public int getState() {
		return _state(varName);
	}

	protected void onClose() {
		for (WebsocketListener listener : listeners)
			listener.onClose();
	}

	protected void onMessage(String msg) {
		for (WebsocketListener listener : listeners)
			listener.onMessage(msg);
	}

	protected void onOpen() {
		for (WebsocketListener listener : listeners)
			listener.onOpen();
	}

	public void open() {
		_open(this, varName, url);
	}

	public void send(String msg) {
		_send(varName, msg);
	}
}