var btns=document.getElementById('btnList').getElementsByTagName('li');
var divs=document.getElementsByClassName('main-container');
for(var i=0;i<btns.length;i++){
    btns[i].index=i;
    btns[i].onclick=function(){
        for(var i=0;i<btns.length;i++){
            divs[i].style.display='none';
        }//排他
        divs[this.index].style.display='block';
    }
}
var Btna=document.getElementById('bbb');
Btna.onclick=function(){
    divs[0].style.display='none';
    divs[1].style.display='block';
}