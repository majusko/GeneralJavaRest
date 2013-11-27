package sk.kapusta.storage;

import java.util.Collection;

import sk.kapusta.entity.User;

public interface UserStorage {

	User findUserByLogin(String login);

	Collection<User> findAllUsers();

	void addUser(User message);

	void setDelegate(UserStorage storageDelegate);
	
}
