$(document).ready(function(){
    // 각 select 박스들을 초기화한다.
    manage_providerinfo.setProviderInput();


//    $('.provider-create-area').find('.listbox').on("click", function(){
//        $('.provider-create-area').find('.listbox').off("click");
//        manage_providerbox.createcontent();
//        $('#time-setting-finish').on("click", function(){
//            $('#time-setting-finish').off("click");
//            $('.provider-create-area').find('.listbox').on("click");
//            manage_providerbox.inputcontenttime();
//        });
//    });

    $('.main-provider').find('.start-area').find('button').click(function(e){
        var targetIdname = $(e.currentTarget).attr('id');
        // ~targetIdname.indexOf('providerinput')
        if(targetIdname.match('providerinput')){
            var nth = targetIdname.substr(13,1);
            if(manage_providerinfo.join_input_check(nth) == true){
                manage_providerinfo.loadjoinprovider(targetIdname);
            }
            else{
                alert(caution_message);
            }
        }
    });
    $('.main-provider').find('.start-pic-list').find('img').click(function(e){
        var imageindex = $(e.currentTarget).attr('id').substr(18,1);
//        manage_providerinfo.uploadimage(imageindex);
        $('#imageFileInput').attr('name', $(e.currentTarget).attr('id'));
        document.getElementById('imageFileInput').click();
    });
    $('#provider-search-body').keyup(function(e){
        var code = e.keyCode || e.which;
        if(code == 13){
            var find_location = $('#provider-search-body').val();
            map_Manage.searchlocation_provider(find_location, function(){
                map_Manage.set_searchMap('provider');
            });
        }
    });
    $('#provider-search-btn').click(function(){
        var find_location = $('#provider-search-body').val();
        map_Manage.searchlocation_provider(find_location, function(){
            map_Manage.set_searchMap('provider');
        });
    });

    $('#provider-search-map-button').click(function(){
        // location-search-map 버튼 바꾸기
        $('#finish-search').hide();
        $('#provider-finish-search').show();
        map_Manage.search_popup_map.clearMap();

        $('#location-search-popup').modal('show');
    });

    // 프로바이더가 자신의 위치를 다시 저장하는 부분
    $('#provider-finish-search').click(function(){
        // 찍어 놓은 마커 값을 가지고 온다.
        // map_Manage.providerModifyLocation
    });

});

//var locationobject = {
//    name : $('.main-provider').find('.start-input-third').find('.targetaddress').text(),
//    description : null,
//    //coordinates :
//    //    [map_Manage.providerSelectLocation.getLat(), map_Manage.providerSelectLocation.getLng()]
//    l_lat : map_Manage.providerSelectLocation.getLat(),
//    l_long : map_Manage.providerSelectLocation.getLng()
//};






var manage_providerinfo = {};
//var temparr = new Array();
manage_providerinfo.setProviderInput = function(){
    for(var i in CONST_MAINCATEGORY){
        $('.main-provider').find('.start-main-category').append(
                '<option>' + CONST_MAINCATEGORY[i] + '</option>'
        );
    }
    for(var i in CONST_SUBCATEGORY){
        $('.main-provider').find('.start-sub-category').append(
                '<option>' + CONST_SUBCATEGORY[i] + '</option>'
        );
    }
    for(var i in CONST_STORETYPE){
        $('#provider_storetype').append(
                '<option>' + CONST_STORETYPE[i] + '</option>'
        );
    }
};
manage_providerinfo.providerformat = {
    providerImage : "",
    contentType : "SEETREET",
    favoriteGenre : "",
    location : "",
    StoreTitle : "",
    StoreType : "",
    StoreAddress : "",
    description : ""
};
manage_providerinfo.providerImageArr = new Array();
manage_providerinfo.putinfo = function(){
};
manage_providerinfo.uploadimage = function(arrindex){
    // 사진 화면에 출력하고
    // 어레이에 저장
};

