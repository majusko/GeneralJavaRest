package sk.kapusta.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sk.kapusta.dao.i.UserDAOInt;
import sk.kapusta.entity.User;
import sk.kapusta.enums.UserStatus;
import sk.kapusta.exceptions.UserRegistrationException;
import sk.kapusta.security.JavaMD5Generator;
import sk.kapusta.security.PasswordHash;
import sk.kapusta.service.i.UserServiceInt;

@Component
public class UserServiceImpl implements UserServiceInt {

	@Autowired(required = true)
	private UserDAOInt userDAO;

	public UserServiceImpl() {

	}
	
	@Override
	public User registerUser(User requestUser) throws UserRegistrationException, NoSuchAlgorithmException, SQLException, InvalidKeySpecException {
		
		validateUserRegistration(requestUser);
		validateUserExistance(requestUser);
		
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		final Date date = new Date();
		final String currentTime = dateFormat.format(date);
		final String entityId = JavaMD5Generator.md5(currentTime);
		final Long userId = Long.valueOf(getNextId());
		final Long revisionNumber = 1L;
		final String passwordSalt = PasswordHash.createHash(requestUser.getPassword());
		
		requestUser.setUserId(userId);
		requestUser.setEntityId(entityId);
		requestUser.setRevisionNumber(revisionNumber);
		requestUser.setPasswordSalt(passwordSalt);
		requestUser.setUserStatus(UserStatus.ACTIVE);
		
		userDAO.saveUser(requestUser);
		
		return requestUser;
		
	}
	
	@Override
	public User getUserByLogin(String login) throws SQLException {
		
		final User user = userDAO.getUserByLogin(login);
		
		return user;
		
	}
	
	@Override
	public User getUserByAccessToken(String accessToken) throws SQLException {
		
		accessToken = accessToken.replace("Bearer", "").trim();
		
		final User user = userDAO.getUserByAccessToken(accessToken);
		
		return user;
		
	}
	
	@Override
	public User getUserByRefreshToken(String refreshToken) throws SQLException {
		
		final User user = userDAO.getUserByRefreshToken(refreshToken);
		
		return user;
		
	}

	@Override
	public int getNextId() throws SQLException {
		
		final int nextId = userDAO.getMaxId() + 1;
		
		return nextId;
		
	}
	
	private void validateUserRegistration(User user) throws UserRegistrationException {
		
		if(user.getLogin() == null || user.getLogin().trim().isEmpty()){
			throw new UserRegistrationException("Nie su vyplnene vsetky povinne polia.");
		}
		
		if(user.getPassword() == null || user.getPassword().trim().isEmpty()){
			throw new UserRegistrationException("Nie su vyplnene vsetky povinne polia.");
		}
		
		if(user.getEmail() == null || user.getEmail().trim().isEmpty()){
			throw new UserRegistrationException("Nie su vyplnene vsetky povinne polia.");
		}
		
	}
	
	private void validateUserExistance(User user) throws SQLException, UserRegistrationException{
		
		if(loginExists(user.getLogin()) || emailExists(user.getEmail())){
			throw new UserRegistrationException("User alebo email uz existuje");
		}
		
	}
	
	private boolean loginExists(String login) throws SQLException{
		
		final User user = userDAO.getUserByLogin(login);
		
		if(user.getLogin() != null){
			if(user.getLogin().equals(login)){
				return true;
			}
		}
		
		return false;
		
	}
	
	private boolean emailExists(String email) throws SQLException{
		
		final User user = userDAO.getUserByEmail(email);
		
		if(user.getLogin() != null){
			if(user.getEmail().equals(email)){
				return true;
			}
		}
		
		return false;
		
	}
}
