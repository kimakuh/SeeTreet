/**
 * Created by Limjiuk on 2014-10-28.
 */
var SERVER_ADDRESS = 'http://211.189.127.63:8080/Server_Seetreet';

// create user account
var postUserCreate = function(userId, user, callback){
    var url = '/user/join/user/' + userId;
    var method = 'POST';
    var body = JSON.stringify(user);
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

// 장소제공자 정보 등록하기
var postProviderCreate = function(providerinfo, callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);

    var url = '/user/join/provider/' + userId;
    var method = 'POST';
    var headers = token;
    var body = JSON.stringify(providerinfo);
    httpRequest(url, method, headers, body, callback);
};

// 장소제공자 정보 수정하기

// 아티스트 정보 등록하기
var postArtistCreate = function(artistinfo, callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);
    var url = '/user/join/artist/' + userId;
    var method = 'POST';
    var headers = token;
    var body = JSON.stringify(artistinfo);
    httpRequest(url, method, headers, body, callback);
};

// 아티스트 정보 수정하기


// content Id로 댓글을 10개를 받는다.


// content Id로 댓글을 등록한다.


// contentId에 replyId의 댓글을 삭제한다.


// 일반 사용자가 content 리스트를 가져온다.
var getUserContent = function(latitude, longitude, page, callback){
    var token = getCookie(COOKIE_USER_TOKEN);
    var userId = getCookie(COOKIE_USER_ID);
    var bodycontent = {
        "l_lat" : latitude,
        "l_long" : longitude,
        "page" : page
    };
    var url = '/user/content/user/' + userId + '?' + 'l_lat=' + latitude + '&' + 'l_long=' + longitude + '&' + 'page=' + page;
    var method = 'GET';
    var headers = {
        "_id" : token
    };
    httpRequest(url, method, headers, null, callback);
};

// httpRequest
var httpRequest = function ( url, method, headers, body , callback  ){
    var fullUrl =  SERVER_ADDRESS + url;
    console.log('=======request start!==========');
    console.log('method=' + method );
    console.log('url='+ fullUrl );
    console.log('header=' + JSON.stringify(headers) );
    console.log('body=' + body);
    console.log('========request end!=========');
//    $.ajaxPrefilter('json', function(options, orig, jqXHR) {
//        return 'jsonp';   // ajaxPrefilter 수정
//    });
    $.ajax({
        crossDomain : true,
        dataType : "json",
        url: fullUrl,
        method: method,
        headers:headers,
        data: body,
        success: function(data, state, res){
            if ( data == null )
                data = '';
            console.log( '[[[[[[[[SUCCESS!!]]]]]]]   url: ' + url + ',   state:' + state + ',   data : ' + JSON.stringify( data ) );
            callback ( data, state, res );
        },
        error: function(data, state){
            if ( data == null )
                data = '';
            console.log( '[[[[[[[[[ERROR!!]]]]]]]]]   url: ' + url + ', s  tate:' + state + ',   data : ' + JSON.stringify( data ) );
            callback( data, state , null );
        }
    });
}




