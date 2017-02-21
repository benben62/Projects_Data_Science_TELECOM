#ifndef VIDEO_H
#define VIDEO_H

#include <iostream>
#include "multimedia.h"
using namespace std;

class Video : public Multimedia
{
private:
    int duree=0;
public:
    Video(string _nomF="", string _nomM="", int _duree=0);
    virtual ~Video(){}
    virtual int getDuree() const;
    virtual void setDuree(int _duree);
    virtual void display(ostream& out, bool flag = false);
    virtual void play() const;
};

#endif
