// JavaScript Document


//star
$(document).ready(function(){
    var stepW = 24;
    var description2 = new Array("很不喜欢 20分","不喜欢 40分","还可以 60分","喜欢 80分","很喜欢 100分");
    var stars2 = $("#star0 > li");
    var descriptionTemp2;
    $("#showb0").css("width",0);
    stars2.each(function(i){
        $(stars2[i]).click(function(e){
            var n = i+1;
            $("#showb0").css({"width":stepW*n});
            descriptionTemp2 = description2[i];
            $(this).find('a').blur();
            return stopDefault(e);
            return descriptionTemp2;
        });
    });
    stars2.each(function(i){
        $(stars2[i]).hover(
            function(){
                $(".description_0").text(description2[i]);
            },
            function(){
                if(descriptionTemp2 != null)
                    $(".description_0").text(descriptionTemp2);
                else 
                    $(".description_0").text(" ");
            }
        );
    });
	 
});

$(document).ready(function(){
    var stepW = 24;
    var description2 = new Array("很不喜欢 20分","不喜欢 40分","还可以 60分","喜欢 80分","很喜欢 100分");
    var stars2 = $("#star1 > li");
    var descriptionTemp2;
    $("#showb1").css("width",0);
    stars2.each(function(i){
        $(stars2[i]).click(function(e){
            var n = i+1;
            $("#showb1").css({"width":stepW*n});
            descriptionTemp2 = description2[i];
            $(this).find('a').blur();
            return stopDefault(e);
            return descriptionTemp2;
        });
    });
    stars2.each(function(i){
        $(stars2[i]).hover(
            function(){
                $(".description_1").text(description2[i]);
            },
            function(){
                if(descriptionTemp2 != null)
                    $(".description_1").text(descriptionTemp2);
                else 
                    $(".description_1").text(" ");
            }
        );
    });
	 
});

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

$(document).ready(function(){
    var stepW = 24;
    var description2 = new Array("很不喜欢 20分","不喜欢 40分","还可以 60分","喜欢 80分","很喜欢 100分");
    var stars2 = $("#star3 > li");
    var descriptionTemp2;
    $("#showb3").css("width",0);
    stars2.each(function(i){
        $(stars2[i]).click(function(e){
            var n = i+1;
            $("#showb3").css({"width":stepW*n});
            descriptionTemp2 = description2[i];
            $(this).find('a').blur();
            return stopDefault(e);
            return descriptionTemp2;
        });
    });
    stars2.each(function(i){
        $(stars2[i]).hover(
            function(){
                $(".description_3").text(description2[i]);
            },
            function(){
                if(descriptionTemp2 != null)
                    $(".description_3").text(descriptionTemp2);
                else 
                    $(".description_3").text(" ");
            }
        );
    });
	 
});

$(document).ready(function(){
    var stepW = 24;
    var description2 = new Array("很不喜欢 20分","不喜欢 40分","还可以 60分","喜欢 80分","很喜欢 100分");
    var stars2 = $("#star4 > li");
    var descriptionTemp2;
    $("#showb4").css("width",0);
    stars2.each(function(i){
        $(stars2[i]).click(function(e){
            var n = i+1;
            $("#showb4").css({"width":stepW*n});
            descriptionTemp2 = description2[i];
            $(this).find('a').blur();
            return stopDefault(e);
            return descriptionTemp2;
        });
    });
    stars2.each(function(i){
        $(stars2[i]).hover(
            function(){
                $(".description_4").text(description2[i]);
            },
            function(){
                if(descriptionTemp2 != null)
                    $(".description_4").text(descriptionTemp2);
                else 
                    $(".description_4").text(" ");
            }
        );
    });
	 
});

$(document).ready(function(){
    var stepW = 24;
    var description2 = new Array("很不喜欢 20分","不喜欢 40分","还可以 60分","喜欢 80分","很喜欢 100分");
    var stars2 = $("#star5 > li");
    var descriptionTemp2;
    $("#showb5").css("width",0);
    stars2.each(function(i){
        $(stars2[i]).click(function(e){
            var n = i+1;
            $("#showb5").css({"width":stepW*n});
            descriptionTemp2 = description2[i];
            $(this).find('a').blur();
            return stopDefault(e);
            return descriptionTemp2;
        });
    });
    stars2.each(function(i){
        $(stars2[i]).hover(
            function(){
                $(".description_5").text(description2[i]);
            },
            function(){
                if(descriptionTemp2 != null)
                    $(".description_5").text(descriptionTemp2);
                else 
                    $(".description_5").text(" ");
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
