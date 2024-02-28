# CDA Trip Agency :airplane: :sunrise:

![](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExaHJvZXV6aGR6M25oa3BwcXlnNXJ3Y2doNjRuZnlwaDNyMXJ2dTEwayZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/M8wGfV2Ohj5mE1PYiX/giphy.gif)





<br>

## Contexte
<hr>
Projet réalisé avec Spring Boot reprenant le sytème d'une agence de voyage permettant d'organiser un voyage selon l'envie du client. <br>


<br>

### Exemple scénarios  :

Le client  :
- veut partir une semaine en bord de mer, hébérgé dans un hôtel 5 étoiles en Juin pour 75 000 ¥ 
- change d’avis et voudrais partir avec sa copine pour le même budget (future feature)


Notre agence  :
- peut rechercher les critères de voyage correspondant à sa demande 
- peut ajouter, modifier, supprimer des éléments pour peaufiner notre super catalogue de voyage
- peut associer des hébergements/tags a des villes/pays

<br>

## Technologies et outils utilisés 
<hr>



| TOOLS                       |                                                                                                                            |
|-----------------------------|----------------------------------------------------------------------------------------------------------------------------|
| Language/ Framework         | ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)              |
| BDD                         | ![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)                     |   |   |   |
| Gestionnaire de dépendances | ![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white) |
| Conteneurisation            | ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)               |
| Test de l' API              | ![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white) ![Insomnia](https://img.shields.io/badge/Insomnia-black?style=for-the-badge&logo=insomnia&logoColor=5849BE)                  |
<br>

## Initialisation
<hr>

1. Cloner le dépôt ou téléchargez le fichier zip
2. Accéder au répertoire du projet
3. Configurer la connexion à la base de données dans le fichier `application.properties` 
4. Compiler avec Maven

<br>

#### Lancer Docker:
````
docker-compose up -d
````

#### Stopper Docker:

````
docker-compose down -v
````

<br>

## Conception
<hr>

### Diagramme de classes

```puml

    left to right direction

class Voyage {
+id : int
+pays: Pays
+ville: Ville
+tarifTotal: double
+nomClient: string
+nombreJours: int
+nombreParticipants: int
+vol: Vol
+hotel: Hotel
creer()
supprimer()
modifier()

}

class Pays {
+id: int
+nom: string
+ville: Ville

}

class Ville {
+id: int
+nom: string
+pays: Pays

}

class Vol {
+id: int
+placesTotal: int
+placesDisponibles: int
+villeDepart: Ville
+villeArrivee: Ville
+dateAller: Date
+dateRetour: Date
+compagnie: String
+prix: double
reserver()
rechercher()
creer()
modifier()
supprimer()

}

class Hebergement {
+id: int
+nom: String
+placesTotal: int
+placesDisponibles: int
+ville: Ville
+nbEtoiles: int
+prix: double
+tag Tag
reserver()
rechercher()
creer()
modifier()
supprimer()

}

class Tag {
+id: int
+nom: string

}



Pays "1" --o "1..N" Ville
Vol "1..N" --o "1" Ville
Voyage "1..N" --o "1" Hebergement
Voyage "1..N" --o "1" Vol
Ville "1" --o "1..N" Hebergement
Hebergement "0.." --o "0.." Tag


  
```
<br>

## Contributeurs  :woman_technologist: :man_technologist:

<hr>

**_[Timothé](https://github.com/timothedepoorter), [Justine](https://github.com/Arheee), [Margot](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExbHd5YTdvcTl0dTduaXVkZ3p0YWJxd3BkY3l3YW10YWliZXdzcTMyZSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/HaBTHigPfnpYvHISuX/giphy.gif), [Juliette](https://github.com/Juliette117)_**

<br>


