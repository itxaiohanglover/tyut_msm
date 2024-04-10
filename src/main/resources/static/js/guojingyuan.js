//ajax请求
var text=document.getElementById('text');
var names=document.getElementById('name');
var classes=document.getElementById('classes');
var button=document.getElementById('button1');



button.onclick=function(e){
    e = e || window.event;
    e.preventDefault();
  
    var xhr=new XMLHttpRequest();
    var content=text.value;
    var name=names.value;
    var cname=classes.value;
    var iname=collegeSelect.value;
    var inputCode=document.getElementById("check").value;

    if (content === '' ) {
            alert('留言内容为空，必须填写留言内容！');
             return; //直接阻止，不再运行后边的代码
     }
     //校验验证码
    if(inputCode.length <= 0) {
        alert("请输入验证码！");
        return;
    }
    //toUpperCase() 方法用于把字符串转换为大写
    //toLowerCase（）方法用于把字符串转换为小写
    else if(inputCode.toUpperCase() != code.toUpperCase()) {
        alert("验证码输入有误！");
        createCode();
        return;
    }
    //发送post请求
    xhr.open('POST','http://42.192.172.214:8091//postData')        
    xhr.setRequestHeader('Content-Type', 'application/json',true); //请求头
    xhr.send(JSON.stringify({"iname":collegeSelect.value,"name":names.value,"cname":classes.value,"content":text.value}));
    xhr.onload=function(){
        if(xhr.status==200){
            console.log(xhr.responseText);
            alert('留言成功！');
        }
        
    }
}


//
// 学院选择其他时，显示文本输入框，否则不显示
//
const collegeSelect = document.getElementById("collegeSelect");
const collegeInput = document.getElementById("college");
const form4=document.getElementById('form4');


collegeSelect.onchange = function (){
    if(collegeSelect.value === "other") {
        collegeInput.type = "text";
        collegeInput.style.marginRight="25%";
        form4.style.marginTop="0.4%";
        form4.style.marginLeft='14%';
        collegeInput.value = "";
    } else {
        collegeInput.type = "hidden";
        collegeInput.value = collegeSelect.value;
    }
}


//验证码
createCode();
var code;  //定义一个变量，用来保存验证码
//生成验证码
function createCode() {
    code = "";
    var codeLength = 6; //验证码的长度
    var checkCode = document.getElementById("checkCode");
    var codeChars = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
        'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'); //所有候选组成验证码的字符，当然也可以用中文的
    for(var i = 0; i < codeLength; i++) {
        var charNum = Math.floor(Math.random() * codeChars.length);
        code += codeChars[charNum];
    }
    if(checkCode) {
        checkCode.innerHTML = code;
    }
}



