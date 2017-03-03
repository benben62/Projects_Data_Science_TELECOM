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
function myShow() {
    var p = document.getElementById("p1");
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "texte.html");
    xhr.onload = function(){
        p.innerHTML = this.responseText;
    };
    xhr.send();
    window.setTimeout("myShow()",1);
}

function EnterPress(_e){
    var e = _e || window.event;
    if(e.keyCode == 13) {
        document.getElementById("send").click();
    }
}
document.onkeydown = EnterPress;
