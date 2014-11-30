package com.seetreet.dao;


import java.util.ArrayList;

import javax.swing.text.AbstractDocument.Content;

import java.util.List;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.seetreet.bean.ContentPublicApiBean;
import com.seetreet.bean.ArtistBean;
import com.seetreet.bean.GenreBean;
import com.seetreet.bean.LocationBean;
import com.seetreet.bean.ProviderBean;
import com.seetreet.bean.ReplyBean;
import com.seetreet.bean.UserBean;
import com.seetreet.bean.UserLoginBean;
import com.seetreet.bean.content.ContentBean;
import com.seetreet.bean.content.ContentProviderBean;
import com.seetreet.util.C;

public class MongoDAO {
	public static final int MAX_LIMIT = 7;
	public static boolean insertUser() {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection("user");
		
		return false;
	}
	
	/*param : UserBean (email, name, age, phone, pw)
	 * description : 회원 가입하는 쿼리
	 * 
	 * */
	public static boolean signinUser(UserBean bean) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_USER);		
		
		if(col.findOne(new BasicDBObject(UserBean.KEY_EMAIL, bean.getEmail())) != null)
			return false;
		
		BasicDBObject newUser 
			= new BasicDBObject()
					.append(UserBean.KEY_EMAIL, bean.getEmail())
					.append(UserBean.KEY_AGE, bean.getAge())
					.append(UserBean.KEY_NAME, bean.getName())
					.append(UserBean.KEY_PHONE , bean.getPhone())
					.append(UserBean.KEY_PW, bean.getPw());
		//System.out.println(newUser.toString());
		col.insert(newUser);
		
		MongoRecDAO.insertDefaultUserGenome(newUser.getObjectId(UserBean.KEY_TOKEN).toString());
		
		return true;
	}
	
	/*param : apiContentIdListBean (email, name, age, phone, pw)
	 * description : 새로운 public Api 삽입
	 * 
	 * */
	
	public static boolean insertPublicApiContent(ContentPublicApiBean bean, BasicDBObject provObj) {
	
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_CONTENTS);		
	
		BasicDBObject genre = new BasicDBObject().append(GenreBean.KEY_CATEGORY, bean.getContentGenre().getCategory())
												 .append(GenreBean.KEY_DETAIL, bean.getContentGenre().getDetailGenre());

		if(bean != null && provObj != null){
			BasicDBList artists = new BasicDBList();
			artists = null;
			BasicDBObject newContent 
			= new BasicDBObject()
					.append(ContentPublicApiBean.KEY_CONTENTTITLE, bean.getContentName())
					.append(ContentPublicApiBean.KEY_GENRE, genre)
					.append(ContentPublicApiBean.KEY_TYPE, bean.getContentType())
					.append(ContentPublicApiBean.KEY_EVENTSTARTDATE, bean.getEventStartDate())
					.append(ContentPublicApiBean.KEY_EVENTENDDATE , bean.getEventEndDate())
					.append(ContentPublicApiBean.KEY_LIKECOUNT, bean.getLikeCount())
					.append(ContentPublicApiBean.KEY_CONFIRMEDTIME, bean.getConfirmedTime())
					.append(ContentPublicApiBean.KEY_FINISHEDTIME, bean.getIsFinished())
					.append(ContentPublicApiBean.KEY_PROVIDER, provObj)
					.append(ContentPublicApiBean.KEY_ARTIST, artists)
					.append(ContentPublicApiBean.KEY_ISCONFIRMED_ARTISTID, bean.getConfirmed_artistId())
					.append(ContentPublicApiBean.KEY_CONTENTID, bean.getContentId());
			
			col.insert(newContent);
		}else
			return false;
		//System.out.println(newContent.toString());
		
		
		return true;
	}
	
	/*param : checkPublicApiContentId(int contentId)
	 * description : 기존에 있는 contentId 값을 확인함.
	 * 
	 * */
	public static boolean checkPublicApiContentId(int contentId) {
		
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_CONTENTS);		
	
		//같은게 있으면 false
		if(col.findOne(new BasicDBObject(ContentPublicApiBean.KEY_CONTENTID, String.valueOf(contentId))) != null){
			System.out.println("Exist ContentId : "+contentId);
			return false;	
		}
			

		//없으면 true
		return true;
	}

	
	/*param : providerId
	 * description : enrollContentByProvider에서 호출, 
	 * 				 providerId를 통해서 체크하고 provider객체 반환.
	 * 
	 * */
	public static DBObject checkProviderId(String providerId) {
		DBObject res = null;
		DBObject res2 = null;
		try{
			DB db = MongoDB.getDB();
			DBCollection col = db.getCollection(MongoDB.COLLECTION_USER);		
			
			BasicDBObject findQuery = new BasicDBObject(UserBean.KEY_TOKEN, new ObjectId(providerId));		
			if((res = col.findOne(findQuery)) != null){
				res2 = (DBObject) res.get("user_provider");
			}
			else
				res2 = null;
		}catch(Exception e){
			e.printStackTrace();
			
		}		
		return res2;
	}
	
	
	/*param : insertPublicProvider(ProviderBean obj)
	 * description : 새로운 공공 api provider 객체 삽입.
	 * 
	 * */
	public static BasicDBObject insertPublicProvider(ProviderBean obj) {
		
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_PROVIDER);	
		try{
			BasicDBObject provider = new BasicDBObject();
			
			BasicDBList list = new BasicDBList();
			BasicDBObject local = new BasicDBObject();
			if(obj.getLocation() != null){
				list.add(obj.getLocation().getLatitude());
				list.add(obj.getLocation().getLongitude());
				local.append(LocationBean.KEY_NAME, obj.getAddress())
					 .append(LocationBean.KEY_DESCRIPT, obj.getStoreTitle())
					 .append("type", "Point")
					 .append(LocationBean.KEY_COORDINATE, list);
			}
			else{
				local = null;
			}
			
			BasicDBList genres = new BasicDBList();
			for(GenreBean genre : obj.getFavoriteGenre()) {
				genres.add(new BasicDBObject()
							   .append(GenreBean.KEY_CATEGORY, genre.getCategory())
							   .append(GenreBean.KEY_DETAIL, genre.getDetailGenre())
						  );
			}
			
			provider.append("providerImage", obj.getImages())
					.append("contentType", obj.getContentType())
					.append("favoriteGenre", genres)
					.append("location", local)
					.append("StoreTitle", obj.getStoreTitle())
					.append("StoreType", obj.getStoreType())
					.append("description", obj.getDescription())
					.append("modifiedTime", obj.getModTime())
					.append("StoreAddress", obj.getAddress());
			col.insert(provider);
			return provider;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error Title : " + obj.getStoreTitle());
			//System.out.println(obj.getStoreTitle());
			//System.out.println(obj.getProviderId());
			return null;
		}
	}
	
	/*param : email , pw
	 * description : 로그인 쿼리
	 * notice : MongoDB의 _id 값을 토큰 값으로 사용함. 다만 JAVA에서 받은 ObjectId 객체를 toString으로 하면 변환 됨
	 * */
	public static UserLoginBean loginUser(String email, String pw) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_USER);
		
		BasicDBObject user
			= new BasicDBObject()
					.append(UserBean.KEY_EMAIL, email)
					.append(UserBean.KEY_PW, pw);
		
		DBObject res;
		if((res = col.findOne(user)) != null) {
			UserBean bean 
				= new UserBean(
						(String)res.get(UserBean.KEY_EMAIL),
						(String)res.get(UserBean.KEY_NAME),
						(int)res.get(UserBean.KEY_AGE),
						(String)res.get(UserBean.KEY_PHONE),
						(String)res.get(UserBean.KEY_MODTIME),
						res.get(UserBean.KEY_TOKEN).toString());
			
			return bean;
		}
				
		return null;
	}
	
	/*param : email , token
	 * description :
	 * 
	 * */
	public static boolean isUser(String email, String token) {
		if(email == null || token == null)return false;
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_USER);
		
		BasicDBObject user
			= new BasicDBObject()
					.append(UserBean.KEY_EMAIL, email)
					.append(UserBean.KEY_TOKEN, new ObjectId(token));
		
		System.out.println(user.toString());
		if(col.findOne(user) != null) 
			return true;
				
		return false;
	}
	
	/* 2014.11.20 
	 * */
	public static boolean hasUser(String email) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_USER);
		
		BasicDBObject user
			= new BasicDBObject()
					.append(UserBean.KEY_EMAIL, email);		
		
		if(col.findOne(user) != null) return true;				
		return false;
	}
	
	/* 2014.11.20
	 * description : MongoPersonDAO.java 로 이동.
	 * 
	 * */
