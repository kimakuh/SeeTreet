package com.seetreet.dao;


import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;
import com.seetreet.recommand.PearsonProcessor;
import com.seetreet.recommand.RecommendEnum;


public class MongoRecDAO {
	public static boolean hasLove(String userId , String contentId) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_LOVE_HISTORY);
				
		DBObject result = col.findOne(new BasicDBObject("user_id", userId).append("content_id" , contentId));
		
		if(result == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean loveContent(String userId , String contentId) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_LOVE_HISTORY);
		
		col.insert(new BasicDBObject("user_id", userId).append("content_id", contentId));
		return true;
	}
	
	public static boolean hateContent(String userId , String contentId) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_LOVE_HISTORY);
		
		col.remove(new BasicDBObject("user_id", userId).append("content_id", contentId));
		return true;
	}
	
	public static void insertDefaultUserGenome(String userId) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_RECOMMEND_HISTORY);
		
		BasicDBObject user = new BasicDBObject();
		user.append(RecommendEnum.REC_HISTORY_USERID.val(), userId);
		
		col.insert(user);
	}
	
	public static void plusUserLove(String userId , String property) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_RECOMMEND_HISTORY);
		
		BasicDBObject findQuery = new BasicDBObject(RecommendEnum.REC_HISTORY_USERID.val(), userId);
		BasicDBObject updateQuery = new BasicDBObject("$inc", new BasicDBObject(RecommendEnum.REC_HISTORY_PROPERTIES.val()+"."+property , 1));
		col.update(findQuery, updateQuery);
		
		/*이전 버전*/
//		BasicDBObject findQuery = new BasicDBObject();
//		findQuery.append(RecommendEnum.REC_HISTORY_USERID.val(), userId)
//				 .append(RecommendEnum.REC_HISTORY_PROPERTIES.val()+"."+RecommendEnum.LOVE_NAME, property);
//				
//		DBObject countObject = col.findOne(findQuery);		
//		
//		BasicDBObject updateQuery = new BasicDBObject();
//		
//		
//		if(countObject != null) {
//			updateQuery.append("$inc", new BasicDBObject().append(RecommendEnum.REC_HISTORY_PROPERTIES+".$."+RecommendEnum.LOVE_VALUE, 1));
//			col.update(findQuery, updateQuery);
//		} else {
//			BasicDBObject findQuery2 = new BasicDBObject();
//			findQuery2.append(RecommendEnum.REC_HISTORY_USERID.val(), userId);
//			updateQuery.append("$push", new BasicDBObject()
//											.append(RecommendEnum.REC_HISTORY_PROPERTIES.val(), new BasicDBObject()
//																									.append(RecommendEnum.LOVE_NAME.val(), property)
//																									.append(RecommendEnum.LOVE_VALUE.val(), 1)));
//			col.update(findQuery2, updateQuery);
//		}	
	}
	
	public static void minusUserLove(String userId , String property) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_RECOMMEND_HISTORY);
		
		BasicDBObject findQuery = new BasicDBObject(RecommendEnum.REC_HISTORY_USERID.val(), userId);
		BasicDBObject updateQuery = new BasicDBObject("$inc", new BasicDBObject(RecommendEnum.REC_HISTORY_PROPERTIES.val()+"."+property , -1));
		col.update(findQuery, updateQuery);
		
		/*이전버전*/
