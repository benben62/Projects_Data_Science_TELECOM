#ifndef FACTORY_H
#define FACTORY_H

#include <fstream>
#include <iostream>
#include <memory>
#include <string>
#include <sstream>
#include <vector>
#include <map>
#include "multimedia.h"
#include "video.h"
#include "photo.h"
#include "film.h"
#include "listmedia.h"
using namespace std;


typedef shared_ptr<int[]> intPtr;
typedef shared_ptr<Multimedia> MediaPtr;
typedef shared_ptr<Video> VideoPtr;
typedef shared_ptr<Photo> PhotoPtr;
typedef shared_ptr<Film> FilmPtr;
template <typename T>
using ListMediaPtr = shared_ptr<ListMedia<T>> ;

typedef map<string, MediaPtr> DictMedia;
template <typename T>
using DictList = map<string, ListMediaPtr<T>>;

class Factory
{
private:
    DictMedia dictm;
    DictList<MediaPtr> dictl;
public:
    Factory();
    virtual ~Factory(){}
    virtual PhotoPtr createPhoto(string _nomF="", string _nomM="", double _latitude=0, double _longitude=0);
    virtual VideoPtr createVideo(string _nomF="", string _nomM="", int _duree=0);
    virtual FilmPtr createFilm(string _nomF="", string _nomM="", int _duree=0, int *_chap=0, int _num_chap=0);
    virtual ListMediaPtr<MediaPtr> createList(string _name="");
    virtual MediaPtr displayMediaByName(string _nom, ostream &out) const;
    virtual ListMediaPtr<MediaPtr> displayGroupByName(string _nom, ostream &out) const;
    virtual void playByName(string _nom) const;
    virtual void readFile(ifstream &fs);
    virtual void saveFile(ofstream &fs);
    virtual void displayAllMedia(ostream &out);

};

#endif // FACTORY_H
