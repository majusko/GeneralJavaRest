package sk.kapusta.resource;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.kapusta.entity.User;
import sk.kapusta.service.i.UserServiceInt;
import sk.kapusta.storage.i.UserStorageInt;

@RequestMapping("/user")
@Controller
public class UserEndpoint extends BaseEndpoint {
	
	@Autowired(required = true)
	private UserServiceInt userService;
	
	@Autowired(required = true)
	private UserStorageInt storageDelegate;

	public UserEndpoint(UserServiceInt userService) {
		super();
		this.userService = userService;
	}

	public UserEndpoint() {

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{login}/login")
	public ResponseEntity<String> findUserByLogin(@PathVariable("login") String login){

		try {
			
			final ObjectMapper mapper = new ObjectMapper();
			//final User responseUser = userService.getUserByLogin(login);
			final User responseUser = storageDelegate.findUserByLogin(login);
		    final String responseJson = mapper.writeValueAsString(responseUser);
		    
			return new ResponseEntity<String>(responseJson, HttpStatus.OK);

		} catch (JsonGenerationException e) {
			return buildInvalidJSONResponse();
		} catch (JsonMappingException e) {
			return buildInvalidJSONResponse();
		} catch (IOException e) {
			return buildSimpleBadRequestResponse();
		}
		
	}
	
}
