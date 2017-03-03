/**
 * Created by Nicolas on 14/12/2016.
 */
//question 3a
var Student = function (name, firstname, id) {
    this.name = name;
    this.firstname = firstname;
    this.id = id;
};
var student = new Student("Dupond", "Jean", 1835);
console.log("\n#question 3a |TEST|#");
console.log(student);

//question 3b
Student.prototype.display = function () {
    console.log("student: "+this.name+", "+this.firstname+", "+this.id);
};//继承的类的prototype.method不能重写this.method
console.log("\n#question 3b |TEST|#");
student.display();

//question 3c
var ForeignStudent = function (name, firstname, id, nation) {
    Student.call(this, name, firstname, id);
    this.nation = nation;
};
ForeignStudent.prototype = new Student();
ForeignStudent.prototype.display = function () {
    console.log("student: "+this.name+", "+this.firstname+", "+this.id+", "+this.nation);
};
console.log("\n#question 3c |TEST|#");
var foreignstudent = new ForeignStudent("Doe", "John", 432, "American");
foreignstudent.display();

//queestion 3d
var exportStudent = (function () {
    //public
    var myTemp = {};
    myTemp.newStudent = function (name, firstname, id) {
        return new Student(name, firstname, id);
    };
    myTemp.newForeignStudent = function (name, firstname, id, nation) {
        return new ForeignStudent(name, firstname, id, nation);
    };
    return myTemp;

})();

/**
 * module一定要把所有method return 出来， 不然就得要new 才能调用，匿名封包的好处在于对于exportStudent这类的工厂函数无需每次都new一个
 *新的exportStudent，节约空间
 */

console.log("\n#question 3d |TEST|#");
exportStudent.newStudent("Doe", "John", 222).display();


//question 3d
var Promotion = (function () {
    //private
    var slist={};
    var myTemp = {};

    //public
    myTemp.add = function (_name, _firstname, _id, _nation) {
        if(slist[_id]!==undefined) {
            console.log("Add failed! There is already a student with the same id. Please check your input!");
            return false;
        }
        if(_nation===undefined) slist[_id] = (exportStudent.newStudent(_name, _firstname, _id));
        else slist[_id] = exportStudent.newForeignStudent(_name, _firstname, _id, _nation);
        console.log("Add successful!");
        return true;
    };
    myTemp.remove = function (_id) {
        if(slist[_id]===undefined){
            console.log("Remove failed! There is not a student with this id. Please check your input!");
            return false;
        }
        delete slist[_id];
        console.log("Remove successful!");
        return true;
    };
    myTemp.list = function () {
        return slist;
    };
    // realizer non seulement lire les mots, mais aussi recréer les objets. donc on peut utiliser les fonctions
    // des objets Student et ForeignStudent
    myTemp.readFromFile = function (file) {
        var temp = require(file);
        slist = {};
        for(var i in temp)
        {
            if(!temp.hasOwnProperty(i)) continue;
            if(temp[i].nation===undefined) slist[i] = (exportStudent.newStudent(temp[i].name, temp[i].firstname, temp[i].id));
            else slist[i] = exportStudent.newForeignStudent(temp[i].name, temp[i].firstname, temp[i].id, temp[i].nation);
        }
        console.log("Read successful!");
        return true;
    };
    myTemp.saveToFile = function (file) {
        var fs = require('fs');
        var json = JSON.stringify(slist);
        fs.writeFile(file, json);
        console.log("Save successful!");
    };
    return myTemp;
})();
Promotion.add("Doe", "John", 111);          //add success
Promotion.add("Do", "do", 111);             //add fail
Promotion.add("ZHA", "Jun", 121, "jjj");    //add success
Promotion.list()["111"].display();          //student: Doe, John, 111
Promotion.remove(111);                      //remove success
Promotion.readFromFile("./data.json");      //Read successful!
Promotion.list()["2"].display();            //student: ZHAO, B, 2, kkk
Promotion.add("Doe", "d", 5);               //Add failed! There is already a student with the same id. Please check your input!
Promotion.add("ZHAO", "Fubang", 100);       //change the number each time execute, so it will show add success!
Promotion.saveToFile('./data.json');        //Save successful!



