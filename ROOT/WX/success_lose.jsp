<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>奖惩方式</title>
    <link type="text/css" href="css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="css/base.css" rel="stylesheet">
    <link type="text/css" href="css/style.css" rel="stylesheet">
    <link type="text/css" href="css/index.css" rel="stylesheet">
    <script src="js/jquery-2.2.0.min.js"></script>
    <script src="js/style.js"></script>
    <script src="js/index.js"></script>
</head>
<body>
<div class="container">
    <div class="success">
        <h4>挑战成功奖励</h4>
        <textarea style="overflow: hidden">请输入奖励内容</textarea>
    </div>
    <div class="lose TheLast">
        <h4>挑战失败惩罚</h4>
        <a href="#">向以下账户支付<img src="images/right_icon_03.png"><span>10元</span></a>
        <label class="label_three col-xs-6"><span class="first_span"></span><input type="radio" name="check" value="" checked class="check_three">&nbsp;宋庆龄基金会</label><label class="label_three col-xs-6"> <span></span><input type="radio" name="check" value="" class="input_right check_three">&nbsp;宋庆龄基金会</label>
        <label class="label_three col-xs-6"><span></span><input type="radio" name="check" value=""  class="check_three" >&nbsp;挑战发起人</label><br><label class="label_three col-xs-6"> <span></span><input type="radio" name="check" value=""class="input_right check_three">&nbsp;挑战发起人</label>
    </div>
    <div class="assign">
        <a href="#" class="click">完成</a>
    </div>
    <div class="bottom" style="display: none">
        <a href="#" class="share fl">分享</a>
        <a href="joinChallenge2.html" class="join fl">参加挑战</a>
    </div>
</div>

<!--完成-->
<div class="finish_bg" style="display: none">
    <div class="finish">
        <div class="close"><i class="glyphicon glyphicon-remove"></i></div>
        <img src="images/finish1_10.png" class="gou">
        <p>你发布的信息将在卡库列表中！</p>
    </div>
</div>
<!--分享-->
<div class="share_bg" style="display: none">
    <div class="share_show">
        <div class="inner_top clearfix">
            <h3>分享<span>即将获得</span>2元红包</h3>
            <h5>日志好精彩～赶紧分享吧</h5>
            <div class="icon_top">
                <a href="#"><img src="images/share_icon_03.png" alt=""></a>
                <p>qq好友</p>
            </div>
            <div class="icon_top">
                <a href="#"><img src="images/share_icon_05.png" alt=""></a>
                <p>新浪微博</p>
            </div>
            <div class="icon_top">
                <a href="#"><img src="images/share_icon_10.png" alt=""></a>
                <p>微信好友</p>
            </div>
            <div class="icon_top">
                <a href="#"><img src="images/share_icon_07.png" alt=""></a>
                <p>朋友圈</p>
            </div>
        </div>
        <div class="quXiao">取消</div>
    </div>
</div>

</body>
</html>