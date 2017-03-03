"use strict";
var express = require('express');
var app = express();
var fs = require('fs');
var list = [];
list = readMyFile();
/*
On utilise :language pour rendre le programme plus générique. 
Si on saisit "http://localhost:8000/hello/english?name=Fubang", 
il va afficher les informations en anglais, "francais" pour francais,
et tous les autres mots represente chinois.

De plus, lorsqu'on saisit "http://localhost:8000/hello?name=Fubang",
ça marche comme auparavant. Parce qu'on a utilisé le redirect
*/
app.use(express.static('public'));

app.get('/', function(request, response){
	response.send('Welcome to the Home Page de Fubang');
});


app.get('/hello', function(request, response){
    response.redirect(301, '/hello/francais?name=' + request.query.name);
});
app.get('/hello/:language', function(request, response){
	var value = request.query.name.toString().replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/'/g, "&#39;").replace(/"/g, "&#34;");     
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