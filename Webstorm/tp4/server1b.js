"use strict";
var http = require('http');
var fs = require('fs');
var path = require("path");
var url = require("url");

/* 
Premièrement, j'ai utilisé "url.parse(request.url).pathname" pour avoir le url de request, si c'est "/", on renvoye le home page!
Sinon, on utilise fs.exists pour judger si le fichier existe. s'il existe, on affiche le ficher, sinon, on affiche"404".

On choit Asynchronous, parce qu'il peut traiter les operations sans attendre le complet de requêtes précédent
*/
http.createServer(function (request, response){
    var my_path = url.parse(request.url).pathname;
    var full_path = path.join(process.cwd(),my_path);
    if(my_path=="/"){
    	response.writeHead(200, {'Content-Type': 'text/plain'});
 		response.write("Welcome to the Home Page de Fubang");
    	response.end();
    }
    else{
	    fs.exists(full_path,function(exists){
	    	//if fs does not exist, we response '404 not found'
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
}).listen(8000, '127.0.0.1');

console.log('Server running on port 8000.');
