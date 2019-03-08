/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.web.session.entity;

import org.iharu.type.BaseAuthorizationType;

/**
 *
 * @author iHaru
 */
public class SessionEntity {

    private long id;
    private String uid;
    private String alias;
    private String voucher; //一次性使用
    private String token;   //一段时间内无限使用
    private BaseAuthorizationType basic_auth_type;
    private long valid_timestamp;
    private boolean oauth;

    
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @return the voucher
     */
    public String getVoucher() {
        return voucher;
    }

    /**
     * @param voucher the voucher to set
     */
    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the basic_auth_type
     */
    public BaseAuthorizationType getBasic_auth_type() {
        return basic_auth_type;
    }

    /**
     * @param basic_auth_type the basic_auth_type to set
     */
    public void setBasic_auth_type(BaseAuthorizationType basic_auth_type) {
        this.basic_auth_type = basic_auth_type;
    }

    /**
     * @return the valid_timestamp
     */
    public long getValid_timestamp() {
        return valid_timestamp;
    }

    /**
     * @param valid_timestamp the valid_timestamp to set
     */
    public void setValid_timestamp(long valid_timestamp) {
        this.valid_timestamp = valid_timestamp;
    }

    /**
     * @return the oauth
     */
    public boolean isOauth() {
        return oauth;
    }

    /**
     * @param oauth the oauth to set
     */
    public void setOauth(boolean oauth) {
        this.oauth = oauth;
    }

}
