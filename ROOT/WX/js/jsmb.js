$(function(){
	$('#tzjs').click(function(){
		var id = $(".list_one").find(':radio:checked').val();
		if(id=="A" || id == "" ){
			  $('#tzzj').attr('disabled',false);
			  $('#tzzj').attr('disabled',true);
			  $('#jssj').attr('disabled',true);
			  $('#jscs').attr('disabled',true);
		  }else if(id="B"){
			  $('#tzjs').attr('disabled',true);
			  $('#tzzj').attr('disabled',false);
			  $('#jssj').attr('disabled',true);
			  $('#jscs').attr('disabled',true);
			  alert("请先点选体重减少按钮");
		  }else if(id="C"){
			  $('#tzjs').attr('disabled',true);
			  $('#tzzj').attr('disabled',true);
			  $('#jssj').attr('disabled',false);
			  $('#jscs').attr('disabled',true);
			  alert("请先点选体重减少按钮");
		  }else if(id="D"){
			  $('#tzjs').attr('disabled',true);
			  $('#tzzj').attr('disabled',true);
			  $('#jssj').attr('disabled',true);
			  $('#jscs').attr('disabled',false);
			  alert("请先点选体重减少按钮");
		  }
    });
	$('#tzzj').click(function(){
		var id = $(".list_one").find(':radio:checked').val();
		if(id=="B" || id == "" ){
			  $('#tzzj').attr('disabled',true);
			  $('#tzzj').attr('disabled',false);
			  $('#jssj').attr('disabled',true);
			  $('#jscs').attr('disabled',true);
		  }else if(id="A"){
			  $('#tzjs').attr('disabled',false);
			  $('#tzzj').attr('disabled',true);
			  $('#jssj').attr('disabled',true);
			  $('#jscs').attr('disabled',true);
			  alert("请先点选体重增加按钮");
		  }else if(id="C"){
			  $('#tzjs').attr('disabled',true);
			  $('#tzzj').attr('disabled',true);
			  $('#jssj').attr('disabled',false);
			  $('#jscs').attr('disabled',true);
			  alert("请先点选体重增加按钮");
		  }else if(id="D"){
			  $('#tzjs').attr('disabled',true);
			  $('#tzzj').attr('disabled',true);
			  $('#jssj').attr('disabled',true);
			  $('#jscs').attr('disabled',false);
			  alert("请先点选体重增加按钮");
		  }
    });
	/*$('#jssj').click(function(){
		var id = $(".list_one").find(':radio:checked').val();
		if(id=="C"){
			  $('#tzjs').attr('disabled',true);
			  $('#tzzj').attr('disabled',true);
			  $('#jscs').attr('disabled',true);
		  }else{
			  $('#tzzj').attr('disabled',true);
			  $('#jssj').attr('disabled',true);
			  $('#jscs').attr('disabled',true);//false
			  alert("请先点选健身时间按钮");
		  }
    });
	$('#jscs').click(function(){
		var id = $(".list_one").find(':radio:checked').val();
		if(id="D"){
			  $('#tzjs').attr('disabled',true);
			  $('#tzzj').attr('disabled',true);
			  $('#jssj').attr('disabled',true);
		  }else{
			  $('#jscs').attr('disabled',true);//false
			  alert("请先点选健身次数按钮");
		  }
    });*/
})