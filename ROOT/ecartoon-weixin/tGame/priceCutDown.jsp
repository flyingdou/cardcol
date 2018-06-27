<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath%>" >
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title></title>
    <script src="ecartoon-weixin/js/mui.min.js"></script>
    <link href="ecartoon-weixin/css/mui.min.css" rel="stylesheet"/>
    <script type="text/javascript"src="ecartoon-weixin/js/jquery.min.js">
    </script>
    <script type="text/javascript" charset="utf-8">
      	mui.init();
    </script>
    <style>
	   
    	.mui-card{
    		margin:0px!important;
    	}
    	.time-over{
    		text-align: center;
    		padding: 2px 0 6px 0;
    	}
    	.bgR{
    		display: inline-block;
         	background-color:#e91d34;
         	padding: 0px 3px;
         	color:white;
         	border-radius: 1px;
    	}
        .cR{
        	color:#12110f;
        }
        .width50{

        	width: 50%;
        	height: 100px;
        	text-align: center;
        	display: block;
        	margin: 10px 0!important;
        	box-sizing: border-box;
            background: white;
            outline: 1px solid #eaeaea;
            overflow: hidden;
            padding: 0!important;
            float: left;
            margin-bottom: 6px;
            padding: 20px;
            box-sizing: border-box;
            line-height: 30px;
            font-size: 18px;
        }
        .hei30{
        	margin-top: 20px;
        	height: 30px;
        	width: 100%;
        	font-weight: 700;
        }
        .colorr{
        	color:#ff4401;
        }
        .now{
        	color:white;
        	background:#ff544c ;
        	border: 1px solid #ff544c;
        	display: inline-block;
        	margin-right: 10px;
        	width: 40%;
        	
        }
        .find{
        	color:white;
        	background:#ffaf0f ;
        	border: 1px solid #ffaf0f;
        	display: inline-block;
        	margin-left: 10px;
        	width: 40%;
        	
        }
        
        .button_grow{
        	margin: 10px;
        }
        .active_show{
        	color:white;
        	background:#20b69d ;
        	border: 1px solid #20b69d;
        	width: 50%;
        	float: left;
        	border-bottom-right-radius: 0!important;
        	border-top-right-radius: 0!important;
        }
        .top10{
        	color:#20b69d;
        	background:white ;
        	border: 1px solid #20b69d;
        	display: inline-block;
        	width: 50%;
        	float: left;
        	border-bottom-left-radius: 0!important;
        	border-top-left-radius: 0!important;	
        }
        .active_shownext{
        	padding: 10px 0;
        	font-size: 13px;
        	color:#666;
        }
   
       #userimg2{
            display:block;
        	width:50px;
        	-webkit-box-align:center ;
        }
       #nick2{
        	
        	overflow: hidden;
        	display:block;
            -webkit-box-flex: 1;
            line-height: 20px;
        }
   
        #top_next{
        	border-bottom: 1px solid #eaeaea;
        	/*border-top:1px solid #eaeaea;*/
        	padding:5px 0 ;
        }
        #top_nextshow{
        	border-bottom: 1px solid #eaeaea;
        	line-height: 30px;
        	padding: 3px 0;
        	display: -webkit-box;
        	-webkit-box-orient: horizontal;
        }
        .seemore{
             color:#666;
        	text-align: center;
        	line-height: 25px;
        	margin-top: 5px;
        }
        
       /* mui原生页面样式修改*/
      .mui-popup{
      	border-radius: 8px;
      }
      .mui-popup-in {
      	 border:6px solid #ff544c;
      	 padding: 0px;
      	 background: #FF544C;
      }

 
     .mui-popup-inner{
     	background-color: #ff544c;
     	color:white;
     	padding: 0;
     	border-radius: 0px;
     	outline-color:white;
     	
     }
      .mui-popup-title{
      font-weight: 400;
      font-size: 15px;
      padding: 5px 0;
      }
      .mui-popup-text{
      	background: white;
      	padding: 15px 0;
      	border-radius: 3px;
      	margin-bottom: 0px!important;
      	color:#000000;
      	font-size: 13px;
      
      }
      .mui-popup-buttons{
      	background: white;
      	height: 46px;
      	margin: 0;
      	border-top:5px solid #FF544C;
      	padding: 0px;
      
      }
      .mui-popup-button{
      	font-size: 14px;
      	color:white;
      	background:#ff544c ;
      	height: 30px;
      	line-height: 30px;
      	width: auto;
      	border-radius: 6px!important;
      	padding:2px 15px ;
      	margin: 5px 10px;
      	
      	
      }
      .a_box{
      	width: 300px;
      	height:44px;
      	margin: 10px auto;
      }
        .ios{
        	display: inline-block;
            width:100px;
            height: 40px;
            margin: 2px 20px;
          
            background: #FF4401;
            color:white;
            text-decoration: none;   
            line-height: 40px;
            text-align: center;
            border-radius: 12px;
                 
            }
    	
    	
    	
    </style>
