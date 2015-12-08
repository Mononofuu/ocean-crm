 $(document).ready(function(){
     
            $("#multiSelect span").hide();
            $("label, #from, #to").hide();

            $(".checkboxappear").click(function(){
                    $("#multiSelect span").toggle('slow');
                    });

     var dates = $("#from, #to").datepicker({
         defaultDate: "+2w",
         autoSize: true,
         changeMonth: true,
         numberOfMonths: 1,
         showOptions: { direction: "up" },
         showAnim: "fold",
         dayNamesMin: ['Пн','Вт','Ср','Чт','Пт','Сб','Вс'],
         monthNamesShort: ['Янв','Фев','Мар','Апр','Май','Июн','Июл','Авг','Сен','Окт','Ноя','Дек'],
         dateFormat: 'dd.mm.yy',
         onSelect: function(selectedDate){
             var option = this.id == "from" ? "minDate" : "maxDate",
                 instance = $( this ).data( "datepicker" ),
                 date = $.datepicker.parseDate(
                     instance.settings.dateFormat || $.datepicker._defaults.dateFormat,
                     selectedDate, instance.settings);
             dates.not(this).datepicker("option", option, date);
         }
     });

     $("#periodfilter").change(function(){
         if($(this).val() == "setperiod") {
             $("label, #from, #to").show();
             $("#radio").css("margin-top", "40%");
         }
         else {
             $("label, #from, #to").hide();
             $("#radio").css("margin-top", "-5%");
         }
     });
    
});
