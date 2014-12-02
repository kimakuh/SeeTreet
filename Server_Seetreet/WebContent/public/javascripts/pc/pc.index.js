/**
 * Created by Limjiuk on 2014-10-25.
 */
$(document).ready(function(){
    // 여기부터 carousel 관리 입니다.
    $('.carousel').carousel({
        interval : 10000
    });
    $('.carousel').find('.left').click(function(e){
        // 부모의 class를 찾아서
        var targetclass = $(e.currentTarget).parent().parent().attr('class');
        // 그 사진을 처리해준다.
        $('.'+targetclass).find('.carousel').carousel('prev');
    });
    $('.carousel').find('.right').click(function(e){
        var targetclass = $(e.currentTarget).parent().parent().attr('class');
        $('.'+targetclass).find('.carousel').carousel('next');
    });
    $('.modal').on('show.bs.modal', function(){
        $('.carousel').carousel(0);
    });
    // 여기까지

    $('.topbar-user').click(function(e){
        // $(e.currentTarget).parent().parent().attr('data-index');
        tabmanager.topbarmanager($(e.currentTarget).attr('class'));
    });
    $('.topbar-provider').click(function(e){
        tabmanager.topbarmanager($(e.currentTarget).attr('class'));
    });
    $('.topbar-artist').click(function(e){
        tabmanager.topbarmanager($(e.currentTarget).attr('class'));
    });

    $('.seetreet-topbar-content').find('.user-info').find('.user-name').text(getCookie(COOKIE_USER_NAME));

    $('.seetreet-topbar-content').find('.user-info').find('.user-name').click(function(){
        console.log('사용자정보클릭');
    });
    // 로그아웃 처리
    $('.seetreet-topbar-content').find('.user-info').find('.user-logout').click(function(){
        postUserLogout(function(data, status, res){
            if(status == 'success'){
                removeCookie(COOKIE_USER_ID);
                removeCookie(COOKIE_USER_TOKEN);
                removeCookie(COOKIE_USER_NAME);
                window.location.href = "./hello.see";
            }
            else{
                alert('ajax error');
            }
        });
    });


    // 여기부터 호버링 효과입니다. // 마우스 인 , 마우스 오버
    $('.seetreet-topbar-content').find('.user-logout').hover(function(){
        $('.seetreet-topbar-content').find('.user-logout').attr('src', './images/seetreetimg/btn-on-yel.png');
    }, function(){
        $('.seetreet-topbar-content').find('.user-logout').attr('src', './images/seetreetimg/btn-on-grey.png');
    });

    $('#content-popup').find('.content-detail-likecount').hover(function(){
        // 켜져있다면
        if($('#content-popup').find('.content-detail-likecount').find('.likeActivate').css('display') == 'inline'){
            $('#content-popup').find('.content-detail-likecount').find('.likeActivate').attr('src', './images/seetreetimg/btn-heart-yel-red.png');
        }
        // 꺼져있다면
        else if($('#content-popup').find('.content-detail-likecount').find('.likeDeActivate').css('display') == 'inline'){
            $('#content-popup').find('.content-detail-likecount').find('.likeDeActivate').attr('src', './images/seetreetimg/btn-heart-yel-white.png');
        }
    }, function(){
        // 켜져있다면
        if($('#content-popup').find('.content-detail-likecount').find('.likeActivate').css('display') == 'inline'){
            $('#content-popup').find('.content-detail-likecount').find('.likeActivate').attr('src', './images/seetreetimg/btn-heart-grey-red.png');
        }
        // 꺼져있다면
        else if($('#content-popup').find('.content-detail-likecount').find('.likeDeActivate').css('display') == 'inline'){
            $('#content-popup').find('.content-detail-likecount').find('.likeDeActivate').attr('src', './images/seetreetimg/btn-heart-grey-white.png');
        }
    });

    $('.seetreet-container').find('.content-append-area').find('img').hover(function(){
        $('.seetreet-container').find('.content-append-area').find('img').attr('src', './images/seetreetimg/btn-plus-yel.png');
    }, function(){
        $('.seetreet-container').find('.content-append-area').find('img').attr('src', './images/seetreetimg/btn-plus-grey.png');
    });
    
    $('#content-popup').find('.write-comment-area').find('#comment-submit').hover(function(){
    	$('#content-popup').find('.write-comment-area').find('#comment-submit').attr('src', './images/seetreetimg/btn-ok-yel.png');
    }, function(){
    	$('#content-popup').find('.write-comment-area').find('#comment-submit').attr('src', './images/seetreetimg/btn-ok-grey.png');
    });

});

var tabmanager = {};
tabmanager.topbarmanager = function(select_menu){
    $('.container').hide();
    if(select_menu == 'topbar-user'){
        $('.seetreet-container').show();
        box_Factory.content.initContentData(function(){
            initialize_location(function(){
                contentshow(client.latitude, client.longitude, function(){
                    $('.content').slideDown();
                });
            });
        });
    }
    else{
        $('.seetreet-makecontent-container').show();
        if(select_menu == 'topbar-provider'){
            manage_providerinfo.loadpage();
        }
        else{
            manage_artistinfo.load.loadpage();

        }
    }
};


















