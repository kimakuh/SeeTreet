/**
 * Created by Limjiuk on 2014-11-06.
 */

var modal_Factory = {};

modal_Factory.contentModal = {};
modal_Factory.contentModal.loadModal = function(contentinfo, callback){
    modal_Factory.reply.clearReplyList();
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
    var content_time = modal_Factory.content_convert_time(contentinfo.contentStartTime, contentinfo.contentEndTime);

    var storeTitle = contentinfo.provider.StoreTitle;
    var providerAddress = contentinfo.provider.StoreAddress;
    var artist_description      = '';
    var provider_description    = '';
    if(content_type == 'public'){
        artist_description = '';
        provider_description = modal_Factory.omit_unnecessary_description(contentinfo.provider.description);
    }
    else{
        artist_description = modal_Factory.omit_unnecessary_description(contentinfo.artists[0].description);
        provider_description = modal_Factory.omit_unnecessary_description(contentinfo.provider.description);
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
            artist_img_array.push('./images/seetreetimg/content_default.jpg');
        }
    }
    else{
        for(var index = 0 ; index < 3 ; index++){
            if(index < contentinfo.artists[0].artistImages.length){
                artist_img_array.push(contentinfo.artists[0].artistImages[index]);
            }
            else{
                artist_img_array.push('./images/seetreetimg/content_default.jpg');
            }
        }
    }
    var mapinfo = {
        latitude : contentinfo.provider.location.coordinates[1],
        longitude : contentinfo.provider.location.coordinates[0],
        contentType : content_type,
        target : content_oMap,
        storeName : storeTitle
    };
    var providerId = contentinfo.provider._id;
    var artistId;
    if(content_type == "private"){
        artistId = contentinfo.artists[0]._id;
    }
    else{
        artistId = "seetreet";
    }
    var likecount = contentinfo.likecount;
    var contentId = contentinfo._id;

    // 이미지 채우기
    var img_target_provider = $('#content-popup').find('.provider-image-slider');
    modal_Factory.addImageslider(provider_img_array, img_target_provider);
    var img_target_artist = $('#content-popup').find('.artist-image-slider');
    modal_Factory.addImageslider(artist_img_array, img_target_artist);
    // 내용 채우기
    $('#content-popup').find('.write-comment-area').find('.comment-id').text(getCookie(COOKIE_USER_ID));
    $('#content-popup').find('.content-detail-area').find('.content-detail-title').find('.detail-body').text(content_name);
    $('#content-popup').find('.content-detail-area').find('.content-detail-artisttitle').find('.detail-body').text(artist_name);
    $('#content-popup').find('.content-detail-area').find('.content-detail-showtime').find('.detail-body').text(content_time);
    $('#content-popup').find('.content-detail-area').find('.content-detail-provider').find('.detail-body').text(storeTitle);
    $('#content-popup').find('.content-detail-area').find('.content-detail-address').find('.detail-body').text(providerAddress);
    $('#content-popup').find('.content-detail-area').find('.content-detail-artistdescription').find('.description').text(artist_description);
    $('#content-popup').find('.content-detail-area').find('.content-detail-providerdescription').find('.description').text(provider_description);
    $('#content-popup').find('.content-detail-area').find('.content-detail-likecount').find('.likecount').text(likecount);
    $('#content-popup').find('.content-detail-area').find('.content-detail-likecount').attr('data-index', contentId);
    $('#content-popup').find('#comment-submit').attr('content-index', contentId);

    $('#content-popup').find('.additional-info-area').find('.provider-info-area').find('img').attr('src', provider_img_array[0]);
    $('#content-popup').find('.additional-info-area').find('.provider-info-area').find('span').text(storeTitle);
    $('#content-popup').find('.additional-info-area').find('.provider-info-area').find('span').text(storeTitle);
    $('#content-popup').find('.additional-info-area').find('.provider-info-area').attr('identification-value', providerId);
    $('#content-popup').find('.additional-info-area').find('.artist-info-area').find('img').attr('src', artist_img_array[0]);
    $('#content-popup').find('.additional-info-area').find('.artist-info-area').find('span').text(artist_name);
    $('#content-popup').find('.additional-info-area').find('.artist-info-area').attr('identification-value', artistId);



    map_Manage.set_map(mapinfo);
    modal_Factory.hideAndshowinfo(content_type);
    callback();
};



