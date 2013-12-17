package sk.kapusta.dao;

public class BaseDAO {
	
	public BaseDAO(){
		
	}
	
	public String inClauseBuilder(String query, int size){
		
		query = query + " IN ( ";
		for(int i = 0; i < size; i++){
			query = query + " ?, ";
		}
		query = query.substring(0, query.length()-2);
		query = query + " ) ";
		
		return query;
		
	}

}
