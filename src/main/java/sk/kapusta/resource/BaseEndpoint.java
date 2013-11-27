/**
 * 
 */
package sk.kapusta.resource;

import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import sk.kapusta.entity.OAuthCommon;

import com.mysql.jdbc.SQLError;

public class BaseEndpoint {
	
	public static final String INVALID_CLIENT_DESCRIPTION = "Client authentication failed (e.g., unknown client, no client authentication included, or unsupported authentication method).";
    
	public BaseEndpoint(){
		
	}
	
    public ResponseEntity<String> buildInvalidClientIdResponse() {
        
		try {
			
			final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
			.setError(OAuthError.TokenResponse.INVALID_CLIENT)
			.setErrorDescription(INVALID_CLIENT_DESCRIPTION)
			.buildJSONMessage();

	    	return new ResponseEntity<String>(response.getBody(), HttpStatus.BAD_REQUEST);
	    	
		} catch (OAuthSystemException e) {

	    	return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	    	
		}
       
    
    }

    public ResponseEntity<String> buildInvalidClientSecretResponse() {
    	
    	try {

        	final OAuthResponse response =
                    OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                    .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT).setErrorDescription(INVALID_CLIENT_DESCRIPTION)
                    .buildJSONMessage();

        	return new ResponseEntity<String>(response.getBody(), HttpStatus.UNAUTHORIZED);
	    	
		} catch (OAuthSystemException e) {

	    	return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	    	
		}
    	
    
    }

    public ResponseEntity<String> buildBadAuthCodeResponse() {
    	
    	try {
        	
        	final OAuthResponse response = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError(OAuthError.CodeResponse.ACCESS_DENIED)
                    .setErrorDescription("invalid authorization code")
                    .buildJSONMessage();

        	return new ResponseEntity<String>(response.getBody(), HttpStatus.BAD_REQUEST);
	    	
		} catch (OAuthSystemException e) {

	    	return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	    	
		}
    
    }

    public ResponseEntity<String> buildInvalidUserPassResponse() {
    	
    	try {

        	final OAuthResponse response = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError("bad_pass_or_user")
                    .setErrorDescription("invalid username or password")
                    .buildJSONMessage();

        	return new ResponseEntity<String>(response.getBody(), HttpStatus.BAD_REQUEST);
        	
		} catch (OAuthSystemException e) {

	    	return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	    	
		}
    
    }
    
    public ResponseEntity<String> buildUnauthorizedResponse() {
    	
    	try {

        	final OAuthResponse response = OAuthRSResponse
                    .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                    .setRealm(OAuthCommon.RESOURCE_SERVER_NAME)
                    .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                    .buildHeaderMessage();

        	final MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
        	
        	header.add(OAuth.HeaderType.WWW_AUTHENTICATE, response.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
        	
        	return new ResponseEntity<String>(response.getBody(), header, HttpStatus.BAD_REQUEST);
            
		} catch (OAuthSystemException e) {

	    	return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	    	
		}
    	
    }
    
    public ResponseEntity<String> buildBadSQLResponse() {
    	
    	try {

        	final OAuthResponse response = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError(SQLError.SQL_STATE_GENERAL_ERROR)
                    .setErrorDescription("SQL syntax chyba alebo ina datova chyba pravdepodobne na strane servera")
                    .buildJSONMessage();
        	
        	return new ResponseEntity<String>(response.getBody(), HttpStatus.BAD_REQUEST);
            
		} catch (OAuthSystemException e) {

	    	return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	    	
		}
    	
    }
    
    public ResponseEntity<String> buildBadOAuthResponse() {
    	
    	try {

        	final OAuthResponse response = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError(OAuthError.TokenResponse.INVALID_GRANT)
                    .setErrorDescription("Zly request v hlavicke")
                    .buildJSONMessage();
        	
        	return new ResponseEntity<String>(response.getBody(), HttpStatus.BAD_REQUEST);
        
		} catch (OAuthSystemException e) {

	    	return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	    	
		}
    	
    }
    
    public ResponseEntity<String> buildInvalidRefreshResponse() {
    	
    	try {

        	final OAuthResponse response = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError(OAuthError.TokenResponse.INVALID_GRANT)
                    .setErrorDescription("refresh token je nespravny")
                    .buildJSONMessage();
        	
        	return new ResponseEntity<String>(response.getBody(), HttpStatus.BAD_REQUEST);
        	
		} catch (OAuthSystemException e) {

	    	return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	    	
		}
    	
    }
    
    public ResponseEntity<String> buildSimpleBadRequestResponse() {
    	
    	try {

        	final OAuthResponse response = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError("simple_request_error")
                    .setErrorDescription("nespravny request")
                    .buildJSONMessage();

        	return new ResponseEntity<String>(response.getBody(), HttpStatus.BAD_REQUEST);
        	
		} catch (OAuthSystemException e) {

	    	return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	    	
		}

    }
    
    public ResponseEntity<String> buildHashProblemResponse() {
    	
    	try {

        	final OAuthResponse response = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError("hash_problem")
                    .setErrorDescription("problem s generovanim hashu")
                    .buildJSONMessage();

        	return new ResponseEntity<String>(response.getBody(), HttpStatus.BAD_REQUEST);
        
		} catch (OAuthSystemException e) {

	    	return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	    	
		}

    }
    
    public ResponseEntity<String> buildInvalidRegistrationResponse() {
    	
    	try {

        	final OAuthResponse response = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError("registration_error")
                    .setErrorDescription("problem pri registracii - prazdne polie alebo user/mail uz existuje")
                    .buildJSONMessage();

        	return new ResponseEntity<String>(response.getBody(), HttpStatus.BAD_REQUEST);
        
		} catch (OAuthSystemException e) {

	    	return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	    	
		}

    }
    
    public ResponseEntity<String> buildInvalidJSONResponse() {
    	
    	try {

        	final OAuthResponse response = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError("bad_json_format")
                    .setErrorDescription("problem pri generovani JSONu")
                    .buildJSONMessage();

        	return new ResponseEntity<String>(response.getBody(), HttpStatus.BAD_REQUEST);
        
		} catch (OAuthSystemException e) {

	    	return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	    	
		}

    }
	
}
