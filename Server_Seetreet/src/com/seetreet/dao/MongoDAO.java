package com.seetreet.dao;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.seetreet.bean.ApiContentBean;
import com.seetreet.bean.ProviderBean;
import com.seetreet.bean.UserBean;
import com.seetreet.bean.UserLoginBean;
import com.seetreet.bean.content.ContentProviderBean;

public class MongoDAO {
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
		
		return true;
	}
	
	/*param : apiContentIdListBean (email, name, age, phone, pw)
	 * description : 새로운 public Api 삽입
	 * 
	 * */
	
	public static boolean insertPublicApiContent(ApiContentBean bean) {
	
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_CONTENTS);		
	
		
		//if(col.findOne(new BasicDBObject(UserBean.KEY_EMAIL, bean.getEmail())) != null)
			//return false;
		
		
		BasicDBObject newContent 
			= new BasicDBObject()
					.append(ApiContentBean.KEY_CONTENTTITLE, bean.getContentName())
					.append(ApiContentBean.KEY_CONTENTID, bean.getContentId())
					.append(ApiContentBean.KEY_GENRE, bean.getContentGenre())
					.append(ApiContentBean.KEY_TYPE, bean.getContentType())
					.append(ApiContentBean.KEY_ARTIST, bean.getArtist())
					.append(ApiContentBean.KEY_PROVIDER, bean.getProviderObject())
					.append(ApiContentBean.KEY_MODIFIEDTIME, bean.getModifiedTime())
					.append(ApiContentBean.KEY_OVERVIEW, bean.getOverview())
					.append(ApiContentBean.KEY_ISCONFIRMED_ARTISTID, bean.getConfirmed_artistId())
					.append(ApiContentBean.KEY_CONFIRMEDTIME, bean.getConfirmedTime())
					.append(ApiContentBean.KEY_FINISHEDTIME, bean.getIsFinishedTime())
					.append(ApiContentBean.KEY_EVENTSTARTDATE, bean.getEventStartDate())
					.append(ApiContentBean.KEY_EVENTENDDATE , bean.getEventEndDate());
					
		//System.out.println(newContent.toString());
		col.insert(newContent);
		
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
		if(col.findOne(new BasicDBObject(ApiContentBean.KEY_CONTENTID, contentId)) != null)
			return false;	

		//없으면 true
		return true;
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
			
			list.add(0, obj.getLocation().getLocation()[0]);
			list.add(1, obj.getLocation().getLocation()[1]);
			BasicDBObject local = new BasicDBObject();
			local.append("type", "Point")
				.append("coordinates", list);

			provider.append("providerImage", obj.getImages())
					.append("contentType", obj.getContentType())
					.append("favoriteGenre", obj.getPublicGenre())
					.append("location", local)
					.append("StoreTitle", obj.getStoreTitle())
					.append("StoreType", obj.getStoreType())
					.append("description", obj.getDescription())
					.append("modifiedTime", obj.getModTime());
			col.insert(provider);
			return provider;
		}catch(Exception e){
			e.printStackTrace();
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
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_USER);
		
		BasicDBObject user
			= new BasicDBObject()
					.append(UserBean.KEY_EMAIL, email)
					.append(UserBean.KEY_TOKEN, new ObjectId(token));
		
		
		if(col.findOne(user) != null) 
			return true;
				
		return false;
	}
	
	/*param : email , pw
	 * description : 
	 * notice : MongoDB의 _id 값을 토큰 값으로 사용함. 다만 JAVA에서 받은 ObjectId 객체를 toString으로 하면 변환 됨 */
	public static UserLoginBean hasUser(String email, String pw) {
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
	
	public static ContentProviderBean enrollContent(ContentProviderBean bean) {		
		ContentProviderBean result = null;
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_CONTENT);
		
		BasicDBObject content
			= new BasicDBObject()
					.append(ContentProviderBean.KEY_TITLE, bean.getTitle())
					.append(ContentProviderBean.KEY_TYPE, bean.getType())
					.append(ContentProviderBean.KEY_PROVIDER, bean.getProviderId())
					.append(ContentProviderBean.KEY_GENRE, bean.getGenre())
					.append(ContentProviderBean.KEY_STARTTIME, bean.getStartTime())
					.append(ContentProviderBean.KEY_ENDTIME, bean.getEndTime());
		
		ObjectId id = (ObjectId) col.insert(content).getUpsertedId();
		
		DBObject res = col.findOne(new BasicDBObject(ContentProviderBean.KEY_ID, id));
		try {
			result = new ContentProviderBean(
					(String)res.get(ContentProviderBean.KEY_TITLE), 
					(String)res.get(ContentProviderBean.KEY_GENRE),
					(int)res.get(ContentProviderBean.KEY_TYPE), 
					(int)res.get(ContentProviderBean.KEY_STARTTIME), 
					(int)res.get(ContentProviderBean.KEY_ENDTIME), 
					res.get(ContentProviderBean.KEY_ID).toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
