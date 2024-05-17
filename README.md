# memoire.franky.nyambi



## Getting started

Sujet : application de gestion de livres
L'apllication doit pouvoir lire des informations sur un livre en focntion de son ISBN et enregistrer les informations dans une base de donnees locale.
https://openlibrary.org/developers/api

## Package 
### Entite 

 Le package Entite est le package où se trouve la classe **Book** qui a comme attribut les élements d'un livre. Cette class sera sauvegardé 
la BD 

### Les différentes requetes pour avoir les informations sur un livre 

#### Récuperer les informations sur un livre sur OPEN LIBRARY :
http://localhost:8080/api/books/9781942788041 

#### Sauvegarder les informations dans la BD :
http://localhost:8080/api/books/books/9781484281963

#### Pour afficher la liste des livres : 
http://localhost:8080/api/books/listBooks

#### Endpoint pour rechercher des livres par ISBN : 
http://localhost:8080/api/books/search/isbn?isbn=

#### Endpoint pour rechercher des livres par titre :
http://localhost:8080/api/books/search/title?title=

#### Endpoint pour rechercher des livres par auteur :
http://localhost:8080/api/books/search/author?author=

#### Nouveau endpoint pour rechercher des livres par différents critères :  
http://localhost:8080/api/books/search?isbn=9780980200447 

http://localhost:8080/api/books/search?title=Slow

http://localhost:8080/api/books/search?author=John

http://localhost:8080/api/books/search?title=Slow&author=John

