"use strict";
var https = require('https');
var fs = require('fs');
var path = require("path");
var url = require("url");
var list = [];
list = readMyFile();
/*
Avant on créer le serveur https, il faut saisir les codes suivantes dans le terminal:
    openssl genrsa -out key.pem
    openssl req -new -key key.pem -out csr.pem
    openssl x509 -req -days 9999 -in csr.pem -signkey key.pem -out cert.pem
    rm csr.pem

Ensuite, on change un peu sur les code de server1d.js. Par exemple, 
'var http = require('http');'==>'var https = require('https');''
*/
function myServer(request, response){
    var my_path = url.parse(request.url).pathname;
    var full_path = path.join(process.cwd(),my_path);
    var queryData = url.parse(request.url, true).query;
    if(my_path=="/"){
        response.writeHead(200, {'Content-Type': 'text/plain'});
        response.write("Welcome to the Home Page de Fubang");
        response.end();
    }
    else if (queryData.name) {
        var value = queryData.name.toString().replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/'/g, "&#39;").replace(/"/g, "&#34;");     
        list.push(value);
        writeMyFile(list);
        response.writeHead(200, {'Content-Type': 'text/html'});
        response.write("Bonjour " + value + ", les utilisateurs suivants ont déjà visités cette page: ");
        for (var i =0;i<list.length-1;i++){
            response.write(list[i]+", ");
        }
        response.write(list[list.length-1]);
        response.end();
    }
    else{
        fs.exists(full_path, function(exists){  
            if(!exists){  
                response.writeHeader(404, {"Content-Type": "text/plain"});    
                response.write("404 Not Found\n");    
                response.end();  
            }  
            else{  
                fs.readFile(full_path, "binary", function(err, file) {
                     if(err) {    
                         response.writeHeader(500, {"Content-Type": "text/plain"});    
                         response.write(err + "\n");    
                         response.end();    
                     
                     }    
                     else{console.log(my_path);
                        response.writeHeader(200);    
                        response.write(file, "binary");    
                        response.end();  
                    }  
                           
                });
            }  
        });
    }
}

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

var options = {
  key: fs.readFileSync('key.pem'),
  cert: fs.readFileSync('cert.pem')
};

https.createServer(options, function(request,response){
      myServer(request,response);
    }).listen(8000);







