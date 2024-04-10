window.addEventListener('load', function() {
    var day = document.querySelector('.day')
    var hours = document.querySelector('.hours')
    var minutes = document.querySelector('.minutes')
    var seconds = document.querySelector('.seconds')

    function countDown(time) {
        var nowTime = +new Date(); // 返回的是当前时间总的毫秒数
        var inputTime = +new Date(time); // 返回的是用户输入时间总的毫秒数
        var times = (inputTime - nowTime) / 1000; // times是剩余时间总的秒数 
        var d = parseInt(times / 60 / 60 / 24).toString().padStart(2, '0') // 天
        var h = parseInt(times / 60 / 60 % 24).toString().padStart(2, '0') //时
        var m = parseInt(times / 60 % 60).toString().padStart(2, '0') // 分
        var s = parseInt(times % 60).toString().padStart(2, '0') // 当前的秒
        day.innerHTML = d
        hours.innerHTML = h
        minutes.innerHTML = m
        seconds.innerHTML = s
    }
    countDown('2022-5-6 00:00:00')
    setInterval(function() {
        countDown('2022-5-6 00:00:00')
    }, 1000)
})
getMessage();

function getMessage() {
    var midContainer = document.querySelector('.mid-container');
    var xhr = new XMLHttpRequest();
    xhr.onload = function() {
        if (xhr.status == 200) {
            var data = JSON.parse(xhr.responseText);
            // console.log(data);
            // var msgs = document.getElementById('msg');
                midContainer.innerHTML = data.data.msmShows.map((v, i) => {
                    return `
                    <div class="mid-container1" id="mid">
                        <div class="msg" id="msg">${v.content}</div>
                        <div class="info" id="info">
                            <div class="inform">姓名：${v.name}</div>
                            <div class="inform">学院：${v.iname}</div>
                            <div class="inform">时间：${v.date}</div>
                        </div>
                    </div>
                    `
                }).join('');
        }
    };
    xhr.open('GET', 'http://42.192.172.214:8091/msms', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify({
        "page": 0,
        "count": 10
    }));
}

//获取留言总人数
var xhr2=new XMLHttpRequest();
xhr2.onload=function(){
    document.getElementById("msgCount").innerHTML =eval("("+a.responseText+")").data.viewNum;
}
xhr2.open('GET','http://42.192.172.214:8091/views',true);
xhr2.setRequestHeader('Content-Type','application/json');
xhr2.send(JSON.stringify({"page":0,"count":10}));