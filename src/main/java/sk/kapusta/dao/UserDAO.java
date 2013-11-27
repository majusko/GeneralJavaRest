package sk.kapusta.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.googlecode.ehcache.annotations.Cacheable;

import sk.kapusta.entity.User;
import sk.kapusta.enums.UserStatus;
import sk.kapusta.security.JavaMD5Generator;

public class UserDAO extends BaseDAO implements DAOInterface {
	
	public static UserDAO userDAO;
	
	public UserDAO(){
		
		super();
		
	}
	
	public static synchronized UserDAO getUserDAO() {
		
		if ( userDAO == null ) {	
			userDAO = new UserDAO();
		}
		
		return userDAO;
		
	}
	
	@Override
	public int getMaxId() throws SQLException{
		
		final String query = " SELECT MAX(userId) maxId FROM User";
		final PreparedStatement preparedStatement = prepareQuery(query);
		final ResultSet rs = preparedStatement.executeQuery();
		int maxId = 0;
		
		while (rs.next()) {
			
			maxId = rs.getInt("maxId");
			
		}
		
		return maxId;
	}
	
	public User getUserByLogin(String login) throws SQLException {
		
		final String query = " SELECT * FROM User WHERE login = ? ";
		final PreparedStatement preparedStatement = prepareQuery(query);
		
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
		
		return user;
		
	}
	
	public User getUserByEmail(String email) throws SQLException {
		
		final String query = " SELECT * FROM User WHERE email = ? ";
		final PreparedStatement preparedStatement = prepareQuery(query);
		
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
		
		return user;
		
	}
	
	public User getUserByAccessToken(String accessToken) throws SQLException {
		
		final String query = " SELECT u.* FROM User u LEFT JOIN AccessToken a ON(u.userId = a.user) WHERE accessToken = ? ";
		final PreparedStatement preparedStatement = prepareQuery(query);
		
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
		
		return user;
		
	}
	
	public User getUserByRefreshToken(String refreshToken) throws SQLException {
		
		final String query = " SELECT u.* FROM User u LEFT JOIN AccessToken a ON(u.userId = a.user) WHERE refreshToken = ? ";
		final PreparedStatement preparedStatement = prepareQuery(query);
		
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
		
		return user;
		
	}
	
	public User getUser(String entityId) throws SQLException {
		
		final String query = " SELECT * FROM User WHERE entityId = ? ";
		final PreparedStatement preparedStatement = prepareQuery(query);
		
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
		
		return user;
		
	}
	
	public User getUser(Long userId) throws SQLException {
		
		final String query = " SELECT * FROM User WHERE userId = ? ";
		final PreparedStatement preparedStatement = prepareQuery(query);
		
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
		
		return user;
		
	}
	
	public User getNewUser(Long devRevisionNum) throws SQLException {
		
		final String query = " SELECT * FROM User WHERE revisionNumber > ? ";
		final PreparedStatement preparedStatement = prepareQuery(query);
		
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
		
		return user;
		
	}
	
	public Set<User> getUsersLong(List<Long> userIds) throws SQLException {
		
		final String initialSQL = " SELECT * FROM User WHERE userId ";
		final String query = inClauseBuilder(initialSQL, userIds.size());
		final PreparedStatement preparedStatement = prepareQuery(query);
		
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
		
		return users;
		
	}
	
	public Set<User> getUsersString(List<String> entityIds) throws SQLException {
		
		final String initialSQL = " SELECT * FROM User WHERE userId ";
		final String query = inClauseBuilder(initialSQL, entityIds.size());
		final PreparedStatement preparedStatement = prepareQuery(query);
		
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
		
		return users;
		
	}
	
	public void saveUser(User user) throws SQLException, NoSuchAlgorithmException {
		
		user = handleEmptyIds(user);
		
		final String query = " INSERT INTO User "
				+ "(entityId, revisionNumber, userId, userStatus, login, password, passwordSalt, email)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		final PreparedStatement preparedStatement = prepareQuery(query);
		
		preparedStatement.setString(1, user.getEntityId());
		preparedStatement.setLong(2, user.getRevisionNumber());
		preparedStatement.setLong(3, user.getUserId());
		preparedStatement.setString(4, user.getUserStatus().getStatusCode());
		preparedStatement.setString(5, user.getLogin());
		preparedStatement.setString(6, user.getPassword());
		preparedStatement.setString(7, user.getPasswordSalt());
		preparedStatement.setString(8, user.getEmail());
		
		preparedStatement.executeUpdate();
		
	}
	
	public void updateUser(User user) throws SQLException {
		
		final String query = " UPDATE User "
				+ " SET revisionNumber = ?, userStatus = ?, login = ?, password = ?, passwordSalt = ?, email = ? "
				+ " WHERE entityId = ?";
		final PreparedStatement preparedStatement = prepareQuery(query);
		
		preparedStatement.setLong(1, user.getRevisionNumber());
		preparedStatement.setString(2, user.getUserStatus().getStatusCode());
		preparedStatement.setString(3, user.getLogin());
		preparedStatement.setString(4, user.getPassword());
		preparedStatement.setString(5, user.getPasswordSalt());
		preparedStatement.setString(6, user.getEmail());
		preparedStatement.setString(7, user.getEntityId());
		
		preparedStatement.executeUpdate();
		
	}
	
	public void deleteUser(Long userId) throws SQLException {
		
		final String query = " DELETE FROM User WHERE userId = ? ";
		final PreparedStatement preparedStatement = prepareQuery(query);
		
		preparedStatement.setLong(1, userId);
		
		preparedStatement.executeUpdate();
		
	}
	
	private User handleEmptyIds(User user) throws SQLException, NoSuchAlgorithmException{
		
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
