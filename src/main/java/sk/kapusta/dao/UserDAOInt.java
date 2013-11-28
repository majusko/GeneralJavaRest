package sk.kapusta.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import sk.kapusta.entity.User;

public interface UserDAOInt extends GenericDAOInt {

	public User getUserByLogin(String login) throws SQLException;
	
	public User getUserByEmail(String email) throws SQLException;
	
	public User getUserByAccessToken(String accessToken) throws SQLException;
	
	public User getUserByRefreshToken(String refreshToken) throws SQLException;
	
	public User getUser(String entityId) throws SQLException;
	
	public User getUser(Long userId) throws SQLException;
	
	public User getNewUser(Long devRevisionNum) throws SQLException;
	
	public Set<User> getUsersLong(List<Long> userIds) throws SQLException;
	
	public Set<User> getUsersString(List<String> entityIds) throws SQLException;
	
	public void saveUser(User user) throws SQLException, NoSuchAlgorithmException;
	
	public void updateUser(User user) throws SQLException;
	
	public void deleteUser(Long userId) throws SQLException;
	
}
