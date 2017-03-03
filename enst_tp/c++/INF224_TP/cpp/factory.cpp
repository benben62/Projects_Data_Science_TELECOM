#include "factory.h"
/**
 * @brief Factory
 * @param 
 * @return 
 * @detail Create a ifstream and read the media in the "dict_m.txt"
 */
Factory::Factory()
{
    ifstream ifile;
    ifile.open("dict_m.txt",ios_base::in);
    this->readFile(ifile);
    ifile.close();
}
/**
 * @brief createPhoto
 * @param _nomF, _nomM, _latitude, _longitude
 * @return photo
 * @detail Create a photo with the parameters, return a smart pointer of photo
 */
PhotoPtr Factory::createPhoto(string _nomF, string _nomM, double _latitude, double _longitude)
{
    PhotoPtr photo(new Photo(_nomF,_nomM, _latitude, _longitude));
    dictm[_nomM] = photo;
    return photo;
}
/**
 * @brief createVideo
 * @param _nomF, _nomM, _duree
 * @return video
 * @detail Create a video with the parameters, return a smart pointer of video
 */
VideoPtr Factory::createVideo(string _nomF, string _nomM, int _duree)
{
    VideoPtr video(new Video(_nomF,_nomM, _duree));
    dictm[_nomM] = video;
    return video;
}
/**
 * @brief createFilm
 * @param _nomF, _nomM, _duree, *_chap, _num_chap
 * @return film
 * @detail Create a film with the parameters, return a smart pointer of film
 */
FilmPtr Factory::createFilm(string _nomF, string _nomM, int _duree, int *_chap, int _num_chap)
{
    FilmPtr film(new Film(_nomF, _nomM, _duree, _chap, _num_chap));
    dictm[_nomM] = film;
    return film;
}
/**
 * @brief createList
 * @param _name
 * @return listmedia
 * @detail Create a listmedia with the parameters, return a smart pointer of listmedia
 */
ListMediaPtr<MediaPtr> Factory::createList(string _name)
{
    ListMediaPtr<MediaPtr> listmedia(new ListMedia<MediaPtr>(_name));
    dictl[_name] = listmedia;
    return listmedia;
}
/**
 * @brief displayMediaByName
 * @param _nom, out
 * @return the smart pointer of found media
 * @detail show the information of a media(given a name of media)
 */
MediaPtr Factory::displayMediaByName(string _nom, ostream &out) const
{
    auto it = dictm.find(_nom);
    if(it == dictm.end())
        out<<_nom<<" doesn't exist!"<<endl;
    else
        it->second->display(out);
    return it->second;
}
/**
 * @brief displayGroupByName
 * @param _nom, out
 * @return the smart pointer of found group
 * @detail show the information of a group(given a name of group)
 */
ListMediaPtr<MediaPtr> Factory::displayGroupByName(string _nom, ostream &out) const
{
    auto it2 =dictl.find(_nom);
    if(it2 == dictl.end())
        out<<_nom<<" doesn't exist!"<<endl;
    else
        it2->second->display(out);
    return it2->second;
}
/**
 * @brief playByName
 * @param _nom
 * @return
 * @detail show a media(given a name of group)
 */
void Factory::playByName(string _nom) const
{
    auto it = dictm.find(_nom);
    if(it == dictm.end())
        cout<<_nom<<" doesn't exist!"<<endl;
    else
        it->second->play();
}
/**
 * @brief saveFile
 * @param fs
 * @return
 * @detail save the media into the ofstream
 */
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
/**
 * @brief readFile
 * @param fs
 * @return
 * @detail read the media into the ofstream
 */
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
/**
 * @brief displayAllMedia
 * @param out
 * @return
 * @detail display all the media
 */
void Factory::displayAllMedia(ostream &out)
{
    for (auto it :dictm){
        it.second->display(out);
        out<<"\n";
    }
}
