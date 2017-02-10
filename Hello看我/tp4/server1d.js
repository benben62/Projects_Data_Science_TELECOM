"use strict";
var http = require('http');
var fs = require('fs');
var path = require("path");
var url = require("url");
var list = new Array();

http.createServer(function (request, response){
    var my_path = url.parse(request.url).pathname;
    var full_path = path.join(process.cwd(),my_path);
    var queryData = url.parse(request.url, true).query;
    if(my_path=="/"){
        response.writeHead(200, {'Content-Type': 'text/plain'});
        response.write("Hello World");
        response.end();
    }
    else if (queryData.name) {
        list.push(queryData.name );
        response.writeHead(200, {'Content-Type': 'text/html'});
        response.write("Bonjour " + queryData.name + ", les utilisateurs suivants ont déjà visités cette page: ");
        for (var i =0;i<list.length-1;i++){
            response.write(list[i]+", ");
        }
        response.write(list[list.length-1]+".");
        response.end();
    }
    else{
        fs.exists(full_path,function(exists){  
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