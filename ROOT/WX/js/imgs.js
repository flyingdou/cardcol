$(function(){
		var all_height=window.innerHeight;
		var img_height=all_height*0.225+"px";

		$(".pic img").css({
			height:function(index,value){
//				return window.innerHeight*0.125;
				return window.innerWidth*0.298*0.75;
			}
		});
		
	});
	