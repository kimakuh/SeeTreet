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
	public static final String COLLECTION_CONTENT = "content";
	public static final String COLLECTION_CONTENTS = "contents";
	public static final String COLLECTION_PROVIDER = "provider";
	
	
	public static final String TEST_COLLECTION_CONTENT = "test_content";	
	public static final String TEST_COLLECTION_PROVIDER = "test_provider";
	public static final String TEST_COLLECTION_ARTIST = "test_artist";
	public static final String TEST_COLLECTION_REPLY = "test_reply";
	
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
		    mongo = new MongoClient(new ServerAddress("localhost", C.DBPORT), Arrays.asList(credential));
		    
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
