package sk.kapusta.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import sk.kapusta.entity.User;
import sk.kapusta.exceptions.UserRegistrationException;

public interface UserServiceInt extends GenericServiceInt {

	User registerUser(User requestUser) throws UserRegistrationException, NoSuchAlgorithmException, SQLException, InvalidKeySpecException;
	
	User getUserByLogin(String login) throws SQLException;
	
	User getUserByAccessToken(String accessToken) throws SQLException;
	
	User getUserByRefreshToken(String refreshToken) throws SQLException;
	
}
