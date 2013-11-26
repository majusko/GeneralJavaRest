package sk.kapusta.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Date;

import sk.kapusta.dao.AccessTokenDAO;
import sk.kapusta.dao.UserDAO;
import sk.kapusta.entity.AccessToken;
import sk.kapusta.entity.OAuthCommon;
import sk.kapusta.entity.User;
import sk.kapusta.security.PasswordHash;

/**
 * 
 * @author Mario Kapusta - kapusta@eglu.sk
 *
 */

public class AuthorizationService {

	private UserDAO userDAO;
	private AccessTokenDAO accessTokenDAO;
	
	public static AuthorizationService authorizationService;
	
	public AuthorizationService(){
		
		userDAO = UserDAO.getUserDAO();
		accessTokenDAO = AccessTokenDAO.getBudgetCategoryDAO();
		
	}
	
	public static synchronized AuthorizationService getAuthorizationService() {
		
		if ( authorizationService == null ) {	
			authorizationService = new AuthorizationService();
		}
		
		return authorizationService;
		
	}
	
	public boolean checkClientId(String clientId) {
		
		if(OAuthCommon.CLIENT_ID.equals(clientId)){
			return true;
		}
		
        return false;
        
    }

    public boolean checkClientSecret(String secret) {

		if(OAuthCommon.CLIENT_SECRET.equals(secret)){
			return true;
		}
		
        return false;
        
    }

    public boolean checkAuthCode(String authCode) {

		if(OAuthCommon.AUTHORIZATION_CODE.equals(authCode)){
			return true;
		}
		
        return false;
        
    }

    public boolean checkUserPass(String login, String password) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
    	
    	final User user = userDAO.getUserByLogin(login);
    	
    	if(PasswordHash.validatePassword(password, user.getPasswordSalt())){
    		return true;
    	}
    	
    	return false;
    }
    
    public boolean checkRegistrationToken(String registrationToken) throws NoSuchAlgorithmException, InvalidKeySpecException {
    	
    	registrationToken = registrationToken.replace("Bearer", "").trim();
    	
    	if(PasswordHash.validatePassword(OAuthCommon.REGISTRATION_CODE, registrationToken)){
    		return true;
    	}
    	
    	return false;
    	
    }
    
    public boolean checkAccessToken(String accessToken) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {

    	accessToken = accessToken.replace("Bearer", "").trim();
    	
    	if(accessToken != null && !accessToken.trim().isEmpty()){
    		
	    	final AccessToken oAuthInfo = accessTokenDAO.getOAuthInfo(accessToken);
	    	final Date currentTime = new Date();
	    	
	    	if(oAuthInfo.getExpireToken() != null){
		    	if(oAuthInfo.getExpireToken().after(currentTime)){
		    		return true;
		    	}
	    	}
	    	
    	}
    	
    	return false;
    	
    }
    
    public boolean checkRefreshToken(String refreshToken, String installationId) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
    	
    	if(refreshToken != null && !refreshToken.trim().isEmpty()){
    		
	    	final User user = userDAO.getUserByRefreshToken(refreshToken);
	    	final AccessToken oAuthInfo = accessTokenDAO.getOAuthInfo(user.getUserId(), installationId);
	    	
	    	if(oAuthInfo.getRefreshToken().equals(refreshToken)){
	    		return true;
	    	}
		
    	}
    	
    	return false;
    	
    }
    
    public void updateOAuthInfo(AccessToken oAuthInfo) throws SQLException{
    	
    	accessTokenDAO.updateOAuthInfo(oAuthInfo);
    	
    }
    
    public void setOAuthInfo(AccessToken oAuthInfo) throws SQLException{
    	
    	accessTokenDAO.saveOAuthInfo(oAuthInfo);
    	
    }
    
    public AccessToken getOAuthInfo(Long userId, String installationId) throws SQLException{
    	
    	 final AccessToken accessToken = accessTokenDAO.getOAuthInfo(userId, installationId);
    	 
    	 return accessToken;
    	
    }
	
}
