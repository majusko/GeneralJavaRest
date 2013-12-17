package sk.kapusta.resource;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sk.kapusta.entity.Message;
import sk.kapusta.storage.i.MessageStorageInt;

@Controller
public class MessageEndpoint {

	@Autowired(required = true)
	private MessageStorageInt messageStorage;

	public MessageEndpoint(MessageStorageInt messageStorage) {
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
	public ResponseEntity<String> getAllMessages() throws JsonGenerationException, JsonMappingException, IOException {
		
			
			final ObjectMapper mapper = new ObjectMapper();
			Message message = messageStorage.findMessage(1);
		    final String responseJson = mapper.writeValueAsString(message);
		    
			return new ResponseEntity<String>(responseJson, HttpStatus.OK);


		
	}
}
