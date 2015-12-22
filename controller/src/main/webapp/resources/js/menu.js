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
    $('.menu .logo').click().toggle(
        function(){
            $(".profile").fadeIn();
        },
        function(){
            $(".profile").fadeOut();
        }
    );
});
$(document).mouseup(function (e) {
    var container = $(".profile");
    if (container.has(e.target).length === 0){
        container.hide();
    }
});