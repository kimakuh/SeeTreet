package com.seetreet.recommand;


import org.json.JSONArray;
import org.json.JSONObject;


public class PearsonProcessor {	
	public static final int MAX_LIMIT = 10;
	public static double getPearsonValue(JSONObject user1, JSONObject user2){
		try {
			if(user1.has(RecommendEnum.REC_HISTORY_PROPERTIES.val()) == false || 
					user2.has(RecommendEnum.REC_HISTORY_PROPERTIES.val()) == false) return 0.0;
			
			JSONObject userLoveHistory1 = user1.getJSONObject(RecommendEnum.REC_HISTORY_PROPERTIES.val());
			JSONObject userLoveHistory2 = user2.getJSONObject(RecommendEnum.REC_HISTORY_PROPERTIES.val());
						
			String[] names1 = JSONObject.getNames(userLoveHistory1);
			String[] names2 = JSONObject.getNames(userLoveHistory2);
			
			if(names1 == null || names2 == null) return 0.0;
			
			JSONArray properties1 = new JSONArray();
			JSONArray properties2 = new JSONArray();
			
			for(String name : names1) {				
				properties1.put(new JSONObject().put(RecommendEnum.LOVE_NAME.val(), name)
												.put(RecommendEnum.LOVE_VALUE.val(),userLoveHistory1.getInt(name)));
//				properties1.put(new JSONObject().put(name,userLoveHistory1.getInt(name)));
			}
			
			for(String name : names2) {
				properties2.put(new JSONObject().put(RecommendEnum.LOVE_NAME.val(), name)
							.put(RecommendEnum.LOVE_VALUE.val(),userLoveHistory2.getInt(name)));
//				properties2.put(new JSONObject().put(name,userLoveHistory2.getInt(name)));
			}
						
//			JSONArray properties1 = userLoveHistory1.getJSONArray(RecommendEnum.REC_HISTORY_PROPERTIES.val());
//			JSONArray properties2 = userLoveHistory2.getJSONArray(RecommendEnum.REC_HISTORY_PROPERTIES.val());
//			System.out.println("======================================================================");
//			System.out.println("======================================================================");
//			System.out.printf("Source : %10s , Target : %10s  \n" , user1.getString(RecommendEnum.REC_HISTORY_USERID.val()), user2.getString(RecommendEnum.REC_HISTORY_USERID.val()));
//			System.out.println("======================================================================");
			int sumLove1 = 0;
			for(int i = 0 ; i < properties1.length(); i++) {
				JSONObject property = properties1.getJSONObject(i);
				sumLove1 += property.getInt(RecommendEnum.LOVE_VALUE.val());
			}			
			
			for(int i = 0 ; i < properties1.length(); i++) {
				JSONObject property = properties1.getJSONObject(i);
				double tValue = (double)property.getInt(RecommendEnum.LOVE_VALUE.val())/sumLove1*10;
				property.put(RecommendEnum.LOVE_VALUE.val(), tValue);
			}
			
			
			int sumLove2 = 0;
			for(int i = 0 ; i < properties2.length(); i++) {
				JSONObject property = properties2.getJSONObject(i);
				sumLove2 += property.getInt(RecommendEnum.LOVE_VALUE.val());
			}			
			
			for(int i = 0 ; i < properties2.length(); i++) {
				JSONObject property = properties2.getJSONObject(i);
				double tValue = (double)property.getInt(RecommendEnum.LOVE_VALUE.val())/sumLove2*10;
				property.put(RecommendEnum.LOVE_VALUE.val(), tValue);
			}
			
			double sum1 = 0 , sum2 = 0 , sum1Sq = 0 , sum2Sq = 0 , pSum = 0;
			int n = 0;
			for(int i = 0 ; i < properties1.length(); i++) {
				for(int j = 0 ; j < properties2.length(); j++) {
					JSONObject property1 = properties1.getJSONObject(i);
					JSONObject property2 = properties2.getJSONObject(j);
					if(property1.getString(RecommendEnum.LOVE_NAME.val()).equals(property2.getString(RecommendEnum.LOVE_NAME.val()))){						
						n++;
						double v1 = property1.getDouble(RecommendEnum.LOVE_VALUE.val());
						double v2 = property2.getDouble(RecommendEnum.LOVE_VALUE.val());
						sum1 += v1; sum2 += v2;
						sum1Sq += v1*v1; sum2Sq += v2*v2;
						pSum += v1*v2;
						
//						System.out.printf("%10s %3.3f %3.3f , %3.3f %3.3f , %3.3f %3.3f , %3.3f \n" ,property1.getString(RecommendEnum.LOVE_NAME.val()),v1,v2, sum1, sum2,sum1Sq, sum2Sq, pSum);
					}
				}
			}					
			if(n == 0) return 0;
			double num = pSum - (sum1*sum2/n);
			double denSq = (sum1Sq - sum1*sum1/n)*(sum2Sq - sum2*sum2/n);
			double den = Math.sqrt(denSq <0? denSq*-1 : denSq);			
			if(den == 0) return 1;
			
			return num/den +1;			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		return 0;
	}
	
	
}




