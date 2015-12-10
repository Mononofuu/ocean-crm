/**
 * Created by antonsakhno on 10.12.15.
 */
$(document).ready(function(){
    var buttoncolor = 'cadetblue';
    var $activebutton = $('.todo');
    var $activeview = $('.todotasks');
    
    $activeview.show();
    $activebutton.css('background-color', buttoncolor);
    
    $('.view').hover(
        function(){
            if($activebutton.get(0)!=$(this).get(0)){
                $(this).css('background-color', 'cadetblue');
            };
        },
        function(){
            if($activebutton.get(0)!=$(this).get(0)){
                $(this).css('background-color', 'gainsboro');
            }else{
                $(this).css('background-color', buttoncolor);
            };
    });
        
    var buttonclick = function(){
        $('.tasks').hide();
        $activeview.show();
        $('.view').css('background-color', 'gainsboro');
        $activebutton.css('background-color', buttoncolor);
    };
    
    $('.todo').click(function(){
        $activebutton=$(this);
        $activeview=$('.todotasks')
        buttonclick();
    });
    $('.day').click(function(){
        $activebutton=$(this);
        $activeview=$('.daytasks')
        buttonclick();
    });
    $('.week').click(function(){
        $activebutton=$(this);
        $activeview=$('.weektasks')
        buttonclick();
    });
    $('.month').click(function(){
        $activebutton=$(this);
        $activeview=$('.monthtasks')
        buttonclick();
    });
    $('.list').click(function(){
        $activebutton=$(this);
        $activeview=$('.listtasks')
        buttonclick();
    });

});