</head>
<body>
	<div class="mui-content">
	    <div class="mui-card">
	    	<div class="mui-card-content">
	    		<img src="ecartoon-weixin/img/banner.png" width="100%"/>
	    		<div class="time-over">
	    			<span id="dao">倒计时：</span>
	    			<span class="bgR"id="day"></span>
	    			<span class="cR"id="day1">天</span>
	    			<span class="bgR"id="hour"></span>
	    			<span class="cR"id="hour1">小时</span>
	    			<span class="bgR"id="mi"></span>
	    			<span class="cR"id="mi1">分</span>
	    			<span class="bgR"id="s"></span>
	    			<span class="cR"id="s1">秒</span>
	    		</div>
	    		
	    		<div class="money_box">
	    		    <div class="width50">
	    			<div class="hei30">
	    				现价：
	    			</div>
	    			<div class="colorr" id="currentPrice">￥${cutInfo.currentPrice}</div>
	    		    </div>
	    		    <div class="width50">
	    			<div class="hei30">
	    				已砍：
	    			</div>
	    			<div class="colorr" id="cutCount">￥${cutInfo.cutCount}</div>
	    		    </div>
	    		    <div class="mui-clearfix">
	    		    	
	    		    </div>
	    		</div>
	    		
	    		
	    		<div class="mui-button-row">
	    		      <button type="button"id='now'  class="mui-btn now">立即购买</button>
                      <button type="button"id='find'  class="mui-btn find">找人砍价</button>
	    		</div>
	    		
	    		
	    		<div class="button_grow">
	    			<div>
	    			  <button type="button" class="mui-btn active_show"id='btn'>活动规则</button>
                      <button type="button" class="mui-btn top10" id="btn1">砍价排名</button>
                      <div class="mui-clearfix">
                      	
                      </div>
	    			</div>
	    				<div class="active_shownext">
	    					<div class="act-say">
	    						
	    					</div>
	    			     
                           <div class="a_box">
                           	<a href="https://itunes.apple.com/us/app/%E5%81%A5%E8%BA%ABe%E5%8D%A1%E9%80%9A/id1218667055?mt=8"class="ios">下载IOS版</a>
                           	<a href="http://www.ecartoon.com.cn/app/ecartoonV1.apk" class="ios">下载安卓版</a>
                           </div>
	    		         </div>
	    		         
	    		         <div class="topnext"style="display:none">
	    			     <div class="seemore" id='seemore'>
	    			     	查看更多
	    			     </div>
	    		         
	    		</div>
	    		
	    		
	    	
	    		
	    	</div>
	    	
	    </div>
	</div>
	
</div>
</body>

<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="http://www.ecartoon.com.cn/js/utils.elisa.js"></script>
<script>
	//分享
	(function(){
		wxUtils.sign("ewechatwx!sign.asp");
		wx.ready(function(){
			wxUtils.share({
				title : '体型测出夫妻幸福指数',
				link : 'egamewx!priceCutDown.asp?id=${cutInfo.id}&product=1&lowPrice=170&money=199',
				img : 'ecartoon-weixin/tGame/img/healthtest.jpg',
				desc : '美国一项研究指出，妻子和丈夫的体型会影响家庭的幸福感。点击测试一下你和另一半的幸福指数吧！'
			});
		});
	})();
</script>
<script type="text/javascript">	

  
   var ddd = ${cutInfo};
   var protypemoney=${cutInfo.money};//原价
		var nowmoney=${cutInfo.currentPrice};//当前价格
		var moneyA=${cutInfo.lowPrice};//底价
