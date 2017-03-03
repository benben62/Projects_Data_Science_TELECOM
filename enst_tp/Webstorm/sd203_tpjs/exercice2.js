/**
 * Created by Nicolas on 10/12/2016.
 */
//question 2a

//a short test
t ="Give me a hug! No,no,no, I don't want a bug!";
function countWords(s) {
    s = s.toLocaleLowerCase();
    var str = s.split(/\W+/);
    var result1={};
    for(var i=0;i<str.length;i++) {
        if(result1[str[i]]===undefined){
            result1[str[i]]=1;
        }else {
            result1[str[i]]+=1;
        }
    }
    delete result1[""];
    return result1;
}
console.log("\n#question 2a |TEST|#");
console.log(countWords(t));

//question 2b
function WordList(str) {
    this.dict = function () {
        var s =str.toLocaleLowerCase().split(/\W+/);
        var result={};
        for(var i=0;i<s.length;i++) {
            if(result[s[i]]===undefined){
                result[s[i]]=1;
            }else {
                result[s[i]]+=1;
            }
        }
        delete result[""];
        return result;
    };
    this.maxCountWord = function () {
        var temp_max = 0;
        var mot_max;
        var a = this.dict();
        for(var i in a) {
            if(a.hasOwnProperty(i)) {
                if (this.dict()[i] >= temp_max) {
                    temp_max = this.dict()[i];
                    mot_max = i;
                } else {
                }
            }
        }
        return mot_max;
    };
    this.minCountWord = function () {
        var temp_min = Number.MAX_VALUE;
        var mot_min;
        var a = this.dict();
        for(var i in a) {
            if(!a.hasOwnProperty(i)) continue;
            if(this.dict()[i]<=temp_min){
                temp_min = this.dict()[i];
                mot_min = i;
            }else{}
        }
        return mot_min;
    };
    this.getWords = function () {
        var temp_wordlist = [];
        var a = this.dict();
        for(var i in a){
            if(!a.hasOwnProperty(i)) continue;
            temp_wordlist.push(i);

        }
        return temp_wordlist;
    };
    this.getCount= function(x){
        return this.dict()[x];
    };
}

var wl = new WordList(t);
console.log("\n#question 2b |TEST|#");
console.log("The max count word is: "+wl.maxCountWord());
console.log("The min count word is: "+wl.minCountWord());
console.log(wl.getWords());
console.log("The count of this world is: "+wl.getCount("no"));

//question 2c
WordList.prototype.applyWordFun = function (fun) {  //what is the difference between||
    var wordlist = this.getWords();
    return wordlist.map(fun);
};



//question 2d'
console.log("\n#question 2d |TEST|#");
console.log(wl.applyWordFun(function (x) {
    return {"mot":x, "nombre_de_occurence":wl.getCount(x), "longueur":x.length};

}));
