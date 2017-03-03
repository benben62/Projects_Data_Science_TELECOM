/**
 * Created by Nicolas on 13/01/2017.
 */
function mySend() {
    var text = document.getElementById("phrase").value;
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "chat.php?phrase="+text);//the absolute url is http://perso.telecom-paristech.fr/~fzhao/tp3/NOM——FICHER.html
    xhr.send();
    document.getElementById("phrase").value = "";
}
var arraySepIps = [];
var arrayColors = [];
function myShow() {
    var div = document.getElementById("div1");
    var xhr = new XMLHttpRequest();
    var regExp = /<br \/>- <a \S+<\/a> - /g;
    xhr.open("GET", "texte.html");//the absolute url is http://perso.telecom-paristech.fr/~fzhao/tp3/NOM——FICHER.html
    xhr.onload = function(){
        div.textContent = "";
        var arrayIps = this.responseText.match(regExp);
        var arrayAnswer =this.responseText.split(regExp);
        var begin = 0;
        if(arrayIps.length>=10){
            begin = arrayIps.length-10;
        }else{
            begin = 0;
        }
        for(var i = begin; i<arrayIps.length;i++){
            var p = document.createElement("p");
            p.innerHTML = arrayIps[i]+arrayAnswer[i+1];
            if(arraySepIps.indexOf(arrayIps[i])==-1){
                arraySepIps.push(arrayIps[i]);
                var myColor = getRandomColor();
                arrayColors.push(myColor);
                p.style.color = myColor;
            }else
            {
                p.style.color = arrayColors[arraySepIps.indexOf(arrayIps[i])];
            }
            div.appendChild(p);
        }
    };
    xhr.send();
    window.setTimeout("myShow()",1);
}


function getRandomColor(){
    return '#'+Math.floor(Math.random()*16777215).toString(16);
}


function EnterPress(_e){
    var e = _e || window.event;
    if(e.keyCode == 13) {
        document.getElementById("send").click();
    }
}
document.onkeydown = EnterPress;
