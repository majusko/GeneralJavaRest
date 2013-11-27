package sk.kapusta.storage;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;

import sk.kapusta.entity.Message;
import sk.kapusta.entity.User;

public class MemoryUserStorage implements UserStorage {
	
	private Map<Long, User> users;

	private UserStorage storageDelegate;
	
	public MemoryUserStorage() {
		
		users = Collections.synchronizedMap(new HashMap<Long, User>());
		
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
		
		users.put(user.getUserId(), user);
		
		if(storageDelegate != null){
			storageDelegate.addUser(user);
		}
		
	}

	@Override
	public void setDelegate(UserStorage storageDelegate) {
		this.storageDelegate = storageDelegate;
	}
	
}
