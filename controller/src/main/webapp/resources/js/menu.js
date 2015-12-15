/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
$(document).ready(function () {
    $('.menu li').hover(
        function(){
            $(this).children('ul').stop(false,true).fadeIn(300);
        },
        function(){
            $(this).children('ul').stop(false,true).fadeOut(300);
        }
    );
});