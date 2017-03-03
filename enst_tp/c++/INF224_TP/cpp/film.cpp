#include "film.h"
/**
 * @brief Film
 * @param 
 * @return 
 * @detail initializer nomFichier, nomMedia, duree, chaptre with parameter and inherit the constructer of video
 */
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
/**
 * @brief ~Film
 * @param 
 * @return 
 * @detail Destruct chap
 */
Film::~Film()
{
    delete []chap;
}
/**
 * @brief display
 * @param out, flag
 * @return
 * @detail show the information of the filmflag is to
 *         judge if it is used for cout or server
 */
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
/**
 * @brief setChap
 * @param the pointer of _chap
 * @return 
 * @detail set the value of chap
 */
void Film::setChap(int *_chap)
{
    chap = new int[num_chap];
    for(int i=0;i<num_chap;i++)
    {
        chap[i] = _chap[i];
    }
}
/**
 * @brief getChap
 * @param 
 * @return temp
 * @detail get the pointer of temp
 */
int * Film::getChap() const
{
    int *temp = new int[num_chap];
    for(int i=0;i<num_chap;i++)
    {
        temp[i] = chap[i];
    }
    return temp;
}
/**
 * @brief getNumchap
 * @param 
 * @return num_chap
 * @detail get the number of chap
 */
int Film::getNumchap() const
{
    return num_chap;
}
