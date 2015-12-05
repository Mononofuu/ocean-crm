/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
$(document).ready(function(){
    $('.menu li').hover(
        function(){
            $('ul',this).slideDown(100);
        },
        function(){
            $('ul',this).slideUp(100);
        }
    );
});