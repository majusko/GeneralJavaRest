package sk.kapusta.storage;

import java.util.Collection;

import sk.kapusta.model.Message;

public interface MessageStorage {

	Message findMessage(long id);

	Collection<Message> findAllMessages();

	void addMessage(Message message);

	void setDelegate(MessageStorage storageDelegate);
}