/* 		var self=0.2;//自身的抽取比例 */
		//判定客户是否第一次进入，TRUE:是；false:否
		var has=${cutInfo.cutActive};//如果某一个用户首次进入页面
		
		
	
	
	//倒数计时，应该在获取json后调用
		function kkk(){
	       var data={
			'time':"2018/8/8 12:00"
		            }
		 var kTime=new Date(data.time);
		 
		 var nowTime=new Date().getTime();
		//剩余时间
          var haveTime=kTime-nowTime;
          
           var seconds=Math.floor(haveTime/1000);
           var minutes=Math.floor(seconds/60);
           var hours=Math.floor(seconds/3600);
           var days=Math.floor(seconds/86400);
           var sec=seconds%60;
           var min=minutes%60;
           var hou=hours%60;
           sec<10?sec='0'+sec:sec; 
           min<10?min='0'+min:min; 
           hou<10?hou='0'+hou:hou; 
           days<10?days='0'+days:days; 
                       if(haveTime>0){
                             var day=document.getElementById('day'); 
                             var hour=document.getElementById('hour');
                             var mi=document.getElementById('mi');
                             var s=document.getElementById("s");
                             s.innerHTML=sec;
                             mi.innerHTML=min;
                             hour.innerHTML=hou;
                             day.innerHTML=days;
                               setTimeout(kkk,1000);
                         
                            }else{
                        
                                 $('.find').css({"text-decoration":"line-through"}).attr("disabled",true);
                              $('.now').css({"background":"#eaeaea","color":"#ff544c","text-decoration":"line-through"}).attr("disabled",true)
                            }
                 } 
        //进入页面砍自己
        function kanziji(){
       
		if(has){
		var removemoney=(protypemoney-moneyA) ;//自己抽取的价格为：原价-底价的百分之二十的最大比率
		var selfmoney=parseInt(Math.random()*(removemoney)+1);//自身砍的价格：取整最小为一元
		nowmoney=protypemoney-selfmoney;//当前自砍后的价格
	        if (!${cutInfo.already}){
			mui.alert('你已成功为自己砍掉￥'+ ${cutInfo.cutMoney} +'元。</br>（做人嘛,就是要对自己下手就要狠一点）','温馨提示：',['确认'],null)
		    } else {
		    mui.alert('${cutInfo.message}','温馨提示：','[确认]',null);
		    }
		} else {
	    
			 if (!${cutInfo.already}){
		          mui.alert('${cutInfo.message}。</br>点击我也要买，为自己砍价','温馨提示：',['确认'],null)
			    } else {
			      mui.alert('${cutInfo.message}','温馨提示：','[确认]',null);
			    }
		//替换按钮内容
		$("#now").attr("id","cutPriceDou");
		$("#cutPriceDou").html("我也要买");
		$("#cutPriceDou").click(function () {
			location.href = "egamewx!priceCutDown.asp";
		});
		
		}
          }
        
        
        
        
       
       //活动介绍，排名
       function activeTop(){
           	data={
			  "banner":"img/banner.png",
              /* "top": [
                      { "nick":"Bill" , "imgSrc":"img/banner.png",'money':123},
                      { "nick":"George" , "imgSrc":"img/banner.png",'money':234},
                      { "nick":"Thomas" , "imgSrc":"img/banner.png",'money':456}
                     ], */
                  "top":${cutInfo.sortList == null ? 0 : cutInfo.sortList},
                     "time":"2017-8-8 19:00",
                      "active_show":" 一、砍价结束后必须马上购买，商品才是你的，手慢可能库存会销完哦！<br/>二、使用方法：购买完成后，请下载健身E卡通APP，到适用俱乐部扫码签到。<br/>三.每个好友帮砍金额均为随机，拼的就是人气哦。<br/>四.活动时间：2017年7月1日-2018年7月31日。</br>五.本活动最终解释权由健身E卡通所有。"
              }
             var dataTop=data.top;
             function sortthis(a,b){
             return b.money-a.money;
             }
             if (dataTop == 0){
            	 dataTop = [];
             }
             dataTop.sort(sortthis);
            // console.log(dataTop);
             var len=dataTop.length;
             var len1=2;//显示的条数
             var n=0;
             if (len != 0){
	           	 for(i=0;i<len;i++){
	                   var topmore=$("<div id='top_nextshow'><span id='userimg2'><img src='"+dataTop[i].imgSrc+"' width='40px'height='40px'style='border-radius: 50%;display: block;'/></span><span id='nick2'>"+dataTop[i].nick+"</span></div>");
	                   $(".seemore").before(topmore);
	             }
             }
             $('.act-say').html(data.active_show);
             var seemore=document.getElementById('seemore');
              seemore.addEventListener("tap",function(){
           n++;
           if(n<=parseInt(len/len1)){
           var maxlen=len1*n+len1
          maxlen>len?maxlen=len:maxlen;
                  for(i=n*len1;i<maxlen;i++){
             var topmore=$("<div id='top_nextshow'><span id='userimg2'><img src='"+dataTop[i].imgSrc+"' width='40px'height='40px'style='border-radius: 50%;display: block;'/></span><span id='nick2'>"+dataTop[i].nick+"</span></div>");
            $(".seemore").before(topmore);
             }
           }else{
             //发送请求更多数据
                  }
           
            })
            
       }

      

	$(function(){
        kkk();
        kanziji();
        activeTop()
		//立即购买按钮触发事件
	    var now=document.getElementById('now');
		now.addEventListener('tap',function(){
		var json = {
						productType:8,
						product:1,
						quantity:1,
						price:${cutInfo.currentPrice}
					}
			if(nowmoney>moneyA){
				mui.confirm('未砍到最低价，是否立即购买？','温馨提示：',['现在就买','再砍一下'],function(e){
					if(e.index==0){
						//A页面中打开B页面，设置show的autoShow为false，则B页面在其loaded事件发生后，不会自动显示；
						mui.openWindow({
                                    url: 'egamewx!priceCutDownOrder.asp?json='+encodeURI(JSON.stringify(json)), 
                                   show:{
                                         autoShow:false
                                        }
                                        });
                                        
                                        //B页面onload从服务器获取列表数据；
                                       window.onload = function(){
                                           //从服务器获取数据
                                                //....
                                         //业务数据获取完毕，并已插入当前页面DOM；
                                        //注意：若为ajax请求，则需将如下代码放在处理完ajax响应数据之后；
                                          mui.plusReady(function(){
                                               //关闭等待框
                                          plus.nativeUI.closeWaiting();
                                                 //显示当前页面
                                            mui.currentWebview.show();
                                               });
                                         }
						
					}
				},'div')
			}else{
					//A页面中打开B页面，设置show的autoShow为false，则B页面在其loaded事件发生后，不会自动显示；
						mui.openWindow({
                                    url: 'egamewx!priceCutDownOrder.asp?json='+encodeURI(JSON.stringify(json)), 
                                   show:{
                                         autoShow:false
                                        }
                                        });
                                        
                                        //B页面onload从服务器获取列表数据；
                                       window.onload = function(){
                                           //从服务器获取数据
                                                //....
                                         //业务数据获取完毕，并已插入当前页面DOM；
                                        //注意：若为ajax请求，则需将如下代码放在处理完ajax响应数据之后；
                                          mui.plusReady(function(){
                                               //关闭等待框
                                          plus.nativeUI.closeWaiting();
                                                 //显示当前页面
                                            mui.currentWebview.show();
                                               });
                                         }
			}
			
		});
		//找朋友砍价
		var find=document.getElementById('find');
		find.addEventListener('tap',function(){
			mui.alert("点击右上角分享按钮，分享给你的好友，让他们帮你砍价吧",'温馨提示：',['朕知道了'],null);
		})
		
		
		
		//活动规则，砍价排名，tap切换；
 var btn=document.getElementById('btn');
 var btn1=document.getElementById('btn1');
 btn.addEventListener("tap",function(){
 	$("#btn1").css({
 		"color":"#20b69d",
 		"background":"white"
 	});
    $(this).css({
 		'background':"#20b69d",
 		"color":'white'
 		
 	});
 	$(".topnext").css("display","none");
 	$(".active_shownext").css("display","block");
 });
 
  btn1.addEventListener("tap",function(){
 	$("#btn").css({
 		"color":"#20b69d",
 		"background":"white"
 	});
    $(this).css({
 		'background':"#20b69d",
 		"color":'white'
 		
 	});
 	$(".topnext").css("display","block");
 	$(".active_shownext").css("display","none");
 });
 
 
 
	})



</script>
</html>