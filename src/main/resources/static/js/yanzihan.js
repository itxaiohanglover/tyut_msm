//模块化 每个功能函数去做自己相应的事情 代码可维护性 可扩展性
//初始化函数
var aShowList = document.querySelectorAll('.s_show div');//获取元素 H5
var oShow = document.querySelector('.s_show');
var oSend = document.querySelector('.send');
var oBtn = document.querySelector('.btn');
String.prototype.replaceAll = function(s1,s2){
	return this.replace(new RegExp(s1,"gm"),s2);
}
//发送弹幕
function sendbarrage(text){//鼠标点击事件
	//oBtn.style.backgroundColor = randomColor();//按钮背景颜色变换
		var oDiv = document.createElement('div');//创建div
		oDiv.innerHTML = text.replaceAll("<br/>"," ").replaceAll("<br>"," ");//添加弹幕内容
		oDiv.className = 'magictime twisterInUp';//弹幕特效
		oShow.appendChild(oDiv);//添加一个子节点
		init(oDiv);//初始化=
		//console.log(time);
}
for(var i = 0;i < aShowList.length;i++){
	init(aShowList[i]);//执行初始化函数
}
function init(obj){//接受弹幕对象
	//确定top值的随机区间
	var screenHeight = document.getElementsByClassName("screen")[0].clientHeight;//获取屏幕可视高度
	// alert(screenHeight)
	var maxTop = screenHeight - obj.offsetHeight;//高度差范围
	obj.style.top = maxTop * Math.random() + 'px';    
	//控制left值
	var screenWidth = document.documentElement.clientWidth;//获取可视宽度
	var maxLeft = screenWidth - obj.offsetWidth;//随机宽度差
	obj.style.left = maxLeft + 'px';
	//弹幕的随机颜色
	obj.style.color = randomColor();
	/*setInterval(function(){
		move(obj,maxLeft);
	},1000);*///普通定时器
	move(Math.random()*2+1,obj,maxLeft);
}
//弹幕移动函数
function move(k,obj,maxLeft){
	var speed = k;//控制速度的变量
	maxLeft -= speed;//往左移动
	if(maxLeft > -obj.offsetWidth){
		obj.style.left = maxLeft + 'px';
		requestAnimationFrame(function(){
		move(k,obj,maxLeft);
	});//H5新增的动画函数
	}else{
		init(obj);//重新初始化 营造循环弹幕效果
	  	/*  oShow.removeChild(obj);//DOM删除子节点 */
	}
}
//随机颜色函数
function randomColor(){
	return '#' + Math.random(12,16).toString(16).slice(-6);//一行简化版截取后六位
	/*var str = '#';
	for(var i = 0;i < 6;i++){
		str += Math.floor(Math.random() * 16).toString(16);
	}
	return str;*///普通逻辑版
}
//获取留言总人数
var a=new XMLHttpRequest();
a.onload=function(){
    console.log('请求完成');
    document.getElementById("msgCOUNT").innerHTML =eval("("+a.responseText+")").data.viewNum;
}
a.open('GET','http://42.192.172.214:8091/views',true);
a.setRequestHeader('Content-Type','application/json');
a.send(JSON.stringify({"page":0,"count":10}));
//获取五条留言形成弹幕
var b=new XMLHttpRequest();
b.onload=function(){
    console.log('请求完成');
    console.log(eval("("+b.responseText+")"));
    console.log(eval("("+b.responseText+")").data.msmShows[0].content);
    document.getElementById("magictime twisterInUp1").innerHTML =eval("("+b.responseText+")").data.msmShows[0].content;
document.getElementById("magictime twisterInUp2").innerHTML =eval("("+b.responseText+")").data.msmShows[1].content;
document.getElementById("magictime twisterInUp3").innerHTML =eval("("+b.responseText+")").data.msmShows[2].content;
document.getElementById("magictime twisterInUp4").innerHTML =eval("("+b.responseText+")").data.msmShows[3].content;
document.getElementById("magictime twisterInUp5").innerHTML =eval("("+b.responseText+")").data.msmShows[4].content;
};
b.open('GET','http://42.192.172.214:8091/msms',true);
b.setRequestHeader('Content-Type','application/json');
b.send(JSON.stringify({"page":0,"count":10}));



