$(document).ready(function(){
    $('.provider-create-area').find('.listbox').on("click", function(){
        $('.provider-create-area').find('.listbox').off("click");
        manage_providerbox.createcontent();
        $('#time-setting-finish').on("click", function(){
            $('#time-setting-finish').off("click");
            $('.provider-create-area').find('.listbox').on("click");
            manage_providerbox.inputcontenttime();
        });
    });
});

var manage_providerinfo = {};
manage_providerinfo.putinfo = function(){

};
manage_providerinfo.uploadimage = function(){

};

var manage_providerbox = {};
manage_providerbox.createcontent = function(){
    console.log('createcontent함수 불림');
    $('.provider-create-area').find('.listbox').find('.beforecreate').hide();
    $('.provider-create-area').find('.listbox').find('.input-time').show();
};
manage_providerbox.inputcontenttime = function(){
    console.log('inputcontenttime함수 불림');
    $('.provider-create-area').find('.listbox').find('.input-time').hide();
    $('.provider-create-area').find('.listbox').find('.beforecreate').show();
    box_Factory.provider.create_provider_group();
};
manage_providerbox.providerconfirmed = function(){

};

