package sk.kapusta.dao.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import sk.kapusta.dao.BaseDAO;
import sk.kapusta.dao.connection.DatabaseConnectionPool;
import sk.kapusta.dao.i.UserDAOInt;
import sk.kapusta.entity.User;
import sk.kapusta.enums.UserStatus;
import sk.kapusta.security.JavaMD5Generator;

@Repository
public class UserDAOImpl extends BaseDAO implements UserDAOInt {
	
	public UserDAOImpl(){
		
		super();
		
	}
	
	@Override
	public int getMaxId() throws SQLException{
		
		final String query = " SELECT MAX(userId) maxId FROM User";
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

	@Override
	public User getUserByLogin(String login) throws SQLException {
		
		final String query = " SELECT * FROM User WHERE login = ? ";
		final Connection connection = DatabaseConnectionPool.getConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setString(1, login);
		
		final ResultSet rs = preparedStatement.executeQuery();
		final User user = new User();
		
		while (rs.next()) {
			
			user.setEntityId(rs.getString("entityId"));
			user.setRevisionNumber(rs.getLong("revisionNumber"));
			user.setUserId(rs.getLong("userId"));
			user.setUserStatus(UserStatus.valueOf((rs.getString("userStatus"))));
			user.setLogin(rs.getString("login"));
			user.setPassword(rs.getString("password"));
			user.setPasswordSalt(rs.getString("passwordSalt"));
			user.setEmail(rs.getString("email"));
			
		}

		connection.close();
		preparedStatement.close();
		rs.close();
		
		return user;
		
	}

	@Override
	public User getUserByEmail(String email) throws SQLException {
		
		final String query = " SELECT * FROM User WHERE email = ? ";
		final Connection connection = DatabaseConnectionPool.getConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setString(1, email);
		
		final ResultSet rs = preparedStatement.executeQuery();
		final User user = new User();
		
		while (rs.next()) {
			
			user.setEntityId(rs.getString("entityId"));
			user.setRevisionNumber(rs.getLong("revisionNumber"));
			user.setUserId(rs.getLong("userId"));
			user.setUserStatus(UserStatus.valueOf((rs.getString("userStatus"))));
			user.setLogin(rs.getString("login"));
			user.setPassword(rs.getString("password"));
			user.setPasswordSalt(rs.getString("passwordSalt"));
			user.setEmail(rs.getString("email"));
			
		}

		connection.close();
		preparedStatement.close();
		rs.close();
		
		return user;
		
	}

	@Override
	public User getUserByAccessToken(String accessToken) throws SQLException {
		
		final String query = " SELECT u.* FROM User u LEFT JOIN AccessToken a ON(u.userId = a.user) WHERE accessToken = ? ";
		final Connection connection = DatabaseConnectionPool.getConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setString(1, accessToken);
		
		final ResultSet rs = preparedStatement.executeQuery();
		final User user = new User();
		
		while (rs.next()) {
			
			user.setEntityId(rs.getString("entityId"));
			user.setRevisionNumber(rs.getLong("revisionNumber"));
			user.setUserId(rs.getLong("userId"));
			user.setUserStatus(UserStatus.valueOf((rs.getString("userStatus"))));
			user.setLogin(rs.getString("login"));
			user.setPassword(rs.getString("password"));
			user.setPasswordSalt(rs.getString("passwordSalt"));
			user.setEmail(rs.getString("email"));
			
		}

		connection.close();
		preparedStatement.close();
		rs.close();
		
		return user;
		
	}

	@Override
	public User getUserByRefreshToken(String refreshToken) throws SQLException {
		
		final String query = " SELECT u.* FROM User u LEFT JOIN AccessToken a ON(u.userId = a.user) WHERE refreshToken = ? ";
		final Connection connection = DatabaseConnectionPool.getConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setString(1, refreshToken);
		
		final ResultSet rs = preparedStatement.executeQuery();
		final User user = new User();
		
		while (rs.next()) {
			
			user.setEntityId(rs.getString("entityId"));
			user.setRevisionNumber(rs.getLong("revisionNumber"));
			user.setUserId(rs.getLong("userId"));
			user.setUserStatus(UserStatus.valueOf((rs.getString("userStatus"))));
			user.setLogin(rs.getString("login"));
			user.setPassword(rs.getString("password"));
			user.setPasswordSalt(rs.getString("passwordSalt"));
			user.setEmail(rs.getString("email"));
			
		}

		connection.close();
		preparedStatement.close();
		rs.close();
		
		return user;
		
	}

	@Override
	public User getUser(String entityId) throws SQLException {
		
		final String query = " SELECT * FROM User WHERE entityId = ? ";
		final Connection connection = DatabaseConnectionPool.getConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setString(1, entityId);
		
		final ResultSet rs = preparedStatement.executeQuery();
		final User user = new User();
		
		while (rs.next()) {
			
			user.setEntityId(rs.getString("entityId"));
			user.setRevisionNumber(rs.getLong("revisionNumber"));
			user.setUserId(rs.getLong("userId"));
			user.setUserStatus(UserStatus.valueOf((rs.getString("userStatus"))));
			user.setLogin(rs.getString("login"));
			user.setPassword(rs.getString("password"));
			user.setPasswordSalt(rs.getString("passwordSalt"));
			user.setEmail(rs.getString("email"));
			
		}

		connection.close();
		preparedStatement.close();
		rs.close();
		
		return user;
		
	}

	@Override
	public User getUser(Long userId) throws SQLException {
		
		final String query = " SELECT * FROM User WHERE userId = ? ";
		final Connection connection = DatabaseConnectionPool.getConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setLong(1, userId);
		
		final ResultSet rs = preparedStatement.executeQuery();
		final User user = new User();
		
		while (rs.next()) {
			
			user.setEntityId(rs.getString("entityId"));
			user.setRevisionNumber(rs.getLong("revisionNumber"));
			user.setUserId(rs.getLong("userId"));
			user.setUserStatus(UserStatus.valueOf((rs.getString("userStatus"))));
			user.setLogin(rs.getString("login"));
			user.setPassword(rs.getString("password"));
			user.setPasswordSalt(rs.getString("passwordSalt"));
			user.setEmail(rs.getString("email"));
			
		}

		connection.close();
		preparedStatement.close();
		rs.close();
		
		return user;
		
	}

	@Override
	public User getNewUser(Long devRevisionNum) throws SQLException {
		
		final String query = " SELECT * FROM User WHERE revisionNumber > ? ";
		final Connection connection = DatabaseConnectionPool.getConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setLong(1, devRevisionNum);
		
		final ResultSet rs = preparedStatement.executeQuery();
		final User user = new User();
		
		while (rs.next()) {
			
			user.setEntityId(rs.getString("entityId"));
			user.setRevisionNumber(rs.getLong("revisionNumber"));
			user.setUserId(rs.getLong("userId"));
			user.setUserStatus(UserStatus.valueOf((rs.getString("userStatus"))));
			user.setLogin(rs.getString("login"));
			user.setPassword(rs.getString("password"));
			user.setPasswordSalt(rs.getString("passwordSalt"));
			user.setEmail(rs.getString("email"));
			
		}

		connection.close();
		preparedStatement.close();
		rs.close();
		
		return user;
		
	}

	@Override
	public Set<User> getUsersLong(List<Long> userIds) throws SQLException {
		
		final String initialSQL = " SELECT * FROM User WHERE userId ";
		final String query = inClauseBuilder(initialSQL, userIds.size());
		final Connection connection = DatabaseConnectionPool.getConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		for(int i = 0; i < userIds.size(); i++){
			preparedStatement.setLong(i + 1, userIds.get(i));
		}

		
		final ResultSet rs = preparedStatement.executeQuery();
		final Set<User> users = new HashSet<User>();
		
		
		while (rs.next()) {
			
			final User user = new User();
			
			user.setEntityId(rs.getString("entityId"));
			user.setRevisionNumber(rs.getLong("revisionNumber"));
			user.setUserId(rs.getLong("userId"));
			user.setUserStatus(UserStatus.valueOf((rs.getString("userStatus"))));
			user.setLogin(rs.getString("login"));
			user.setPassword(rs.getString("password"));
			user.setPasswordSalt(rs.getString("passwordSalt"));
			user.setEmail(rs.getString("email"));
			
			users.add(user);
			
		}

		connection.close();
		preparedStatement.close();
		rs.close();
		
		return users;
		
	}

	@Override
	public Set<User> getUsersString(List<String> entityIds) throws SQLException {
		
		final String initialSQL = " SELECT * FROM User WHERE userId ";
		final String query = inClauseBuilder(initialSQL, entityIds.size());
		final Connection connection = DatabaseConnectionPool.getConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		for(int i = 0; i < entityIds.size(); i++){
			preparedStatement.setString(i + 1, entityIds.get(i));
		}
		
		final ResultSet rs = preparedStatement.executeQuery();
		final Set<User> users = new HashSet<User>();
		
		while (rs.next()) {
			
			final User user = new User();
			
			user.setEntityId(rs.getString("entityId"));
			user.setRevisionNumber(rs.getLong("revisionNumber"));
			user.setUserId(rs.getLong("userId"));
			user.setUserStatus(UserStatus.valueOf((rs.getString("userStatus"))));
			user.setLogin(rs.getString("login"));
			user.setPassword(rs.getString("password"));
			user.setPasswordSalt(rs.getString("passwordSalt"));
			user.setEmail(rs.getString("email"));
			
			users.add(user);
			
		}

		connection.close();
		preparedStatement.close();
		rs.close();
		
		return users;
		
	}

	@Override
	public void saveUser(User user) throws SQLException, NoSuchAlgorithmException {
		
		user = handleEmptyIds(user);
		
		final String query = " INSERT INTO User "
				+ "(entityId, revisionNumber, userId, userStatus, login, password, passwordSalt, email)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		final Connection connection = DatabaseConnectionPool.getConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setString(1, user.getEntityId());
		preparedStatement.setLong(2, user.getRevisionNumber());
		preparedStatement.setLong(3, user.getUserId());
		preparedStatement.setString(4, user.getUserStatus().getStatusCode());
		preparedStatement.setString(5, user.getLogin());
		preparedStatement.setString(6, user.getPassword());
		preparedStatement.setString(7, user.getPasswordSalt());
		preparedStatement.setString(8, user.getEmail());
		
		preparedStatement.executeUpdate();

		connection.close();
		preparedStatement.close();
		
	}

	@Override
	public void updateUser(User user) throws SQLException {
		
		final String query = " UPDATE User "
				+ " SET revisionNumber = ?, userStatus = ?, login = ?, password = ?, passwordSalt = ?, email = ? "
				+ " WHERE entityId = ?";
		final Connection connection = DatabaseConnectionPool.getConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setLong(1, user.getRevisionNumber());
		preparedStatement.setString(2, user.getUserStatus().getStatusCode());
		preparedStatement.setString(3, user.getLogin());
		preparedStatement.setString(4, user.getPassword());
		preparedStatement.setString(5, user.getPasswordSalt());
		preparedStatement.setString(6, user.getEmail());
		preparedStatement.setString(7, user.getEntityId());
		
		preparedStatement.executeUpdate();

		connection.close();
		preparedStatement.close();
		
	}
	
	@Override
	public void deleteUser(Long userId) throws SQLException {
		
		final String query = " DELETE FROM User WHERE userId = ? ";
		final Connection connection = DatabaseConnectionPool.getConnection();
		final PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setLong(1, userId);
		
		preparedStatement.executeUpdate();

		connection.close();
		preparedStatement.close();
		
	}
	
	private User handleEmptyIds(User user) throws SQLException, NoSuchAlgorithmException {
		
		if(user.getUserId() == null){
			
			final int id = getMaxId() + 1;
			
			user.setUserId(Long.valueOf(id));
			
		}
		
		if(user.getEntityId() == null || user.getEntityId().trim().isEmpty()){
			
			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			final Date date = new Date();
			final String currentTime = dateFormat.format(date);
			final String entityId = JavaMD5Generator.md5(currentTime);
			
			user.setEntityId(entityId);
			
		}
		
		return user;
		
	}

}
