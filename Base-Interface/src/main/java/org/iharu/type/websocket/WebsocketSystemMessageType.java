/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.type.websocket;

/**
 *
 * @author iTeamVEP
 */
public enum WebsocketSystemMessageType {
    SYSTEM_INFO(),
    DEBUG_MSG(),
    PAYLOAD_ERROR(),
    CLIENT_REBOOT(),
    CLIENT_SHUTDOWN(),
    SERVER_REBOOT(),
    SERVER_SHUTDOWN(),
    AUTHORIZATION_REQUIRED(),
    AUTHORIZATION_FAIL(),
    AUTHORIZATION_SUCCESS(),
    PING()
    
}
