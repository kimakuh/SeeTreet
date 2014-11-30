/**
 * Created by Limjiuk on 2014-10-28.
 */
//var SERVER_ADDRESS = 'http://211.189.127.63:8080/Server_Seetreet';
var SERVER_ADDRESS = 'http://211.189.127.61:8090/TEST';

// create user account
var postUserCreate = function(userId, user, callback){
    var url = '/user/join/user/' + userId;
    var method = 'POST';
    var body = user;
    httpRequest(url, method, null, body, callback);
};
// get user information

// putUser information

// getUserDuplication
var getUserDuplication = function(userid, callback){
    var url = '';
    var method = 'GET';
    var headers = '';
    httpRequest(url, method, headers, body, callback);
};

// userLogin
var postUserLogin = function(userId, password, callback){
    var url = '/user/login/' + userId;
    var method = 'POST';
    var headers = {
        'password' : password
    };
    httpRequest(url, method, headers, null, callback);
};

// userLogout
var postUserLogout = function(callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);

    var url = '/user/logout/' + userId;
    var method = 'POST';
    var headers = {
        "_id" : token
    };
    httpRequest(url, method, headers, null, callback);
};

var getUser = function(callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);
    var headers = {
        "_id" : token
    };
    var getbody = '?user_email=' + userId;
    var url = '/user/person/get/user/' + userId + getbody;
    var method = 'GET';

    httpRequest(url, method, headers, null, callback);
};

var getCheckProvider = function(callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);
    var url = '/user/person/check/provider/' + userId;
    var method = 'GET';
    var headers = {
        "_id" : token
    };
    httpRequest(url, method, headers, null, callback);
};

// 장소제공자 정보 등록하기
var postProviderCreate = function(providerinfo, callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);
    var url = '/user/join/provider/' + userId;
    var method = 'POST';
    var headers = {
        "_id" : token
    };
    var body = providerinfo;
    httpRequest(url, method, headers, body, callback);
};

// 장소제공자 정보 수정하기

// 장소제공자 정보 조회하기
var getProvider = function(providerId, callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);
    var urlparam = '?_id=' + providerId;
    var url = '/user/person/get/provider/' + userId + urlparam;
    var method = 'GET';
    var headers = {
        "_id" : token
    };
    httpRequest(url, method, headers, null, callback);
};

var getCheckArtist = function(callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);
    var url = '/user/person/check/artist/' + userId;
    var method = 'GET';
    var headers = {
        "_id" : token
    };
    httpRequest(url, method, headers, null, callback);
};

// 아티스트 정보 등록하기
var postArtistCreate = function(artistinfo, callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);
    var url = '/user/join/artist/' + userId;
    var method = 'POST';
    var headers = {
        "_id" : token
    };
    var body = artistinfo;
    httpRequest(url, method, headers, body, callback);
};

// 아티스트 정보 가져오기
var getArtist = function(artistId, callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);
    var urlparam = '?' + "_id=" + artistId;
    var url = '/user/person/get/artist/' + userId + urlparam;
    var method = 'GET';
    var headers = {
        "_id" : token
    };
    httpRequest(url, method, headers, null, callback);
};
// 아티스트 정보 수정하기

// content Id로 댓글을 10개를 받는다.

// content Id로 댓글을 등록한다.

// contentId에 replyId의 댓글을 삭제한다.


// 해당 컨텐트의 좋아요 카운트를 가져온다.
var getLikecount = function(contentId, callback){
    // 토큰, 유저 아이디, 컨텐트 아이디
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);
    var url_param = '?_id=' + contentId;
    var url = '/user/content/user/like/search/' + userId + url_param;
    var method = 'GET';
    var headers = {
        "_id" : token
    };
    httpRequest(url, method, headers, null, callback);
};