var providerImage = '';
var changeImage = function(ev){
    var targetImageid = $('#imageFileInput').attr('name');
    var file = $('#imageFileInput').prop('files')[0];
    if(file != null && file.name != null && file.name.length >0){
        $('#'+targetImageid).attr('src', URL.createObjectURL(file));
    }
    if(targetImageid == 'upload-comment-image'){
        readFile2Base64(file, function(e){
            modal_Factory.reply.replyImage = btoa(event.target.result);
        });
    }
    if(~targetImageid.indexOf('providerInputimage')){
        readFile2Base64(file, function(e){
            providerImage = btoa(event.target.result);
            manage_providerinfo.providerImageArr.push(providerImage);
        });
    }
    else{
        readFile2Base64(file, function(e){
            manage_artistinfo.join.tempImage = btoa(event.target.result);
            manage_artistinfo.join.imageArr.push(manage_artistinfo.join.tempImage);
        });
    }
};
var readFile2Base64 = function(file, callback){
    var reader = new FileReader();
    reader.onload = callback;
    reader.readAsBinaryString(file);
};
manage_providerinfo.loadpage = function(){
    $('.main-provider').show();
    $('.main-artist').hide();
//    프로바이더 정보 있는지 없는 지 검색
    getCheckProvider(function(data, status, res){
        if(status == 'success'){
            if(data.data == true){
                // 프로바이더 메인 페이지 부분
                manage_providerinfo.loadmainprovider();
                // 프로바이더 왼쪽바의 기본 정보 세팅
                manage_providerinfo.getUserinfo();
                Listtab.show(Listtab.TAB_PROVIDER);
            }
            else{
                // 프로바이더 정보 입력 페이지
                manage_providerinfo.loadjoinprovider('');

            }
        }
    });
};
manage_providerinfo.loadmainprovider = function(){
    $('.main-provider').find('.modifytab').show();
    $('.main-provider').find('.listtab').show();
    $('.main-provider').find('.start-area').hide();
};
manage_providerinfo.loadjoinprovider = function(btntag){
    $('.main-provider').find('.modifytab').hide();
    $('.main-provider').find('.listtab').hide();
    $('.main-provider').find('.start-area *').hide();
    $('.main-provider').find('.start-area').show();
    // 마지막 자리의 숫자를 판별
    if(btntag == ''){
        btntag = 'providerinput0';
    }
    var nth = btntag.substr(13,1);
    if(nth == 0){
        $('.main-provider').find('.start-area').find('.start-input-first').show();
        $('.main-provider').find('.start-area').find('.start-input-first *').show();
    }
    else if(nth == 1){
        // 첫 번째 페이지 정보 저장
        manage_providerinfo.providerformat.StoreTitle = $('#input-storename').val();
        manage_providerinfo.providerformat.providerImage = JSON.stringify(manage_providerinfo.providerImageArr);
        $('.main-provider').find('.start-area').find('.start-input-second').show();
        $('.main-provider').find('.start-area').find('.start-input-second *').show();
    }
    else if(nth == 2){
        // 두 번째 페이지 정보 저장
        var genre = {
            category : $('.main-provider').find('.start-main-category option:selected').val(),
            detailGenre : $('.main-provider').find('.start-sub-category option:selected').val()
        };
        manage_providerinfo.providerformat.favoriteGenre = JSON.stringify([genre]);
        $('.main-provider').find('.start-area').find('.start-input-third').show();
        $('.main-provider').find('.start-area').find('.start-input-third *').show();
    }
    else if(nth == 3){
        // 세 번째 페이지 정보 저장
        manage_providerinfo.providerformat.StoreAddress = $('.main-provider').find('.start-input-third').find('.targetaddress').text();
        var locationobject = {
            name : $('.main-provider').find('.start-input-third').find('.targetaddress').text(),
            description : null,
            //coordinates :
            //    [map_Manage.providerSelectLocation.getLat(), map_Manage.providerSelectLocation.getLng()]
            l_lat : map_Manage.providerSelectLocation.getLat(),
            l_long : map_Manage.providerSelectLocation.getLng()
        };
        manage_providerinfo.providerformat.location = JSON.stringify(locationobject);

        $('#input-provider-description').val('');
        $('.main-provider').find('.start-area').find('.start-input-fourth').show();
        $('.main-provider').find('.start-area').find('.start-input-fourth *').show();
    }
    else if(nth == 4){
        // 네 번째 페이지 정보 저장
        manage_providerinfo.providerformat.StoreType = $('#provider_storetype option:selected').val();
        manage_providerinfo.providerformat.description = $('#input-provider-description').val();
        postProviderCreate(manage_providerinfo.providerformat, function(data, status, res){
            if(status == 'success'){
                if(data.data == true){
                    alert('장소제공자 정보가 입력되었습니다.');
                    manage_providerinfo.loadpage('');
                }
                else{
                    alert('입력이  거절되었습니다.');
                }
            }
        });
    }
};

// modify tab에 대한 정보를 가져온다.

// 수정 할 데이터의 미리 저장해둔다.
manage_providerinfo.myProviderInfo = {};

