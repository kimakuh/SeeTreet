/**
 * Created by Limjiuk on 2014-10-28.
 */
$(document).ready(function(){

    // start-area의 select option을 초기화한다.
    manage_artistinfo.load.setOption();


    $('.listboxgroup.artist.busking').find('.listbox.createbox').click(function(){
        $('#busking-popup').modal('show');
    });

    $('#busking-register').click(function(){
        $('#busking-popup').modal('hide');
        box_Factory.artist.create_busking_box();
    });

    // start-area에 있는 다음 버튼을 눌렀을 때의 동작
//    $('.main-artist').find('.start-area').find('button').click(function(e){
//        var targetIdname = $(e.currentTarget).attr('id');
//        console.log(targetIdname.indexOf('artistinput'));
//        if(targetIdname.indexOf('artistinput') != -1){
//            manage_artistinfo.join.loadjoinartist(targetIdname);
//        }
//    });

    $('.main-artist').find('.start-pic-list').find('img').click(function(e){
        $('#imageFileInput').attr('name', $(e.currentTarget).attr('id'));
        document.getElementById('imageFileInput').click();
    });
    $('#artist-search-body').keyup(function(e){
        var code = e.keyCode || e.which;
        if(code == 13){
            var find_location = $('#artist-search-body').val();
            map_Manage.searchlocation_artist(find_location, function(){
                map_Manage.set_searchMap('artist');
            });
        }
    });

    $('.main-artist').find('.start-area').find('button').click(function(e){
        var targetIdname = $(e.currentTarget).attr('id');
        console.log(targetIdname);
        var comparestring = 'artistinput';
        if(targetIdname.indexOf(comparestring) != -1){
            var nth = targetIdname.substr(comparestring.length, 1);
            if(manage_artistinfo.join.input_check(nth) == true){
                manage_artistinfo.join.loadjoinartist(targetIdname);
            }
            else{
                alert(artistCreate_caution);
            }
        }
    });
});

var artistCreate_caution = '';

manage_artistinfo = {};

manage_artistinfo.load = {};
manage_artistinfo.load.setOption = function(){
    // 예외 처리 => textarea의 간단 소개 부분에 빈 공간이 들어가서 이 부분에서 예외처리한다.
    $('#artist-description').val('');
    for(var i in CONST_MAINCATEGORY){
        $('.main-artist').find('.start-main-category').append(
            '<option>' + CONST_MAINCATEGORY[i] + '</option>'
        );
        $('.modifytab.artist').find('.modify-category').find('.main-category').append(
                '<option>' + CONST_MAINCATEGORY[i] + '</option>'
        );
    }
    for(var i in CONST_SUBCATEGORY){
        $('.main-artist').find('.start-sub-category').append(
                '<option>' + CONST_SUBCATEGORY[i] + '</option>'
        );
        $('.modifytab.artist').find('.modify-category').find('.sub-category').append(
                '<option>' + CONST_SUBCATEGORY[i] + '</option>'
        );
    }
};
manage_artistinfo.load.loadpage = function(){
    $('.main-provider').hide();
    $('.main-artist').show();
    // 아티스트 객체 정보가 있는지 없는지 검사
    getCheckArtist(function(data, status, res){
        if(status == 'success'){
            if(data.data == true){
                // 아티스트 데이터 있음 => 메인메이지 로드
                manage_artistinfo.load.mainartist();
                // 아티스트의 왼쪽 기본 바 정보 세팅
                manage_artistinfo.modify.getUserInfo();
                Listtab.show(Listtab.TAB_ARTIST);
            }
            else{
                // 아티스트  데이터 없음 => 아티스트 정보 가입 페이지 로드
                manage_artistinfo.join.loadjoinartist('artistinput0');
            }
        }
    });
};

manage_artistinfo.load.mainartist = function(){
    $('.main-artist').find('.modifytab').show();
    $('.main-artist').find('.listtab').show();
    $('.main-artist').find('.start-area').hide();
};
manage_artistinfo.join = {};

manage_artistinfo.join.tempImage = '';
manage_artistinfo.join.imageArr = new Array();