modal_Factory.checkLike = function(contentId, callback){
    getChecklikefromMe(contentId, function(data, status, res){
        if(status == 'success'){
            // like처리
            if(data.data == false){
                // 비활성좋아요아이콘
                $('#content-popup').find('.content-detail-likecount').find('.likeActivate').hide();
                $('#content-popup').find('.content-detail-likecount').find('.likeDeActivate').show();
            }
            else{
                // 활성좋아요아이콘
                $('#content-popup').find('.content-detail-likecount').find('.likeActivate').show();
                $('#content-popup').find('.content-detail-likecount').find('.likeDeActivate').hide();
            }
            callback();
        }
    });
};
modal_Factory.submitLike = function(contentId, likeflag){
    postlikeContent(contentId, likeflag, function(data, status, res){
        if(status == 'success'){
            // 좋아요를 비활성화
            if(data.data == true){
                if(likeflag == false){
                    $('#content-popup').find('.content-detail-area').find('.content-detail-likecount[data-index = "' + contentId + '"]').find('.likeActivate').hide();
                    $('#content-popup').find('.content-detail-area').find('.content-detail-likecount[data-index = "' + contentId + '"]').find('.likeDeActivate').show();
                    var current_likecount = $('#content-popup').find('.content-detail-area').find('.content-detail-likecount[data-index = "' + contentId + '"]').find('.likecount').text();
                    current_likecount--;
                    $('#content-popup').find('.content-detail-area').find('.content-detail-likecount[data-index = "' + contentId + '"]').find('.likecount').text(current_likecount);
                }
                else{
                    $('#content-popup').find('.content-detail-area').find('.content-detail-likecount[data-index = "' + contentId + '"]').find('.likeActivate').show();
                    $('#content-popup').find('.content-detail-area').find('.content-detail-likecount[data-index = "' + contentId + '"]').find('.likeDeActivate').hide();
                    var current_likecount = $('#content-popup').find('.content-detail-area').find('.content-detail-likecount[data-index = "' + contentId + '"]').find('.likecount').text();
                    current_likecount++;
                    $('#content-popup').find('.content-detail-area').find('.content-detail-likecount[data-index = "' + contentId + '"]').find('.likecount').text(current_likecount);
                }
            }
        }
    });
};

modal_Factory.reply = {};
modal_Factory.reply.current_replypage = 1;
modal_Factory.getReply = function(contentId){
    $('#content-popup').modal('show');
    getcontentReply(contentId, modal_Factory.reply.current_replypage, function(data, status, res){
        if(status == 'success'){
            if(data.state == true){
                var replyArray = data.data;
                for(var i in replyArray){
                    modal_Factory.reply.prependReplyData(replyArray[i]);
                }
                modal_Factory.reply.current_replypage++;
            }
        }
    });
};
// 댓글 이미지 등록 때 담아 놓을 변수
modal_Factory.replyImage = '';

modal_Factory.reply.replyImage ='';
modal_Factory.reply.submitReply = function(contentId){
    var replytext = $('#content-comment').val();
    var replyImage = modal_Factory.reply.replyImage;
    // http://cphoto.asiae.co.kr/listimglink/6/2013111815071932564_1.jpg
//    var replyImage = 'http://cphoto.asiae.co.kr/listimglink/6/2013111815071932564_1.jpg';
    postwriteReply(contentId, replytext, replyImage, function(data, status, res){
        if(status == 'success'){
            modal_Factory.reply.clearReply();
            modal_Factory.reply.prependReplyData(data.data);
            // Prepend Reply 함수 호출
        }
    });
};
modal_Factory.reply.clearReply = function(){
    // 사진 청소
    $('#upload-comment-image').attr('src', './images/seetreetimg/default_image.jpg');
    // replyImage 변수 청소
    modal_Factory.reply.replyImage ='';
    // replytext청소
    $('#content-comment').val('');
};
modal_Factory.reply.clearReplyList = function(){
    $('#content-popup').find('.content-comment-area *').remove();
    modal_Factory.reply.current_replypage = 1;
};
modal_Factory.reply.prependReplyData = function(replydata){
    var replyImage;
    if(replydata.replyimage == ''){
        replyImage = "./images/seetreetimg/default_image.jpg";
    }
    else{
        replyImage = "http://" + replydata.replyimage;
    }
    $('#content-popup').find('.content-comment-area').prepend(
        '<div class = "comment-object" comment-id = "' + replydata.contentId + '">'
            + '<img  class = "user-upload-image" src = ' + replyImage + '>'
            + '<div class = "comment-user-name">' + replydata.userEmail + '</div>'
            + '<div class = "comment-upload-time">2014/09/07 17:53:00</div>'
            + '<div class = "comment-user-content">' + replydata.replytext +'</div>'
    );
};

modal_Factory.hideAndshowinfo = function(contentType){
    if(contentType == 'public'){
        // 아티스트 소개 (hide)
        $('#content-popup').find('.content-detail-area').find('.content-detail-artistdescription').hide();
        // 아티스트 이름 (hide)
        $('#content-popup').find('.artist-info-area').hide();
    }
    else{
        // 아티스트 소개 (show)
        $('#content-popup').find('.content-detail-area').find('.content-detail-artistdescription').show();
        // 아티스트 이름 (show)
        $('#content-popup').find('.artist-info-area').show();
    }
};



