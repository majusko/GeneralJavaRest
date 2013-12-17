package sk.kapusta.dao.i;

import java.sql.SQLException;

import sk.kapusta.entity.AccessToken;

public interface AccessTokenDAOInt extends GenericDAOInt {

	public void saveOAuthInfo(AccessToken oAuthInfo) throws SQLException;
	public void updateOAuthInfo(AccessToken oAuthInfo) throws SQLException;
	public AccessToken getOAuthInfo(Long userId, String installationId) throws SQLException;
	public AccessToken getOAuthInfo(String accessToken) throws SQLException;
	
}
