/**
 * Created by Limjiuk on 2014-10-27.
 */
var box_Factory = {};

box_Factory.content = {};
box_Factory.content.contentArray = new Array();
box_Factory.content.contentpagenum = 0;
box_Factory.content.finishdatanum = 0;
var testnum = 0;
//
box_Factory.content.initContentData = function(){
    // contentArray를 비워주고

    // page넘버를 1로 만들어준다.
};

// 더미데이터 넣기

// 데이터를 추가해서 갱신해준다.
box_Factory.content.appendArray = function(latitude, longitude, callback){
    for(var i=0;i<2;i++){
        box_Factory.content.contentpagenum += 1;
        var pagenumber = box_Factory.content.contentpagenum;
        getUserContent(latitude, longitude, pagenumber, function(data, status, res){
            if(status == 'success'){
                for(var index in data.data){
                    box_Factory.content.contentArray.push(data.data[index]);
                }
                callback();
            }
            else{
                console.log('데이터가 안받아짐');
            }
        });
    }
};
// createContentGroup는 data가 1, 8, 15 ... 단위로 만들어지면 된다.
box_Factory.content.createContentGroup = function(groupnumber){
    $('.seetreet-container.container').find('.content-list-area').append(
        '<div class = "contentlist" group-list = "' + groupnumber + '">'
        + '<div class = "largesize-area">'
        + '</div>'
        + '<div class = "smallsize-area">'
        + '</div>'
    );
};
// largesizearea는 1번째, 8번째, 15번째 ... 단위로 만들어지면 된다.
box_Factory.content.createContent = function(contentinfo, groupnumber, size){
    // contentId
    var content_id = contentinfo._id;
//    var content_id = testnum;
//    testnum++;
    // 공공 데이터 인지 Seetreet데이터인지 판별
    var content_type = '';
    if(contentinfo.contentType == 'PUBLIC'){
        content_type = 'public';
    }
    else{
        content_type = 'private';
    }
    // 공연명
    var content_name = contentinfo.contentTitle;
    // 아티스트명 또는 공공주최명
    var performed_name = '';
    if(content_type == 'public'){
        performed_name = contentinfo.provider.StoreTitle;
    }
    else{
        performed_name = contentinfo.artists[0].name;
//        performed_name = '임지욱';
    }
    // 공연 시간 만들기 시간 단위면 XX월 XX일 XX:XXPM ~ XX:XXPM
    // 일이 넘어가게 되면 XX월 XX일 ~ XX월 XX일
    // 시간 포맷 201411130800PM
    // 공연 시작 시간
    var start_time = contentinfo.contentStartTime;
    // 공연 종료 시간
    var end_time = contentinfo.contentEndTime;

    var content_time = box_Factory.convert_time(start_time, end_time);
//    var content_time = '11월 13일 08:00PM~10:00PM';

    // 공연 장소
    var content_address = contentinfo.provider.StoreAddress;
//    var content_address = '수원시 팔달구 인계동 1125번지';

    // 공연 설명
    var content_description;
    if(content_type == 'public'){
        content_description = contentinfo.provider.description;
    }
    else{
        content_description = contentinfo.artists[0].description;
    }


    // 공연 이미지

    var content_img = contentinfo.provider.providerImage[0];
    if(content_img == ""){
        content_img = "./images/seetreetimg/content_default.jpg";
    }

    if(size == 'large'){
        $('.contentlist[group-list = "' +  groupnumber + '"]').find('.largesize-area').append(
                '<div class = "content' + ' ' +  content_type + '" data-index = "' + content_id + '">'
                    + '<img class = "content-image" src = "' + content_img + '"/>'
                    + '<div class = "content-info">'
                        + '<div class = "place-title">' +  content_name + '</div>'
                        + '<div class = "artist-title">' + performed_name + '</div>'
                        + '<div class = "showtime-title">' + content_time + '</div>'
                        + '<div class = "location-title">' + content_address + '</div>'
                        + '<div class = "description-title">' + content_description + '</div>'
                    + '</div>'
                + '</div>'
        );
    }
    else{
        $('.contentlist[group-list = "' +  groupnumber + '"]').find('.smallsize-area').append(
                '<div class = "content' + ' ' + content_type + '" data-index = "' + content_id + '">'
                + '<img class = "content-image" src = "' + content_img + '"/>'
                + '<div class = "content-info">'
                + '<div class = "place-title">' +  content_name + '</div>'
                + '<div class = "artist-title">' + performed_name + '</div>'
                + '<div class = "showtime-title">' + content_time + '</div>'
                + '<div class = "location-title">' + content_address + '</div>'
                + '</div>'
                + '</div>'
        );
    }
};

box_Factory.content.get_a_content = function(dataindex){
    var contentarray = box_Factory.content.contentArray;
    for(var index in contentarray){
        if(contentarray[index]._id == dataindex){
            return contentarray[index];
        }
    }
};




