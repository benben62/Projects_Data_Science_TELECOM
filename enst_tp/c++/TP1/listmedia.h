#ifndef LISTMEDIA_H
#define LISTMEDIA_H

#include <iostream>
#include<list>
using namespace std;

template <typename T>
class ListMedia : public list<T>
{
private:
    string name_group;
public:
    ListMedia(string _name="")
    {
        name_group = _name;

    }
    virtual ~ListMedia()
    {

    }
    virtual string getname() const
    {
        return name_group;
    }
    virtual void display(ostream& out, bool flag = false) const
    {
        if (flag)
        {
            out<<name_group<<';';
            for(auto it:*this)
            {
                it->display(out, flag);
                out<<';';
            }
            out<<'+';
        }
        else
        {
            out<<name_group<<endl;
            for(auto it:*this)
            {
                it->display(out);
                out<<endl;
            }
        }
    }
};

#endif // LISTMEDIA_H
