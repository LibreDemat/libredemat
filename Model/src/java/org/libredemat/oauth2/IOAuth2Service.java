package org.libredemat.oauth2;

import org.libredemat.business.users.Adult;
import org.libredemat.exception.CvqAuthenticationFailedException;
import org.libredemat.exception.CvqDisabledAccountException;
import org.libredemat.exception.CvqModelException;
import org.libredemat.exception.CvqUnknownUserException;
import org.libredemat.oauth2.model.AccessToken;
import org.libredemat.oauth2.model.Token;

public interface IOAuth2Service {

    Adult authenticate(String token) throws CvqModelException, CvqUnknownUserException,
        CvqAuthenticationFailedException, CvqDisabledAccountException;

    AccessToken valide(String token) throws InvalidTokenException;

    String authorizationRequestUri(String state) throws OAuth2Exception;

    Token authorizationCodeGrant(String code) throws OAuth2Exception;

    OAuth2ConfigurationBean getOAuth2Configuration() throws OAuth2Exception;
}
