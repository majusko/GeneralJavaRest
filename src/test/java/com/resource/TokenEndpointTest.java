/**
 * 
 */
package com.resource;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Test;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import sk.kapusta.entity.OAuthCommon;
import sk.kapusta.entity.User;

/**
 * @author mario
 *
 */
public class TokenEndpointTest {


    private URL url;

    public TokenEndpointTest() throws MalformedURLException{
    	url = new URL("http://localhost:8088/GeneralJavaRest/");
    }

    @Test
    public void directTokenRequest() throws OAuthSystemException, OAuthProblemException {
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(url.toString() + "rest/token")
                    .setGrantType(GrantType.PASSWORD)
                    .setClientId(OAuthCommon.CLIENT_ID)
                    .setClientSecret(OAuthCommon.CLIENT_SECRET)
                    .setUsername("pejko")
                    .setPassword("pejko")
                    .setParameter("installation_id", "gbrgbr4bt6rb5r4b55br4")
                    .buildQueryMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthAccessTokenResponse oauthResponse = oAuthClient.accessToken(request);
            assertNotNull(oauthResponse.getAccessToken());
            assertNotNull(oauthResponse.getExpiresIn());
        
    }
 
    @Test
    public void userGenerate() throws JsonGenerationException, JsonMappingException, IOException{
    	User user = new User();
    	user.setLogin("testUser");
    	user.setPassword("thisIsMyPass");
    	user.setEmail("email@me.here");
    	final ObjectMapper mapper = new ObjectMapper();
    	String userJSON = mapper.writeValueAsString(user);
    	System.out.print(userJSON);
    }
}
