package com.praszapps.fingertip.manager;

public class FingertipAuthConfig {
    private AuthenticationStyle mAuthStyle;
    private String errorString;

    public AuthenticationStyle getmAuthStyle() {
        return mAuthStyle;
    }

    public void setAuthenticationStyle(AuthenticationStyle mAuthStyle) {
        this.mAuthStyle = mAuthStyle;
    }
}