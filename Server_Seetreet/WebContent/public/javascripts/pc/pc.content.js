/**
 * Created by Limjiuk on 2014-10-26.
 */
var latitude;
var longitude;
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
            latitude = position.coords.latitude;
            longitude = position.coords.longitude;
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
        contentshow(latitude, longitude);
    });
    $(document).on("click", ".content", function(e){
            var target_dataindex = $(e.currentTarget).attr('data-index');
            var contentinfo = box_Factory.content.get_a_content(target_dataindex);
//        var contentinfo = box_Factory.content.contentArray[target_dataindex];

            modal_Factory.contentModal.loadModal(contentinfo);
    });
    $('.content-append-area').find('img').click(function(){
        contentshow(latitude, longitude);
    });
    $('#content-popup').find('.provider-info-area').click(function(e){
        var targetId = $(e.currentTarget).attr('identification-value');
        modal_Factory.providerModal.getProviderInfo(targetId);
    });
    $('#content-popup').find('.artist-info-area').click(function(){
        $('#content-popup').modal('hide');
        $('#artist-popup').modal('show');
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
        map_Manage.refreshContent(map_Manage.userSelectLocation.getLat(), map_Manage.userSelectLocation.getLng());
        $('#location-search-popup').modal('hide');
    });
});




