# CDA Trip Agency :airplane: :sunrise:

<hr>

<div align="center">
  
  ![](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExaHJvZXV6aGR6M25oa3BwcXlnNXJ3Y2doNjRuZnlwaDNyMXJ2dTEwayZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/M8wGfV2Ohj5mE1PYiX/giphy.gif)
  
</div>







<br>

## Contexte

Ce projet, développé en Java avec le framework Spring Boot, vise à créer un système de gestion d'agence de voyage.  Il permet d'organiser des voyages sur mesure selon les préférences des clients.
. <br>


<br>

### Exemple scénarios  :

Client :

Le client veut partir une semaine en bord de mer, hébergé dans un hôtel 5 étoiles en Juin pour 75 000 ¥ avec Internet, car c'est un développeur.
Le client change d'avis et souhaite partir avec sa copine pour le même budget.
Agence :

L'agence peut rechercher les critères de voyage correspondant à la demande du client.
Elle peut ajouter, modifier, supprimer des éléments pour affiner son catalogue de voyages.
Elle peut associer des hébergements/tags à des villes/pays.
Elle peut organiser des voyages de qualité supérieure, surpassant les offres de Lidl voyage et offrant des expériences plus haut de gamme.
<br>

🐙Pour un voyage tentaculaire, choisissez CDA Trip Agency 🐙

⚠️ Nos vols vers le Canada sont tous en retard, cause de Titouan dans l'avion !!!!!! ⚠️

<br>

## Technologies et outils utilisés 




| TOOLS                       |                                                                                                                            |
|-----------------------------|----------------------------------------------------------------------------------------------------------------------------|
| Language/ Framework         | ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)              |
| BDD                         | ![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)                     |   |   |   |
| Gestionnaire de dépendances | ![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white) |
| Conteneurisation            | ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)               |
| API              | ![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white) ![Insomnia](https://img.shields.io/badge/Insomnia-black?style=for-the-badge&logo=insomnia&logoColor=5849BE)    ![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)              |
<br>

## Initialisation


1. Cloner le dépôt ou téléchargez le fichier zip
2. Accéder au répertoire du projet
3. Configurer la connexion à la base de données dans le fichier `application.properties` 
4. Compiler avec Maven

<br>

### Gestion des Conteneurs Docker 🐋

##### Lancer conteneur Docker:
````
docker-compose up -d
````

##### Stopper conteneur Docker:

````
docker-compose down -v
````

<br>

## Conception


### Diagramme de classes

![](https://www.planttext.com/api/plantuml/png/fLDDQyCm3BtxLvYSDiB2TisfPHsK7KRPIkzEegO2nmwoMrR6_ljavxCTFOm54YjFblpq92-SL-IFZPP2mzvBRoLXVV2oGeBIepL2b5ev9tVsh6gGNoB5IkOIZHTfgyukbnl-YlI4MaCkTs4HAMV5_TPwfNDPsMCHC6ERPurWV2wT9pHrX0g2LtiarwVia0sJmX9RPVpWE5bEjRDQf0VhWUrrM4H907HtBzonRGcRQ3UsmZr6KtmF_0FBZlrqqaJaWlqK5FzlHytgdyLOVH7HQbM2wxMO8IjqhJN8qembXjDMq7Bnev0HMnBX2IPrA-LXoHRbSiLc1xo3Pnq7fBHDgsg37FGni2Ny7Bj1u81EKIDkz04elFmpw3dJS2xjLEOQ2g0Q6kxdRu768_zM-rYWALwynSbxGPs7gyxbLjN_BuAJtEnks2L479laAP4F3vRNB7jBUfAXpptG-IP7l2bNlfbUDtQ4uUu6xkgyUUXSzkGno_gDqGgITCy2JCMN-WS0)

[//]: # ()
[//]: # (```puml)

[//]: # ()
[//]: # (    left to right direction)

[//]: # ()
[//]: # (class Voyage {)

[//]: # (+id : int)

[//]: # (+pays: Pays)

[//]: # (+ville: Ville)

[//]: # (+tarifTotal: double)

[//]: # (+nomClient: string)

[//]: # (+nombreJours: int)

[//]: # (+nombreParticipants: int)

[//]: # (+vol: Vol)

[//]: # (+hotel: Hotel)

[//]: # (creer&#40;&#41;)

[//]: # (supprimer&#40;&#41;)

[//]: # (modifier&#40;&#41;)

[//]: # ()
[//]: # (})

[//]: # ()
[//]: # (class Pays {)

[//]: # (+id: int)

[//]: # (+nom: string)

[//]: # (+ville: Ville)

[//]: # ()
[//]: # (})

[//]: # ()
[//]: # (class Ville {)

[//]: # (+id: int)

[//]: # (+nom: string)

[//]: # (+pays: Pays)

[//]: # ()
[//]: # (})

[//]: # ()
[//]: # (class Vol {)

[//]: # (+id: int)

[//]: # (+placesTotal: int)

[//]: # (+placesDisponibles: int)

[//]: # (+villeDepart: Ville)

[//]: # (+villeArrivee: Ville)

[//]: # (+dateAller: Date)

[//]: # (+dateRetour: Date)

[//]: # (+compagnie: String)

[//]: # (+prix: double)

[//]: # (reserver&#40;&#41;)

[//]: # (rechercher&#40;&#41;)

[//]: # (creer&#40;&#41;)

[//]: # (modifier&#40;&#41;)

[//]: # (supprimer&#40;&#41;)

[//]: # ()
[//]: # (})

[//]: # ()
[//]: # (class Hebergement {)

[//]: # (+id: int)

[//]: # (+nom: String)

[//]: # (+placesTotal: int)

[//]: # (+placesDisponibles: int)

[//]: # (+ville: Ville)

[//]: # (+nbEtoiles: int)

[//]: # (+prix: double)

[//]: # (+tag Tag)

[//]: # (reserver&#40;&#41;)

[//]: # (rechercher&#40;&#41;)

[//]: # (creer&#40;&#41;)

[//]: # (modifier&#40;&#41;)

[//]: # (supprimer&#40;&#41;)

[//]: # ()
[//]: # (})

[//]: # ()
[//]: # (class Tag {)

[//]: # (+id: int)

[//]: # (+nom: string)

[//]: # ()
[//]: # (})

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (Pays "1" --o "1..N" Ville)

[//]: # (Vol "1..N" --o "1" Ville)

[//]: # (Voyage "1..N" --o "1" Hebergement)

[//]: # (Voyage "1..N" --o "1" Vol)

[//]: # (Ville "1" --o "1..N" Hebergement)

[//]: # (Hebergement "0.." --o "0.." Tag)

[//]: # ()
[//]: # ()
[//]: # (  )
[//]: # (```)
<br>

## Contributeurs 🧑‍✈️👨‍✈️👩‍✈️



**_[Timothé](https://github.com/timothedepoorter),
[Justine](https://github.com/Arheee),
[Margot](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExbHd5YTdvcTl0dTduaXVkZ3p0YWJxd3BkY3l3YW10YWliZXdzcTMyZSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/HaBTHigPfnpYvHISuX/giphy.gif),
[Juliette](https://github.com/Juliette117)_**

<br>


