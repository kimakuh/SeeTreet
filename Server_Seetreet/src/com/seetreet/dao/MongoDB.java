package com.seetreet.dao;

import java.net.UnknownHostException;
import java.util.Arrays;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.seetreet.util.C;

public class MongoDB {	
	public static final String COLLECTION_USER = "user";
	//public static final String COLLECTION_CONTENT = "content";
	public static final String COLLECTION_CONTENTS = "content";
	public static final String COLLECTION_PROVIDER = "provider";
	public static final String COLLECTION_ARTIST = "artist";
	public static final String COLLECTION_REPLY = "reply";
	
	
	public static final String TEST_COLLECTION_CONTENT = "content";	
	public static final String TEST_COLLECTION_PROVIDER = "provider";
	public static final String TEST_COLLECTION_ARTIST = "artist";
	public static final String TEST_COLLECTION_REPLY = "test_reply";
	
	public static final String COLLECTION_RECOMMEND_HISTORY = "test_recommend_history";
	public static final String COLLECTION_RECOMMEND_VALUE= "test_recommend_value";
	public static final String COLLECTION_SIMILARITY_MAP = "test_similarity_map";
	public static final String COLLECTION_LOVE_HISTORY = "test_love_history";	
	
	
	private static MongoClient mongo;
    private static DB db;
    
    private static MongoDB mongodb = new MongoDB();
    
    public static DB getDB() {    	
    	return db;
    }
    
    public static void close() {
    	mongo.close();
    }
    
    private MongoDB() {
    	
    	MongoCredential credential = MongoCredential.createMongoCRCredential(C.DBID, C.DBTABLE, C.DBPW);
        try {
		    mongo = new MongoClient(new ServerAddress(C.DBIP, C.DBPORT), Arrays.asList(credential));
		    
			db = mongo.getDB(C.DBTABLE);		
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

//    public DB getDB() {
//        return db;
//    }
//
//    public void close(){
//        mongo.close();
//    }
}
