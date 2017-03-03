/**
 * Created by Nicolas on 11/01/2017.
 */
function loadDoc(){
    var body = document.getElementsByTagName("body")[0];
    var obj = document.createElement("textarea");
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "text.txt");//the absolute url is http://perso.telecom-paristech.fr/~fzhao/tp3/NOM——FICHER.html
    xhr.onload = function() {
        obj.textContent = this.responseText;
        obj.style.cssText = "width:500px; height:300px";
        body.appendChild(obj);
    };
    xhr.send();
}

function loadDoc2(){
    var body = document.getElementsByTagName("body")[0];
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "text.txt");

    xhr.onload = function() {
        var line = this.responseText.split('\n');
        for(var i=0;i<line.length;i++){
            var p = document.createElement("p");
            p.textContent = line[i];
            p.style.color = getRandomColor();
            body.appendChild(p);
        }
    };
    xhr.send();

}

function getRandomColor(){
    return '#'+Math.floor(Math.random()*16777215).toString(16);
}
