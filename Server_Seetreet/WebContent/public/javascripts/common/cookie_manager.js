/**
 * Created by Limjiuk on 2014-11-02.
 */

var COOKIE_USER_ID = 'userId';
var COOKIE_USER_NAME = 'userName';
var COOKIE_USER_TOKEN = 'userToken';
var COOKIE_REMEMBER_USER_ID = 'rememberUserId';


var setCookie = function( cookieName, cookieValue, expireDate ){
    var today = new Date();
    today.setDate( today.getDate() + parseInt( expireDate ) );
    document.cookie = cookieName + "=" + escape( cookieValue ) + "; path=/; expires=" + today.toGMTString() + ";";
};


var removeCookie  = function ( cookieName ){
    setCookie (cookieName, '', -1 );
};


var getCookie = function(cookieName){
    var search = cookieName + "=";
    var cookie = document.cookie;

    if( cookie.length > 0 )
    {
        var  startIndex = cookie.indexOf( cookieName );
        if( startIndex != -1 )
        {
            startIndex += cookieName.length;
            var endIndex = cookie.indexOf( ";", startIndex );
            if( endIndex == -1)
                endIndex = cookie.length;
            return unescape( cookie.substring( startIndex + 1, endIndex ) );
        }
        else
        {
            return false;
        }
    }
    else
    {
        return false;
    }
};
