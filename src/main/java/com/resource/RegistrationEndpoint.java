package com.resource;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import sk.kapusta.entity.User;
import sk.kapusta.exceptions.UserRegistrationException;
import sk.kapusta.service.AuthorizationService;
import sk.kapusta.service.UserService;



/**
 * @author Mario Kapusta - kapusta@eglu.sk
 *
 */
@Path("/registration")
public class RegistrationEndpoint  extends BaseEndpoint {

	private AuthorizationService authorizationService;
	private UserService userService;
	
	public RegistrationEndpoint(){
		
		authorizationService = AuthorizationService.getAuthorizationService();
		userService = UserService.getUserService();
		
	}
	
	@POST
	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response userRegistration(User requestUser, @Context HttpServletRequest request) throws OAuthSystemException {
	    
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
		    
		    return Response.ok(responseJson, MediaType.APPLICATION_JSON).build();
			
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
