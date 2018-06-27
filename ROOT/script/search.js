// JavaScript Document

var $ = function(id){return document.getElementById(id);}
var formSubmit = function(id){
	document.forms[id].submit();
	return false;
}
var tip = function(q, for_q){
	q = $(q);
	for_q = $(for_q);
	q.onfocus = function(){
		for_q.style.display = 'none';
		q.style.backgroundPosition = "right -22px";
	}
	q.onblur = function(){
		if(!this.value) for_q.style.display = 'block';
		q.style.backgroundPosition = "right 0";
	}
	for_q.onclick = function(){
		this.style.display = 'none';
		q.focus();
	}
};
tip('keyword','for-keyword');


var timoutid;
$(document).ready(function(){
	
	$("#tabfirst li").each(function(index){
	
		$(this).mouseover(function(){	
			var liNode = $(this);
			timoutid = setTimeout(function(){
				
				$("div.contentin").removeClass("contentin");
			
				$("#tabfirst li.tabin").removeClass("tabin");
				
				$("div.contentfirst:eq(" + index + ")").addClass("contentin");
				liNode.addClass("tabin");	
			},300);			
		}).mouseout(function(){
			clearTimeout(timoutid);	
		});
	});
	
});

