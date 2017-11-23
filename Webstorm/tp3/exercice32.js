/**
 * Created by Nicolas on 15/01/2017.
 */
var myJsonFile;
var flag = true;
var count = 0;
var stopFlag = false;
var countAddFlag = false;
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
    if(myJsonFile!==undefined && count<5 && stopFlag===false)
    {
        urlShow();
        window.setTimeout(iframeShow, 1000*(myJsonFile.slides[count+1].time-myJsonFile.slides[count].time));
        count++;
        countAddFlag = true;
    }else
    window.setTimeout(iframeShow, 1);
}

function urlShow() {
    var myUrl = myJsonFile.slides[count].url;
    var myIframe = "<iframe style='height:100%;width:100%;margin:0;' src='"+myUrl+"'></iframe>";
    document.getElementById("MAIN").innerHTML = myUrl + myIframe;
}

function myNextShow() {
    if(countAddFlag===true){
        count--;
        countAddFlag=false;
    }
    if(count<4) {
        count++;
        urlShow();
    }else{
        alert("The End!");
    }
}
function myPreviousShow() {
    if(count>0) {
        if(countAddFlag===true){
            count--;
            countAddFlag=false;
        }
        count--;
        urlShow();
    }else{
        alert("The beginning!");
    }
}
function stopContinue(){
    stopFlag=!stopFlag;
}
