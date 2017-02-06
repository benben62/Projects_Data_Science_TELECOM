#include "factory.h"

Factory::Factory()
{

}
PhotoPtr Factory::createPhoto(string _nomF, string _nomM, double _latitude, double _longitude)
{
    PhotoPtr photo(new Photo(_nomF,_nomM, _latitude, _longitude));
    dictm[_nomM] = photo;
    return photo;
}
VideoPtr Factory::createVideo(string _nomF, string _nomM, int _duree)
{
    VideoPtr video(new Video(_nomF,_nomM, _duree));
    dictm[_nomM] = video;
    return video;
}
FilmPtr Factory::createFilm(string _nomF, string _nomM, int _duree, int *_chap, int _num_chap)
{
    FilmPtr film(new Film(_nomF, _nomM, _duree, _chap, _num_chap));
    dictm[_nomM] = film;
    return film;
}
ListMediaPtr<MediaPtr> Factory::createList(string _name)
{
    ListMediaPtr<MediaPtr> listmedia(new ListMedia<MediaPtr>(_name));
    dictl[_name] = listmedia;
    return listmedia;
}
void Factory::displayByName(string _nom, ostream &out) const
{
    auto it = dictm.find(_nom);
    auto it2 =dictl.find(_nom);
    if(it == dictm.end()&&it2 == dictl.end())
        out<<_nom<<" doesn't exist!"<<endl;
    else if(it != dictm.end()&&it2 !=dictl.end()){
        it->second->display(out);
        it2->second->display(out);
    }
    else if(it != dictm.end())
        it->second->display(out);
    else
        it2->second->display(out);
}
void Factory::playByName(string _nom) const
{
    auto it = dictm.find(_nom);
   if(it == dictm.end())
       cout<<_nom<<" doesn't exist!"<<endl;
   else
       it->second->play();
}
