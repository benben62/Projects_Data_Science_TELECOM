"use strict";
var express = require('express');
var app = express();
var fs = require('fs');
var list = [];
list = readMyFile();
/*
On utilise app.get() pour choisir la reponse.

En particulier, on utilise que 'static' au lieu
de le programme de server1d qui est très long.
Mais, il faut mettre les dossiers qu'on veut ouvrir dans le fichier public
*/
app.use(express.static('public'));

app.get('/', function(request, response){
	response.send('Welcome to the Home Page de Fubang');
});

app.get('/hello', function(request, response){
	var value = request.query.name.toString().replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/'/g, "&#39;").replace(/"/g, "&#34;");     
	list.push(value);
	writeMyFile(list);
	response.writeHead(200, {'Content-Type': 'text/html'});
    response.write("Bonjour " + value + ", les utilisateurs suivants ont déjà visités cette page: ");
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