/**
 * Created by Limjiuk on 2014-10-28.
 */
$(document).ready(function(){
    $('.listboxgroup.artist.busking').find('.listbox.createbox').click(function(){
        $('#busking-popup').modal('show');
    });

    $('#busking-register').click(function(){
        $('#busking-popup').modal('hide');
        box_Factory.artist.create_busking_box();
    });




});

manage_artistinfo = {};

manage_artistinfo.load = {};
manage_artistinfo.load.loadpage = function(){
    $('.main-provider').hide();
    $('.main-artist').show();
    // 아티스트 객체 정보가 있는지 없는지 검사
    getCheckArtist(function(data, status, res){
        if(status == 'success'){
            if(data.data == true){
                // 아티스트 데이터 있음 => 메인메이지 로드

            }
            else{
                // 아티스트  데이터 없음 => 아티스트 정보 가입 페이지 로드
                manage_artistinfo.join.loadjoinartist('artistinput0');
            }
        }
    });

};
manage_artistinfo.load.mainartist = function(){

};
manage_artistinfo.join = {};
manage_artistinfo.join.loadjoinartist = function(buttonId){
    $('.main-artist').find('.modifytab').hide();
    $('.main-artist').find('.listtab').hide();
    $('.main-artist').find('.start-area *').hide();
    $('.main-artist').find('.start-area').show();
    var nth = buttonId.substr(11,1);
    if(nth == 0){
        // start-input-first load
        $('.main-artist').find('.start-area').find('.start-input-first').show();
        $('.main-artist').find('.start-area').find('.start-input-first *').show();
    }
    else if(nth == 1){
        // start-input-first save
        // start-input-second load
    }
    else if(nth == 2){
        // start-input-second save
        // start-input-third load
    }
    else if(nth == 3){
        // start-input-third save
        // start-input-fourth load
    }

};
manage_artistinfo.join.input_check = function(pagenum){
    // 버튼의 아이디 뒷자리 숫자로 판별한다.

};
manage_artistinfo.modify = {};
manage_artistinfo.modify.getUserInfo = function(){

};
manage_artistinfo.modify.getArtistInfo = function(){

};
manage_artistinfo.modify.setModifyTab = function(){

};






