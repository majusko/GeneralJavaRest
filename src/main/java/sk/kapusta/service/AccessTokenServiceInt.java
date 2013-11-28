package sk.kapusta.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import sk.kapusta.entity.AccessToken;

public interface AccessTokenServiceInt {

	public boolean checkClientId(String clientId);
	
	public boolean checkClientSecret(String secret);
	
	public boolean checkAuthCode(String authCode);
	
	public boolean checkUserPass(String login, String password) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException;
	
	public boolean checkRegistrationToken(String registrationToken) throws NoSuchAlgorithmException, InvalidKeySpecException;
	
	public boolean checkAccessToken(String accessToken) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException;
    
	public boolean checkRefreshToken(String refreshToken, String installationId) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException;
    
	public void updateOAuthInfo(AccessToken oAuthInfo) throws SQLException;
    
	public void setOAuthInfo(AccessToken oAuthInfo) throws SQLException;
	
	public AccessToken getOAuthInfo(Long userId, String installationId) throws SQLException;
	
}
