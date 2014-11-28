/**
 * Created by Limjiuk on 2014-10-28.
 */
var CONST_MAINCATEGORY  = ["장르를 선택해주세요", "음악", "미술", "마술", "플래시몹"];

var CONST_SUBCATEGORY   = ["세부장르를 선택하세요", "인디공연",  "팝", "힙합", "전시회"];

var CONST_STORETYPE     = ["업종을 선택하세요", "커피전문점", "호프", "음식점", "라이브카페"];

var maincategory2Index = function(value){
    var returnIndex = 0;
    for(var i in CONST_MAINCATEGORY){
        if(value == CONST_MAINCATEGORY[i]){
            returnIndex = i;
        }
    }
    return returnIndex;
};
var subcategory2Index = function(value){
    var returnIndex;
    for(var i in CONST_SUBCATEGORY){
        if(value == CONST_SUBCATEGORY[i]){
            returnIndex = i;
        }
    }
    return returnIndex;
};
var storeType2Index = function(value){
    var returnIndex;
    for(var i in CONST_STORETYPE){
        if(value == CONST_STORETYPE[i]){
            returnIndex = i;
        }
    }
    return returnIndex;
};