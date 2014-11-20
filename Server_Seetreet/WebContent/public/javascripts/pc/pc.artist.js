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