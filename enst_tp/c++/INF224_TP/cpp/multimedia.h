#ifndef MULTIMEDIA_H
#define MULTIMEDIA_H

#include <iostream>
using namespace std;
class Multimedia{
private:
    string nomMedia = "";
    string nomFichier = "";
protected:
    string classMedia = "";
public:
    Multimedia();
    Multimedia(string _nomF, string _nomM, string _nomC);
    virtual ~Multimedia();
    virtual string getnomF() const;
    virtual void setnomF(string _nomF);
    virtual string getnomM() const;
    virtual void setnomM(string _nomM);
    virtual void display(ostream& out, bool flag = false);
    virtual void play() const=0;
    virtual string getClassMedia() const;
};

#endif
