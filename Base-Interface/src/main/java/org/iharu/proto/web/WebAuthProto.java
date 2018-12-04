/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.proto.web;

import org.iharu.type.BaseAuthorizationType;

/**
 *
 * @author iTeamVEP
 */
public class WebAuthProto {
    private String voucher;
    private String token;
    private long valid_timestamp;
    private boolean signin;

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
     * @return the signin
     */
    public boolean isSignin() {
        return signin;
    }

    /**
     * @param signin the signin to set
     */
    public void setSignin(boolean signin) {
        this.signin = signin;
    }
}
