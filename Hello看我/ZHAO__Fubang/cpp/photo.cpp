#include "photo.h"

Photo::Photo(string _nomF, string _nomM, double _latitude, double _longitude):Multimedia(_nomF, _nomM, "Photo")
{
    latitude = _latitude;
    longitude = _longitude;
}

double Photo::getLatitude() const
{
    return latitude;
}

double Photo::getLongitude() const
{
    return longitude;
}
void Photo::setLatitude(double _latitude)
{
    latitude = _latitude;
}
void Photo::setLongitude(double _longitude)
{
    longitude = _longitude;
}
void Photo::display(ostream &out, bool flag)
{
    Multimedia::display(out, flag);
    if (flag)
        out<<','<<latitude<<','<<longitude;
    else
    {
        out<<"C'est la latitude de photo: "<<getLatitude()<<endl;
        out<<"C'est la longitude de photo: "<<getLongitude()<<endl;
    }
}
void Photo::play() const
{
    string str = "open -a preview "+getnomF()+getnomM()+" &";//for mac
    //string str = "imagej"+getnomF()+getnomM()+" &";//for lunix
    const char *command = str.c_str();
    system(command);
}
