/**
 * Created by Administrator on 2016/10/25.
 */
$(function () {
//    $(".left_list").on("mouseover", function () {
//        $(this).css("color","#fff")
//        $(this).css("backgroundColor","#92ba23");
//        $(".left_list").find(".inner_list").hide();
//        $(this).find(".inner_list").show();
//    }).on("mouseout", function () {
//        $(this).css("color","")
//        $(this).css("backgroundColor","")
//        $(this).find(".inner_list").hide();
//    });
	
	


    $(".course").on("mouseover", function () {
        $(this).css("box-shadow"," 0 0 10px 3px #ccc")
    }).on("mouseout", function () {
        $(this).css("box-shadow","")
    });

    $(".card").find("span").on("mouseover",function(){
        $(this).find("a").css("backgroundColor","#f69346");
        $(this).find("a").css("color","#fff");
    }).on("mouseout", function () {
        $(this).find("a").css("backgroundColor","");
        $(this).find("a").css("color","");
    });

    $(".middle_T").find("span").on("mouseover", function () {
        $(this).find("a").css("color","#f47e17");
    }).on("mouseout",function(){
        $(this).find("a").css("color","");
    });
    
});    

   /*banner图片轮播*/
  /*  change();
    time = setInterval("change()", 3000);


    $(".inner_list1").on("click", "span",function () {
        var oId=$(this).parent().attr("id") ;   //父级的id名字
        if ($(this).hasClass("select-all")) {
            $('.'+oId).remove();
        } else {
            var $p='<span class="'+oId+'">'+$(this).text()+'<img src="images/cha-icon_03.png"></span>';
            if($('.'+oId).length >0){
                $('.'+oId).html($(this).text()+'<img src="images/cha-icon_03.png">');
            }else{
                $(".chose .list_span").append($p);
            }
        }
    });
    $(".list_span1").on("click","img", function () {
        $(this).parent().remove();
    });
});
var time;
var count=0;*/
/*function change() {
    var $Li= $(".slides>li");
    var $address =$(".store_address");
    for (var i = 0; i < $address.length; i++) {
        $address[i].index=i;
        $address[i].onmouseover = function () {
            clearInterval(time);
            for (var j = 0; j < $Li.length; j++) {
                $Li[j].style.display = "none";
                $address[j].style.backgroundColor = "";
            }
            this.style.backgroundColor = "#f48824";
            $Li[this.index].style.display = "block";
        };
        $address[i].onmouseout = function () {
            time = setInterval("change()", 3000);
            count =this.index;
        };
        $Li[i].style.display = "none";
        $address[i].style.backgroundColor = "";
    }
    $Li[count % 5].style.display = "block";
    $address[count % 5].style.backgroundColor = "#f48824";
    count++;
    if (count == $address.length) {
        count = 0;
    }
}*/
