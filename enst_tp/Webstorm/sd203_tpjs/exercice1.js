/**
 * Created by Nicolas on 10/12/2016.
 */
// question 1a
function factoriellt(n){
    var temp = 1;
    for(var x=n;x>1;x--)
    {
        temp *=x;
    }
    return temp;
}
console.log("\n#question 1a |TEST|#");
console.log(factoriellt(5));

//question 1b
function factorielRec(n){
    if(n==1){
        return 1;
    } else return n*factorielRec(n-1);
}
console.log("\n#question 1b |TEST|#");
console.log(factorielRec(5));

//question 1c
function factorielTableau(tab){
    var temp=[];
    for(var i=0;i<tab.length;i++)
    {
        temp.push(factoriellt(tab[i]));
    }
    return temp;
}
console.log("\n#question 1c |TEST|#");
console.log(factorielTableau([1,2,3,4,5]));

//question 1d
var tab2 = [1,2,3,4,5];
console.log("\n#question 1d |TEST|#");
console.log(tab2.map(factoriellt));