manage_providerinfo.getUserinfo = function(){
    getUser(function(data, status, res){
        if(status == 'success'){
            var provider_id = data.data.user_provider._id;
            manage_providerinfo.getProviderInfo(provider_id);
        }
    });
};
// providerId = 5474c1c1398ca80d9acbac50
manage_providerinfo.getProviderInfo = function(providerId){
    var providerInfo;
    getProvider(providerId, function(data, status, res){
        if(status == 'success'){
            providerInfo = data.data;
            manage_providerinfo.setModifyTab(providerInfo);
        }
    });
};
manage_providerinfo.setModifyTab = function(providerInfo){
    manage_providerinfo.myProviderInfo = providerInfo;
    console.log(providerInfo);
//    var providerInfo =
//    {
//        providerImage : ["./images/seetreetimg/5mile1.jpg", "./images/seetreetimg/5mile2.jpg", "./images/seetreetimg/5mile3.jpg"],
//        contentType : "SEETREET",
//        favoriteGenre : {category : "음악", detailGenre : "인디공연"},
//        location : {"coordinates" : [127.00947, 37.2754700000006]},
//        StoreTitle : "ZooCoffee",
//        StoreAddress : "수원시 팔달구 인계동",
//        StoreType : "커피전문점",
//        description : "안녕하세요 주커피입니다."
//    };
    // 사진 세장
    for(var i in providerInfo.providerImage){
        $('.main-provider').find('.modifytab').find('.tabdetailinfo').find('.pic-youtube').find('.pic' + i).attr('src', providerInfo.providerImage[i]);
    }
    // 상호명
    $('.main-provider').find('.modifytab').find('.tabdetailinfo').find('.modify-name').find('input').val(providerInfo.StoreTitle);
    // 업종
    var storeTypeIndex = storeType2Index(providerInfo.StoreType);
    $('.main-provider').find('.modifytab').find('.tabdetailinfo').find('.modify-category').find('select option').eq(storeTypeIndex).prop('selected', true);
    // 지역
    $('.main-provider').find('.modifytab').find('.tabdetailinfo').find('.modify-location').find('input').val(providerInfo.StoreAddress);
    // 선호장르
    var mainCategoryIndex = maincategory2Index(providerInfo.favoriteGenre[0].category);
    var subCategoryIndex = subcategory2Index(providerInfo.favoriteGenre[0].detailGenre);
    $('.main-provider').find('.modifytab').find('.tabdetailinfo').find('.modify-favorite').find('select.category option').eq(mainCategoryIndex).prop('selected', true);
    $('.main-provider').find('.modifytab').find('.tabdetailinfo').find('.modify-favorite').find('select.detailGenre option').eq(subCategoryIndex).prop('selected', true);
    // 소개말
    $('.main-provider').find('.modifytab').find('.tabdetailinfo').find('.modify-description').find('textarea').val(providerInfo.description);
};


// 페이지에서 입력하지 않은 부분에 대한 체크를 한다. 확인 된 사항은 저장한다.
var caution_message = "";
manage_providerinfo.join_input_check = function(pagenum){
    var inputcheck = true;
    if(pagenum == 1){
        // 상점명이 입력되었는지
        if($('#input-storename').val() == ""){
            inputcheck = false;
            caution_message = "상점명을 입력해주세요";
        }
        // 사진이 세장 입력 되었는지
        if(manage_providerinfo.providerImageArr.length < 3){
            inputcheck = false;
            caution_message = "가게의 사진 정보를 채워주세요";
        }
    }
    else if(pagenum == 2){
        // 카테고리 정보가 널이 아닌지 확인
        if($('.main-provider').find('.start-main-category option:selected').index() == 0 ||
            $('.main-provider').find('.start-sub-category option:selected').index() == 0){
            inputcheck = false;
            caution_message = "선호 장르를 선택해주세요";
        }
    }
    else if(pagenum == 3){
        // 맵정보가 입력 되었는지 확인
        var selectmapaddress = $('.main-provider').find('.start-input-third').find('.targetaddress').text();
        if(selectmapaddress == ''){
            inputcheck = false;
            caution_message = "맵에 특정 지역을 선택해주세요.";
        };
    }
    else{
        // 업종정보와 description이 비어있는지 확인
        if($('#provider_storetype option:selected').index() == 0){
            inputcheck = false;
            caution_message = "업종 정보를 선택해주세요";
        }
        var description_value = $('#input-provider-description').val();
        if(description_value == ""){
            inputcheck = false;
            caution_message = "소개말을 입력해주세요.";
        }
    }
    return inputcheck;
};

var manage_providerbox = {};
manage_providerbox.createcontent = function(){
    $('.provider-create-area').find('.listbox').find('.beforecreate').hide();
    $('.provider-create-area').find('.listbox').find('.input-time').show();
};
manage_providerbox.inputcontenttime = function(){
    $('.provider-create-area').find('.listbox').find('.input-time').hide();
    $('.provider-create-area').find('.listbox').find('.beforecreate').show();
    box_Factory.provider.create_provider_group();
};
manage_providerbox.providerconfirmed = function(){
};




//var manage_providerinfo = {};