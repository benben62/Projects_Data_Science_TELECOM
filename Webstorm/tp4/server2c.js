"use strict";
var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var app = express();
var fs = require('fs');
var list = [];
list = readMyFile();

/*
Pour injection Javascript, il n'y a pas de problem avec body-parser.
Mais pour injection html, ça marche pas.
Donc, on fait la même chose comme execice1, c'est à dire replacer '<>'.

D'ailleurs, car il n'y a pas de redirect pour POST, donc on peut juste 
annuler cette fonction.

On peut utiliser exercice2c.html pour tester les fonctions.
*/
var urlencodedParser = bodyParser.urlencoded({ extended: true });
//now we can open the documents in public
app.use(express.static('public'));

app.get('/', function(request, response){
    response.send('Welcome to the Home Page de Fubang');
});

app.post('/hello/:language', urlencodedParser, function(request, response){
    if (!request.body) return response.sendStatus(400);
    var value = request.body.name.toString().replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/'/g, "&#39;").replace(/"/g, "&#34;");     
    list.push(value);
    writeMyFile(list);
    response.writeHead(200, {'Content-Type': 'text/html'});
    if(request.params.language=='francais'){
        response.write("Bonjour " + value + ", les utilisateurs suivants ont déjà visités cette page: ");
    }else if (request.params.language=='english') {
        response.write("Hello " + value + ", the following users have visited this page: ");        
    }else{
        response.write("Nihao " + value + ", Xia mian de you ke fang wen guo gai wang zhan: ");        
    }
    for (var i =0;i<list.length-1;i++){
        response.write(list[i]+", ");
    }
    response.write(list[list.length-1]);
    response.end();
});

app.listen(8000, '127.0.0.1');
console.log('Server running on port 8000.');

function readMyFile(){
    var temp = require('./data.json');
    return temp.visiters;
}

function writeMyFile(list){
    var vlist = {};
    vlist.visiters = list;
    var json = JSON.stringify(vlist);
    fs.writeFile('./data.json', json);
}