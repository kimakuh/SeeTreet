/**
 * Created by Limjiuk on 2014-11-06.
 */

var modal_Factory = {};

modal_Factory.contentModal = {};
modal_Factory.contentModal.loadModal = function(contentinfo){
    var content_type = '';
    if(contentinfo["contentType"] == 'PUBLIC'){
        content_type = 'public';
    }
    else{
        content_type = 'private';
    }
    var content_name = contentinfo.contentTitle;
    if(content_type == 'private'){
        var artist_name = contentinfo.artists[0].name;
    }
//    var artist_name = "임지욱";

    var content_time = modal_Factory.content_convert_time(contentinfo.contentStartTime, contentinfo.contentEndTime);
//    var content_time = modal_Factory.content_convert_time("1411200300PM", "1411200630PM");

    var storeTitle = contentinfo.provider.StoreTitle;
    var providerAddress = contentinfo.provider.StoreAddress;
//    var providerAddress = '수원시 팔달구 인계동 1125번지';
    var artist_description      = '';
    var provider_description    = '';
    if(content_type == 'public'){
        artist_description = '';
//        provider_description = modal_Factory.omit_unnecessary_description(contentinfo.provider.description);
        provider_description = modal_Factory.omit_unnecessary_description(contentinfo.provider.description);
    }
    else{
        artist_description = modal_Factory.omit_unnecessary_description(contentinfo.artists[0].description);
        provider_description = modal_Factory.omit_unnecessary_description(contentinfo.provider.description);
//        artist_description = contentinfo.artists[0].description;
//        provider_description = contentinfo.provider.description;
    }
    var provider_img_array = new Array();
    for(var index=0 ; index < 3 ; index++){
        if(index < contentinfo.provider.providerImage.length){
            if(contentinfo.provider.providerImage[index] == ''){
                provider_img_array.push("./images/seetreetimg/content_default.jpg");
            }
            else{
                provider_img_array.push(contentinfo.provider.providerImage[index]);
            }
        }
        else{
            provider_img_array.push('./images/seetreetimg/content_default.jpg');
        }
    }
    var artist_img_array = new Array();
    if(content_type == 'public'){
        for(var index=0 ; index < 3 ; index++){
            artist_img_array.push('./images/seetreetimg/default_image.jpg');
        }
    }
    else{
        for(var index = 0 ; index < 3 ; index++){
            if(index < contentinfo.artists[0].artistImages.length){
                artist_img_array.push(contentinfo.artists[0].artistImages[index]);
            }
            else{
                artist_img_array.push('./images/seetreetimg/default_image.jpg');
            }
        }
    }
    var mapinfo = {
        latitude : contentinfo.provider.location.coordinates[1],
        longitude : contentinfo.provider.location.coordinates[0],
        contentType : content_type,
        storeName : storeTitle,
        target : content_oMap
    };

    $('#content-popup').modal('show');
    console.log('모달 열리고 데이터 로딩');
    // 이미지 채우기
    var img_target_provider = $('#content-popup').find('.provider-image-slider');
    modal_Factory.addImageslider(provider_img_array, img_target_provider);
    var img_target_artist = $('#content-popup').find('.artist-image-slider');
    modal_Factory.addImageslider(artist_img_array, img_target_artist);
    // 내용 채우기
    $('#content-popup').find('.write-comment-area').find('.comment-id').text(getCookie(COOKIE_USER_NAME));
    $('#content-popup').find('.content-detail-area').find('.content-detail-title').find('.detail-body').text(content_name);
    $('#content-popup').find('.content-detail-area').find('.content-detail-artisttitle').find('.detail-body').text(artist_name);
    $('#content-popup').find('.content-detail-area').find('.content-detail-showtime').find('.detail-body').text(content_time);
    $('#content-popup').find('.content-detail-area').find('.content-detail-provider').find('.detail-body').text(storeTitle);
    $('#content-popup').find('.content-detail-area').find('.content-detail-address').find('.detail-body').text(providerAddress);
    $('#content-popup').find('.content-detail-area').find('.content-detail-artistdescription').find('.description').text(artist_description);
    $('#content-popup').find('.content-detail-area').find('.content-detail-providerdescription').find('.description').text(provider_description);
    $('#content-popup').find('.additional-info-area').find('.provider-info-area').find('img').attr('src', provider_img_array[0]);
    $('#content-popup').find('.additional-info-area').find('.provider-info-area').find('span').text(storeTitle);
    $('#content-popup').find('.additional-info-area').find('.artist-info-area').find('img').attr('src', artist_img_array[0]);
    $('#content-popup').find('.additional-info-area').find('.artist-info-area').find('span').text(artist_name);
    map_Manage.set_map(mapinfo);
//    $('#content-popup').on('shown.bs.modal', function(){
//        console.log('모달 열리고 데이터 로딩');
//        // 이미지 채우기
//        var img_target_provider = $('#content-popup').find('.provider-image-slider');
//        modal_Factory.addImageslider(provider_img_array, img_target_provider);
//        var img_target_artist = $('#content-popup').find('.artist-image-slider');
//        modal_Factory.addImageslider(artist_img_array, img_target_artist);
//        // 내용 채우기
//        $('#content-popup').find('.write-comment-area').find('.comment-id').text(getCookie(COOKIE_USER_NAME));
//        $('#content-popup').find('.content-detail-area').find('.content-detail-title').find('.detail-body').text(content_name);
//        $('#content-popup').find('.content-detail-area').find('.content-detail-artisttitle').find('.detail-body').text(artist_name);
//        $('#content-popup').find('.content-detail-area').find('.content-detail-showtime').find('.detail-body').text(content_time);
//        $('#content-popup').find('.content-detail-area').find('.content-detail-provider').find('.detail-body').text(storeTitle);
//        $('#content-popup').find('.content-detail-area').find('.content-detail-address').find('.detail-body').text(providerAddress);
//        $('#content-popup').find('.content-detail-area').find('.content-detail-artistdescription').find('.description').text(artist_description);
//        $('#content-popup').find('.content-detail-area').find('.content-detail-providerdescription').find('.description').text(provider_description);
//        $('#content-popup').find('.additional-info-area').find('.provider-info-area').find('img').attr('src', provider_img_array[0]);
//        $('#content-popup').find('.additional-info-area').find('.provider-info-area').find('span').text(storeTitle);
//        $('#content-popup').find('.additional-info-area').find('.artist-info-area').find('img').attr('src', artist_img_array[0]);
//        $('#content-popup').find('.additional-info-area').find('.artist-info-area').find('span').text(artist_name);
//        map_Manage.set_map(mapinfo);
//    });
};
var teststring = '2014년 3월부터 매월 셋째 주 목요일마다 성남아트센터 &lt;마티네 콘서트&gt;가 진행된다. 보다 다양한 주제를 통해 폭넓은 클래식 레퍼토리를다루고자 한다. 최수열 지취자가 이끄는 여러 단체의 유려한 연주와 4년 연속 &lt;마티네콘서트&gt;의진행' +
    '을 맡은 팝페라 가수 카이의 따뜻한 해설로 클래식에 대한 궁금증을 ' +
    '해결해 나간다.<br>'
var unnessary_description = ["<br>", "<br />", "&lt;", "&gt;", "&nbsp;", "&nbsp;"];

modal_Factory.omit_unnecessary_description = function(description){
    var result_description = description;
    for(var word in unnessary_description){
        console.log(unnessary_description[word]);
        result_description = result_description.replace(unnessary_description[word], "");
    }
    return result_description;
};

// 모달에 띄우는 시간을 변형시키는 함수
modal_Factory.content_convert_time = function(starttime, endtime){
    var showday = '';
    showday = starttime.substr(0,4) + "년 " + starttime.substr(4,2) + "월 " + starttime.substr(6,2) + "일 "
        + starttime.substr(8,2) + ":" + starttime.substr(10,2) + starttime.substr(12,2) + " ~ "
        + endtime.substr(4,2) + "월 " + endtime.substr(6,2) + "일 "
        + endtime.substr(8,2) + ":" + endtime.substr(10,2) + endtime.substr(12,2);
    return showday;
};

// 모달에 이미지 슬라이더에 그림을 삽입
modal_Factory.addImageslider = function(imagearray, targetslider){
    for(var index=0;index<3;index++){
        var number = index+1;
        $(targetslider).find('.pic' + number).attr('src', imagearray[index]);
    }
};

