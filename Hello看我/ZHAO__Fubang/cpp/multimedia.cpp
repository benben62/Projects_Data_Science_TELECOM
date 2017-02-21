#include <iostream>
#include "multimedia.h"
using namespace std;
/**
 * @brief Multimedia
 * @param 
 * @return 
 * @detail initializer nomFichier, nomMedia without parameter
 */
Multimedia::Multimedia()
{
    nomFichier = "";
    nomMedia = "";
}
/**
 * @brief ~Multimedia
 * @param 
 * @return 
 * @detail Output the information of destruction
 */
Multimedia::~Multimedia()
{
    cout<<nomMedia<<" est destruit!"<<endl;
}
/**
 * @brief Multimedia
 * @param 
 * @return 
 * @detail initializer nomFichier, nomMedia with parameter
 */
Multimedia::Multimedia(string _nomF, string _nomM, string _nomC)
{
    nomFichier = _nomF;
    nomMedia = _nomM;
    classMedia = _nomC;
}
/**
 * @brief getnomF
 * @param 
 * @return nomFichier
 * @detail return nomFichier
 */
string Multimedia::getnomF() const
{
    return nomFichier;
}
/**
 * @brief getnomM
 * @param 
 * @return getnomM
 * @detail return nomMedia
 */
string Multimedia::getnomM() const
{
    return nomMedia;
}
/**
 * @brief setnomF
 * @param _nomF
 * @return 
 * @detail set the value of nomFichier
 */
void Multimedia::setnomF(string _nomF)
{
    nomFichier = _nomF;
}
/**
 * @brief setnomM
 * @param _nomM
 * @return 
 * @detail et the value of nomMedia
 */
void Multimedia::setnomM(string _nomM)
{
    nomMedia = _nomM;
}
/**
 * @brief display
 * @param 
 * @return
 * @detail show the information of the multimedia
 */
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
/**
 * @brief play
 * @param 
 * @return
 * @detail
 */
void Multimedia::play() const
{

}
/**
 * @brief getClassMedia
 * @param 
 * @return classMedia
 * @detail return classMedia
 */
string Multimedia::getClassMedia() const
{
    return classMedia;
}
