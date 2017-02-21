#include "video.h"
/**
 * @brief Video
 * @param 
 * @return 
 * @detail initializer nomFichier, nomMedia, duree with parameter and inherit the constructer of multimedia
 */
Video::Video(string _nomF, string _nomM, int _duree):Multimedia(_nomF, _nomM, "Video")
{
    duree = _duree;
}
/**
 * @brief getDuree
 * @param 
 * @return duree
 * @detail get the value of duree
 */
int Video::getDuree() const
{
    return duree;
}
/**
 * @brief setDuree
 * @param _duree
 * @return 
 * @detail set the value of duree
 */
void Video::setDuree(int _duree)
{
    duree=_duree;
}
/**
 * @brief display
 * @param out, flag
 * @return
 * @detail show the information of the video, flag is to
 *         judge if it is used for cout or server
 */
void Video::display(ostream &out, bool flag)
{
    Multimedia::display(out, flag);
    if (flag)
        out<<','<<duree;
    else
        out<<"C'est la duree de video: "<<duree<<endl;
}
/**
 * @brief play
 * @param
 * @return
 * @detail show the video
 */
void Video::play() const
{
    //string str = "open -a mpv "+getnomF()+getnomM()+" &";//for mac
    string str = "mpv "+getnomF()+getnomM()+" &";//for lunix
    const char *command = str.c_str();
    system(command);
}
