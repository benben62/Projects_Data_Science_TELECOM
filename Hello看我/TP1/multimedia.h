#ifndef MULTIMEDIA_H
#define MULTIMEDIA_H

#include <iostream>
using namespace std;
class Multimedia{
private:
    string nomMedia="";
    string nomFichier="";
public:
    Multimedia();
    Multimedia(string _nomF, string _nomM);
    virtual ~Multimedia();
    virtual string getnomF() const;
    virtual void setnomF(string _nomF);
    virtual string getnomM() const;
    virtual void setnomM(string _nomM);
    virtual void display(ostream& out);
    virtual void play() const=0;
};

#endif
