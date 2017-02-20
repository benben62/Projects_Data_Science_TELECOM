#include <iostream>
#include "multimedia.h"
using namespace std;

Multimedia::Multimedia()
{
    nomFichier = "";
    nomMedia = "";
}
Multimedia::~Multimedia()
{
    cout<<nomMedia<<" est destruit!"<<endl;
}
Multimedia::Multimedia(string _nomF, string _nomM, string _nomC)
{
    nomFichier = _nomF;
    nomMedia = _nomM;
    classMedia = _nomC;
}
string Multimedia::getnomF() const
{
    return nomFichier;
}
string Multimedia::getnomM() const
{
    return nomMedia;
}
void Multimedia::setnomF(string _nomF)
{
    nomFichier = _nomF;
}
void Multimedia::setnomM(string _nomM)
{
    nomMedia = _nomM;
}
void Multimedia::display(ostream& out, bool flag)
{
    if (flag)
        out<<getClassMedia()<<','<<nomFichier<<','<<nomMedia;
    else
    {
        out<<"C'est le nom de fichier: "<<nomFichier<<endl;
        out<<"C'est le nom de media: "<<nomMedia<<endl;
    }
}
void Multimedia::play() const
{

}
string Multimedia::getClassMedia() const
{
    return classMedia;
}
