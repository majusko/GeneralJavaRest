package sk.kapusta.storage.i;

import java.util.Collection;

import sk.kapusta.entity.Message;

public interface MessageStorageInt extends GenericStorageInt {

	Message findMessage(long id);

	Collection<Message> findAllMessages();

	void addMessage(Message message);

	void setDelegate(MessageStorageInt storageDelegate);
}
