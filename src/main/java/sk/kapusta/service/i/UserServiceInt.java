package sk.kapusta.service.i;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import sk.kapusta.entity.User;
import sk.kapusta.exceptions.UserRegistrationException;

public interface UserServiceInt extends GenericServiceInt {

	public User registerUser(User requestUser) throws UserRegistrationException, NoSuchAlgorithmException, SQLException, InvalidKeySpecException;
	
	public User getUserByLogin(String login) throws SQLException;
	
	public User getUserByAccessToken(String accessToken) throws SQLException;
	
	public User getUserByRefreshToken(String refreshToken) throws SQLException;
	
}
