//package com.goyello.esa;
//
//import static org.mockito.Mockito.*;
//import net.sf.ehcache.CacheManager;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import sk.kapusta.entity.Message;
//import sk.kapusta.resource.MessageEndpoint;
//import sk.kapusta.storage.MessageStorageInt;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "/spring-context-test.xml" })
//public class CachingTest {
//	
//	@Autowired
//	ApplicationContext context;
//	
//	@Autowired
//	CacheManager cacheManager;
//	
//	MessageStorageInt storage;
//	
//	MessageStorageInt storageDelegate;
//	
//	MessageEndpoint controller;
//	
//	
//	@Before
//	public void before() throws Exception {
//		storageDelegate = Mockito.mock(MessageStorageInt.class);
//		storage = (MessageStorageInt) context.getBean("messageStorage");
//		storage.setDelegate(storageDelegate);
//		controller = new MessageEndpoint(storage);
//		
//		cacheManager.clearAll();
//	}
//	
//	@Test
//	public void testCaching_MessagesCache() {
//		controller.getAllMessages();
//		controller.getAllMessages();
//		verify(storageDelegate, times(1)).findAllMessages();
//	}
//	
//	@Test
//	public void testCaching_MessagesCacheRemove() {
//		controller.getAllMessages();
//		controller.getAllMessages();
//		controller.addMessage(new Message());
//		controller.getAllMessages();
//		
//		verify(storageDelegate, times(2)).findAllMessages();
//		verify(storageDelegate, times(1)).addMessage(any(Message.class));
//	}
//	
//	@Test
//	public void testCaching_MessageCache() {
//		controller.getMessageById(1);
//		controller.getMessageById(1);
//		controller.addMessage(new Message());
//		controller.getMessageById(1);
//		
//		verify(storageDelegate, times(1)).findMessage(1);
//		verify(storageDelegate, times(1)).addMessage(any(Message.class));
//	}
//	
//}
