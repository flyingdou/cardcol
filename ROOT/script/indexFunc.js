//focus picture
(function(){
var getElem=function(a){return"string"==typeof a?document.getElementById(a):a};
var Class={create:function(){return function(){this.initialize.apply(this,arguments)}}};
function extend_(a,c){for(var b in c){a[b]=c[b]}return a}var TransformView=Class.create();
TransformView.prototype={initialize:function(b,g,h,f,c){if(h<=0||f<=0){return}var e=getElem(b),a=getElem(g),d=this;this.Index=0;
this._timer=null;this._slider=a;this._parameter=h;this._count=f||0;this._target=0;this.SetOptions(c);
this.Up=!!this.options.Up;this.Step=Math.abs(this.options.Step);this.Time=Math.abs(this.options.Time);
this.Auto=!!this.options.Auto;this.Pause=Math.abs(this.options.Pause);this.onStart=this.options.onStart;
this.onFinish=this.options.onFinish;e.style.overflow="hidden";e.style.position="relative";a.style.position="absolute";
a.style.top=a.style.left=0},SetOptions:
function(a){this.options={Up:true,Step:5,Time:20,Auto:true,Pause:5000,onStart:function(){},onFinish:function(){}};
extend_(this.options,a||{})},Start:function(){if(this.Index<0){this.Index=this._count-1}
else{if(this.Index>=this._count){this.Index=0}}
this._target=-1*this._parameter*this.Index;this.onStart();this.Move()},Move:function(){clearTimeout(this._timer);
var b=this,c=this.Up?"top":"left",a=parseInt(this._slider.style[c])||0,d=this.GetStep(this._target,a);
if(d!=0){this._slider.style[c]=(a+d)+"px";this._timer=setTimeout(function(){b.Move()},this.Time)}
else{this._slider.style[c]=this._target+"px";this.onFinish();
if(this.Auto){this._timer=setTimeout(function(){b.Index++;b.Start()},this.Pause)}}},
GetStep:function(c,a){var b=(c-a)/this.Step;if(b==0){return 0}if(Math.abs(b)<1){return(b>0?1:-1)}return b},
Stop:function(b,a){clearTimeout(this._timer);this._slider.style[this.Up?"top":"left"]=this._target+"px"}};
function Each(d,b){for(var c=0,a=d.length;c<a;c++){b(d[c],c)}}var objs2=getElem("focusMenu").getElementsByTagName("li");
var tv2=new TransformView("focusPic","focusSlider",654,3,
{onStart:function(){Each(objs2,function(b,a){b.className=tv2.Index==a?"on":""})},
Up:false});tv2.Start();Each(objs2,function(b,a){b.onmouseover=function(){b.className="on";
tv2.Auto=false;tv2.Index=a;tv2.Start()};b.onmouseout=function(){b.className="";tv2.Auto=true;tv2.Start()}});})();

//sales volume
(function(){
var $salesVolume=$('#indexFoodList,#indexEntertainmentList,#indexBeautyList,#indexLifeList').find('s');
	if($salesVolume.length>0){
		var salesVolumeArr=[],salesVolumeStr='';
		for(var j=0;j<$salesVolume.length;j++){
			salesVolumeArr.push(($salesVolume.eq(j).attr('id')).replace(/\D/g,''));
		}
		salesVolumeStr=salesVolumeArr.join(',');
		var _vlomeUrl=CONTEXTPATH+'/goods/refreshSalesCount.do?req='+(new Date()).getTime();
		$.ajax({
			url:_vlomeUrl,
			type:'POST',
			cache:'false',
			data:'type=goods&ids='+salesVolumeStr,
			dataType:'json',
			success:function(data){
				if(data){
					for(var i=0,len=data.length;i<len;i++){
						$('#sal_'+data[i].goodsId).html(data[i].salesCount);
					}
				}
			}	
		});
	}
})();