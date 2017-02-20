//
//  server.cpp
//  TP C++
//  Eric Lecolinet - Telecom ParisTech - 2016.
//

#include <memory>
#include <string>
#include <iostream>
#include <sstream>
#include <vector>
#include <algorithm>
#include "tcpserver.h"
#include "multimedia.h"
#include "video.h"
#include "photo.h"
#include "film.h"
#include "listmedia.h"
#include "factory.h"
using namespace std;
using namespace cppu;

typedef shared_ptr<int[]> intPtr;
typedef shared_ptr<Multimedia> MediaPtr;
typedef shared_ptr<Video> VideoPtr;
typedef shared_ptr<Photo> PhotoPtr;
typedef shared_ptr<Film> FilmPtr;
template <typename T>
using ListMediaPtr = shared_ptr<ListMedia<T>> ;

const int PORT = 3331;

class MyBase {
private:
  shared_ptr<Factory> myFac;
  vector<string> command;
public:
  /* Cette méthode est appelée chaque fois qu'il y a une requête à traiter.
   * Ca doit etre une methode de la classe qui gere les données, afin qu'elle
   * puisse y accéder.
   *
   * Arguments:
   * - 'request' contient la requête
   * - 'response' sert à indiquer la réponse qui sera renvoyée au client
   * - si la fonction renvoie false la connexion est close.
   *
   * Cette fonction peut etre appelée en parallele par plusieurs threads (il y a
   * un thread par client).
   *
   * Pour eviter les problemes de concurrence on peut créer un verrou en creant
   * une variable Lock **dans la pile** qui doit etre en mode WRITE (2e argument = true)
   * si la fonction modifie les donnees.
   */
  MyBase(){
      myFac.reset(new Factory());
      command.clear();

  }
  bool processRequest(TCPConnection& cnx, const string& request, string& response)
  {
    cerr << "\nRequest: '" << request << "'" << endl;

    stringstream ss;
    ss.str("");
    ss.clear();
    ss.str(request);
    string temp_command = "";
    command.clear();
    while (getline(ss, temp_command, ';'))
    {
      command.push_back(temp_command);
      cerr<<temp_command<<endl;
    }
    string func_name = command.at(0);
    int size = command.size();
    if (func_name == "createPhoto")
    {
      if (size == 5)
      {
        myFac->createPhoto(command.at(1), command.at(2), stod(command.at(3)), stod(command.at(4)));
        response = "Create successful!";
      }else
      {
        response = "Wrong size of command";
      }
    }
    else if (func_name == "createVideo")
    {
      if (size == 4)
      {
        myFac->createVideo(command.at(1), command.at(2), stoi(command.at(3)));
        response = "Create successful!";
      }else
      {
        response = "Wrong size of command";
      }
    }
    else if (func_name == "createFilm")
    {
      if (size >= 6)
      {
        int list_size = stoi(command.at(4));
        int *list = new int[list_size];
        for(int i=5;i<list_size+5;i++){
          list[i-5] = stoi(command.at(i));
        }
        myFac->createFilm(command.at(1), command.at(2), stoi(command.at(3)), list, list_size);
        response = "Create successful!";
        delete []list;
        list = nullptr;
      }else
      {
        response = "Wrong size of command";
      }

    }
    else if (func_name == "createList")
    {
      if (size == 2)
      {
        myFac->createList(command.at(1));
        response = "Create successful!";
      }else
      {
        response = "Wrong size of command";
      }
    }
    else if(func_name == "displayMediaByName")
    {
      ss.str("");
      ss.clear();
      if (size == 2)
      {
        myFac->displayMediaByName(command.at(1), ss);
        response = ss.str();
        replace(response.begin(), response.end(), '\n', ';');
      }else
        response = "Wrong size of command";
    }
    else if(func_name == "displayGroupByName")
    {
      ss.str("");
      ss.clear();
      if (size == 2)
      {
        myFac->displayGroupByName(command.at(1), ss);
        response = ss.str();
        replace(response.begin(), response.end(), '\n', ';');
      }else
        response = "Wrong size of command";
    }
    else if(func_name == "playByName")
    {
      if (size == 2)
      {
        myFac->playByName(command.at(1));
        response = "play successful";
      }else
      response = "Wrong size of command";
    }
    else if(func_name == "save")
    {
        ofstream ofile;
        ofile.open("dict_m.txt",ios_base::out);
        myFac->saveFile(ofile);
        ofile.close();
    }
    else
    {
      response = "OK:" + request;
    }

    cerr << "response: " << response << endl;
    // 1) pour decouper la requête:
    // on peut par exemple utiliser stringstream et getline()
    
    
    // 2) faire le traitement:
    // - si le traitement modifie les donnees inclure: TCPLock lock(cnx, true);
    // - sinon juste: TCPLock lock(cnx);


    // 3) retourner la reponse au client:
    // - pour l'instant ca retourne juste OK suivi de la requête
    // - pour retourner quelque chose de plus utile on peut appeler la methode print()
    //   des objets ou des groupes en lui passant en argument un stringstream
    // - attention, la requête NE DOIT PAS contenir les caractères \n ou \r car
    //   ils servent à délimiter les messages entre le serveur et le client

    
    // renvoyer false si on veut clore la connexion avec le client
    return true;
  }
};


int main(int argc, char* argv[])
{
  // cree le TCPServer
  shared_ptr<TCPServer> server(new TCPServer());
  
  // cree l'objet qui gère les données
  shared_ptr<MyBase> base(new MyBase());

  // le serveur appelera cette méthode chaque fois qu'il y a une requête
  server->setCallback(*base, &MyBase::processRequest);
  
  // lance la boucle infinie du serveur
  cout << "Starting Server on port " << PORT << endl;
  int status = server->run(PORT);
  
  // en cas d'erreur
  if (status < 0) {
    cerr << "Could not start Server on port " << PORT << endl;
    return 1;
  }
  
  return 0;
}

