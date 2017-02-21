#include "video.h"

Video::Video(string _nomF, string _nomM, int _duree):Multimedia(_nomF, _nomM, "Video")
{
    duree = _duree;
}
int Video::getDuree() const
{
    return duree;
}
void Video::setDuree(int _duree)
{
    duree=_duree;
}
void Video::display(ostream &out, bool flag)
{
    Multimedia::display(out, flag);
    if (flag)
        out<<','<<duree;
    else
        out<<"C'est la duree de video: "<<duree<<endl;
}
void Video::play() const
{
    //string str = "open -a mpv "+getnomF()+getnomM()+" &";//for mac
    string str = "mpv "+getnomF()+getnomM()+" &";//for lunix
    const char *command = str.c_str();
    system(command);
}
