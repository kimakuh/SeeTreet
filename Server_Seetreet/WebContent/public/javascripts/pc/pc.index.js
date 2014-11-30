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

});

var tabmanager = {};
tabmanager.topbarmanager = function(select_menu){
    $('.container').hide();
    if(select_menu == 'topbar-user'){
        $('.seetreet-container').show();
        box_Factory.content.initContentData(function(){
            initialize_location(function(){
                contentshow(client.latitude, client.longitude);
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


















