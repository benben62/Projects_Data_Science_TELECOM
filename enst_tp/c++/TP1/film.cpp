#include "film.h"

Film::Film(string _nomF, string _nomM, int _duree, int *_chap, int _num_chap):Video(_nomF, _nomM, _duree)
{
    classMedia = "Film";
    chap = new int[_num_chap];
    for(int i=0;i<_num_chap;i++)
    {
        chap[i] = _chap[i];
    }
    num_chap = _num_chap;
}
Film::~Film()
{
    delete []chap;
}
void Film::display(ostream &out, bool flag)
{
    Video::display(out, flag);
    if (flag)
    {
        out<<','<<num_chap;
        for(int i=0;i<num_chap;i++)
        {
            out<<","<<chap[i];
        }
    }
    else
    {
        out<<"C'est les chaptres de video: ";
        for(int i=0;i<num_chap;i++)
        {
            out<<chap[i]<<" ";
        }
        out<<endl;
    }
}
void Film::setChap(int *_chap)
{
    chap = new int[num_chap];
    for(int i=0;i<num_chap;i++)
    {
        chap[i] = _chap[i];
    }
}
int * Film::getChap() const
{
    int *temp = new int[num_chap];
    for(int i=0;i<num_chap;i++)
    {
        temp[i] = chap[i];
    }
    return temp;
}
int Film::getNumchap() const
{
    return num_chap;
}
