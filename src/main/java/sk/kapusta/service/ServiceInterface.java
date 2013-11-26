/**
 * 
 */
package sk.kapusta.service;

import java.sql.SQLException;

/**
 * @author Mario Kapusta - kapusta@eglu.sk
 *
 */
public interface ServiceInterface {

	public int getNextId() throws SQLException;
	
}