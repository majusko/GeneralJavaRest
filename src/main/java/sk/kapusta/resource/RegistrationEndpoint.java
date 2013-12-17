package sk.kapusta.resource;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.kapusta.entity.User;
import sk.kapusta.exceptions.UserRegistrationException;
import sk.kapusta.service.i.AccessTokenServiceInt;
import sk.kapusta.service.i.UserServiceInt;

/**
 * @author Mario Kapusta - kapusta@eglu.sk
 *
 */
@Controller
@RequestMapping("/registration")
public class RegistrationEndpoint  extends BaseEndpoint {

	@Autowired(required = true)
	private AccessTokenServiceInt authorizationService;
	
	@Autowired(required = true)
	private UserServiceInt userService;
	
	public RegistrationEndpoint(UserServiceInt userService, AccessTokenServiceInt authorizationService) {
		super();
		this.userService = userService;
		this.authorizationService = authorizationService;	
	}

	public RegistrationEndpoint() {

	}
	
    @RequestMapping(method = RequestMethod.POST,
	value = "/user",
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> userRegistration(@ModelAttribute User requestUser, HttpServletRequest request) throws OAuthSystemException {
	    
		try {
			
		    final ObjectMapper mapper = new ObjectMapper();
		    final OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.HEADER);
		    final String accessToken = oauthRequest.getAccessToken();
		    
		    if(!authorizationService.checkRegistrationToken(accessToken)){
		    	return buildUnauthorizedResponse();
		    }
		    
		    userService.registerUser(requestUser);
		    
		    final User responseUser = userService.getUserByLogin(requestUser.getLogin());
		    final String responseJson = mapper.writeValueAsString(responseUser);

            return new ResponseEntity<String>(responseJson, HttpStatus.OK);
			
		} catch (OAuthSystemException e) {
			return buildSimpleBadRequestResponse();
		} catch (OAuthProblemException e) {
			return buildSimpleBadRequestResponse();
		} catch (NoSuchAlgorithmException e) {
			return buildHashProblemResponse();
		} catch (InvalidKeySpecException e) {
			return buildHashProblemResponse();
		} catch (SQLException e) {
			return buildBadSQLResponse();
		} catch (JsonGenerationException e) {
			return buildInvalidJSONResponse();
		} catch (JsonMappingException e) {
			return buildInvalidJSONResponse();
		} catch (IOException e) {
			return buildInvalidJSONResponse();
		} catch (UserRegistrationException e) {
			return buildInvalidRegistrationResponse();
		}
	}
}
