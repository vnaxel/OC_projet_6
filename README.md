## Frontend

Depuis le dossier front/MDD-frontend

Run `npm i`
Puis `ng serve`
Le frontend est maintenant accessible à l'adresse http://localhost:4200


## Backend
Pour la base de donnée, j'utilise docker avec spring-boot-docker-compose

Avec le docker engine qui tourne, spring-boot-cocker-compose va pull et lancer les conteneurs nécessaires
On aura une base de donnée mysql sur le port 3306 et une gui adminer sur le port 8080
Pour lancer le backend, la variable d'environnement $JAVA_HOME doit pointer vers une version du JDK 17 ou supérieure

Depuis le repertoire backend, run `./mvnw spring-boot:run`

On peut maintenant se rendre sur http://localhost:4200 pour tester le bon fonctionnement de l'application fullstack
