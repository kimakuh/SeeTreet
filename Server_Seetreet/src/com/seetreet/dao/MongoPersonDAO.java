package com.seetreet.dao;

import org.bson.types.ObjectId;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.seetreet.bean.ArtistBean;
import com.seetreet.bean.ProviderBean;
import com.seetreet.bean.UserBean;
import com.seetreet.util.C;

public class MongoPersonDAO {
	
	public static boolean isArtist(String email , String token) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_USER);
		
		DBObject user = col.findOne(new BasicDBObject().append(UserBean.KEY_EMAIL, email)
													   .append(UserBean.KEY_TOKEN, new ObjectId(token)));
		
		
		DBObject artist = (DBObject)user.get(UserBean.KEY_ARTIST);
		
		if(artist == null) return false;		
		return true;
	}
	
	public static boolean isProvider(String email , String token) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_USER);
		
		DBObject user = col.findOne(new BasicDBObject().append(UserBean.KEY_EMAIL, email)
													   .append(UserBean.KEY_TOKEN, new ObjectId(token)));
		
		
		DBObject provider = (DBObject)user.get(UserBean.KEY_PROVIDER);
		
		if(provider == null) return false;
		return true;
	}
	
	/* 2014.11.20 
	 * developer : yw
	 * description : 유저 email 정보로 유저 정보 다라함.  
	 */ 
	public static JSONObject getUser(String email) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_USER);
				
		DBObject user = col.findOne(new BasicDBObject().append(UserBean.KEY_EMAIL, email));
		JSONObject result = null;
		if(user == null ) return null;
		try {
			result = new JSONObject(C.convertObjectId(user.toString()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return result;
	}
	
	/* 2014.11.20
	 * developer : yw
	 * description : artistId로 아트스트 달라함.
	 * 
	 * */
	public static JSONObject getArtist(String artistId) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_ARTIST);
		System.out.println(artistId);
		DBObject artist = col.findOne(new BasicDBObject().append(ArtistBean.KEY_ID, new ObjectId(artistId)));
		System.out.println(artist.toString());
		JSONObject result = null;
		
		if(artist == null) return result;
		try {
			result = new JSONObject(C.convertObjectId(artist.toString()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return result;
	}
	
	/* 2014.11.20
	 * developer : yw
	 * description : providerId로 아트스트 달라함.
	 * 
	 * */
	public static JSONObject getProvider(String providerId) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_PROVIDER);
	
		DBObject provider = col.findOne(new BasicDBObject().append(ProviderBean.KEY_ID, new ObjectId(providerId)));
		JSONObject result = null;
		if(provider == null) return result;
		
		try {			
			result = new JSONObject(C.convertObjectId(provider.toString()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return result;
	}
}
