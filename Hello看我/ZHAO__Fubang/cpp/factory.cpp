#include "factory.h"

Factory::Factory()
{
    ifstream ifile;
    ifile.open("dict_m.txt",ios_base::in);
    this->readFile(ifile);
    ifile.close();
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
MediaPtr Factory::displayMediaByName(string _nom, ostream &out) const
{
    auto it = dictm.find(_nom);
    if(it == dictm.end())
        out<<_nom<<" doesn't exist!"<<endl;
    else
        it->second->display(out);
    return it->second;
}
ListMediaPtr<MediaPtr> Factory::displayGroupByName(string _nom, ostream &out) const
{
    auto it2 =dictl.find(_nom);
    if(it2 == dictl.end())
        out<<_nom<<" doesn't exist!"<<endl;
    else
        it2->second->display(out);
    return it2->second;
}
void Factory::playByName(string _nom) const
{
    auto it = dictm.find(_nom);
    if(it == dictm.end())
        cout<<_nom<<" doesn't exist!"<<endl;
    else
        it->second->play();
}
void Factory::saveFile(ofstream &fs)
{
    if (fs.is_open())
    {
        if(dictm.size()){
            for(auto it = dictm.begin(); it != dictm.end(); it++){
                it->second->display(fs, true);
                fs<<';';
            }
        }
    }
}
void Factory::readFile(ifstream &fs)
{
    stringstream ss;
    ss.str("");
    ss.clear();
    string data = "";
    vector<string> command;
    command.clear();
    string param = "";
    while (getline(fs, data, ';'))
    {
        ss.clear();
        ss.str(data);
        command.clear();
        while (getline(ss, param, ','))
        {
            command.push_back(param);
        }
        if (command.at(0) == "Photo")
        {
            this->createPhoto(command.at(1), command.at(2), stod(command.at(3)), stod(command.at(4)));
        }
        else if (command.at(0) == "Video")
        {
            this->createVideo(command.at(1), command.at(2), stoi(command.at(3)));
        }
        else if (command.at(0) == "Film")
        {
            int list_size = stoi(command.at(4));
            int *list = new int[list_size];
            for(int i=5;i<list_size+5;i++)
            {
              list[i-5] = stoi(command.at(i));
            }
            this->createFilm(command.at(1), command.at(2), stoi(command.at(3)), list, list_size);
            delete []list;
            list = nullptr;
        }
        else
        {

        }
    }
}
void Factory::displayAllMedia(ostream &out)
{
    for (auto it :dictm){
        it.second->display(out);
    }
}