box_Factory.provider = {};
box_Factory.provider.create_provider_group = function(){
    // 새로운 listboxgroup provider를 만든다.
    $('.listtab.provider').find('.provider-list-area').prepend(
            '<div class = "listboxgroup provider" data-group = "2">'
            + '<div class = "listbox createbox provider">'
            + '<div class = "finish-create">'
            + '<img class = "content-image" src = "./images/seetreetimg/5mile1.jpg"/>'
            + '<div class = "content-info">'
            + '<div class = "place-title">Zoo Coffee</div>'
            + '<div class = "showdate-title">2014/09/21</div>'
            + '<div class = "showtime-title">08:00PM ~ 10:00PM</div>'
            + '</div>'
            + '</div>'
            + '</div>'
            + '</div>'
    );
};
box_Factory.provider.create_provider_box = function(datagroup){
    $('.listgroup.provider[data-group="2"]').append(
            '<div class = "listbox artist">'
            + '<img class = "image" src = "./images/seetreetimg/5mile1.jpg"/>'
            + '<div class = "info">'
            +   '<div class = "title">Maroon5</div>'
            +   '<div class = "category">Music-Pop</div>'
            + '<button type = "button" class = "btn btn-default">confirmed</button>'
            + '</div>'
            + '</div>'
    )
};

box_Factory.artist = {};
box_Factory.artist.create_busking_box = function(){
    $('.listboxgroup.artist.busking').append(
        '<div class = "listbox buskingbox artist ">'
            + '<img class = "image" src = "./images/seetreetimg/5mile1.jpg"/>'
            + '<div class = "info">'
                + '<div class = "place-title">Zoo Coffee</div>'
                + '<div class = "showdate-title">2014/09/21</div>'
                + '<div class = "showtime-title"> 08:00PM ~ 10:00PM</div>'
                + '<button type = "button" class = "btn btn-default">confirmed</button>'
            + '</div>'
        + '</div>'
    );
};

// 14 11 13 08 00 PM
// 01 23 45 67 89 1011
box_Factory.convert_time = function(starttime, endtime){
    var startday = starttime.substr(6,2);
    var endday = endtime.substr(6,2);

    var showday = '';

    if(startday != endday){
        showday = starttime.substr(4,2) + "월 " + starttime.substr(6,2) + "일 " + '~ '
            +  endtime.substr(4,2) + '월 ' + endtime.substr(6,2) + "일";
    }
    else{
        showday = starttime.substr(4,2) + "월 " + starttime.substr(4,2) + "일 "
            + starttime.substr(8,2) + ":" + starttime.substr(10,2) + starttime.substr(12,2) + " ~ "
        + endtime.substr(8,2) + ":" + endtime.substr(10,2) + endtime.substr(12,2);
    }
    return showday;
};