var getChecklikefromMe = function(contentId, callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);
    var url_param = '?_id=' + contentId;
    var url = '/user/content/user/like/check/' + userId + url_param;
    var method = 'GET';
    var headers = {
        "_id" : token
    };
    httpRequest(url, method, headers, null, callback);
};

var postlikeContent = function(contentId, islike, callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);
    var method = 'POST';
    var headers = {
        "_id" : token
    };
    var url = '/user/content/user/like/update/' + userId;
    var body = {
        "_id" : contentId,
        islike : islike
    };
    httpRequest(url, method, headers, body, callback);
};

var getcontentReplyCount = function(contentId, callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);
    var method = 'GET';
    var headers = {
        "_id" : token
    };
    var url_param = '?contentId=' + contentId;
    var url = '/user/content/user/reply/count/' + userId + url_param;
    httpRequest(url, method, headers, null, callback);
};

// 컨텐트의 댓글 정보들을 가져옵니다.
var getcontentReply = function(contentId, page, callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);
    var method = 'GET';
    var headers = {
        "_id" : token
    };
    var url_param = '?contentId=' + contentId + '&page=' + page;
    var url = '/user/content/user/reply/search/' + userId + url_param;
    httpRequest(url, method, headers, null, callback);
};


// 컨텐트의 댓글을 추가합니다.
var postwriteReply = function(contentId, replytext, replyImage, callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);
    var method = 'POST';
    var headers = {
        "_id" : token
    };
    var url = '/user/content/user/reply/enroll/' + userId;
    var body = {
        contentId : contentId,
        replytext : replytext,
        replyimage : replyImage
    };
    httpRequest(url, method, headers, body, callback);
};


// 일반 사용자가 content 리스트를 가져온다.
var getUserContent = function(latitude, longitude, page, callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);
    var url = '/user/content/user/search/' + userId + '?' + 'l_lat=' + latitude + '&' + 'l_long=' + longitude + '&' + 'page=' + page;
    var method = 'GET';
    var headers = {
        "_id" : token
    };
    httpRequest(url, method, headers, null, callback);
};

// 장소 제공자의 히스토리를 가져옵니다.
var getProviderHistory = function(providerId, callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);
    var url_param = '?_id=' + providerId + '&isProvider=true';
    var url = '/user/content/user/history/' + userId + url_param;
    var method = 'GET';
    var headers = {
        "_id" : token
    };
    httpRequest(url, method, headers, null, callback);
};
var getArtistHistory = function(artistId, callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);
    var url_param = '?_id=' + artistId + '&isProvider=false';
    var url = '/user/content/user/history/' + userId + url_param;
    var method = 'GET';
    var headers = {
        "_id" : token
    };
    httpRequest(url, method, headers, null, callback);
};

// 문자열 주소를 통해 좌표값을 받아온다.
var getlocatestr2coord = function(locatestr, callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var headers = {
        "_id" : token
    };
    var body = {
        address : locatestr
    };
    var url = '/admin/map/addtocoord/';
    var method = 'POST';
    httpRequest(url, method, headers, body, callback);
};
var getcoord2locatestr = function(lat, long, callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var headers = {
      "_id" : token
    };
    var body = {
        longitude : long,
        latitude : lat
    };
    var url = '/admin/map/coordtoadd/';
    var method = 'POST';
    httpRequest(url, method, headers, body, callback);
};

//제공자 등록 콘텐츠 정보 보기
var getProviderContents = function(page, callback) {
   var token = getCookie(COOKIE_USER_TOKEN);
   var userId = getCookie(COOKIE_USER_ID);
   var url = '/user/content/provider/search/' + userId;
   var method = 'GET';
    var headers = {
        "_id" : token
    };
    var body = {
    	"page" : page
    };
   httpRequest(url, method, headers, body, callback);
};

