/**
 * Created by Limjiuk on 2014-10-02.
 */
$(document).ready(function(){
    // remmber user Id 처리
    var remember = getCookie(COOKIE_REMEMBER_USER_ID);
    if(remember != null && remember.length > 0){
        $("input[name='remember']:checkbox").prop('checked', true);
        $('#input-email').attr('value', remember);
        $('#input-password').attr("autofocus", true);
    }
    else{
        $("input[name='remember']:checkbox").prop('checked', false);
        $('#input-email').attr("autofocus", true);
    }
    // 이벤트 처리
    $('button').click(function(e){
        // var target_id = $(e.currentTarget).attr('id');
        console.log($(e.currentTarget).attr('id'));
        var target_id = $(e.currentTarget).attr('id');
        if(target_id == "login-button"){
            login();
        }
        else if(target_id == "join-button"){
            switching_join();
        }
        else if(target_id == "join-next"){
            joinnext();
        }
        else if(target_id == "join-finish"){
            joinfinish();
        }
    });
});

var join_id = '';
var join_body = {
    "password" : "dummypassword",
    "name" : "dummyname",
    "age" : "dummyage",
    "phone" : "dummyphone"
};

// join버튼 이벤트


// login 버튼 이벤트
var login = function(){
    var inputemail = $('#input-email[type=text]').val();
    var inputpassword = $('#input-password[type=password]').val();

    postUserLogin(inputemail, inputpassword, function(data, status, res){
        if(status == 'success'){
            if(data.state == true){
                console.log(data);
                if($("input[name='remember']:checkbox").is(":checked")){
                    setCookie(COOKIE_REMEMBER_USER_ID, inputemail);
                }
                else{
                    removeCookie(COOKIE_REMEMBER_USER_ID);
                }
                setCookie(COOKIE_USER_ID, data.data.user_email);
                setCookie(COOKIE_USER_NAME, data.data.user_name);
                setCookie(COOKIE_USER_TOKEN, data.data._id);
                window.location.href = "./enjoy.see";
            }
            else{
                alert('아이디 또는 비밀번호를 확인하세요');
            }
        }
        else{
            alert('Login Error');
        }
    });
};

var switching_join = function(){
    $('#join-button').hide();
    $('.login-input-group').hide();
    $('.join-input-group').show();
};

// 다음 버튼 이벤트
var joinnext = function(){
    join_id = $('#join-input-email').val();
    join_body.password = $('#join-input-password').val();
    $('.join-input-group').hide();
    $('.join-input-additional-group').show();
    // 이미 있는 이메일인지
//    getUserDuplication(join_body.email, function(data, status, res){
//        if(status == 'success'){
//            // 수정필요, 데이터 값에 따라서 유저 아이디가 있는지 검사
//            if(data == false){
//                $('.join-input-group').hide();
//                $('.join-input-additional-group').show();
//            }
//            else{
//                alert('이미 존재하는 아이디입니다.');
//            }
//        }
//        else{
//            alert('ajax error');
//        }
//    });
};

var joinfinish = function(){
    join_body.name = $('#join-input-name').val();
    join_body.age = $('#join-input-age').val();
    join_body.phone = $('#join-input-phone').val();

    console.log(join_id);
    console.log(join_body);
    postUserCreate(join_id, join_body, function(data, status, res){
        if(status == 'success'){
            if(data.data == true){
                console.log(data);
//                $('.login-input-group').show();
//                $('.join-input-group').hide();
//                $('#join-button').show();
//                init_joindata();
//                window.location.href = "./hello.see";
            }
            else{
                alert('가입에 실패하였습니다.');
//                init_joindata();
//                window.location.href = "./hello.see";
            }
        }
        else{
            alert('통신에러');
        }
    });
};
var init_joindata = function(){
    $('#join-input-email[type="text"]').val('');
    $('#join-input-password[type="password"]').val('');
    $('#join-input-name[type="text"]').val('');
    $('#join-input-age[type="text"]').val('');
    $('#join-input-phone[type="text"]').val('');
};

