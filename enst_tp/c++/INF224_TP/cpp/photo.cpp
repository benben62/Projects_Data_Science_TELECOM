#include "photo.h"

/**
 * @brief Multimedia
 * @param 
 * @return 
 * @detail initializer nomFichier, nomMedia with parameter and inherit the constructer of multimedia
 */
Photo::Photo(string _nomF, string _nomM, double _latitude, double _longitude):Multimedia(_nomF, _nomM, "Photo")
{
    latitude = _latitude;
    longitude = _longitude;
}
/**
 * @brief getLatitude
 * @param 
 * @return latitude
 * @detail get the value of latitude
 */
double Photo::getLatitude() const
{
    return latitude;
}
/**
 * @brief getLongitude
 * @param 
 * @return longitude
 * @detail get the value of longitude
 */
double Photo::getLongitude() const
{
    return longitude;
}
/**
 * @brief setLatitude
 * @param _latitude
 * @return 
 * @detail set the value of latitude
 */
void Photo::setLatitude(double _latitude)
{
    latitude = _latitude;
}
/**
 * @brief setLongitude
 * @param _longitude
 * @return 
 * @detail set the value of longitude
 */
void Photo::setLongitude(double _longitude)
{
    longitude = _longitude;
}
/**
 * @brief display
 * @param out, flag
 * @return
 * @detail show the information of the photo, flag is to
 *         judge if it is used for cout or server
 */
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
/**
 * @brief play
 * @param
 * @return
 * @detail show the photo
 */
void Photo::play() const
{
    string str = "imagej "+getnomF()+getnomM()+" &";//for lunix
    const char *command = str.c_str();
    system(command);
}