//제공자 등록 콘텐츠 정보 보기
var postNewProviderContents = function(title, address, stime, etime, callback) {
	var token = getCookie(COOKIE_USER_TOKEN);
	var userId = getCookie(COOKIE_USER_ID);
	var url = '/user/content/provider/enroll/' + userId;
	var method = 'POST';
	var headers = {
		"_id" : token
	};
	var body = {
		contentTitle : title,
		contentAddress : address,
		contentStartTime : stime,
		contentEndTime : etime
	};
	httpRequest(url, method, headers, body, callback);
};

// 아티스트가 지원했던 콘텐츠 정보를 가져옵니다.
var getArtistApplications = function(artistId , page , callback) {
	var token = getCookie(COOKIE_USER_TOKEN);
	var userId = getCookie(COOKIE_USER_ID);
	var url = '/user/content/artist/applications/' + userId;
	var method = 'GET';
	var headers = {
		"_id" : token
	};
	var body = {
		"_id" : artistId,
		"page" : page
	};
	httpRequest(url, method, headers, body, callback);
}

//아티스트의 선호 위치를 기반으로 콘텐츠 정보를 가져옵니다.
var getArtistRecommends = function(page , callback) {
	var token = getCookie(COOKIE_USER_TOKEN);
	var userId = getCookie(COOKIE_USER_ID);
	var url = '/user/content/artist/rec/' + userId;
	var method = 'GET';
	var headers = {
		"_id" : token
	};
	var body = {
		"page" : page	
	};
	httpRequest(url, method, headers, body, callback);
}

var postApplication = function(contentId , callback) {
	var token = getCookie(COOKIE_USER_TOKEN);
	var userId = getCookie(COOKIE_USER_ID);
	var url = '/user/content/artist/apply/' + userId;
	var method = 'POST';
	var headers = {
		"_id" : token
	};
	var body = {
		"_id" : contentId	
	};
	httpRequest(url, method, headers, body, callback);
};

var permitArtist = function(artistId , contentId , callback) {
	var token = getCookie(COOKIE_USER_TOKEN);
	var userId = getCookie(COOKIE_USER_ID);
	var url = '/user/content/provider/permit/' + userId;
	var method = 'POST';
	var headers = {
		"_id" : token
	};
	var body = {
		"artistId" : artistId,
		"contentId" : contentId
	};
	httpRequest(url, method, headers, body, callback);
};

var postConfirmFromArtist = function(genre , contentId , callback) {
	var token = getCookie(COOKIE_USER_TOKEN);
	var userId = getCookie(COOKIE_USER_ID);
	var url = '/user/content/artist/confirm/' + userId;
	var method = 'POST';
	var headers = {
		"_id" : token
	};
	var body = {
		"contentId" : contentId,
		"genre" : JSON.stringify(genre)
	};
	httpRequest(url, method, headers, body, callback);
};



// httpRequest
var httpRequest = function ( url, method, headers, body , callback  ){
    var fullUrl =  SERVER_ADDRESS + url;
//    console.log('=======request start!==========');
//    console.log('method=' + method );
//    console.log('url='+ fullUrl );
//    console.log('header=' + JSON.stringify(headers) );
//    console.log('body=' + JSON.stringify(body));
//    console.log('========request end!=========');
    $.ajax({
        crossDomain : true,
        dataType : "json",
        contentType : "application/x-www-form-urlencoded; charset=UTF-8",
//        contentType : "application/json; charset=UTF-8",
        url: fullUrl,
        method: method,
        headers:headers,
        data: body,
        success: function(data, state, res){
            if ( data == null )
                data = '';
//            console.log( '[[[[[[[[SUCCESS!!]]]]]]]   url: ' + url + ',   state:' + state + ',   data : ' + JSON.stringify( data ) );
            callback ( data, state, res );
        },
        error: function(data, state){
            if ( data == null )
                data = '';
//            console.log( '[[[[[[[[[ERROR!!]]]]]]]]]   url: ' + url + ', s  tate:' + state + ',   data : ' + JSON.stringify( data ) );
            callback( data, state , null );
        }
    });
}




