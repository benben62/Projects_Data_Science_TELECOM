#ifndef PHOTO_H
#define PHOTO_H

#include <iostream>
#include "multimedia.h"
using namespace std;
class Photo : public Multimedia
{
private:
    double latitude = 0;
    double longitude = 0;
public:
    Photo(string _nomF="", string _nomM="", double _latitude=0, double _longitude=0);
    virtual ~Photo(){} //虚析构函数
    virtual double getLatitude() const;
    virtual void setLatitude(double _latitude);
    virtual double getLongitude() const;
    virtual void setLongitude(double _longitude);
    virtual void display(ostream& out, bool flag = false);
    virtual void play() const;
};

#endif
