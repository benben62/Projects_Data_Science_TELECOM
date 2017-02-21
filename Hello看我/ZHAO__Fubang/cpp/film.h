#ifndef FILM_H
#define FILM_H

#include <iostream>
#include "video.h"
using namespace std;

class Film : public Video
{
private:
    int *chap;
    int num_chap;
public:
    Film(string _nomF="", string _nomM="", int _duree=0, int *_chap=0, int _num_chap=0);
    virtual ~Film();
    virtual void display(ostream &out, bool flag = false);
    virtual void setChap(int *_chap);
    virtual int * getChap() const;
    virtual int getNumchap() const;
};

#endif // FILM_H
