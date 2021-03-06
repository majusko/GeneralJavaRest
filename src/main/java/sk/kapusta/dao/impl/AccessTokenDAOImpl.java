package sk.kapusta.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

import sk.kapusta.dao.BaseDAO;
import sk.kapusta.dao.connection.DatabaseConnectionPool;
import sk.kapusta.dao.i.AccessTokenDAOInt;
import sk.kapusta.entity.AccessToken;

@Repository
public class AccessTokenDAOImpl extends BaseDAO implements AccessTokenDAOInt {
	
	public AccessTokenDAOImpl(){
		
		super();
		
	}
	
	@Override
	public int getMaxId() throws SQLException{
		
		final String query = " SELECT MAX(accessTokenId) maxId FROM AccessToken";
		final Connection connection = DatabaseConnectionPool.getConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(query);
		final ResultSet rs = preparedStatement.executeQuery();
		int maxId = 0;
		
		while (rs.next()) {
			
			maxId = rs.getInt("maxId");
			
		}

		connection.close();
		preparedStatement.close();
		rs.close();
		
		return maxId;
		
	}
	
	//---------------------------------------------------------------------------------
	//								OAUTH
	//---------------------------------------------------------------------------------
	
	public void saveOAuthInfo(AccessToken oAuthInfo) throws SQLException {
		
		oAuthInfo = handleEmptyIds(oAuthInfo);
		
		final String query = " INSERT INTO AccessToken "
				+ " (`accessTokenId`, `installationId`, `user`, `accessToken`, `refreshToken`, `expireToken`) "
				+ " VALUES(?, ?, ?, ?, ?, ?) ";
		final Connection connection = DatabaseConnectionPool.getConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(query);

		preparedStatement.setLong(1, oAuthInfo.getAccessTokenId());
		preparedStatement.setString(2, oAuthInfo.getInstallationId());
		preparedStatement.setLong(3, oAuthInfo.getUserId());
		preparedStatement.setString(4, oAuthInfo.getAccessToken());
		preparedStatement.setString(5, oAuthInfo.getRefreshToken());
		preparedStatement.setTimestamp(6, new Timestamp(oAuthInfo.getExpireToken().getTime()));
		
		preparedStatement.executeUpdate();

		connection.close();
		preparedStatement.close();
		
	}
	
	public void updateOAuthInfo(AccessToken oAuthInfo) throws SQLException {
		
		final String query = " UPDATE AccessToken "
				+ " SET accessToken = ?, "
				+ " refreshToken = ?, "
				+ " expireToken = ? "
				+ " WHERE user = ? "
				+ " AND installationId = ? ";
		final Connection connection = DatabaseConnectionPool.getConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setString(1, oAuthInfo.getAccessToken());
		preparedStatement.setString(2, oAuthInfo.getRefreshToken());
		preparedStatement.setTimestamp(3, new Timestamp(oAuthInfo.getExpireToken().getTime()));
		preparedStatement.setLong(4, oAuthInfo.getUserId());
		preparedStatement.setString(5, oAuthInfo.getInstallationId());
		
		preparedStatement.executeUpdate();

		connection.close();
		preparedStatement.close();
		
	}
	
	public AccessToken getOAuthInfo(Long userId, String installationId) throws SQLException {
		
		final String query = " SELECT * FROM AccessToken WHERE user = ? AND installationId = ? ";
		final Connection connection = DatabaseConnectionPool.getConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setLong(1, userId);
		preparedStatement.setString(2, installationId);
		
		final ResultSet rs = preparedStatement.executeQuery();
		
		final AccessToken oAuthInfo = new AccessToken();
		
		while (rs.next()) {
			
			oAuthInfo.setAccessTokenId(rs.getLong("accessTokenId"));
			oAuthInfo.setInstallationId(rs.getString("installationId"));
			oAuthInfo.setUserId(rs.getLong("user"));
			oAuthInfo.setAccessToken(rs.getString("accessToken"));
			oAuthInfo.setRefreshToken(rs.getString("refreshToken"));
			oAuthInfo.setExpireToken(rs.getTimestamp("expireToken"));
			
		}

		connection.close();
		preparedStatement.close();
		rs.close();
		
		return oAuthInfo;
		
	}
	
	public AccessToken getOAuthInfo(String accessToken) throws SQLException {
		
		final String query = " SELECT * FROM AccessToken WHERE accessToken = ? ";
		final Connection connection = DatabaseConnectionPool.getConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setString(1, accessToken);
		
		final ResultSet rs = preparedStatement.executeQuery();
		
		final AccessToken oAuthInfo = new AccessToken();
		
		while (rs.next()) {
			
			oAuthInfo.setAccessTokenId(rs.getLong("accessTokenId"));
			oAuthInfo.setInstallationId(rs.getString("installationId"));
			oAuthInfo.setUserId(rs.getLong("user"));
			oAuthInfo.setAccessToken(rs.getString("accessToken"));
			oAuthInfo.setRefreshToken(rs.getString("refreshToken"));
			oAuthInfo.setExpireToken(rs.getTimestamp("expireToken"));
			
		}

		connection.close();
		preparedStatement.close();
		rs.close();
		
		return oAuthInfo;
		
	}
	
	private AccessToken handleEmptyIds(AccessToken oAuthInfo) throws SQLException {
		
		if(oAuthInfo.getAccessTokenId() == null){
			
			final int id = getMaxId() + 1;
			
			oAuthInfo.setAccessTokenId(Long.valueOf(id));
			
		}
		
		return oAuthInfo;
		
	}
	
}
