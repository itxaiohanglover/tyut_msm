//刚开始显示的数据
const tbody=document.querySelector('tbody');
    var xhr=new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.status==200){
            var data=JSON.parse(xhr.responseText);
            tbody.innerHTML=data.data.collegeRank.map((v,i)=>{
                return `
                    <tr>
                        <td>${v.iname}</td>
                        <td></td>
                        <td>${v.icount}</td>
                    </tr>
                `
            }).join('');
        }
    }
    xhr.open('GET','http://42.192.172.214:8091/getCollegeRank',true);
    xhr.send();
//刚开始显示的表格头
var classify=document.getElementById('classify');
var trTitle=classify.children;
trTitle[0].innerHTML='<td><h4>'+'学院'+'<h4><td>';
trTitle[0].innerHTML+='<td><h4>'+'留言数'+'<h4><td>';

//获取点击盒子
var colleges=document.getElementById('colleges');
var classes=document.getElementById('classes');

//学院排名盒子点击事件
colleges.onclick=function(){
    trTitle[0].innerHTML='<td><h4>'+'学院'+'<h4><td>';
    trTitle[0].innerHTML+='<td><h4>'+'留言数'+'<h4><td>';
    const tbody=document.querySelector('tbody');
    var xhr=new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.status==200){
            var data=JSON.parse(xhr.responseText);
            // console.log(data);
            // console.log(data.data.collegeRank);
            tbody.innerHTML=data.data.collegeRank.map((v,i)=>{
                return `
                    <tr>
                        <td>${v.iname}</td>
                        <td></td>
                        <td>${v.icount}</td>
                    </tr>
                `
            }).join('');
        }
    }
    xhr.open('GET','http://42.192.172.214:8091/getCollegeRank',true);
    xhr.send();
}

//班级排名盒子点击事件
classes.onclick=function(){
    trTitle[0].innerHTML='<td><h4>'+'班级'+'<h4><td>';
    trTitle[0].innerHTML+='<td><h4>'+'留言数'+'<h4><td>';
    const tbody=document.querySelector('tbody');
    var xhr=new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.status==200){
            var data=JSON.parse(xhr.responseText);
            // console.log(data);
            // console.log(data.data.classRank);
            tbody.innerHTML=data.data.classRank.map((v,i)=>{
                return `
                    <tr>
                        <td>${v.cname}</td>
                        <td></td>
                        <td>${v.ccount}</td>
                    </tr>
                `
            }).join('');
        }
    }
    xhr.open('GET','http://42.192.172.214:8091/getClassRank',true);
    xhr.send();
}

//网页刷新计时器
fnTime();
function fnTime(){
    var myTime=new Date();
    var year=myTime.getFullYear();
    var month=myTime.getMonth();
    var date=myTime.getDate();
    var hour=myTime.getHours()
    var minute=myTime.getMinutes();
    var second=myTime.getSeconds();

    var str='';
    str='排名实时统计，当前榜单更新时间:'+year+'年'+toTwo(month+1)+'月'+toTwo(date)+'日'+toTwo(hour)+':'+toTwo(minute)+':'+toTwo(second);
    oP.innerHTML=str;
}

function toTwo(n){
    if(n<10){
        return '0'+n;
    }else{
        return ''+n;
    }
}

//page2校庆倒计时
var countdown=document.getElementById('countdown');
var iNew=new Date('May 6,2022 0:0:0');
var iNow=null;
var t=0;
var str='';
var timer=null;
countDown();
function countDown(){
    clearInterval(timer);
    timer=setInterval(function(){
        iNow=new Date();
        t=Math.floor((iNew-iNow)/1000);
        if(t>=0){
            str=' '+Math.floor(t/86400)+'天'+' '+Math.floor(t%86400/3600)+'时'+' '+Math.floor(t%86400%3600/60)+'分'+' '+t%60+'秒';
            countdown.innerHTML=str;
        }else{
            clearInterval(timer);
        }
    },1000);
}


//page4校庆倒计时
var timedown=document.getElementById('timedown');
var iNew=new Date('May 6,2022 0:0:0');
var iNow=null;
var t=0;
var str='';
var timer=null;
timeDown();
function timeDown(){
    clearInterval(timer);
    timer=setInterval(function(){
        iNow=new Date();
        t=Math.floor((iNew-iNow)/1000);
        if(t>=0){
            str=' '+Math.floor(t/86400)+'天'+' '+Math.floor(t%86400/3600)+'时'+' '+Math.floor(t%86400%3600/60)+'分'+' '+t%60+'秒';
            timedown.innerHTML=str;
        }else{
            clearInterval(timer);
        }
    },1000);
}

//allpage浏览量与留言数
var xhr2=new XMLHttpRequest();
xhr2.onload=function(){
    // console.log(xhr2.responseText);
    if(xhr2.status==200){
        var data=JSON.parse(xhr2.responseText);
        // console.log(data);
        var views=document.getElementsByClassName('stop');
        for(var i=0;i<views.length;i++){
            views[i].innerHTML='统计信息：浏览'+data.data.viewNum+'次&nbsp;&nbsp;留言'+data.data.countMsm+'条';
        }
    }
}
xhr2.open('GET','http://42.192.172.214:8091/views',true);
xhr2.send();