// JavaScript Document


//star
$(document).ready(function(){
    var stepW = 24;
    var description2 = new Array("很不喜欢 20分","不喜欢 40分","还可以 60分","喜欢 80分","很喜欢 100分");
    var stars2 = $("#star2 > li");
    var descriptionTemp2;
    $("#showb2").css("width",0);
    stars2.each(function(i){
        $(stars2[i]).click(function(e){
            var n = i+1;
            $("#showb2").css({"width":stepW*n});
            descriptionTemp2 = description2[i];
            $(this).find('a').blur();
            return stopDefault(e);
            return descriptionTemp2;
        });
    });
    stars2.each(function(i){
        $(stars2[i]).hover(
            function(){
                $(".description_2").text(description2[i]);
            },
            function(){
                if(descriptionTemp2 != null)
                    $(".description_2").text(descriptionTemp2);
                else 
                    $(".description_2").text(" ");
            }
        );
    });
	 
});
function stopDefault(e){
    if(e && e.preventDefault)
           e.preventDefault();
    else
           window.event.returnValue = false;
    return false;
};