//var dummy_content_data = [
//    {
//        "contentId" : "abcdefg1",
//        "artists":[
//            {
//                "artistUrl":"http://img.youtube.com/vi/f5ShDNOqq1E/default.jpg",
//                "description":"artist_hello_23",
//                "artistImages":[
//                    "http://file.thisisgame.com/upload/tboard/user/2014/03/22/20140322190349_2413.jpg",
//                    "http://cfile228.uf.daum.net/image/21191C48527CF4B20E1B4C"
//                ]
//            }
//        ],
//        "contentEndTime":141042234,
//        "isConfirmed_artistId":"544bab7ffd6733c17d5db672",
//        "contentTitle":"5Loaves 에서 공공_76",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"축제"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://designsori.com/files/attach/images/215078/273/402/ddp.jpg",
//                "http://designsori.com/files/attach/images/215078/273/402/ddp2.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    127.01084999999999,
//                    37.27685
//                ]
//            },
//            "description":"hello Every one_3",
//            "StoreType":"커피전문점",
//            "contentType":"공공",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"페스티발"
//                }
//            ],
//            "StoreTitle":"5Loaves"
//        },
//        "contentType":"공공",
//        "ConfirmedTime":"144101640",
//        "contentStartTime":1433153
//    },
//    {
//        "contentId" : "abcdefg2",
//        "artists":[
//            {
//                "artistUrl":"http://img.youtube.com/vi/f5ShDNOqq1E/default.jpg",
//                "description":"artist_hello_28",
//                "artistImages":[
//                    "http://pds.joins.com/news/component/htmlphoto_mmdata/201309/17/htm_20130917164315c010c011.jpg",
//                    "http://cfile219.uf.daum.net/image/1375C93E503B74E228FD52"
//                ]
//            }
//        ],
//        "contentEndTime":1411291159,
//        "isConfirmed_artistId":"544bab7ffd6733c17d5db677",
//        "contentTitle":"쌈지길 에서 SEETREET_6",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"축제"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://www.naeilshot.co.kr/filedata/wzine/3223/editor/%EC%BB%AC%EC%8A%A41.jpg",
//                "http://designsori.com/files/attach/images/215078/273/402/ddp.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    127.01048,
//                    37.27648
//                ]
//            },
//            "description":"hello Every one_8",
//            "StoreType":"주점",
//            "contentType":"SEETREET",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"공연"
//                }
//            ],
//            "StoreTitle":"쌈지길"
//        },
//        "contentType":"SEETREET",
//        "ConfirmedTime":"145555",
//        "contentStartTime":14428428
//    },
//    {
//        "contentId" : "abcdefg3",
//        "artists":[
//            {
//                "artistUrl":"http://img.youtube.com/vi/f5ShDNOqq1E/default.jpg",
//                "description":"artist_hello_8",
//                "artistImages":[
//                    "http://cfile22.uf.tistory.com/image/27101D4B5274E949269D60",
//                    "http://img.tenasia.hankyung.com/webwp_kr/wp-content/uploads/2013/10/2013100814305070956.jpg"
//                ]
//            }
//        ],
//        "contentEndTime":14391539,
//        "isConfirmed_artistId":"544bab7ffd6733c17d5db663",
//        "contentTitle":"쌈지길 에서 SEETREET_89",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"페스티발"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://www.naeilshot.co.kr/filedata/wzine/3223/editor/%EC%BB%AC%EC%8A%A41.jpg",
//                "http://designsori.com/files/attach/images/215078/273/402/ddp.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    127.01048,
//                    37.27648
//                ]
//            },
//            "description":"hello Every one_8",
//            "StoreType":"주점",
//            "contentType":"SEETREET",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"공연"
//                }
//            ],
//            "StoreTitle":"쌈지길"
//        },
//        "contentType":"SEETREET",
//        "ConfirmedTime":"14915945",
//        "contentStartTime":148888
//    },
//    {
//        "contentId" : "abcdefg4",
//        "artists":[
//            {
//                "artistUrl":"http://img.youtube.com/vi/f5ShDNOqq1E/default.jpg",
//                "description":"artist_hello_28",
//                "artistImages":[
//                    "http://pds.joins.com/news/component/htmlphoto_mmdata/201309/17/htm_20130917164315c010c011.jpg",
//                    "http://cfile219.uf.daum.net/image/1375C93E503B74E228FD52"
//                ]
//            }
//        ],
//        "contentEndTime":1439339,
//        "isConfirmed_artistId":"544bab7ffd6733c17d5db677",
//        "contentTitle":"쌈지길 에서 SEETREET_12",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"공연"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://www.naeilshot.co.kr/filedata/wzine/3223/editor/%EC%BB%AC%EC%8A%A41.jpg",
//                "http://designsori.com/files/attach/images/215078/273/402/ddp.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    127.01048,
//                    37.27648
//                ]
//            },
//            "description":"hello Every one_8",
//            "StoreType":"주점",
//            "contentType":"SEETREET",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"공연"
//                }
//            ],
//            "StoreTitle":"쌈지길"
//        },
//        "contentType":"SEETREET",
//        "ConfirmedTime":"149152145",
//        "contentStartTime":1488208
//    },
//    {
//        "contentId" : "abcdefg5",
//        "artists":[
//            {
//                "artistUrl":"http://img.youtube.com/vi/f5ShDNOqq1E/default.jpg",
//                "description":"artist_hello_28",
//                "artistImages":[
//                    "http://pds.joins.com/news/component/htmlphoto_mmdata/201309/17/htm_20130917164315c010c011.jpg",
//                    "http://cfile219.uf.daum.net/image/1375C93E503B74E228FD52"
//                ]
//            }
//        ],
//        "contentEndTime":1439339,
//        "isConfirmed_artistId":"544bab7ffd6733c17d5db677",
//        "contentTitle":"쌈지길 에서 SEETREET_13",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"미술 전시"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://www.naeilshot.co.kr/filedata/wzine/3223/editor/%EC%BB%AC%EC%8A%A41.jpg",
//                "http://designsori.com/files/attach/images/215078/273/402/ddp.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    127.01048,
//                    37.27648
//                ]
//            },
//            "description":"hello Every one_8",
//            "StoreType":"주점",
//            "contentType":"SEETREET",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"공연"
//                }
//            ],
//            "StoreTitle":"쌈지길"
//        },
//        "contentType":"SEETREET",
//        "ConfirmedTime":"149152145",
//        "contentStartTime":1488208
//    },
//    {
//        "contentId" : "abcdefg6",
//        "artists":[
//            {
//                "artistUrl":"http://img.youtube.com/vi/f5ShDNOqq1E/default.jpg",
//                "description":"artist_hello_25",
//                "artistImages":[
//                    "http://cfile228.uf.daum.net/image/21191C48527CF4B20E1B4C",
//                    "http://cfile22.uf.tistory.com/image/27101D4B5274E949269D60"
//                ]
//            }
//        ],
//        "contentEndTime":1406036,
//        "isConfirmed_artistId":"544bab7ffd6733c17d5db674",
//        "contentTitle":"ZooCoffee 에서 SEETREET_61",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"축제"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://designsori.com/files/attach/images/215078/273/402/ddp2.jpg",
//                "http://designsori.com/files/attach/images/215078/302/402/pm.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    127.01026,
//                    37.27626
//                ]
//            },
//            "description":"hello Every one_5",
//            "StoreType":"디자인 샵",
//            "contentType":"SEETREET",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"공원"
//                }
//            ],
//            "StoreTitle":"ZooCoffee"
//        },
//        "contentType":"SEETREET",
//        "ConfirmedTime":"146121842",
//        "contentStartTime":1455175
//    },
//    {
//        "contentId" : "abcdefg7",
//        "artists":[
//            {
//                "artistUrl":"http://img.youtube.com/vi/f5ShDNOqq1E/default.jpg",
//                "description":"artist_hello_5",
//                "artistImages":[
//                    "http://cfile228.uf.daum.net/image/21191C48527CF4B20E1B4C",
//                    "http://cfile22.uf.tistory.com/image/27101D4B5274E949269D60"
//                ]
//            }
//        ],
//        "contentEndTime":14416416,
//        "isConfirmed_artistId":"544bab7ffd6733c17d5db660",
//        "contentTitle":"ZooCoffee 에서 SEETREET_26",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"축제"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://designsori.com/files/attach/images/215078/273/402/ddp2.jpg",
//                "http://designsori.com/files/attach/images/215078/302/402/pm.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    127.01026,
//                    37.27626
//                ]
//            },
//            "description":"hello Every one_5",
//            "StoreType":"디자인 샵",
//            "contentType":"SEETREET",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"공원"
//                }
//            ],
//            "StoreTitle":"ZooCoffee"
//        },
//        "contentType":"SEETREET",
//        "ConfirmedTime":"1410222222",
//        "contentStartTime":149152145
//    },
//    {
//        "contentId" : "abcdefg8",
//        "artists":[
//            {
//                "artistUrl":"http://img.youtube.com/vi/f5ShDNOqq1E/default.jpg",
//                "description":"artist_hello_5",
//                "artistImages":[
//                    "http://cfile228.uf.daum.net/image/21191C48527CF4B20E1B4C",
//                    "http://cfile22.uf.tistory.com/image/27101D4B5274E949269D60"
//                ]
//            }
//        ],
//        "contentEndTime":14061236,
//        "isConfirmed_artistId":"544bab7ffd6733c17d5db660",
//        "contentTitle":"ZooCoffee 에서 SEETREET_44",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"페스티발"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://designsori.com/files/attach/images/215078/273/402/ddp2.jpg",
//                "http://designsori.com/files/attach/images/215078/302/402/pm.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    127.01026,
//                    37.27626
//                ]
//            },
//            "description":"hello Every one_5",
//            "StoreType":"디자인 샵",
//            "contentType":"SEETREET",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"공원"
//                }
//            ],
//            "StoreTitle":"ZooCoffee"
//        },
//        "contentType":"SEETREET",
//        "ConfirmedTime":"14612642",
//        "contentStartTime":145555
//    },
//    {
//        "contentId" : "abcdefg9",
//        "artists":[
//            {
//                "artistUrl":"http://img.youtube.com/vi/f5ShDNOqq1E/default.jpg",
//                "description":"artist_hello_25",
//                "artistImages":[
//                    "http://cfile228.uf.daum.net/image/21191C48527CF4B20E1B4C",
//                    "http://cfile22.uf.tistory.com/image/27101D4B5274E949269D60"
//                ]
//            }
//        ],
//        "contentEndTime":144161616,
//        "isConfirmed_artistId":"544bab7ffd6733c17d5db674",
//        "contentTitle":"ZooCoffee 에서 SEETREET_4",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"페스티발"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://designsori.com/files/attach/images/215078/273/402/ddp2.jpg",
//                "http://designsori.com/files/attach/images/215078/302/402/pm.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    127.01026,
//                    37.27626
//                ]
//            },
//            "description":"hello Every one_5",
//            "StoreType":"디자인 샵",
//            "contentType":"SEETREET",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"공원"
//                }
//            ],
//            "StoreTitle":"ZooCoffee"
//        },
//        "contentType":"SEETREET",
//        "ConfirmedTime":"1410221022",
//        "contentStartTime":14915945
//    },
//    {
//        "contentId" : "abcdefg10",
//        "artists":[
//            {
//                "artistUrl":"http://img.youtube.com/vi/f5ShDNOqq1E/default.jpg",
//                "description":"artist_hello_2",
//                "artistImages":[
//                    "http://cfile30.uf.tistory.com/original/26649B3B53AE5B6106D890",
//                    "http://file.thisisgame.com/upload/tboard/user/2014/03/22/20140322190349_2413.jpg"
//                ]
//            }
//        ],
//        "contentEndTime":1493933,
//        "isConfirmed_artistId":"544bab7ffd6733c17d5db65d",
//        "contentTitle":"제비다방 에서 SEETREET_82",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"공연"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://designsori.com/files/attach/images/215078/302/402/jrpro.jpg",
//                "http://pds.joinsmsn.com/news/component/htmlphoto_mmdata/201208/30/htm_20120830153446n120n122.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    127.00992,
//                    37.27592
//                ]
//            },
//            "description":"hello Every one_2",
//            "StoreType":"주점",
//            "contentType":"SEETREET",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"축제"
//                }
//            ],
//            "StoreTitle":"제비다방"
//        },
//        "contentType":"SEETREET",
//        "ConfirmedTime":"1439339",
//        "contentStartTime":142222
//    },
//    {
//        "contentId" : "abcdefg11",
//        "artists":[
//            {
//                "artistUrl":"http://img.youtube.com/vi/f5ShDNOqq1E/default.jpg",
//                "description":"artist_hello_2",
//                "artistImages":[
//                    "http://cfile30.uf.tistory.com/original/26649B3B53AE5B6106D890",
//                    "http://file.thisisgame.com/upload/tboard/user/2014/03/22/20140322190349_2413.jpg"
//                ]
//            }
//        ],
//        "contentEndTime":1493933,
//        "isConfirmed_artistId":"544bab7ffd6733c17d5db65d",
//        "contentTitle":"제비다방 에서 SEETREET_82",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"공연"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://designsori.com/files/attach/images/215078/302/402/jrpro.jpg",
//                "http://pds.joinsmsn.com/news/component/htmlphoto_mmdata/201208/30/htm_20120830153446n120n122.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    127.00992,
//                    37.27592
//                ]
//            },
//            "description":"hello Every one_2",
//            "StoreType":"주점",
//            "contentType":"SEETREET",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"축제"
//                }
//            ],
//            "StoreTitle":"제비다방"
//        },
//        "contentType":"SEETREET",
//        "ConfirmedTime":"1439339",
//        "contentStartTime":142222
//    },
//    {
//        "contentId" : "abcdefg12",
//        "artists":[
//            {
//                "artistUrl":"http://img.youtube.com/vi/f5ShDNOqq1E/default.jpg",
//                "description":"artist_hello_2",
//                "artistImages":[
//                    "http://cfile30.uf.tistory.com/original/26649B3B53AE5B6106D890",
//                    "http://file.thisisgame.com/upload/tboard/user/2014/03/22/20140322190349_2413.jpg"
//                ]
//            }
//        ],
//        "contentEndTime":1493933,
//        "isConfirmed_artistId":"544bab7ffd6733c17d5db65d",
//        "contentTitle":"제비다방 에서 SEETREET_82",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"공연"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://designsori.com/files/attach/images/215078/302/402/jrpro.jpg",
//                "http://pds.joinsmsn.com/news/component/htmlphoto_mmdata/201208/30/htm_20120830153446n120n122.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    127.00992,
//                    37.27592
//                ]
//            },
//            "description":"hello Every one_2",
//            "StoreType":"주점",
//            "contentType":"SEETREET",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"축제"
//                }
//            ],
//            "StoreTitle":"제비다방"
//        },
//        "contentType":"SEETREET",
//        "ConfirmedTime":"1439339",
//        "contentStartTime":142222
//    },
//    {
//        "contentId" : "abcdefg13",
//        "artists":[
//            {
//                "artistUrl":"http://img.youtube.com/vi/f5ShDNOqq1E/default.jpg",
//                "description":"artist_hello_2",
//                "artistImages":[
//                    "http://cfile30.uf.tistory.com/original/26649B3B53AE5B6106D890",
//                    "http://file.thisisgame.com/upload/tboard/user/2014/03/22/20140322190349_2413.jpg"
//                ]
//            }
//        ],
//        "contentEndTime":1493933,
//        "isConfirmed_artistId":"544bab7ffd6733c17d5db65d",
//        "contentTitle":"제비다방 에서 SEETREET_82",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"공연"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://designsori.com/files/attach/images/215078/302/402/jrpro.jpg",
//                "http://pds.joinsmsn.com/news/component/htmlphoto_mmdata/201208/30/htm_20120830153446n120n122.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    127.00992,
//                    37.27592
//                ]
//            },
//            "description":"hello Every one_2",
//            "StoreType":"주점",
//            "contentType":"SEETREET",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"축제"
//                }
//            ],
//            "StoreTitle":"제비다방"
//        },
//        "contentType":"SEETREET",
//        "ConfirmedTime":"1439339",
//        "contentStartTime":142222
//    },
//    {
//        "contentId" : "abcdefg14",
//        "artists":[
//            {
//                "artistUrl":"http://img.youtube.com/vi/f5ShDNOqq1E/default.jpg",
//                "description":"artist_hello_2",
//                "artistImages":[
//                    "http://cfile30.uf.tistory.com/original/26649B3B53AE5B6106D890",
//                    "http://file.thisisgame.com/upload/tboard/user/2014/03/22/20140322190349_2413.jpg"
//                ]
//            }
//        ],
//        "contentEndTime":1493933,
//        "isConfirmed_artistId":"544bab7ffd6733c17d5db65d",
//        "contentTitle":"제비다방 에서 SEETREET_82",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"공연"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://designsori.com/files/attach/images/215078/302/402/jrpro.jpg",
//                "http://pds.joinsmsn.com/news/component/htmlphoto_mmdata/201208/30/htm_20120830153446n120n122.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    127.00992,
//                    37.27592
//                ]
//            },
//            "description":"hello Every one_2",
//            "StoreType":"주점",
//            "contentType":"SEETREET",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"축제"
//                }
//            ],
//            "StoreTitle":"제비다방"
//        },
//        "contentType":"SEETREET",
//        "ConfirmedTime":"1439339",
//        "contentStartTime":142222
//    },
//    {
//        "contentId" : "abcdefg15",
//        "artists":[
//            {
//                "artistUrl":"http://img.youtube.com/vi/f5ShDNOqq1E/default.jpg",
//                "description":"artist_hello_2",
//                "artistImages":[
//                    "http://cfile30.uf.tistory.com/original/26649B3B53AE5B6106D890",
//                    "http://file.thisisgame.com/upload/tboard/user/2014/03/22/20140322190349_2413.jpg"
//                ]
//            }
//        ],
//        "contentEndTime":1493933,
//        "isConfirmed_artistId":"544bab7ffd6733c17d5db65d",
//        "contentTitle":"제비다방 에서 SEETREET_82",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"공연"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://designsori.com/files/attach/images/215078/302/402/jrpro.jpg",
//                "http://pds.joinsmsn.com/news/component/htmlphoto_mmdata/201208/30/htm_20120830153446n120n122.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    127.00992,
//                    37.27592
//                ]
//            },
//            "description":"hello Every one_2",
//            "StoreType":"주점",
//            "contentType":"SEETREET",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"축제"
//                }
//            ],
//            "StoreTitle":"제비다방"
//        },
//        "contentType":"SEETREET",
//        "ConfirmedTime":"1439339",
//        "contentStartTime":142222
//    },
//    {
//        "contentId" : "abcdefg16",
//        "contentEndTime":20141012,
//        "isConfirmed_artistId":"공공",
//        "contentTitle":"강화도 새우젓축제 2014",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"일반축제"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://tong.visitkorea.or.kr/cms/resource/69/689469_image2_1.jpg",
//                "http://tong.visitkorea.or.kr/cms/resource/69/689469_image3_1.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    126.38082188786092,
//                    37.70084745179421
//                ]
//            },
//            "description":"축제의 유래 및 특성역사의 고장, 넉넉한 강화도의 \n인심과 풍요속에 열리는 <강화 새우젓 축제>는 강화연안에서 잡은 싱싱한 젓새우와 \n국내염전의 소금으로 담근 강화도새우젓의 우수성을 널리 알려 지역의 경제활성화 \n및 관광이미지 제고에 기여하고 있다. 지역특성강화연안에서의 새우잡이는 \n볼음도, 주문도, 내리,석모도 근처에서 주로 이루어지고 수산물 집산지였던 외포리에는 \n수협 새우젓 위판장 및  지하 새우젓 저장시설이 있어 젓갈류를 저장해 둔다. \n예부터 외포리 앞 진두바다에서는 특히 백하(白鰕)가 많이 잡혔으며 최근 화도면 \n내리의 선수선착장과 석모도의 어류정선착장, 내가면 창후리 선착장에 새우잡이배가 \n많으며 강화연안 일대에 분포돼 있는 새우는 12월까지 잡힌다. <br /> <br />새우젓 자랑임진강과 \n예성강, 한강이 합류하는 강화도 앞바다에서 생산되는 강화도 새우젓은 내륙에서 \n유입되는 풍부한 영양염류를 섭취하여 감칠맛과 높은 영양가를 가지고 있으며, 옛날에는 \n한강 마포나루 등으로 공급되어 임금님께 진상할 정도로 그 품질이 유명하다. 또한 \n9월~11월경 외포리 새우젓경매장을 통해 전국 각지로 유통되고 있으며, 새우젓축제 \n전후, 김장철을 맞아 많은 관광객들이 새우젓을 구입하기 위해 강화군 14개 항포구를 \n방문하고 있다. <br>",
//            "StoreType":"일반축제",
//            "contentType":"공공",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"일반축제"
//                }
//            ],
//            "StoreTitle":"강화도 새우젓축제 2014"
//        },
//        "contentType":"공공",
//        "contentStartTime":20141010
//    },
//    {
//        "contentId" : "abcdefg17",
//        "contentEndTime":20141005,
//        "isConfirmed_artistId":"공공",
//        "contentTitle":"강화 개천대축제 2014",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"일반축제"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://tong.visitkorea.or.kr/cms/resource/50/1659650_image2_1.jpg",
//                "http://tong.visitkorea.or.kr/cms/resource/50/1659650_image3_1.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    126.42326432004498,
//                    37.63321441380695
//                ]
//            },
//            "description":"단군왕검께서 최초의 민족국가 건국을 기념하는 \n국경일인 개천절을 맞이해서 강화군이 지닌 마니산 참성단을 소재로 민족 자존심과 \n애국심을 고취하는 행사로써 천제봉행, 태극물결 마니산 동반대회 등 다양한 행사를 \n통하여 우리 민족의 화합과 조화를 기원하는 전국 유일의 축제이다. 단군께서 친히 \n하늘에 제를 올린 참성단이 있는 마니산은 우리민족의 정기가 솟구치는 전국 제일의 \n생기발원처로 그 명성이 자자하며 다양한 기 체험 프로그램과 샘솟는 기운과 새로운 \n활력을 되찾을 수 있는 친환경 웰빙축제이다. <br>",
//            "StoreType":"일반축제",
//            "contentType":"공공",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"일반축제"
//                }
//            ],
//            "StoreTitle":"강화 개천대축제 2014"
//        },
//        "contentType":"공공",
//        "contentStartTime":20141003
//    },
//    {
//        "contentId" : "abcdefg18",
//        "contentEndTime":20141005,
//        "isConfirmed_artistId":"공공",
//        "contentTitle":"삼랑성 역사문화축제 2014",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"일반축제"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://tong.visitkorea.or.kr/cms/resource/66/183066_image2_1.jpg",
//                "http://tong.visitkorea.or.kr/cms/resource/66/183066_image3_1.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    126.4866557754119,
//                    37.6318906823223
//                ]
//            },
//            "description":"인천 \n강화도 전등사에서 9월 19일부터 10월 5일까지 <삼랑성 역사문화축제>가 열린다. \n \n올해로 14회를 맞이하는 이번 축제는 삼랑성 역사문화축제 조직위원회가 \n주관하여 가을음악회, 마당극 등 볼거리와 함께 다례재, 영산대재 등 의식으로 \n진행되며 시민들에게 볼거리, \n즐길거리, 먹을거리를 제공한다. <br>",
//            "StoreType":"일반축제",
//            "contentType":"공공",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"일반축제"
//                }
//            ],
//            "StoreTitle":"삼랑성 역사문화축제 2014"
//        },
//        "contentType":"공공",
//        "contentStartTime":20140919
//    },
//    {
//        "contentId" : "abcdefg20",
//        "contentEndTime":20141012,
//        "isConfirmed_artistId":"공공",
//        "contentTitle":"평화누리길 걷기행사 with 자전거투어 (김포)",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"기타행사"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://tong.visitkorea.or.kr/cms/resource/55/1942255_image2_1.jpg",
//                "http://tong.visitkorea.or.kr/cms/resource/55/1942255_image2_1.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    126.54518908500513,
//                    37.63986300606115
//                ]
//            },
//            "description":"해안선을 따라 길게 늘어선 철책선 사이로, 발걸음마다 시시각각 변하는 풍광이 아름다운 평화누리길 1코스 염하강 철책길. 함상공원부터 덕포진, 해안가 철책선에 이르기까지 분단의 역사가 흐르는 이 길을 따라 떠나보자. <br /> <br />\n\u203b '평화누리 자전거길 개장기념', 걷기 다음날 10.12(일), DMZ자전거투어 개최 <br />\n - 걷기행사 : 함상공원 ~ 덕포진 ~ 부래도 ~ 함상공원(총 7km) <br />\n - 자전거투어 : 조각공원 ~ 검문소A ~ 매화미르마을 ~ 검문소B ~ 조각공원(총 23.5km) <br>",
//            "StoreType":"기타행사",
//            "contentType":"공공",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"기타행사"
//                }
//            ],
//            "StoreTitle":"평화누리길 걷기행사 with 자전거투어 (김포)"
//        },
//        "contentType":"공공",
//        "contentStartTime":20141011
//    },
//    {
//        "contentId" : "abcdefg22",
//        "contentEndTime":20141123,
//        "isConfirmed_artistId":"공공",
//        "contentTitle":"파주장단콩축제 2014",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"일반축제"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://tong.visitkorea.or.kr/cms/resource/23/508123_image2_1.jpg",
//                "http://tong.visitkorea.or.kr/cms/resource/23/508123_image3_1.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    126.74053082476588,
//                    37.88952347116511
//                ]
//            },
//            "description":"가을이 깊게 무르익어 가면 파주 임진각에서는 대한민국 대표 웰빙축제로 자리매김한 <파주장단콩축제>가 열린다. 파주장단콩축제는 파주시가 \u2018파주장단콩\u2019의 우수성을 널리 알리고 이를 통한 지역 농특산물의 소비 촉진 및 지역경제 활성화를 위해 지나 1997년부터 매년 11월 콩 수확시기에 맞추어 개최하고 있다.\n <br /> <br />\n\u2018웰빙 명품! 파주장단콩 세상!\u2019 이라는 주제로 DMZ 청정환경에서 자란 장단콩을 가지고 \n파주 경제를 살찌우는 실속있는 축제로 평가받고자 마련한 <파주장단콩축제>는 파주시 임진각 광장에서 펼쳐진다. \n파주장단콩은 예로부터 맛과 영양이 뛰어나 임금님께 진상되었고, 20세기 초에는 우리나라 콩 장려품종으로 뽑혀 전국에 보급되기도 했다. \n<파주장단콩축제>는 \u2018관람객과 함께 할 수 있는 축제\u2019로 파주장단콩과 관련된 다양한 체험 및 직접 참여할 수 있는 여러 가지 프로그램을 운영하고 있다. 농특산물 판매마당에서 구입한 파주의 농특산물의 무료배달 및 관람객의 편의를 도모하기 위한 편의시설을 설치하는 등 다른 축제와는 차별성을 두고 고객중심의 서비스를 제공하고 있다.  남녀노소 누구나 온 가족이 함께 참여할 수 있는 \u2018가족체험형친환경테마축제\u2019 로 재미있는 축제, 다시 찾고 싶은 축제가 될 파주장단콩축제와 함께 하면 영원히 기억에 남을 만한 추억이 될 것이다. \n 최근에는 웰빙 열풍에 힘입어 건강식으로 콩에 대한 관심이 날로 높아지고 있고, 또한 높아지고 있는 콩의 소비 추세를 살려 파주장단콩축제를 대한민국의 대표적인 지역축제로 자리매김 할 수 있도록 가꿔가고 있다. <br>",
//            "StoreType":"일반축제",
//            "contentType":"공공",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"일반축제"
//                }
//            ],
//            "StoreTitle":"파주장단콩축제 2014"
//        },
//        "contentType":"공공",
//        "contentStartTime":20141121
//    },
//    {
//        "contentId" : "abcdefg23",
//        "contentEndTime":20141003,
//        "isConfirmed_artistId":"공공",
//        "contentTitle":"원코리아 온누리 페스티벌 2014",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"일반축제"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://tong.visitkorea.or.kr/cms/resource/58/1950058_image2_1.jpg",
//                "http://tong.visitkorea.or.kr/cms/resource/58/1950058_image2_1.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    126.743630456676,
//                    37.892467627436055
//                ]
//            },
//            "description":"대한민국 외교통상부 소속의 사단법인 원코리아는, 전세계 온누리에 흩어져 사는 720만 재외동포들과 함께 코리아의 화합과 코리안의 하나됨을 위하여, 또한, 문화를 통해 자긍심을 심어주고 코리안의 세계시민 의식 창출과 평화 정신을 기리기 위해서 미국, 일본, 중국 등 전 세계에서 원코리아 온누리 페스티벌을 개최하고 있다.\n'2014 원코리아 온누리 페스티벌'은 경기도 파주시에서 진행될 예정이며, 한국을 비롯하여 세계 각 국에서 참가하는 한민족의 다채로운 문화예술 공연을 비롯해 대학응원단, 락페스티벌 경연대회 등이 펼쳐질 예정이다. 또한 백일장, 사진촬영대회, 미술대회를 통해 해외 동포와 국내의 시민들이 함께 참여하고 공유하는 뜻깊은 축제의 장이다. <br>",
//            "StoreType":"일반축제",
//            "contentType":"공공",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"일반축제"
//                }
//            ],
//            "StoreTitle":"원코리아 온누리 페스티벌 2014"
//        },
//        "contentType":"공공",
//        "contentStartTime":20141003
//    },
//    {
//        "contentId" : "abcdefg25",
//        "contentEndTime":20141005,
//        "isConfirmed_artistId":"공공",
//        "contentTitle":"K-POP EXPO in ASIA 2014",
//        "contentGenre":{
//            "category":"",
//            "detailGenre":"기타행사"
//        },
//        "isFinished":false,
//        "provider":{
//            "providerImage":[
//                "http://tong.visitkorea.or.kr/cms/resource/31/1947831_image2_1.jpg",
//                "http://tong.visitkorea.or.kr/cms/resource/31/1947831_image2_1.jpg"
//            ],
//            "location":{
//                "coordinates":[
//                    126.60869420717634,
//                    37.546744411121466
//                ]
//            },
//            "description":"전 세계인이 기억하는 한국음악의 축제이며 한류의 본산인 K-POP을 45개국 13,000명이 참가하는 아시안인들의 최고 축제인 <K-POP EXPO in ASIA>가 제17회 인천아시아경기대회와 함께 주 경기장 근처 25만평의 부지에서 참가국 관련인은 물론 전 세계 관광객 및 내국인을 대상으로 9월 \n19일부터 10월 5일까지 17일 동안 개최 한다. 아울러 90명의 후보자들이 참가하는 미스유니버시아드대회 국내 최종 선발전도 이 기간중에 K-POP 무대에서 최종 선발전을 개최 한다. <br>",
//            "StoreType":"기타행사",
//            "contentType":"공공",
//            "favoriteGenre":[
//                {
//                    "category":"",
//                    "detailGenre":"기타행사"
//                }
//            ],
//            "StoreTitle":"K-POP EXPO in ASIA 2014"
//        },
//        "contentType":"공공",
//        "contentStartTime":20140919
//    }
//];






