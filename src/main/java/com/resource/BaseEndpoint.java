/**
 * 
 */
package com.resource;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;

import sk.kapusta.entity.OAuthCommon;

import com.mysql.jdbc.SQLError;

/**
 * @author mario
 *
 */
public class BaseEndpoint {
	
	public static final String INVALID_CLIENT_DESCRIPTION = "Client authentication failed (e.g., unknown client, no client authentication included, or unsupported authentication method).";
    
	public BaseEndpoint(){
		
	}
	
    public Response buildInvalidClientIdResponse() throws OAuthSystemException {
        
    	final OAuthResponse response =
                OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                .setErrorDescription(INVALID_CLIENT_DESCRIPTION)
                .buildJSONMessage();
       
        return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
    
    }

    public Response buildInvalidClientSecretResponse() throws OAuthSystemException {
    	
    	final OAuthResponse response =
                OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT).setErrorDescription(INVALID_CLIENT_DESCRIPTION)
                .buildJSONMessage();
        
    	return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
    
    }

    public Response buildBadAuthCodeResponse() throws OAuthSystemException {
    	final OAuthResponse response = OAuthASResponse
                .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.CodeResponse.ACCESS_DENIED)
                .setErrorDescription("invalid authorization code")
                .buildJSONMessage();
    	
        return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
    
    }

    public Response buildInvalidUserPassResponse() throws OAuthSystemException {
    	
    	final OAuthResponse response = OAuthASResponse
                .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError("bad_pass_or_user")
                .setErrorDescription("invalid username or password")
                .buildJSONMessage();
    	
        return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
    
    }
    
    public Response buildUnauthorizedResponse() throws OAuthSystemException{
    	
    	OAuthResponse oauthResponse = OAuthRSResponse
                .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                .setRealm(OAuthCommon.RESOURCE_SERVER_NAME)
                .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                .buildHeaderMessage();

        return Response.status(Response.Status.UNAUTHORIZED)
                .header(OAuth.HeaderType.WWW_AUTHENTICATE,
                oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE))
                .build();
        
    }
    
    public Response buildBadSQLResponse() throws OAuthSystemException {
    	
    	final OAuthResponse response = OAuthASResponse
                .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(SQLError.SQL_STATE_GENERAL_ERROR)
                .setErrorDescription("SQL syntax chyba alebo ina datova chyba pravdepodobne na strane servera")
                .buildJSONMessage();
    	
        return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
    
    }
    
    public Response buildBadOAuthResponse() throws OAuthSystemException {
    	
    	final OAuthResponse response = OAuthASResponse
                .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.TokenResponse.INVALID_GRANT)
                .setErrorDescription("Zly request v hlavicke")
                .buildJSONMessage();
    	
        return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
    
    }
    
    public Response buildInvalidRefreshResponse() throws OAuthSystemException {
    	
    	final OAuthResponse response = OAuthASResponse
                .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.TokenResponse.INVALID_GRANT)
                .setErrorDescription("refresh token je nespravny")
                .buildJSONMessage();
    	
        return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
    
    }
    
    public Response buildSimpleBadRequestResponse() throws OAuthSystemException {
    	
    	final OAuthResponse response = OAuthASResponse
                .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError("simple_request_error")
                .setErrorDescription("nespravny request")
                .buildJSONMessage();
    	
        return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
    
    }
    
    public Response buildHashProblemResponse() throws OAuthSystemException {
    	
    	final OAuthResponse response = OAuthASResponse
                .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError("hash_problem")
                .setErrorDescription("problem s generovanim hashu")
                .buildJSONMessage();
    	
        return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
    
    }
    
    public Response buildInvalidRegistrationResponse() throws OAuthSystemException {
    	
    	final OAuthResponse response = OAuthASResponse
                .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError("registration_error")
                .setErrorDescription("problem pri registracii - prazdne polie alebo user/mail uz existuje")
                .buildJSONMessage();
    	
        return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
    
    }
    
    public Response buildInvalidJSONResponse() throws OAuthSystemException {
    	
    	final OAuthResponse response = OAuthASResponse
                .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError("bad_json_format")
                .setErrorDescription("problem pri generovani JSONu")
                .buildJSONMessage();
    	
        return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
    
    }
	
}
