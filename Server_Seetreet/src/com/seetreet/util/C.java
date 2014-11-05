package com.seetreet.util;

import javax.servlet.http.HttpServletRequest;

public class C {
//	public static final String DBIP = "211.189.127.155";
	public static final String DBIP = "211.189.127.61";
	
	public static final int DBPORT = 27017;
	public static final String DBID = "seetreet";
	public static final String DBPWSTR = "see";
	public static final char[] DBPW = DBPWSTR.toCharArray();
	//public static final String DBTABLE = "seetreet_test";
	public static final String DBTABLE = "seetreet";
	
	public static final String CONTENTTYPE_JSON = "application/json";
	public static final String CONTENTTYPE_HTML = "text/html";
	public static final String CONTENTTYPE_CSS  = "text/css";
		
	public static final String APISERVER = "http://api.visitkorea.or.kr/openapi/service/rest/KorService";
	//public static final String SERVICEKEY = "?ServiceKey=%2F6Zn8OJhDvfGgLf7GPednHRsj5iW669wUrfdrURKt%2FKvpUw%2F1PYsnTL75PFHlZ%2BgU4mCP0qiN74SV%2F6NMM8M%2Fg%3D%3D";
	public static final String SERVICEKEY = "?ServiceKey=x9silkNV5NAwSY11q%2BB3wEIwMLN%2Bk41GRMIMMHseSBI%2BZZJacUaPhAAUno73xVl//xg/6jq7ximTUaUY1NkL3Q%3D%3D";
	//public static final String SERVICEKEY = "?ServiceKey=%2Bjvgyj%2FpkZ2s2LRybZLnbB2FMv2i8PvJlK1%2F%2FNLL8KXwqo9Ian%2FUXJciP9MKJFqlqQkvnQaO9Xqyw6dC955xTg%3D%3D";
	

	public static final String DETAILCOMMON = "/detailCommon";
	public static final String SEARCHFESTIVAL = "/searchFestival";
	public static final String DEFAULTYN = "&defaultYN=Y";
	public static final String MAPINFOYN = "&mapinfoYN=Y";
	public static final String CONTENTID = "&contentId=";
	public static final String CATCODEYN = "&catcodeYN=Y";
	public static final String FIRSTIMAGEYN = "&firstImageYN=Y";
	public static final String OVERVIEWYN = "&overviewYN=Y";
	public static final String CONTENTIDDATELIST = "&eventStartDate=" + currentDate() + "&listYN=Y";
	public static final String SETTINGVALUE = "&numOfRows=1000&MobileOS=ETC&MobileApp=AppTesting&_type=json";
	
	static int currentDate(){
		return 20141001;
	}
	
	public static final String ENCODING = "UTF-8";
	
	public static String extractSuffix(HttpServletRequest req , String prefix) {
		String reqURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String cmd = reqURI.substring(contextPath.length());
		
		return cmd.replace(prefix, ""); 
	}
}
