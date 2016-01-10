/**
 * Created by antonsakhno on 10.12.15.
 */
$(document).ready(function(){
    $("#datepicker").datepicker();
    $("#timepicker").timepicker({ 'timeFormat': 'H:i' });
    $(".multipleselect").select2();
});
$(document).on("submit", "#newcompanyform", function(event){
    var $form = $(this);
    $.post("/new_company_veryfy", $form.serialize(), function(responseJson){
        if(Object.keys(responseJson).length>0){
            $(".remove").remove();
            $(".has-error").removeClass("has-error");
            $.each(responseJson, function(key, value) {
                $("#"+key).attr("placeholder", value);
                $("#"+key).parent().addClass("has-error");
                $("#"+key).parent().append($("<span>").addClass("help-block remove").text(value));
            });
        }else{
            $.post($form.attr("action"), $form.serialize(), function(responseJson){
                //добвляем переданную из сервлета компанию в выпадающий список компаний и устанавливаем на ней выбор
                $("#companyselect").append($("<option>").text(responseJson.name).attr("value", responseJson.id).attr("selected","selected"));
            });
        }
    });
    event.preventDefault();
});
$(document).on("submit", "#newcontactform", function(event){
    var $form = $(this);
    $.post("/new_contact_verify", $form.serialize(), function(responseJson){
        if(Object.keys(responseJson).length>0){
            $(".removecontactfeild").remove();//очищаем ранее созданные span с сообщениями об ошибке
            $(".has-error").removeClass("has-error"); //убираем всю ранее созданную красную подсветку
            $.each(responseJson, function(key, value) { // проходимся по всей map переданной из сервлета
                $("#"+key).attr("placeholder", value);//заменем  pleceholder на сообщение об ошибке
                $("#"+key).parent().addClass("has-error");//добавляем красную подсветку
                $("#"+key).parent().append($("<span>").addClass("help-block removecontactfeild").text(value));//добавляем элемент span с сообщением об ошибке
            });
        }else{
            $form.submit();
        }
    });
    event.preventDefault();
});