//	public static boolean isArtist(String email , String token) {
//		DB db = MongoDB.getDB();
//		DBCollection col = db.getCollection(MongoDB.COLLECTION_USER);
//		
//		DBObject user = col.findOne(new BasicDBObject().append(UserBean.KEY_EMAIL, email)
//													   .append(UserBean.KEY_TOKEN, new ObjectId(token)));
//		
//		
//		DBObject artist = (DBObject)user.get(UserBean.KEY_ARTIST);
//		
//		if(artist == null) return false;		
//		return true;
//	}
//	
//	public static boolean isProvider(String email , String token) {
//		DB db = MongoDB.getDB();
//		DBCollection col = db.getCollection(MongoDB.COLLECTION_USER);
//		
//		DBObject user = col.findOne(new BasicDBObject().append(UserBean.KEY_EMAIL, email)
//													   .append(UserBean.KEY_TOKEN, new ObjectId(token)));
//		
//		
//		DBObject provider = (DBObject)user.get(UserBean.KEY_PROVIDER);
//		
//		if(provider == null) return false;
//		return true;
//	}
	
	public static boolean joinArtist(ArtistBean bean , String token) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_ARTIST);
		
		BasicDBList images = new BasicDBList();
		for(String image : bean.getArtistImages()) {
			images.add(image);
		}
		
		BasicDBList locations = new BasicDBList();
		for(LocationBean loc : bean.getFavoriteLocation()) {
			BasicDBList coords = new BasicDBList();
			coords.put(LocationBean.LONG , loc.getLongitude());
			coords.put(LocationBean.LAT , loc.getLatitude());			
			
			locations.add(new BasicDBObject()
							  .append(LocationBean.KEY_NAME, loc.getName())
							  .append(LocationBean.KEY_DESCRIPT, loc.getDescription())
							  .append("type", "Point")
							  .append(LocationBean.KEY_COORDINATE, coords)
						 );
		}
		
		BasicDBList genres = new BasicDBList();
		for(GenreBean genre : bean.getGenre()) {
			genres.add(new BasicDBObject()
						   .append(GenreBean.KEY_CATEGORY, genre.getCategory())
						   .append(GenreBean.KEY_DETAIL, genre.getDetailGenre())
					  );
		}
		
		BasicDBObject artist = new BasicDBObject()
								   .append(ArtistBean.KEY_NAME, bean.getName())
								   .append(ArtistBean.KEY_IMAGES, images)
								   .append(ArtistBean.KEY_DESCRIPT, bean.getDescription())
								   .append(ArtistBean.KEY_LOCATIONS, locations)
								   .append(ArtistBean.KEY_VIDEO, bean.getVideoUrl())
								   .append(ArtistBean.KEY_GENRE, genres)
								   .append(ArtistBean.KEY_MODTIME, bean.getModTime());
		
		col.insert(artist);
		
		DBCollection userCollection = db.getCollection(MongoDB.COLLECTION_USER);
		
		BasicDBObject findQuery = new BasicDBObject()
									  .append(UserBean.KEY_TOKEN, new ObjectId(token));

		
		BasicDBObject updateQuery = new BasicDBObject()
										.append("$set", new BasicDBObject(UserBean.KEY_ARTIST, artist));
		
		userCollection.update(findQuery, updateQuery);							   
		
		return true;
	}
	
	
	public static boolean joinProvider(ProviderBean bean , String token) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_PROVIDER);
		
		BasicDBList images = new BasicDBList();
		for(String image : bean.getImages()) {
			images.add(image);
		}				
		
		LocationBean loc = bean.getLocation();
		BasicDBList coords = new BasicDBList();		
		coords.put(LocationBean.LAT, loc.getLatitude());
		coords.put(LocationBean.LONG, loc.getLongitude());	
		
		BasicDBObject location = new BasicDBObject()
				  .append(LocationBean.KEY_NAME, loc.getName())
				  .append(LocationBean.KEY_DESCRIPT, loc.getDescription())
				  .append("type", "Point")
				  .append(LocationBean.KEY_COORDINATE, coords);
						
		BasicDBList genres = new BasicDBList();
		for(GenreBean genre : bean.getFavoriteGenre()) {
			genres.add(new BasicDBObject()
						   .append(GenreBean.KEY_CATEGORY, genre.getCategory())
						   .append(GenreBean.KEY_DETAIL, genre.getDetailGenre())
					  );
		}
		
		BasicDBObject provider = new BasicDBObject()
								   .append(ProviderBean.KEY_IMAGES, images)
								   .append(ProviderBean.KEY_DESCRIPT, bean.getDescription())
								   .append(ProviderBean.KEY_LOCATION, location)
								   .append(ProviderBean.KEY_TYPE, bean.getContentType())
								   .append(ProviderBean.KEY_GENRE, genres)
								   .append(ProviderBean.KEY_STORETITLE, bean.getStoreTitle())
								   .append(ProviderBean.KEY_STORETYPE, bean.getStoreType())
								   .append(ProviderBean.KEY_MODTIME, bean.getModTime())
								   .append(ProviderBean.KEY_ADDRESS, bean.getAddress());
		
		col.insert(provider);
		
		DBCollection userCollection = db.getCollection(MongoDB.COLLECTION_USER);
		
		BasicDBObject findQuery = new BasicDBObject()
									  .append(UserBean.KEY_TOKEN, new ObjectId(token));

		
		BasicDBObject updateQuery = new BasicDBObject()
										.append("$set", new BasicDBObject(UserBean.KEY_PROVIDER, provider));
		
		userCollection.update(findQuery, updateQuery);							   
		
		return true;
	}
	
	/* date 		: 2014.10.12
	 * description 	: �꾩튂 媛믪뿉 �곕씪 ContentBean��異쒕젰�⑸땲��
	 * param		: double long, double lat
	 * notice		: �꾩옱 �ㅽ뿕�쇱븘 collection 'test_content' �ъ슜
	 * 					lat : 127.00111 , long : 37.26711
	 * */
	
	static double[] InGyae = {127.00111, 37.26711 };

	public static JSONArray getContentsByLocation(float l_lat, float l_long , int page) {
		JSONArray result = new JSONArray();		
		
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_CONTENTS);
		
		BasicDBList position = new BasicDBList();
		position.put(LocationBean.LAT, l_lat);
		position.put(LocationBean.LONG, l_long);		
		DBCursor iter = 
				col.find(new BasicDBObject("provider.location", 
							new BasicDBObject("$near" , 
								new BasicDBObject("$geometry", 
										new BasicDBObject("type","Point")
										.append("coordinates", position)
								)
							)
						)
						.append(ContentBean.KEY_FINISHIED, false)
						.append(ContentBean.KEY_C_ARTIST, new BasicDBObject("$ne", null))
				).skip((page-1)*MAX_LIMIT).limit(MAX_LIMIT);
			
		try {
			while(iter.hasNext()) {
				DBObject item = iter.next();
				String str = item.toString().replaceAll(C.REX_ID, "$1");				
				result.put(new JSONObject(str));
			}		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		
		return result;
	}
	
	public static JSONArray getReplyByContentId(String contentId, int page) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_REPLY);
		
		DBCursor iter =col.find(new BasicDBObject()
									.append(ReplyBean.KEY_CONTENTID, contentId))
							.skip((page-1)*MAX_LIMIT).limit(MAX_LIMIT);
		
		JSONArray res = new JSONArray();
		
		while(iter.hasNext()) {
			DBObject obj = iter.next();
			String sObj = C.convertObjectId(obj.toString());
			
			try {
				res.put(new JSONObject(sObj));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return res;
	}
	
	public static JSONObject enrollReply(JSONObject bean) throws Exception{
		DB db = MongoDB.getDB();
		DBCollection contentCol = db.getCollection(MongoDB.COLLECTION_CONTENTS);
		DBCollection contentRe = db.getCollection(MongoDB.COLLECTION_REPLY);
		
		BasicDBObject rep = new BasicDBObject();
		
		System.out.println(bean.toString());
		
		rep.append(ReplyBean.KEY_CONTENTID, bean.getString(ReplyBean.KEY_CONTENTID))
		   .append(ReplyBean.KEY_REPLYIMAGE, bean.getString(ReplyBean.KEY_REPLYIMAGE))
		   .append(ReplyBean.KEY_REPLYTEXT, bean.getString(ReplyBean.KEY_REPLYTEXT))
		   .append(ReplyBean.KEY_USEREMAIL, bean.getString(ReplyBean.KEY_USEREMAIL))
		   .append(ReplyBean.KEY_MODTIME, bean.getString(ReplyBean.KEY_MODTIME));
		
		contentRe.insert(rep);
		
//		rep.append(ReplyBean.KEY_ID, rep.get(ReplyBean.KEY_ID).toString());
		
		BasicDBObject findQuery = new BasicDBObject()
									  .append(ContentBean.KEY_ID, new ObjectId(bean.getString(ReplyBean.KEY_CONTENTID)));
		BasicDBObject updateQuery 
		= new BasicDBObject()
			  .append("$push", new BasicDBObject()
								   .append(ContentBean.KEY_REPLY, rep)
					 );
		
		contentCol.update(findQuery, updateQuery);		
		JSONObject json = new JSONObject();		
		try {
			json.put(ReplyBean.KEY_ID , rep.get(ReplyBean.KEY_ID).toString())
				.put(ReplyBean.KEY_CONTENTID , rep.get(ReplyBean.KEY_CONTENTID))
				.put(ReplyBean.KEY_REPLYIMAGE , rep.get(ReplyBean.KEY_REPLYIMAGE))
				.put(ReplyBean.KEY_REPLYTEXT , rep.get(ReplyBean.KEY_REPLYTEXT))
				.put(ReplyBean.KEY_USEREMAIL , rep.get(ReplyBean.KEY_USEREMAIL));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	public static boolean deleteReply(ReplyBean bean) {		
		DB db = MongoDB.getDB();
		DBCollection colReply = db.getCollection(MongoDB.COLLECTION_REPLY);
		DBCollection colContent = db.getCollection(MongoDB.COLLECTION_CONTENTS);
		
		System.out.println("bean : " + bean.getReplyId() + " , " +bean.getUserEmail() + " , " + bean.getContentId());;
		
		colReply.remove(new BasicDBObject()
							.append(ReplyBean.KEY_ID, new ObjectId(bean.getReplyId()))
							.append(ReplyBean.KEY_USEREMAIL, bean.getUserEmail()));
		
		BasicDBObject findQuery = new BasicDBObject()
		  							  .append(ContentBean.KEY_ID, new ObjectId(bean.getContentId()));
		BasicDBObject updateQuery = new BasicDBObject()
										.append("$pull", new BasicDBObject()
															 .append(ContentBean.KEY_REPLY, 
																	 new BasicDBObject()
															 			 .append(ReplyBean.KEY_ID, new ObjectId(bean.getReplyId()))
															 			 .append(ReplyBean.KEY_USEREMAIL, bean.getUserEmail())
																	 )
												);
		colContent.update(findQuery, updateQuery);		
		
		return true;
	}
	
	public static int countReplayByContentId(String contentId) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_REPLY);
		
		long count = col.count(new BasicDBObject(ReplyBean.KEY_CONTENTID, contentId));
		return (int)count;
	}
	
	public static JSONObject getArtist(String email , String token) throws JSONException {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_USER);
		
		DBObject user = col.findOne(new BasicDBObject().append(UserBean.KEY_EMAIL, email)
													   .append(UserBean.KEY_TOKEN, new ObjectId(token)));
		
		
		DBObject artist = (DBObject)user.get(UserBean.KEY_ARTIST);		
		
		return new JSONObject(artist.toString());
	}
	
	public static JSONArray searchContentByLocationFromArtist(double l_lat,	double l_long, int page) {
//		ContentProviderBean[] res = null;
		JSONArray res = new JSONArray();

		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_CONTENTS);

		BasicDBList position = new BasicDBList();
		position.put(LocationBean.LAT, l_lat);
		position.put(LocationBean.LONG, l_long);
		
		
		DBCursor iter = col.find(
						new BasicDBObject("provider.location", 
								new BasicDBObject("$near", 
										new BasicDBObject("$geometry",
												new BasicDBObject("type", "Point")
													.append("coordinates", position))))
							.append(ContentBean.KEY_FINISHIED, false)
							.append(ContentBean.KEY_C_ARTIST, null))
						.skip((page - 1) * MAX_LIMIT).limit(MAX_LIMIT);

		System.out.println(iter.size());
		try {
			while(iter.hasNext()) {
				DBObject item = iter.next();
				item.put(ContentBean.KEY_ARTIST	, new BasicDBList());
				String sItem = C.convertObjectId(item.toString());
				res.put(new JSONObject(sItem));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}			
		
		return res;
	}


	//public static boolean insertContentByProvider(String _contentTitle, String _contentStartTime, String _contentEndTime, String _providerId, ProviderBean _tempProvider){
	public static JSONObject insertContentByProvider(String _contentTitle, String _contentStartTime, String _contentEndTime, DBObject _providerObject){
	
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_CONTENTS);
		BasicDBObject content = new BasicDBObject();
		
		content.append(ContentProviderBean.KEY_TITLE, _contentTitle)
		   	   .append(ContentProviderBean.KEY_STARTTIME, _contentStartTime)
		   	   .append(ContentProviderBean.KEY_ENDTIME, _contentEndTime)
			   .append(ContentProviderBean.KEY_PROVIDER, _providerObject)
			   .append(ContentBean.KEY_C_ARTIST, null)
			   .append(ContentBean.KEY_FINISHIED, false);
		
		col.insert(content);
		JSONObject json = new JSONObject();		
		try {
			json.put(ContentProviderBean.KEY_ID , content.get(ContentProviderBean.KEY_ID))
				.put(ContentProviderBean.KEY_TITLE , content.get(ContentProviderBean.KEY_TITLE))
				.put(ContentProviderBean.KEY_STARTTIME , content.get(ContentProviderBean.KEY_STARTTIME))
				.put(ContentProviderBean.KEY_ENDTIME , content.get(ContentProviderBean.KEY_ENDTIME))
				.put(ContentProviderBean.KEY_PROVIDER , content.get(ContentProviderBean.KEY_PROVIDER))
				.put(ContentBean.KEY_FINISHIED , false);
		}catch(Exception e){
			e.printStackTrace();
		}		
		return json;
	
	}	
	
	
 
	public static JSONArray searchContentByProvider(String user_id , int page , boolean isHistory){
		JSONArray res=null;
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_CONTENTS);
		DBCollection userCol = db.getCollection(MongoDB.COLLECTION_USER);
		
		try{
			BasicDBObject findQuery = new BasicDBObject(UserBean.KEY_TOKEN, new ObjectId(user_id));
			DBObject provObj;
			String _id;
			if((provObj = userCol.findOne(findQuery)) != null){
				provObj = (DBObject) provObj.get("user_provider");
				_id = provObj.get("_id").toString();
			}
			else
				_id = null;
				
			DBCursor iter = 
					col.find(new BasicDBObject("provider._id", new ObjectId(_id)))
					   .skip((page - 1) * MAX_LIMIT).limit(MAX_LIMIT);
						
			res = new JSONArray();
			
			
			int i = 0;
			while(iter.hasNext()){
				DBObject obj = iter.next();
				if(isHistory) obj.put(ContentBean.KEY_ARTIST, new BasicDBList());
				String str_obj = C.convertObjectId(obj.toString());
				res.put(new JSONObject(str_obj));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
			
		return res;
	}
	
	public static boolean deleteContentByProvider(String _contentId){
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_CONTENTS);
		JSONObject res;
		int nCount = 0;
		try {
			res = new JSONObject(col.remove(new BasicDBObject("_id", new ObjectId(_contentId))).toString());
			nCount = res.getInt("n");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		if(nCount != 0)
			return true;
		else
			return false;
	}
	
	public static boolean insertCandidateByArtistWithContentId(String contentId , String email) {
		DB db = MongoDB.getDB();
		DBCollection contentCol = db.getCollection(MongoDB.COLLECTION_CONTENTS);
		DBCollection userCol = db.getCollection(MongoDB.COLLECTION_USER);
		
		BasicDBObject findArtistQuery = new BasicDBObject(UserBean.KEY_EMAIL, email);		
		DBObject user = userCol.findOne(findArtistQuery);
		DBObject artist = (DBObject)user.get(UserBean.KEY_ARTIST);
					
		if(artist == null) return false;
		
		BasicDBObject findArtistInContentQuery = new BasicDBObject();
		findArtistInContentQuery.put(ContentBean.KEY_ID, new ObjectId(contentId));
		findArtistInContentQuery.put(ArtistBean.KEY_ID, artist.get(ArtistBean.KEY_ID));
		DBObject isInserted 
		= contentCol.findOne(
				new BasicDBObject().append(ContentBean.KEY_ID , new ObjectId(contentId)),
				new BasicDBObject()
					.append(ContentBean.KEY_ARTIST, 
							new BasicDBObject()
								.append("$elemMatch",
										new BasicDBObject()
											.append(ArtistBean.KEY_ID, artist.get(ArtistBean.KEY_ID))
										)								
							)				
				);		
		
		if(isInserted != null) return false;
		
		BasicDBObject findContentQuery 		= new BasicDBObject();
		findContentQuery.append(ContentBean.KEY_ID , new ObjectId(contentId));
		BasicDBObject updateContentQuery 	= new BasicDBObject();
		updateContentQuery.append("$push", new BasicDBObject(ContentBean.KEY_ARTIST, artist));
				
		contentCol.update(findContentQuery, updateContentQuery);	
			
		return true;
	}
	
	public static boolean deleteCandidateByArtistWithContentId(String contentId , String email) {
		DB db = MongoDB.getDB();
		DBCollection contentCol = db.getCollection(MongoDB.COLLECTION_CONTENTS);
		DBCollection userCol = db.getCollection(MongoDB.COLLECTION_USER);
		
		BasicDBObject findArtistQuery = new BasicDBObject(UserBean.KEY_EMAIL, email);		
		DBObject user = userCol.findOne(findArtistQuery);
		DBObject artist = (DBObject)user.get(UserBean.KEY_ARTIST);
		
		if(artist == null) return false;		
		
		BasicDBObject findContentQuery = new BasicDBObject();
		findContentQuery.append(ContentBean.KEY_ID , new ObjectId(contentId));
		BasicDBObject updateContentQuery = new BasicDBObject();
		updateContentQuery.append("$pull", new BasicDBObject(ContentBean.KEY_ARTIST, artist));
		
		contentCol.update(findContentQuery, updateContentQuery);		
		return true;
	}
	
	public static int countLikesByContentId(String contentId) {		
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_CONTENTS);
		
		DBObject content = col.findOne(new BasicDBObject().append(ContentBean.KEY_ID, new ObjectId(contentId)));
		
		if(content == null) return -1;
		
		return (int)content.get(ContentBean.KEY_LIKECOUNT);		
	}
	
	public static boolean updateLikesByContentId(String token, String contentId, boolean isLike) {
		if(isLike == MongoRecDAO.hasLove(token, contentId)) return false;
		
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_CONTENTS);
		
		int inc = isLike?1:-1;
		
		BasicDBObject findQuery = new BasicDBObject(ContentBean.KEY_ID, new ObjectId(contentId));
		BasicDBObject updateQuery = new BasicDBObject().append("$inc", new BasicDBObject().append(ContentBean.KEY_LIKECOUNT, inc));
		
		col.update(findQuery, updateQuery);		
		
		DBObject content = col.findOne(new BasicDBObject(ContentBean.KEY_ID, new ObjectId(contentId)));		
		DBObject genre = (DBObject)content.get(ContentBean.KEY_GENRE);
		String property = (String)genre.get(GenreBean.KEY_DETAIL);	
		if(isLike) {
			MongoRecDAO.plusUserLove(token, property);
			MongoRecDAO.loveContent(token, contentId);
		} else {			
			MongoRecDAO.minusUserLove(token, property);
			MongoRecDAO.hateContent(token, contentId);
		}
		
		return true;
	}	
	
	public static JSONArray searchApplicationsByArtist(String artistId , int page) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_CONTENTS);
		
		DBCursor iter = col.find(new BasicDBObject()
								.append(ContentBean.KEY_TYPE, "SEETREET")
								.append(ContentBean.KEY_ARTIST , new BasicDBObject()
												 .append("$elemMatch", new BasicDBObject()
												 					   .append(ContentBean.KEY_ID, new ObjectId(artistId))
												 		)
										)
								).skip((page-1)*MAX_LIMIT).limit(MAX_LIMIT);
		
		JSONArray res = new JSONArray();
		try {
			while(iter.hasNext()) {
				DBObject obj = iter.next();			
				obj.put(ContentBean.KEY_ARTIST, new BasicDBList());
				String sObj = C.convertObjectId(obj.toString());
				
				res.put(new JSONObject(sObj));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return res;
	}
	
	public static JSONArray searchContentHistory(String _id, boolean check){
		JSONArray res = new JSONArray();
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_CONTENTS);
		try{
			if(check == true){
				
				DBCursor iter = 
						col.find(new BasicDBObject("provider._id", new ObjectId(_id)));
				System.out.println("PROVIDER ITER COUNT : " + iter.count());
				while(iter.hasNext()){
					DBObject obj = iter.next();
					String resObj = obj.toString();
					res.put(new JSONObject(resObj));
				}
			}
			else if(check == false){
				DBCursor iter = 
						col.find(new BasicDBObject(ContentBean.KEY_C_ARTIST, _id));
				System.out.println("ARTIST ITER COUNT : " + iter.count());
				while(iter.hasNext()){
					DBObject obj = iter.next();
					res.put(new JSONObject(obj.toString()));
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println(res.toString());
		return res;
	}

	
	public static boolean enrollConfirmedArtist(String _artistId, String _contentId) throws Exception{
		DB db = MongoDB.getDB();
		DBCollection contentCol = db.getCollection(MongoDB.COLLECTION_CONTENTS);
		ObjectId contentId = new ObjectId(_contentId);
		
		
		BasicDBObject findQuery = new BasicDBObject()
									  .append(ContentBean.KEY_ID, contentId);
		BasicDBObject updateQuery 
		= new BasicDBObject()
			  .append("$set", new BasicDBObject()
								   .append(ContentBean.KEY_C_ARTIST, _artistId)
					 );
		contentCol.update(findQuery, updateQuery);
		
		BasicDBObject updateTimeQuery 
		= new BasicDBObject()
			  .append("$set", new BasicDBObject()
								   .append(ContentBean.KEY_C_TIME, C.currentDate())
					 );
		contentCol.update(findQuery, updateTimeQuery);
		return true;
	}
	
	public static boolean confirmContentGenreByArtist(String _contentId, String genre) throws Exception{
		DB db = MongoDB.getDB();
		DBCollection contentCol = db.getCollection(MongoDB.COLLECTION_CONTENTS);
		ObjectId contentId = new ObjectId(_contentId);
		
		JSONObject genreObj = new JSONObject(genre);
		BasicDBObject genreDBObject = new BasicDBObject().append(GenreBean.KEY_CATEGORY, genreObj.get(GenreBean.KEY_CATEGORY))
														 .append(GenreBean.KEY_DETAIL, genreObj.get(GenreBean.KEY_DETAIL));
		
		BasicDBObject findQuery = new BasicDBObject()
									  .append(ContentBean.KEY_ID, contentId);
		BasicDBObject updateQuery 
		= new BasicDBObject()
			  .append("$set", new BasicDBObject()
								   .append(ContentBean.KEY_GENRE, genreDBObject)
					 );
		contentCol.update(findQuery, updateQuery);
		return true;
	}

}
