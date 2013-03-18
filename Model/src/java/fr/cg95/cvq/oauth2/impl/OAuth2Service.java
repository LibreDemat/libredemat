package fr.cg95.cvq.oauth2.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import fr.cg95.cvq.business.users.Adult;
import fr.cg95.cvq.business.users.HomeFolder;
import fr.cg95.cvq.business.users.UserState;
import fr.cg95.cvq.dao.users.IAdultDAO;
import fr.cg95.cvq.dao.authority.IAgentDAO;
import fr.cg95.cvq.business.authority.Agent;
import fr.cg95.cvq.exception.CvqAuthenticationFailedException;
import fr.cg95.cvq.exception.CvqDisabledAccountException;
import fr.cg95.cvq.exception.CvqModelException;
import fr.cg95.cvq.exception.CvqUnknownUserException;
import fr.cg95.cvq.oauth2.IOAuth2Service;
import fr.cg95.cvq.oauth2.InvalidTokenException;
import fr.cg95.cvq.oauth2.OAuth2ConfigurationBean;
import fr.cg95.cvq.oauth2.OAuth2Exception;
import fr.cg95.cvq.oauth2.model.AccessToken;
import fr.cg95.cvq.oauth2.model.Token;
import fr.cg95.cvq.security.SecurityContext;
import fr.cg95.cvq.util.JSONUtils;
import fr.cg95.cvq.util.web.WS;
import fr.cg95.cvq.util.web.WS.HttpResponse;

public class OAuth2Service implements IOAuth2Service {

    private static final Logger logger = Logger.getLogger(OAuth2Service.class);
    private IAdultDAO adultDAO;
    private IAgentDAO agentDAO;

    @Override
    public Adult authenticate(String token) throws CvqModelException,
        CvqUnknownUserException, CvqAuthenticationFailedException, CvqDisabledAccountException {
        AccessToken accessToken;
        try {
           accessToken = valide(token);
        } catch (InvalidTokenException e) {
            logger.info("Authentication error : invalid token.", e);
            throw new CvqAuthenticationFailedException("Authentication error : invalid token.");
        }

        Adult adult = adultDAO.findByLogin(accessToken.getResourceOwnerName());
        if (adult == null)
            throw new CvqUnknownUserException();
        HomeFolder homeFolder = adult.getHomeFolder();
        if (homeFolder == null)
            throw new CvqModelException("authenticate() : no home folder bound to individual " + adult.getId());
        if (homeFolder.getState().equals(UserState.ARCHIVED)) {
            logger.warn("authenticate() user belongs to an archived account");
            throw new CvqUnknownUserException();
        }
        if (!homeFolder.getEnabled()) {
            logger.warn("authenticate() user belongs to a disabled account");
            throw new CvqDisabledAccountException();
        }
        if (UserState.ARCHIVED.equals(adult.getState())) {
            logger.warn("authenticate() user is archived");
            throw new CvqUnknownUserException();
        }
        return adult;
    }

    public Agent authenticateAgent(String token) throws CvqModelException,
        CvqUnknownUserException, CvqAuthenticationFailedException, CvqDisabledAccountException {
        AccessToken accessToken;
        try {
            accessToken = valide(token);
        } catch (InvalidTokenException e) {
            logger.info("Authentication error : invalid token.", e);
            throw new CvqAuthenticationFailedException("Authentication error : invalid token.");
        }
        Agent agent = agentDAO.findByLogin(accessToken.getResourceOwnerName());

        return agent;
    }

    @Override
    public AccessToken valide(String token) throws InvalidTokenException {
        try {
            OAuth2ConfigurationBean oauth2Config = getOAuth2Configuration();
            String jsonToken = new String(verify(Base64.decodeBase64(token.getBytes("UTF-8"))));
            JsonObject json = JSONUtils.deserialize(jsonToken);
            AccessToken accessToken = new AccessToken(
                json.get("server_name").getAsString(),
                json.get("owner_name").getAsString(),
                json.get("scope").getAsString(),
                json.get("expiration").getAsLong());
            if (!accessToken.isValid(oauth2Config.getResourceServerName())) {
                throw new InvalidTokenException();
            }
            return accessToken;
        } catch (Exception e) {
            logger.info("Invalid access token.", e);
            throw new InvalidTokenException();
        }
    }

    private byte[] verify(byte[] token)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, OAuth2Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, getOAuth2Configuration().getPublicKey());
        return cipher.doFinal(token);
    }

    @Override
    public String authorizationRequestUri(String state) throws OAuth2Exception {
      return authorizationRequestUri(state, false);
    }

    public String authorizationRequestUri(String state, boolean agent) throws OAuth2Exception {
        String redirectUri = null;
        String stateParam = null;
        OAuth2ConfigurationBean oauth2Config = getOAuth2Configuration();
        try {
            redirectUri = URLEncoder.encode(oauth2Config.getRedirectionUri(), "UTF-8");
            stateParam = (state != null) ? URLEncoder.encode(state, "UTF-8") : "";
        } catch (UnsupportedEncodingException e) {
            logger.error("Encoding redirectUri error.", e);
            return null;
        }
        return oauth2Config.getAuthorizationUri() +
                "?response_type=code&client_id=" + oauth2Config.getClientId() +
                "&redirect_uri=" + redirectUri +
                "&scope=" + (agent ? oauth2Config.getAgentScope() : oauth2Config.getIdentificationScope() ) +
                "&state=" + stateParam +
                "&resource_server=" + oauth2Config.getResourceServerName();
    }

    @Override
    public Token authorizationCodeGrant(String code) throws OAuth2Exception {
        String auth = null;
        OAuth2ConfigurationBean oauth2Config = getOAuth2Configuration();
        try {
            auth = new String(Base64.encodeBase64(
                    (oauth2Config.getClientId() + ":" + oauth2Config.getPassword())
                    .getBytes("UTF-8")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Encoding authentification error.", e);
            return null;
        }

        HttpResponse response = WS.url(oauth2Config.getTokenUri())
            .setHeader("Content-Type", "application/x-www-form-urlencoded")
            .setHeader("Authorization", "Basic " + auth)
            .body("grant_type=authorization_code&code="+ code +
                  "&redirect_uri=" + oauth2Config.getRedirectionUri() +
                  "&resource_server=" + oauth2Config.getResourceServerName())
            .post();

        if (logger.isDebugEnabled()) {
            logger.debug("Token response status : " + response.getStatus());
        }

        if (response.getStatus() == 200) {
            JsonObject json = JSONUtils.deserialize(response.getString());
            return new Token(
                    json.get("access_token").getAsString(),
                    json.get("token_type").getAsString(),
                    json.get("expires_in").getAsInt(),
                    json.get("scope").getAsString(),
                    json.get("refresh_token").getAsString());
        }
        return null;
    }

    @Override
    public OAuth2ConfigurationBean getOAuth2Configuration() throws OAuth2Exception {
        OAuth2ConfigurationBean config = SecurityContext.getCurrentConfigurationBean()
                .getOauth2ConfigurationBean();
        if (config == null) {
            throw new OAuth2Exception(500, "invalid_configuration", "");
        }
        return config;
    }

    public void setAdultDAO(IAdultDAO adultDAO) {
        this.adultDAO = adultDAO;
    }

    public void setAgentDAO(IAgentDAO agentDAO) {
        this.agentDAO = agentDAO;
    }

}
