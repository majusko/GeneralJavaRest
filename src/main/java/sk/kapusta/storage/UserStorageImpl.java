package sk.kapusta.storage;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;

import sk.kapusta.entity.User;

@Component
public class UserStorageImpl implements UserStorageInt {
	
	private Map<String, User> users;

	private UserStorageInt storageDelegate;
	
	public UserStorageImpl() {
		
		users = Collections.synchronizedMap(new ConcurrentHashMap<String, User>());
		
	}
	
	@PostConstruct
	public void initialize() {
		
		User user = new User();
    	user.setLogin("testUser");
    	user.setPassword("thisIsMyPass");
    	user.setEmail("email@me.here");
    	
		addUser(user);
		
	}

	@Override
	@Cacheable(cacheName = "userCache")
	public User findUserByLogin(String login) {
		
		if(storageDelegate != null){
			storageDelegate.findUserByLogin(login);
		}
		
		return users.get(login);
		
	}

	@Override
	@Cacheable(cacheName = "messagesCache")
	public Collection<User> findAllUsers() {
		
		Collection<User> values = users.values();
		Set<User> users = new HashSet<User>();
		
		synchronized (users) {
			Iterator<User> iterator = values.iterator();
			while (iterator.hasNext()) {
				users.add(iterator.next());
			}
		}
		
		if(storageDelegate != null){
			storageDelegate.findAllUsers();
		}
		
		return Collections.unmodifiableCollection(users);
		
	}

	@Override
	@TriggersRemove(cacheName = "messagesCache", when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public void addUser(User user) {
		
		users.put(user.getLogin(), user);
		
		if(storageDelegate != null){
			storageDelegate.addUser(user);
		}
		
	}

	@Override
	public void setDelegate(UserStorageInt storageDelegate) {
		this.storageDelegate = storageDelegate;
	}
	
}
