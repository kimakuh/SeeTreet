package com.seetreet.dao;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.seetreet.bean.ApiContentBean;
import com.seetreet.bean.ArtistBean;
import com.seetreet.bean.GenreBean;
import com.seetreet.bean.LocationBean;
import com.seetreet.bean.ProviderBean;
import com.seetreet.bean.ReplyBean;
import com.seetreet.bean.UserBean;
import com.seetreet.bean.UserLoginBean;
import com.seetreet.bean.content.ContentBean;
import com.seetreet.bean.content.ContentProviderBean;

public class MongoDAO {
	public static final int MAX_LIMIT = 10;
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
			
			list.add(0, obj.getLocation().getLatitude());
			list.add(1, obj.getLocation().getLongitude());
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
	
	/* date 		: 2014.10.12
	 * description 	: provider ���섑빐 �깅줉�⑸땲��
	 * param		: provider Bean
	 * notice		:
	 * */
	public static ContentProviderBean enrollContent(ContentProviderBean bean) {		
		ContentProviderBean result = null;
//		DB db = MongoDB.getDB();
//		DBCollection col = db.getCollection(MongoDB.COLLECTION_CONTENTS);
//		
//		BasicDBObject content
//			= new BasicDBObject()
//					.append(ContentProviderBean.KEY_TITLE, bean.getTitle())
//					.append(ContentProviderBean.KEY_TYPE, bean.getType())
//					.append(ContentProviderBean.KEY_PROVIDER, bean.getProviderId())
//					.append(ContentProviderBean.KEY_GENRE, bean.getGenre())
//					.append(ContentProviderBean.KEY_STARTTIME, bean.getStartTime())
//					.append(ContentProviderBean.KEY_ENDTIME, bean.getEndTime());
//		
//		ObjectId id = (ObjectId) col.insert(content).getUpsertedId();
//		
//		DBObject res = col.findOne(new BasicDBObject(ContentProviderBean.KEY_ID, id));
//		try {
//			result = new ContentProviderBean(
//					(String)res.get(ContentProviderBean.KEY_TITLE), 
//					(String)res.get(ContentProviderBean.KEY_GENRE),
//					(String)res.get(ContentProviderBean.KEY_TYPE), 
//					Integer.parseInt((String)res.get(ContentProviderBean.KEY_STARTTIME)), 
//					Integer.parseInt((String)res.get(ContentProviderBean.KEY_ENDTIME)), 
//					res.get(ContentProviderBean.KEY_ID).toString());
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return result;
	}
	
	/* date 		: 2014.10.12
	 * description 	: �꾩튂 媛믪뿉 �곕씪 ContentBean��異쒕젰�⑸땲��
	 * param		: double long, double lat
	 * notice		: �꾩옱 �ㅽ뿕�쇱븘 collection 'test_content' �ъ슜
	 * 					�멸퀎���꾩튂 : 127.00111 , 37.26711
	 * */
	
	static double[] InGyae = {127.00111, 37.26711 };
	public static ContentBean[] getContentsByLocation(float l_long, float l_lat , int page) {
		ContentBean[] res = null;
		
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection("test_content");
		
		BasicDBList position = new BasicDBList();
		position.put(0, l_lat);
		position.put(1, l_long);		
		
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
		
		res = new ContentBean[MAX_LIMIT];
		int i = 0;
		while(iter.hasNext()){
			DBObject obj = iter.next();
			
			
			
			DBObject dbProvider = (DBObject)obj.get(ContentBean.KEY_PROVIDER);						
			BasicDBList dbArtists = (BasicDBList)obj.get(ContentBean.KEY_ARTIST);
			DBObject dbLocation = (DBObject)dbProvider.get(ProviderBean.KEY_LOCATION);
			DBObject dbArtist = null;
			String selectedArtistId = (String)obj.get(ContentBean.KEY_C_ARTIST);
			
			for(int n = 0 ; n < dbArtists.size(); n++) {
				String temp = ((DBObject)dbArtists.get(n)).get("_id").toString();
				System.out.println(temp + " :: " + selectedArtistId);
				if(temp.equals(selectedArtistId)) {
					dbArtist = (DBObject)dbArtists.get(n);
					break;
				}
			}
			BasicDBList pos = (BasicDBList)dbLocation.get(LocationBean.KEY_COORDINATE);
			LocationBean location = new LocationBean(
					(String)dbLocation.get(LocationBean.KEY_NAME),
					(String)dbLocation.get(LocationBean.KEY_DESCRIPT),
					(double)pos.get(0), 
					(double)pos.get(1));
			GenreBean[] genre = {new GenreBean("", (String)dbProvider.get(ProviderBean.KEY_GENRE))};
			
			BasicDBList images = (BasicDBList)dbProvider.get(ProviderBean.KEY_IMAGES); 	
			String[] t = {};
			ProviderBean provider = new ProviderBean(
					(String)dbProvider.get(ProviderBean.KEY_TYPE),
					images.toArray(t),
					location,
					genre,
					(String)dbProvider.get(ProviderBean.KEY_STORETITLE), 
					(String)dbProvider.get(ProviderBean.KEY_STORETYPE),
					(String)dbProvider.get(ProviderBean.KEY_DESCRIPT));
			
			images = (BasicDBList)dbArtist.get(ArtistBean.KEY_IMAGES);
			String[] t2 = {};
			ArtistBean[] artist = {new ArtistBean(
					images.toArray(t2), 
					(String)dbArtist.get(ArtistBean.KEY_VIDEO), 
					(String)dbArtist.get(ArtistBean.KEY_DESCRIPT))};
			
			res[i++] = new ContentBean(
						(String)obj.get(ContentBean.KEY_TITLE),
						new GenreBean("",(String)obj.get(ContentBean.KEY_GENRE)),
						(String)obj.get(ContentBean.KEY_TYPE),
						Integer.parseInt((String)obj.get(ContentBean.KEY_STARTTIME)),
						Integer.parseInt((String)obj.get(ContentBean.KEY_ENDTIME)),
						provider,
						artist,
						selectedArtistId,
						(String)obj.get(ContentBean.KEY_C_TIME),
						(boolean)obj.get(ContentBean.KEY_FINISHIED),
						null				
					);
		}
		
		
		return res;
	}
	
	public static ReplyBean[] getReplyByContentId(String contentId) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection("test_reply");
		
		DBCursor iter =col.find(new BasicDBObject()
									.append(ReplyBean.KEY_CONTENTID, contentId));
		ReplyBean[] res = new ReplyBean[iter.count()];
		int i = 0;
		while(iter.hasNext()) {
			DBObject obj = iter.next();
			res[i++] = new ReplyBean(
					obj.get(ReplyBean.KEY_ID).toString(),
					obj.get(ReplyBean.KEY_CONTENTID).toString(),
					obj.get(ReplyBean.KEY_REPLYTEXT).toString(),
					obj.get(ReplyBean.KEY_REPLYIMAGE).toString());		
		}
		
		return res;
	}
}
