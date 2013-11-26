package sk.kapusta.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class JavaMD5Generator {

    public static String md5(String input) throws NoSuchAlgorithmException {
         
        if(input == null || input.trim().isEmpty()) {
        	return null;
        }
        
        final MessageDigest digest = MessageDigest.getInstance("MD5");
		         
	    digest.update(input.getBytes(), 0, input.length());
	 
	    final String md5 = new BigInteger(1, digest.digest()).toString(16);
	      
        return md5;
        
    }
	
}
