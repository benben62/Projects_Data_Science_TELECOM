#include <iostream>
#include "multimedia.h"
#include "video.h"
#include "photo.h"
#include "film.h"
#include "listmedia.h"
#include "factory.h"

typedef shared_ptr<int[]> intPtr;
typedef shared_ptr<Multimedia> MediaPtr;
typedef shared_ptr<Video> VideoPtr;
typedef shared_ptr<Photo> PhotoPtr;
typedef shared_ptr<Film> FilmPtr;
template <typename T>
using ListMediaPtr = shared_ptr<ListMedia<T>> ;

using namespace std;
int main(){
    int step = 12;
    //cout<<"which step do you want to test"<<endl;
    switch(step)
    {
    case 3:
    {
        Multimedia *test = new Video();
        test->setnomF("");
        test->setnomM("test.mp4");
        test->display(cout);
        cout<<endl;
        //test->play();
        delete test;
        break;
    }
    case 4:
    {
        Video *video_test = new Video("", "test.mp4", 60);//absolute path
        video_test->display(cout);
        cout<<endl;
        video_test->play();
        Photo *photo_test = new Photo("","test.jpg", 100, 200);//relative path
        photo_test->display(cout);
        cout<<endl;
        photo_test->play();

        delete video_test;
        video_test = nullptr;
        delete photo_test;
        photo_test = nullptr;
        break;
    }
    case 5:
    {
        Multimedia **list= new Multimedia *[2];
        list[0] = new Video("", "test.mp4", 5);
        list[1] = new Photo("","test.jpg", 100, 200);
        for(int i=0;i<2;i++)
        {
            list[i]->play();
            list[i]->display(cout);
            cout<<endl;
        }

        delete list[0];
        delete list[1];
        list[0] = nullptr;
        list[1] = nullptr;
        delete []list;
        list = nullptr;
        break;
    }
    case 6:
    {
        int *chap = new int[3];
        chap[0] = 1;
        chap[1] = 2;
        chap[2] = 2;
        Film *film_test = new Film("", "test.3gp", 5, chap, 3);
        film_test->display(cout);
        cout<<endl;
        chap[2] = 3;
        film_test->setChap(chap);
        delete []chap;
        chap = nullptr;
        film_test->display(cout);
        cout<<endl;
        cout<<"The test of getChap fuction: ";
        int *temp = film_test->getChap();
        for(int i=0;i<film_test->getNumchap();i++)
        {
            cout<<temp[i]<<" ";
        }
        cout<<endl<<endl;

        delete temp;
        temp = nullptr;
        delete film_test;
        film_test = nullptr;

        break;
    }
    case 7:
    {
        int *chap = new int[3]{1,2,2};
        Film *film_test = new Film("", "test.3gp", 5, chap, 3);
        film_test->display(cout);
        cout<<endl;
        chap[2] = 3;
        film_test->display(cout);//the information in film_test is not changed
        cout<<endl;
        film_test->setChap(chap);
        delete []chap;
        chap = nullptr;
        film_test->display(cout);//after we delete chap, the information in film_test do not disappear
        cout<<endl;
        delete film_test;
        film_test = nullptr;

        break;
    }
    case 8:
    {
        int chap[3]{1,2,2};
        Film *film_test = new Film("", "test.3gp", 5, chap, 3);
//        delete []chap;
//        chap = nullptr;
        Video *video_test = new Video("", "test.mp4", 60);
        Photo *photo_test = new Photo("","test.jpg", 100, 200);

        ListMedia<Multimedia*> *temp = new ListMedia<Multimedia*>("hhhh");
        cout<<temp->getname()<<endl;
        temp->push_back(film_test);
        temp->push_back(video_test);
        temp->push_front(photo_test);
        temp->display(cout);
        for(auto it:*temp)
        {
            delete it;
        }

        break;
    }
    case 9:
    {
        int *chap = new int[3]{1,2,2};
        FilmPtr film_test(new Film("", "test.3gp", 5, chap, 3));
        delete []chap;
        chap = nullptr;
        VideoPtr video_test(new Video("", "test.mp4", 60));
        PhotoPtr photo_test(new Photo("","test.jpg", 100, 200));
        ListMediaPtr<MediaPtr> temp(new ListMedia<MediaPtr> ("hi"));
        cout<<temp->getname()<<endl;
        temp->push_back(film_test);
        temp->push_back(video_test);
        temp->push_front(photo_test);
        temp->display(cout);

        break;
    }
    case 10:
    {
        shared_ptr<Factory> temp(new Factory());
        temp->createPhoto("","test.jpg", 100, 200);

        temp->createVideo("", "test.mp4", 60);

        int *chap = new int[3]{1,2,2};
        temp->createFilm("", "test.3gp", 5, chap, 3);

        ListMediaPtr<MediaPtr> list = temp->createList("hi");
        FilmPtr film_test(new Film("", "test.3gp", 5, chap, 3));
        delete []chap;
        chap = nullptr;
        VideoPtr video_test(new Video("/Users/Nicolas/Desktop/movie/test/", "test.mkv", 60));
        PhotoPtr photo_test(new Photo("","test.jpg", 100, 200));
        list->push_back(film_test);
        list->push_back(video_test);
        list->push_back(photo_test);

        temp->displayMediaByName("test.jpg", cout);
        temp->displayGroupByName("hi", cout);
        temp->playByName("test.3gp");
        break;
    }
    case 11:
    {
        shared_ptr<Factory> temp(new Factory());
        temp->createPhoto("","test.jpg", 100, 200);

        temp->createVideo("", "test.mp4", 60);

        int *chap = new int[3]{1,2,2};
        temp->createFilm("", "test.3gp", 5, chap, 3);
        delete []chap;
        chap = nullptr;

        ofstream ofile;
        ofile.open("dict_m.txt",ios_base::out);
        temp->saveFile(ofile);
        ofile.close();

        break;
    }
    case 12:
    {
        shared_ptr<Factory> temp(new Factory());
        temp->playByName("test.jpg");
        break;
    }
    }

    return 0;
}

