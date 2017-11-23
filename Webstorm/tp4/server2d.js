"use strict";
var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var app = express();
var fs = require('fs');
var list = [];
list = readMyFile();
var ejs = require('ejs');
app.set('view engine', 'ejs');
/*
On rédefinit '/hello' pour affichier le tableau des personnes 
ayant utilisées la page et la langue utilisée.

Pour éviter le problem de combiner les code de js et html ensemble
dans le code de js, on utilise le module 'ejs'. Donc, on peut juste
utiliser 'response.render()' pour passer le paramétre au le fichier
de ejs.

Dans le 'hello.ejs', on utilise
      <% visiters.forEach(function(visiter) { %>
        <tr>
              <td><%= visiter.name %></td>
              <td><%= visiter.language %></td>
        </tr>
      <% }); %>
qui est très simple et pratique pour affichier un html avec les paramètres.

De plus, pour realiser cette fonction, il faut modifier les fonctions de 
read et write.
Donc, on met tous les données dans le fichier 'data2.json'.
La donnée est comme
{"visiters":[{"name":"Nicolas","language":"francais"},...]}

*/
var urlencodedParser = bodyParser.urlencoded({ extended: true });
//now we can open the documents in public
app.use(express.static('public'));

app.get('/', function(request, response){
    response.send('Welcome to the Home Page de Fubang');
});

//fonction: affichier un tableau
app.get('/hello', function(request, response){
    response.render('hello', {
      visiters: list
    });
});

app.post('/hello/:language', urlencodedParser, function(request, response){
    if (!request.body) return response.sendStatus(400);
    var value = request.body.name.toString().replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/'/g, "&#39;").replace(/"/g, "&#34;");     
    var obj = {'name': value, 'language': request.params.language};
    list.push(obj);
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
        response.write(list[i].name+", ");
    }
    response.write(list[list.length-1].name);
    response.end();
});

var server = app.listen(8000, '127.0.0.1');
console.log('Server running on port 8000.');

function readMyFile(){
    var temp = require('./data2.json');
    return temp.visiters;
}

function writeMyFile(list){
    var vlist = {};
    vlist.visiters = list;
    var json = JSON.stringify(vlist);
    fs.writeFile('./data2.json', json);
}