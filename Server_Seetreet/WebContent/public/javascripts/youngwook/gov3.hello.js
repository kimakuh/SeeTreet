$(function() {
	  // Try HTML5 geolocation
    if(navigator.geolocation){
        navigator.geolocation.getCurrentPosition(function(position){            
            AJAX("/user/login/10km/" , "GET" , {} , {"l_lat" : position.coords.latitude, "l_long" : position.coords.longitude} , function(res, state) {
        		if(state = "success") {
        			$(".surrouned-play-count .info-number").text(res.data);
        		}
        	});
        }, function(){
            handleNoGeolocation(true);
        });
    }
    else{
        handleNoGeolocation(false);
    }
    
	AJAX("/user/login/ready/" , "GET" , {} , {} , function(res, state) {
		if(state = "success") {			
			$(".waiting-play-count .info-number").text(res.data);
		}
	});
	AJAX("/user/login/complete/" , "GET" , {} , {} , function(res, state) {
		if(state = "success") {
			$(".finished-play-count .info-number").text(res.data);
		}
	});	
	
});


var AJAX = function ( url, method, headers, body , callback  ){
    var fullUrl =  SERVER_ADDRESS + url;
    $.ajax({
        crossDomain : true,
        dataType : "json",
        contentType : "application/x-www-form-urlencoded; charset=UTF-8",
        url: fullUrl,
        method: method,
        headers:headers,
        data: body,
        success: function(data, state, res){
            if ( data == null )
                data = '';
            callback ( data, state, res );
        },
        error: function(data, state){
            if ( data == null )
                data = '';
            callback( data, state , null );
        }
    });
}