//		BasicDBObject findQuery = new BasicDBObject();
//		findQuery.append(RecommendEnum.REC_HISTORY_USERID.val(), userId)
//				 .append(RecommendEnum.REC_HISTORY_PROPERTIES.val()+"."+RecommendEnum.LOVE_NAME.val(), property);
//				
//		DBObject countObject = col.findOne(findQuery);		
//		BasicDBObject updateQuery = new BasicDBObject();
//				
//		if(countObject != null) {
//			updateQuery.append("$inc", new BasicDBObject().append(RecommendEnum.REC_HISTORY_PROPERTIES.val()+".$."+RecommendEnum.LOVE_VALUE.val(), -1));
//			col.update(findQuery, updateQuery);
//		}
	}
	
	public static JSONObject getUserLoves(String userId) {
		DB db = MongoDB.getDB();
		DBCollection col = db.getCollection(MongoDB.COLLECTION_RECOMMEND_HISTORY);
		
		BasicDBObject findQuery = new BasicDBObject().append(RecommendEnum.REC_HISTORY_USERID.val(), userId);
		
		DBObject result = col.findOne(findQuery);
		JSONObject json = null;
		try {
			 json = new JSONObject(result.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		return json;
	}
	
	public static JSONObject findRecommandValue(String userId) {
		DB db = MongoDB.getDB();
		DBCollection recommendDB = db.getCollection(MongoDB.COLLECTION_RECOMMEND_VALUE);
		
		DBObject result = recommendDB.findOne(new BasicDBObject(RecommendEnum.REC_USERID.val() , userId));
		JSONObject json = null;
		JSONObject res = new JSONObject();
		if(result == null ) return json;
		try {
			json = new JSONObject(result.toString());
			JSONArray arr = json.getJSONArray(RecommendEnum.REC_PROPERTIES.val());
			if(arr == null) return res;
			
			for(int i = 0 ; i < arr.length(); i++) {
				String name = JSONObject.getNames(arr.getJSONObject(i))[0];
				double value = arr.getJSONObject(i).getDouble(name);
				res.put(name, value);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		return res;
	}

//	Warning: Calling toArray or length on a DBCursor will irrevocably turn it into an array. 
//	This means that, if the cursor was iterating over ten million results 
//	(which it was lazily fetching from the database), 
//	suddenly there will be a ten-million element array in memory. 
//	Before converting to an array, make sure that there are a reasonable number of results using skip() and limit().
	
	
	public static void updateRecommandValue() {
		DB db = MongoDB.getDB();			
		DBCollection sourceCol 	= db.getCollection(MongoDB.COLLECTION_RECOMMEND_HISTORY);
		DBCollection targetCol 	= db.getCollection(MongoDB.COLLECTION_RECOMMEND_HISTORY);
		DBCollection valueMap 	= db.getCollection(MongoDB.COLLECTION_SIMILARITY_MAP);
		DBCollection recommendDB = db.getCollection(MongoDB.COLLECTION_RECOMMEND_VALUE);
		
		DBObject sItem = null , tItem = null;
		DBCursor sCursor = sourceCol.find();		
		while(sCursor.hasNext()) {
			sItem = sCursor.next();
			DBCursor tCursor = targetCol.find();
			
			System.out.printf("%10s 의 피어슨 점수를 계산합니다.\n",sItem.get(RecommendEnum.REC_HISTORY_USERID.val()));
			while(tCursor.hasNext()) {
				tItem = tCursor.next();		
				//같은 것끼리의 유사도는 계산 안함.
				if(sItem.get("_id").toString().equals(tItem.get("_id").toString())) continue;
				
				try {
					double v = PearsonProcessor.getPearsonValue(new JSONObject(sItem.toString()), new JSONObject(tItem.toString()));
//					System.out.printf("%10s 와 %10s 의 유사도는 %3.3f \n",sItem.get(RecommendEnum.REC_HISTORY_USERID.val()) ,tItem.get(RecommendEnum.REC_HISTORY_USERID.val()) , v);
					updateSimilarity(sItem.get(RecommendEnum.REC_HISTORY_USERID.val()).toString(), tItem.get(RecommendEnum.REC_HISTORY_USERID.val()).toString(), v);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}				
			}
			
						
			System.out.printf("%10s 의 가장 유사한 10명을 선택합니다.\n",sItem.get(RecommendEnum.REC_HISTORY_USERID.val()));
			DBCursor pCursor = valueMap.find(new BasicDBObject().append(RecommendEnum.SIM_MAP_SOURCE.val(), sItem.get(RecommendEnum.REC_HISTORY_USERID.val())))
									   .sort(new BasicDBObject().append(RecommendEnum.SIM_MAP_VALUE.val(), -1))
									   .limit(PearsonProcessor.MAX_LIMIT);
						
			JSONObject sumProperty = new JSONObject();
			int sum = 0;
			try {
				while (pCursor.hasNext()) {
					DBObject cur = pCursor.next();					
					double similarity = Double.parseDouble(cur.get(RecommendEnum.SIM_MAP_VALUE.val()).toString());
					similarity = similarity == 0 ? 0.1 : similarity;
					String targetId = cur.get(RecommendEnum.SIM_MAP_TARGET.val()).toString();
					
//					System.out.printf("%10s : %2.2f ",targetId , similarity);
					
					DBObject temp = targetCol.findOne(new BasicDBObject(RecommendEnum.REC_HISTORY_USERID.val(), targetId));
					JSONObject tempObj = new JSONObject(temp.toString());
					if(tempObj.has(RecommendEnum.REC_HISTORY_PROPERTIES.val()) == false) continue;
					JSONObject propertiesObj = new JSONObject(temp.get(RecommendEnum.REC_HISTORY_PROPERTIES.val()).toString());
					tempObj = null;
					
//					System.out.println(propertiesObj.toString());
					String[] propertyNames = JSONObject.getNames(propertiesObj);
					if(propertyNames == null) continue;
					for(String propertyName : propertyNames) {
						double pValue = propertiesObj.getDouble(propertyName);
						sum+= pValue*similarity;
//						System.out.printf(" - %10s %2.2f %2.2f : ",propertyName,pValue ,similarity);
						sumProperty.put(propertyName, sumProperty.has(propertyName)? sumProperty.getDouble(propertyName) + pValue * similarity : pValue * similarity);
					}					
				}				
												
				JSONArray names = sumProperty.names();
				if(names == null) continue;
				System.out.printf("%10s : %7s \n" , "특성" , "추천값");
				BasicDBList recValueList = new BasicDBList();
				for (int i = 0; i < names.length(); i++) {
					String key = names.getString(i);
					if(sum == 0) {
						sumProperty.put(key, 0);
						recValueList.add(new BasicDBObject(key, 0));
					} else {
						double value = sumProperty.getDouble(key) * 100 / sum;
						sumProperty.put(key, value);
						recValueList.add(new BasicDBObject(key, value));
						System.out.printf("%10s : %3.3f ||\n " , key , value);
					}
				}				
				
				BasicDBObject findData = new BasicDBObject().append(RecommendEnum.REC_USERID.val(), sItem.get(RecommendEnum.REC_HISTORY_USERID.val()));				  
				
				BasicDBObject insertData = new BasicDBObject();			
				insertData.append(RecommendEnum.REC_USERID.val(), sItem.get(RecommendEnum.REC_HISTORY_USERID.val()))
						  .append(RecommendEnum.REC_PROPERTIES.val(), recValueList);
				
				BasicDBObject updateData = new BasicDBObject("$set", insertData);
				
				recommendDB.update(findData, updateData, true, false);				
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}	
			
		}		
		
	}
	
	public static void updateSimilarity(String source , String target , double similarity) {
		DB db = MongoDB.getDB();
		DBCollection valueMap = db.getCollection(MongoDB.COLLECTION_SIMILARITY_MAP);	
		
		BasicDBObject findQuery = new BasicDBObject().append(RecommendEnum.SIM_MAP_SOURCE.val(), source)
													 .append(RecommendEnum.SIM_MAP_TARGET.val(), target);
		
		BasicDBObject updateQuery = new BasicDBObject().append("$set", new BasicDBObject().append(RecommendEnum.SIM_MAP_SOURCE.val(), source)
																						  .append(RecommendEnum.SIM_MAP_TARGET.val(), target)
																						  .append(RecommendEnum.SIM_MAP_VALUE.val(), similarity));
		
		valueMap.update(findQuery, updateQuery, true, false);
	}	
	
}
