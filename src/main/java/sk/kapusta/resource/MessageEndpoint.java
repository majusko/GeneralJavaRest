package sk.kapusta.resource;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sk.kapusta.model.CollectionOfElements;
import sk.kapusta.model.Message;
import sk.kapusta.storage.MessageStorage;

@Controller
public class MessageEndpoint {

	@Autowired(required = true)
	private MessageStorage messageStorage;

	public MessageEndpoint(MessageStorage messageStorage) {
		super();
		this.messageStorage = messageStorage;
	}

	public MessageEndpoint() {

	}

	@RequestMapping(method = RequestMethod.GET, value = "/message/add")
	public ModelAndView messageForm() {
		return new ModelAndView("message-form", "command", new Message());
	}

	@RequestMapping(method = RequestMethod.POST, value = "/message/add")
	public ModelAndView addMessage(@ModelAttribute Message message) {
		messageStorage.addMessage(message);
		return getMessageById(message.getId());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/message/{id}")
	public ModelAndView getMessageById(@PathVariable("id") long id) {
		Message message = messageStorage.findMessage(id);
		ModelAndView mav = new ModelAndView("message-details");
		mav.addObject("message", message);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/message")
	public ModelAndView getAllMessages() {
		Collection<Message> messages = messageStorage.findAllMessages();
		ModelAndView mav = new ModelAndView("messages");
		mav.addObject("messages", new CollectionOfElements(messages));
		return mav;
	}
}
