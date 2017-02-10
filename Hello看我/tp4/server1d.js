"use strict";
var http = require('http');
var fs = require('fs');
var path = require("path");
var url = require("url");
/*

*/
http.createServer(function (request, response){
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
        writeMyFile(value);
        response.writeHead(200, {'Content-Type': 'text/html'});
        response.write("Bonjour " + value + ", les utilisateurs suivants ont déjà visités cette page: ");
        response.write(readMyFile());
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

function readMyFile(){
    fs = require('jsonfile');
    fs.writeFile()
};

function writeMyFile(str){
    var f = new File("data.txt");
    f.open("w");
    f.writeln(str+", ");
    f.close();
}