manage_artistinfo.join.format = {
    name : "",
    artistImages : "", // 이미지 어레이
    videourl : "",
    artistGenre : "",
    description : "",
    favoriteLocation : ""
};
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
        /*아티스트 명, 아티스트 사진 3장 정보 저장*/
        manage_artistinfo.join.format.name = $('#input-artist-name').val();
        manage_artistinfo.join.format.artistImages = JSON.stringify(manage_artistinfo.join.imageArr);
        // start-input-second load
        $('.main-artist').find('.start-area').find('.start-input-second').show();
        $('.main-artist').find('.start-area').find('.start-input-second *').show();
    }
    else if(nth == 2){
        // start-input-second save
        var artistcategory = $('.main-artist').find('.start-main-category option:selected').val();
        var artistsubcategory = $('.main-artist').find('.start-sub-category option:selected').val();
        var genre = {
            category : artistcategory,
            detailGenre : artistsubcategory
        };
        manage_artistinfo.join.format.artistGenre = JSON.stringify([genre]);
        // start-input-third load
        $('.main-artist').find('.start-area').find('.start-input-third').show();
        $('.main-artist').find('.start-area').find('.start-input-third *').show();
    }
    else if(nth == 3){
        // start-input-third save
        var locationObject = {
            name : $('.main-artist').find('.start-input-third').find('.targetaddress').text(),
            description : null,
            l_lat : map_Manage.artistSelectLocation.getLat(),
            l_long : map_Manage.artistSelectLocation.getLng()
        };
        manage_artistinfo.join.format.favoriteLocation = JSON.stringify([locationObject]);
        // start-input-fourth load
        $('.main-artist').find('.start-area').find('.start-input-fourth').show();
        $('.main-artist').find('.start-area').find('.start-input-fourth *').show();
    }
    else if(nth == 4){
        manage_artistinfo.join.format.videourl = $('#artist-youtube-url').val();
        manage_artistinfo.join.format.description = $('#artist-description').val();
        postArtistCreate(manage_artistinfo.join.format , function(data, status, res){
            if(status == 'success'){
                alert('아티스트 정보가 입력되었습니다.');
                manage_artistinfo.load.loadpage();
            }
            else{
                alert('입력이 거절되었습니다.');
            }
        });
    }
};
manage_artistinfo.join.input_check = function(pagenum){
    // 버튼의 아이디 뒷자리 숫자로 판별한다.
    var inputcheck = true;
    // artistCreate_caution
    if(pagenum == 1){
        if($('#input-artist-name').val() == ''){
            inputcheck = false;
            artistCreate_caution = "아티스트의 이름을 적어주세요";
        }
        if(manage_artistinfo.join.imageArr.length < 3){
            inputcheck = false;
            artistCreate_caution = "아티스트의 사진 정보를 채워주세요";
        }
    }
    else if(pagenum == 2){
        if($('.main-artist').find('.start-main-category option:selected').index() == 0 ||
            $('.main-artist').find('.start-sub-category option:selected').index() == 0){
            inputcheck = false;
            artistCreate_caution = "아티스트의 장르를 선택해주세요";
        }
    }
    else if(pagenum == 3){
        // 맵정보가 입력되었는지
        if($('.main-artist').find('.start-input-third').find('.targetaddress').text() == ''){
            inputcheck = false;
            artistCreate_caution = '맵에 특정 지역을 선택해주세요';
        }
    }
    else if(pagenum == 4){
        if($('#artist-youtube-url').val() == ''){
            inputcheck = false;
            artistCreate_caution = 'youTube Url을 입력해주세요.';
        }
        if($('.main-artist').find('.start-input-fourth').find('textarea').val() == ''){
            inputcheck = false;
            artistCreate_caution = '소개말을 입력해주세요';
        }
    }
    return inputcheck;
};
manage_artistinfo.modify = {};
manage_artistinfo.modify.artistInfo = {};
manage_artistinfo.modify.getUserInfo = function(){
    getUser(function(data, status, res){
        if(status == 'success'){
            var artistInfomation = data.data.user_artist;
            manage_artistinfo.modify.setModifyTab(artistInfomation);
        }
    });
};
manage_artistinfo.modify.setModifyTab = function(artistInfo){
    manage_artistinfo.modify.artistInfo = artistInfo;
    // 아티스트 이름
    var artistName = artistInfo.name;
    $('.modifytab.artist').find('.modify-name').find('input').val(artistName);
    // 아티스트 장르 세팅
    var maincategoryIndex = maincategory2Index(artistInfo.artistGenre[0].category);
    var subcategoryIndex = subcategory2Index(artistInfo.artistGenre[0].detailGenre);
    $('.modifytab.artist').find('.modify-category').find('select.main-category option').eq(maincategoryIndex).prop('selected', true);
    $('.modifytab.artist').find('.modify-category').find('select.sub-category option').eq(subcategoryIndex).prop('selected', true);
    // 선호지역
    var location_address = artistInfo.favoriteLocation[0].name;
    $('.modifytab.artist').find('.modify-location').find('input').val(location_address);
    // YouTubeUrl
    var youtube_url = artistInfo.artistUrl;
    $('.modifytab.artist').find('.modify-youtube').find('input').val(youtube_url);
    // 소개말
    var artist_description = artistInfo.description;
    $('.modifytab.artist').find('.modify-description').find('textarea').val(artist_description);
    // 아티스트 사진 정보 세팅
    for(var i in artistInfo.artistImages){
        $('.modifytab.artist').find('.pic-youtube').find('.pic' + i).attr('src', 'http://' + artistInfo.artistImages[i]);
    }
};






