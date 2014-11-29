/**
 * Created by Limjiuk on 2014-10-28.
 */

var map_Manage = {};
map_Manage.locationArray = new Array();
map_Manage.selectLocation = new nhn.api.map.LatLng(63.1212, 122.1212);
map_Manage.userSelectLocation = new nhn.api.map.LatLng(63.1212, 122.1212);
map_Manage.providerSelectLocation = new nhn.api.map.LatLng(63.1212, 122.1212);
map_Manage.providerModifyLocation = new nhn.api.map.LatLng(63.1212, 122.1212);
map_Manage.artistSelectLocation = new nhn.api.map.LatLng(63.1212, 122.1212);

map_Manage.set_map = function(mapinfo){
    var targetMap = mapinfo.target;
    targetMap.clearOverlay();

    // 맵 위치 세팅
    var target_position = new nhn.api.map.LatLng(mapinfo.latitude, mapinfo.longitude);
    mapinfo.target.setCenter(target_position);

    // 마커 세팅
    var oSize = new nhn.api.map.Size(28, 50);
    var oOffset = new nhn.api.map.Size(14, 37);
    if(mapinfo.contentType == 'public'){
        var oIcon = new nhn.api.map.Icon('./images/seetreetimg/map_point.png', oSize, oOffset);
    }
    else{
        var oIcon = new nhn.api.map.Icon('./images/seetreetimg/map_point.png', oSize, oOffset);
    }
    // 마커의 타이틀 정하기
    var oLabel = new nhn.api.map.MarkerLabel();
    mapinfo.target.addOverlay(oLabel);

    // 마커의 위치 정하기
    var oMarker = new nhn.api.map.Marker(oIcon, { title : mapinfo.storeName });;
    oMarker.setPoint(target_position);
    mapinfo.target.addOverlay(oMarker);
    oLabel.setVisible(true, oMarker);
};

// 검색 정보를 받아 오는 부분입니다.
map_Manage.searchlocation = function(locatestr, callback){
    getlocatestr2coord(locatestr, function(data, status, res){
        if(status == 'success'){
            map_Manage.selectLocation = new nhn.api.map.LatLng(data.data[0].lat, data.data[0].lng);
            map_Manage.userSelectLocation = new nhn.api.map.LatLng(data.data[0].lat, data.data[0].lng);
            callback();
        }
    });
};
// 검색 정보를 받아 오는 부분입니다.
map_Manage.searchlocation_provider = function(locatestr,callback){
    getlocatestr2coord(locatestr, function(data, status, res){
        if(status == 'success'){
            map_Manage.providerSelectLocation = new nhn.api.map.LatLng(data.data[0].lat, data.data[0].lng);
            callback();
        }
    });
};
map_Manage.searchlocation_artist = function(locatestr,callback){
    getlocatestr2coord(locatestr, function(data, status, res){
        if(status == 'success'){
            map_Manage.artistSelectLocation = new nhn.api.map.LatLng(data.data[0].lat, data.data[0].lng);
            callback();
        }
    });
};
map_Manage.set_searchMap = function(mode){
    if(mode == 'search'){
        search_oMap.setCenter(map_Manage.selectLocation);
    }
    else if(mode == 'provider'){
        provider_location_oMap.setCenter(map_Manage.providerSelectLocation);
    }
    else if(mode == 'artist'){
        artist_location_oMap.setCenter(map_Manage.artistSelectLocation);
    }
};
// 검색 정보를 화면에 드랍다운으로 출력해주는 부분입니다.
//map_Manage.show2list = function(locationlist){
//    var test_location = {
//        location_name : "임시1",
//        location : new nhn.api.map.LatLng(37.567541, 126.9773356)
//    };
//    for(var i = 0; i<5;i++){
//        map_Manage.locationArray.push(test_location);
//    }
//};

// 선택된 좌표를 통해 contentlist를 갱신합니다.
map_Manage.refreshContent = function(find_latitude, find_longitude){
    box_Factory.content.initContentData(function(){
        contentshow(find_latitude, find_longitude);
    });
};

map_Manage.search_popup_map = {};
map_Manage.search_popup_map.clearMap = function(){
    $('#location-search-body').val('');
    search_oMap.clearOverlay();
};