modal_Factory.providerModal ={};
modal_Factory.providerModal.getProviderInfo = function(providerId){
    $('#content-popup').modal('hide');
    getProvider(providerId, function(data, status, res){
        if(status == 'success'){
            var providerdata = data.data;
            modal_Factory.providerModal.loadModal(providerdata);
            $('#provider-popup').modal('show');
        }
        else{
            alert('실패');
        }
    });
};
modal_Factory.providerModal.loadModal = function(providerInfo){
    // provider Image Array
    var providerImageSlider = $('#provider-popup').find('.provider-image-slider');
    modal_Factory.addImageslider(providerInfo.providerImage, providerImageSlider);
    // StoreTitle
    var Modal_storeTitle = providerInfo.StoreTitle;
    $('#provider-popup').find('.detail-area').find('.provider-detail-title').find('span').text(Modal_storeTitle);
    // StoreType
    var Modal_storeType = providerInfo.StoreType;
    $('#provider-popup').find('.detail-area').find('.detail-location').find('.detail-body').text(Modal_storeType);
    // location address
    var Modal_storeAddress = providerInfo.StoreAddress;
    $('#provider-popup').find('.detail-area').find('.detail-category').find('.detail-body').text(Modal_storeAddress);
    // store description
    var Modal_description = modal_Factory.omit_unnecessary_description(providerInfo.description);
    $('#provider-popup').find('.detail-area').find('.detail-description').find('.detail-body').text(Modal_description);
    // favorite Genre
    var Modal_favoriteGenre = providerInfo.favoriteGenre.category + ' / ' + providerInfo.favoriteGenre.detailGenre;
    $('#provider-popup').find('.detail-area').find('.detail-want-category').find('.detail-body').text(Modal_favoriteGenre);
    // location geography
    var mapinfo = {
        latitude : providerInfo.location.coordinates[1],
        longitude : providerInfo.location.coordinates[0],
        contentType : "public",
        target : provider_oMap,
        storeName : Modal_storeTitle
    };
    map_Manage.set_map(mapinfo);

};

modal_Factory.providerModal.loadHistory = function(){

};


modal_Factory.artistModal = {};
modal_Factory.artistModal.artistInfo = {};
modal_Factory.artistModal.getArtistInfo = function(artistId, callback){
    // 5475b49be7760be08ac72c00
    getArtist(artistId, function(data, status, res){
        if(status == 'success'){
            modal_Factory.artistModal.artistInfo = data.data;

            callback();
        }
        else{
            alert('실패');
        }
    });
};

modal_Factory.artistModal.loadModal = function(){
    var artistInfo = modal_Factory.artistModal.artistInfo;
    // 사진
    var artistImages = artistInfo.artistImages;
    var targetSlider = $('#artist-popup').find('.artist-image-slider');
    modal_Factory.addImageslider(artistImages, targetSlider);
    // 이름
    var artist_name = artistInfo.name;
    $('#artist-popup').find('.artist-detail-title').find('span').text(artist_name);
    // 장르
    var artist_genre = artistInfo.artistGenre[0].category;
    var artist_detailgenre = artistInfo.artistGenre[0].detailGenre;
    var show_genre = artist_genre + ' / ' + artist_detailgenre;
    $('#artist-popup').find('.detail-category').find('.detail-body').text(show_genre);
    // 선호지역
    var favorite_location = artistInfo.favoriteLocation[0].name;
//    var favorite_location = "서울시 마포구 서교동";
    $('#artist-popup').find('.detail-location').find('.detail-body').text(favorite_location);
    // 소개
    var artist_description = artistInfo.description;
    $('#artist-popup').find('.detail-description').find('.detail-body').text(artist_description);
    $('#content-popup').modal('hide');
    $('#artist-popup').modal('show');
};



var teststring = '2014년 3월부터 매월 셋째 주 목요일마다&lt; 성남아트센터 &lt;마티네 콘서트&gt;가 진행된다. 보&lt;다 다양한 주제를 통해 폭넓은 클래식 레퍼토리를다루고자 한다. 최수열 지취자가 이끄는 여러 단체의 유려한 연주와 4년 연속 &lt;마티네콘서트&gt;의진행' +
    '을 맡은 팝페라 가수 카이의 따뜻한 해설로 클래식에 대한 궁금증을 ' +
    '해결해 나간다.<br>'
var unnessary_description = ["<br>", "<br />", "&lt;", "&gt;", "&nbsp;"];
modal_Factory.omit_unnecessary_description = function(description){
    var result_description = description;
    for(var word in unnessary_description){
        while(result_description.search(unnessary_description[word]) != -1){
            result_description = result_description.replace(unnessary_description[word], "");
        }
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
    var imageArrLength = imagearray.length;
    for(var index=0;index<3;index++){
        if(index < imageArrLength){
            $(targetslider).find('.pic' + index).attr('src', imagearray[index]);
        }
        else{
            $(targetslider).find('.pic' + index).attr('src', './images/seetreetimg/content_default.jpg');
        }
    }
};

