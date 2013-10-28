package org.libredemat.oauth2;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

public final class OAuth2ConfigurationBean {

    //Authorization server configuration
    private PublicKey publicKey;
    private String authorizationUri;
    private String tokenUri;
    private String logoutUri;

    // Client configuration
    private String resourceServerName;
    private String clientId;
    private String password;
    private String redirectionUri;
    private String identificationScope;
    private String agentScope;

    // Client style for Oauth2 login page
    private String cssNameFront;
    private String cssNameBack;

    public OAuth2ConfigurationBean(String publicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
       KeyFactory keyFactory = KeyFactory.getInstance("RSA");
       byte [] key = Base64.decodeBase64(publicKey.getBytes("UTF-8"));
       this.publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(key));
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String getAuthorizationUri() {
        return authorizationUri;
    }

    public void setAuthorizationUri(String authorizationUri) {
        this.authorizationUri = authorizationUri;
    }

    public String getTokenUri() {
        return tokenUri;
    }

    public void setTokenUri(String tokenUri) {
        this.tokenUri = tokenUri;
    }

    public String getLogoutUri() {
        return logoutUri;
    }

    public void setLogoutUri(String logoutUri) {
        this.logoutUri = logoutUri;
    }

    public String getResourceServerName() {
        return resourceServerName;
    }

    public void setResourceServerName(String resourceServerName) {
        this.resourceServerName = resourceServerName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRedirectionUri() {
        return redirectionUri;
    }

    public void setRedirectionUri(String redirectionUri) {
        this.redirectionUri = redirectionUri;
    }

    public String getIdentificationScope() {
        return identificationScope;
    }

    public void setIdentificationScope(String identificationScope) {
        this.identificationScope = identificationScope;
    }

    public String getAgentScope() {
        return agentScope;
    }

    public void setAgentScope(String agentScope) {
        this.agentScope = agentScope;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String getCssNameFront() {
        return cssNameFront;
    }

    public void setCssNameFront(String cssNameFront) {
        this.cssNameFront = cssNameFront;
    }

    public String getCssNameBack() {
        return cssNameBack;
    }

    public void setCssNameBack(String cssNameBack) {
        this.cssNameBack = cssNameBack;
    }

}
