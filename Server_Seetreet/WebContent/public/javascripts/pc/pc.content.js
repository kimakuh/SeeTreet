/**
 * Created by Limjiuk on 2014-10-26.
 */
var client = {};
client.latitude = '';
client.longitude = '';
//var latitude
//var longitude;
var current_content_num = 0;
var current_group_num = 0;

var initialize_location = function(cb){
    var err_msg = '';
    function handleNoGeolocation(errorFlag) {
        if (errorFlag) {
            var err_msg = 'Error: The Geolocation service failed';
        } else {
            var err_msg = 'Error : Your Browser doesn\'t support geolocation';
        }
    }
    // Try HTML5 geolocation
    if(navigator.geolocation){
        navigator.geolocation.getCurrentPosition(function(position){
            client.latitude = position.coords.latitude;
            client.longitude = position.coords.longitude;
            cb();
        }, function(){
            handleNoGeolocation(true);
        });
    }
    else{
        handleNoGeolocation(false);
    }
};
var contentshow = function(latitude_, longitude_){
    // 박스를 단지 그려주는 역할을 한다.
    // box_Factory.content.appendArray('30', '50', function()
    box_Factory.content.appendArray(latitude_, longitude_, function(){
        // 가져온 데이터를 그려준다.
        var offset_index = current_content_num;
        for(var i = offset_index; i < offset_index + 7;i++){
            // 현재 그려진 데이터 개수 갱신
            current_content_num++;
            // group번호 갱신
            if(current_content_num % 7 == 1){
                current_group_num++;
                box_Factory.content.createContentGroup(current_group_num);
            }
            // content를 화면에 그려준다.
            if(current_content_num % 7 == 1){
                box_Factory.content.createContent(box_Factory.content.contentArray[i], current_group_num ,'large');
            }
            else{
                box_Factory.content.createContent(box_Factory.content.contentArray[i], current_group_num ,'small');
            }
        }
    });
};

$(document).ready(function(){
    initialize_location(function(){
        contentshow(client.latitude, client.longitude);
    });
    $(document).on("click", ".content", function(e){
        var target_dataindex = $(e.currentTarget).attr('data-index');
        var contentinfo = box_Factory.content.get_a_content(target_dataindex);
        modal_Factory.contentModal.loadModal(contentinfo, function(){
            modal_Factory.checkLike(target_dataindex, function(){
               modal_Factory.getReply(target_dataindex, function(){
                   modal_Factory.getReplyCount(target_dataindex);
               });
            });
        });
    });
    $('.content-append-area').find('img').click(function(){
        contentshow(client.latitude, client.longitude);
    });
    $('#content-popup').find('.provider-info-area').click(function(e){
        console.log('click!!!');
        var targetId = $(e.currentTarget).attr('identification-value');
        modal_Factory.providerModal.getProviderInfo(targetId);
    });
    // 여기 까지 구현



    $('#content-popup').find('.artist-info-area').click(function(e){
        var targetId = $(e.currentTarget).attr('identification-value');
        modal_Factory.artistModal.getArtistInfo(targetId, function(){
            modal_Factory.artistModal.loadModal();
        });
    });

    $('#search-location').click(function(){
        map_Manage.search_popup_map.clearMap();
        $('#finish-search').show();
        $('#provider-finish-search').hide();
        $('#location-search-popup').modal('show');
    });

    $('#location-search-body').keydown(function(e){
        var code = e.keyCode || e.which;
        if(code == 13){
            var find_location = $('#location-search-body').val();
            map_Manage.searchlocation(find_location, function(){
                map_Manage.set_searchMap('search');
            });
        }
    });
    $('#finish-search').click(function(){
        client.latitude = map_Manage.userSelectLocation.getLat();
        client.longitude = map_Manage.userSelectLocation.getLng();
        map_Manage.refreshContent(client.latitude, client.longitude);
        $('#location-search-popup').modal('hide');
    });
    $('#upload-comment-image').click(function(e){
        $('#imageFileInput').attr('name', $(e.currentTarget).attr('id'));
        document.getElementById('imageFileInput').click();
    });
    $('#comment-submit').click(function(e){
        var contentId = $(e.currentTarget).attr('content-index');
        modal_Factory.reply.submitReply(contentId);
    });
    // postlikeContent
    $('#content-popup').find('.content-detail-area').find('.content-detail-likecount').click(function(e){
        var contentId = $(e.currentTarget).attr('data-index');
        var likeflag;
        // 좋아요를 누른 상태
        if($(e.currentTarget).find('.likeActivate').css('display') == 'inline'){
            likeflag = false;
            modal_Factory.submitLike(contentId, likeflag);
        }
        // 좋아요를 아직 누리지 않은 상태
        else{
            likeflag = true;
            modal_Factory.submitLike(contentId, likeflag);
        }
    });
    $('#artist-popup').on('hide.bs.modal', function(){
        stopVideo();
    });
});




