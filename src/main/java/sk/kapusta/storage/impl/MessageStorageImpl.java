package sk.kapusta.storage.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import sk.kapusta.entity.Message;
import sk.kapusta.storage.i.MessageStorageInt;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;

@Component
public class MessageStorageImpl implements MessageStorageInt {
	
	private static final Logger LOG = LoggerFactory.getLogger(MessageStorageImpl.class);
	
	private Map<Long, Message> messages;

	private AtomicLong newID;

	private MessageStorageInt storageDelegate;

	public MessageStorageImpl() {
		messages = Collections.synchronizedMap(new ConcurrentHashMap<Long, Message>());
		newID = new AtomicLong(0);
	}
	
	@Override
	public void setDelegate(MessageStorageInt storageDelegate) {
		this.storageDelegate = storageDelegate;
	}
	
	@PostConstruct
	public void initialize() {
		
		// add some messages
		addMessage(new Message("user:1", "content-1"));
		addMessage(new Message("user:2", "content-2"));
		addMessage(new Message("user:3", "content-3"));
		addMessage(new Message("user:4", "content-4"));
		addMessage(new Message("user:5", "content-5"));
		
	}
	
	@Override
	@Cacheable(cacheName = "messagesCache")
	public Message findMessage(long id) {
		LOG.debug("== find message by id={}", id);
		if(storageDelegate != null)
			storageDelegate.findMessage(id);
		return messages.get(id);
	}

	@Override
	@Cacheable(cacheName = "messagesCache")
	public Collection<Message> findAllMessages() {
		
		Collection<Message> values = messages.values();
		Set<Message> messages = new HashSet<Message>();
		synchronized (messages) {
			Iterator<Message> iterator = values.iterator();
			while (iterator.hasNext()) {
				messages.add(iterator.next());
			}
		}
		LOG.debug("== got all messages, size={}", messages.size());
		if(storageDelegate != null)
			storageDelegate.findAllMessages();
		return Collections.unmodifiableCollection(messages);
	}

	@Override
	@TriggersRemove(cacheName = "messagesCache", when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public void addMessage(Message message) {
		long id = newID.incrementAndGet();
		message.setId(id);
		messages.put(id, message);
		LOG.debug("== added a message with id={}", id);
		if(storageDelegate != null)
			storageDelegate.addMessage(message);
	}
}
