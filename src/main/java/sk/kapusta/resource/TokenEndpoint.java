package sk.kapusta.resource;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import sk.kapusta.entity.AccessToken;
import sk.kapusta.entity.OAuthCommon;
import sk.kapusta.entity.User;
import sk.kapusta.security.PasswordHash;
import sk.kapusta.service.i.AccessTokenServiceInt;
import sk.kapusta.service.i.UserServiceInt;

@Controller
@RequestMapping("/token")
public class TokenEndpoint extends BaseEndpoint {
	
	@Autowired(required = true)
	private AccessTokenServiceInt authorizationService;
	
	@Autowired(required = true)
	private UserServiceInt userService;
	
	public TokenEndpoint(UserServiceInt userService, AccessTokenServiceInt authorizationService) {
		super();
		this.userService = userService;
		this.authorizationService = authorizationService;	
	}

	public TokenEndpoint() {

	}
	
    @RequestMapping(method = RequestMethod.POST,
    		value = "",
    		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authorize(HttpServletRequest request) throws OAuthSystemException, SQLException {
        
    	try {
        	
            final OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
            final OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
            final Date currentTimestamp = new Date();
            final long hoursInMillis = 60L * 60L * 1000L;
            final Date expireTimestamp = new Date(currentTimestamp.getTime() + hoursInMillis);
            final AccessToken oAuthInfo = new AccessToken();
            final String installationId = oauthRequest.getParam("installation_id");
            
            String accessToken = oauthIssuerImpl.accessToken();
            String refreshToken = oauthIssuerImpl.refreshToken();
            
            oAuthInfo.setAccessToken(accessToken);
            oAuthInfo.setRefreshToken(refreshToken);
            oAuthInfo.setExpireToken(expireTimestamp);
			oAuthInfo.setInstallationId(installationId);
            
            // check if clientid is valid
            if (!authorizationService.checkClientId(oauthRequest.getClientId())) {
                return buildInvalidClientIdResponse();
            }

            // check if client_secret is valid
            if (!authorizationService.checkClientSecret(oauthRequest.getClientSecret())) {
                return buildInvalidClientSecretResponse();
            }

            // do checking for different grant types
            if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())) {
                
            	if (!authorizationService.checkAuthCode(oauthRequest.getParam(OAuth.OAUTH_CODE))) {
                    return buildBadAuthCodeResponse();
                }
                
                accessToken = PasswordHash.createHash(OAuthCommon.REGISTRATION_CODE);
                refreshToken = "";
                
            } else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.PASSWORD.toString())) {
            	
            	if (!authorizationService.checkUserPass(oauthRequest.getUsername(), oauthRequest.getPassword())) {
            		return buildInvalidUserPassResponse();
                }
            	
            	if(installationId == null){
            		return buildSimpleBadRequestResponse();
            	}
                
                final User user = userService.getUserByLogin(oauthRequest.getUsername());
                final AccessToken tempOAuthInfo = authorizationService.getOAuthInfo(user.getUserId(), installationId);
                
                oAuthInfo.setUserId(user.getUserId());
                
                if(tempOAuthInfo.getAccessTokenId() != null){
                	authorizationService.updateOAuthInfo(oAuthInfo);
                } else {
                	authorizationService.setOAuthInfo(oAuthInfo);
                }
                
            } else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.REFRESH_TOKEN.toString())) {
            	
            	if(installationId == null){
            		return buildSimpleBadRequestResponse();
            	}
            	
            	if (!authorizationService.checkRefreshToken(oauthRequest.getRefreshToken(), installationId)) {
                    return buildInvalidRefreshResponse();
                }
            	
            	final User user = userService.getUserByRefreshToken(oauthRequest.getRefreshToken());
            	
            	oAuthInfo.setUserId(user.getUserId());
            
            	authorizationService.updateOAuthInfo(oAuthInfo);
            	
            }
            
            final OAuthResponse response = OAuthASResponse
                    .tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(accessToken)
                    .setRefreshToken(refreshToken)
                    .setExpiresIn(String.valueOf(expireTimestamp.getTime()))
                    .buildJSONMessage();
            
            return new ResponseEntity<String>(response.getBody(), HttpStatus.OK);
            
        } catch (OAuthProblemException e) {
            return buildSimpleBadRequestResponse();
        } catch (OAuthSystemException e) {
            return buildSimpleBadRequestResponse();
		} catch (NoSuchAlgorithmException e) {
			return buildHashProblemResponse();
		} catch (InvalidKeySpecException e) {
			return buildHashProblemResponse();
		} 
    }

    
}