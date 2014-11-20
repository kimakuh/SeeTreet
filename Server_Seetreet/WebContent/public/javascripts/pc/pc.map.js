/**
 * Created by Limjiuk on 2014-10-28.
 */

var oTargetPoint = new nhn.api.map.LatLng(37.567541, 126.9773356);
var defaultLevel = 10;
var oMap = new nhn.api.map.Map(document.getElementById('content-location-map'), {
    point : oTargetPoint,
    zoom : defaultLevel,
    enableWheelZoom : true,
    enableDragPan : true,
    enableDblClickZoom : false,
    mapMode : 0,
    activateTrafficMap : false,
    activateBicycleMap : false,
    minMaxLevel : [ 1, 14 ],
    size : new nhn.api.map.Size(800, 480)
});

var map_Manage = {};
map_Manage.set_map = function(mapinfo){
    // 여기부터 content-popup의 map입니다.
    console.log(mapinfo);
//    var target_position = new nhn.api.map.LatLng(mapinfo.latitude, mapinfo.longitude);
//    oMarker.setPoint(target_position);
//    oLabel.setVisible(true, oMarker);
//    oLabel.setPosition({right : 0, top : 0});
//    mapinfo.target.setCenter(target_position);
//    mapinfo.target.addOverlay(oMarker);
//    mapinfo.target.addOverlay(oLabel); // - 마커 라벨 지도에 추가. 기본은 라벨이 보이지 않는 상태로 추가됨.
};

//$(document).ready(function(){
//    // 여기부터 content-popup의 map입니다.
//    var content_oPoint = new nhn.api.map.LatLng(37.5010226, 127.0396037);
//    nhn.api.map.setDefaultPoint('LatLng');
//    content_oMap = new nhn.api.map.Map('content-location-map' ,{
//        point : content_oPoint,
//        zoom : 10,
//        enableWheelZoom : false,
//        enableDragPan : true,
//        enableDblClickZoom : false,
//        mapMode : 0,
//        activateTrafficMap : false,
//        activateBicycleMap : false,
//        minMaxLevel : [ 5, 13 ],
//        size : new nhn.api.map.Size(300, 195)
//    });
//    // 여기까지
//    // 여기부터 provider-popup의 map입니다.
//    var provider_oPoint = new nhn.api.map.LatLng(37.5010226, 127.0396037);
//    nhn.api.map.setDefaultPoint('LatLng');
//    oMap = new nhn.api.map.Map('provider-location-map' ,{
//        point : provider_oPoint,
//        zoom : 10,
//        enableWheelZoom : true,
//        enableDragPan : true,
//        enableDblClickZoom : false,
//        mapMode : 0,
//        activateTrafficMap : false,
//        activateBicycleMap : false,
//        minMaxLevel : [ 5, 13 ],
//        size : new nhn.api.map.Size(410, 223)
//    });
//    // 여기까지
//
//    // 여기부터 busking-popup의 map입니다.
////    var busking_oPoint = new nhn.api.map.LatLng(37.5010226, 127.0396037);
////    nhn.api.map.setDefaultPoint('LatLng');
////    oMap = new nhn.api.map.Map('busking-location-map' ,{
////        point : oPoint,
////        zoom : 10,
////        enableWheelZoom : true,
////        enableDragPan : true,
////        enableDblClickZoom : false,
////        mapMode : 0,
////        activateTrafficMap : false,
////        activateBicycleMap : false,
////        minMaxLevel : [ 5, 13 ],
////        size : new nhn.api.map.Size(998, 290)
////    });
//    // 여기까지
//
//    //여기부터 location-search-popup의 map입니다.
//    var location_search_oPoint = new nhn.api.map.LatLng(37.5010226, 127.0396037);
//    nhn.api.map.setDefaultPoint('LatLng');
//    oMap = new nhn.api.map.Map('search-map' ,{
//        point : location_search_oPoint,
//        zoom : 10,
//        enableWheelZoom : true,
//        enableDragPan : true,
//        enableDblClickZoom : false,
//        mapMode : 0,
//        activateTrafficMap : false,
//        activateBicycleMap : false,
//        minMaxLevel : [ 5, 13 ],
//        size : new nhn.api.map.Size(568, 460)
//    });
//    var circle = new nhn.api.map.Circle({
//        strokeColor : "red",
//        strokeOpacity : 1,
//        strokeWidth : 1,
//        fillColor : "blue",
//        fillOpacity : 0.5
//    });
//    var radius = 200; // - radius의 단위는 meter
//    circle.setCenterPoint(location_search_oPoint); // - circle 의 중심점을 지정한다.
//    circle.setRadius(radius); // - circle 의 반지름을 지정하며 단위는 meter이다.
//    circle.setStyle("strokeColor", "blue"); // - 선의 색깔을 지정함.
//    circle.setStyle("strokeWidth", 5); // - 선의 두께를 지정함.
//    circle.setStyle("strokeOpacity", 0.5); // - 선의 투명도를 지정함.
//    circle.setStyle("fillColor", "blue"); // - 채우기 색상. none 이면 투명하게 된다.
//    oMap.addOverlay(circle);
//
//    //여기까지
//});







