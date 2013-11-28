package sk.kapusta.storage;

import java.util.Collection;

import sk.kapusta.entity.User;

public interface UserStorageInt extends GenericStorageInt {

	User findUserByLogin(String login);

	Collection<User> findAllUsers();

	void addUser(User message);

	void setDelegate(UserStorageInt storageDelegate);
	
}
