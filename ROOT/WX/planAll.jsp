<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>通用健身计划</title>
    <link type="text/css" href="css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="css/swiper.min.css" rel="stylesheet">
    <link type="text/css" href="css/base.css" rel="stylesheet">
    <link type="text/css" href="css/style.css" rel="stylesheet">
    <link type="text/css" href="css/index.css" rel="stylesheet">
    <script type="text/javascript" src="js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="js/swiper-3.4.0.min.js"></script>
    <script type="text/javascript" src="js/imgs.js"></script>
    <script type="text/javascript">
        $(function () {
            /*轮播*/
            var swiper = new Swiper('.swiper-container', {
                pagination: '.swiper-pagination',
                paginationClickable: true,
                loop:true,
                autoplay : 1500,
                autoplayDisableOnInteraction:false
            });
        });
        
      /*   function search(){
  		  var keyword = $("#keyword").val();
  		   $.ajax({ 
  			type:"post", 
  			url:"planlistwx.asp", 
  			data:"keyword="+keyword, 
  			success:function(msg){
  				$("#part").html($(msg).find("#part").html());
  			} 
  			}); 
  	} */
    </script>
</head>
<body>
<div class="container">
    <div class="banner">

        <div class="swiper-container">
            <ul class="swiper-wrapper">
             <s:iterator value="#request.slides">
               <li class="swiper-slide">
					<a href="plandetail.asp?id=<s:property value="id"/>">
						<img src="../<s:if test="icon == null || icon == \"\""><s:if test="image == null || image == \"\"">images/img-defaul.jpg</s:if><s:else>picture/<s:property value="image"/></s:else></s:if><s:else>picture/<s:property value="icon"/></s:else>" alt="">
					</a>
				</li>
            </s:iterator>
            
            </ul>
            <div class="swiper-pagination"></div>
        </div>
        <div class="search">
            <input type="search" value="" placeholder="搜索健身计划"  id="keyword">
            <span class="glyphicon glyphicon-search search_icon"></span>
            <a href="planlistwx!onTerm.asp" class="glyphicon glyphicon-list fr"></a>
        </div>
    </div>
    <div class="table_list" id="part">
        <s:iterator value="pageInfo.items">
        <div class="list">
            <a href="plandetail.asp?id=<s:property value="id"/> ">
                <div class="pic fl"><img src="../picture/<s:property value="image1"/>" alt=""></div>
                <div class="txt fl">
                    <h6><s:property value="plan_name"/></h6>
                    <p>已有<span><s:if test="saleNum==0\\saleNum==null">0</s:if>
                                 <s:else><s:property value="saleNum"/></s:else>
                                        </span>人参加</p>
                </div>
            </a>
        </div>
        </s:iterator>
    </div>
</div>
</body>

</html>