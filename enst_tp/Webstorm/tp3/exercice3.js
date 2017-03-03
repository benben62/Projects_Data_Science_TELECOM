/**
 * Created by Nicolas on 15/01/2017.
 */
var myJsonFile;
var flag = true;
var count = 0;
function myReadJson() {

    var xhr = new XMLHttpRequest();
    xhr.open("GET", "slides.json");//the absolute url is http://perso.telecom-paristech.fr/~fzhao/tp3/NOM——FICHER.html
    xhr.onload = function () {
        myJsonFile = JSON.parse(this.responseText);
        flag = false;
    };
    xhr.send();
}

function myshow(){
    if(flag===true){
        myReadJson();
    }
    iframeShow();

}

function iframeShow() {
    if(myJsonFile!==undefined&&count<5)
    {
        var myUrl = myJsonFile.slides[count].url;
        var myIframe = "<iframe style='height:100%;width:100%;margin:0;' src='"+myUrl+"'></iframe>";
        document.getElementById("MAIN").innerHTML = myUrl + myIframe;
        window.setTimeout(iframeShow, 1000*(myJsonFile.slides[count+1].time-myJsonFile.slides[count].time));
        count++;
    }else
        window.setTimeout(iframeShow, 1);
}
