import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PrintHighestJerseyNumber();
		} catch(Exception e) {
			e.getMessage();
		}
	}
	
	/**
	 * 
	 * @throws NullPointerException
	 */
	public static void PrintHighestJerseyNumber() throws NullPointerException {
		Mongo m = null;
		try {
			 m = new Mongo("dbh63.mongolab.com", 27637);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		DB trendrr = m.getDB("trendrr");
		/* authentication line */
		trendrr.authenticate("", "".toCharArray());
		DBCollection players = trendrr.getCollection("players");
		
		
		BasicDBObject query = new BasicDBObject();
		query.put("team", "jets");
	    query.put("position", "wr");
	     
	    DBCursor cur = players.find(query);
	     
	    int maxJerseyNumber = 0;
	    String playerName = "";
	    	     
	    while(cur.hasNext()) {
	    	DBObject playerDetails = cur.next();
	    	String nameAndNumber = playerDetails.get("name").toString();
	    	String NameNumberTokens[] = nameAndNumber.split(",");
	    	try {
	    		if (NameNumberTokens.length != 2) {
	    			System.out.println("Invalid name field in the database");
		    		System.out.println("Name: "+NameNumberTokens[0]+" Jersey Number: "+NameNumberTokens[1]);
		    	}
	    	} catch(NullPointerException e) {
	    		System.out.println("NULL value in name field " + e);
	    		continue;
	    	} 
		    try {
		    		if (maxJerseyNumber < Integer.parseInt(NameNumberTokens[1].trim())) {
		    			maxJerseyNumber = Integer.parseInt(NameNumberTokens[1].trim());
		    			playerName = NameNumberTokens[0];
		    		}
		    	} catch (NumberFormatException e) {
	    		System.out.println("Invalid data in name field " + e);
	    		continue;
	    	}
	    }
	    System.out.println("Player Name: "+playerName);
	}
}