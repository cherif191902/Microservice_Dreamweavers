# Étape 1 : Utilisez une image de base Java
FROM openjdk:17-jdk-slim

# Étape 2 : Installer Netcat pour vérifier la disponibilité des dépendances
RUN apt-get update && apt-get install -y netcat && apt-get clean

# Étape 3 : Définir un répertoire de travail dans le conteneur
WORKDIR /app

# Étape 4 : Copier le fichier JAR généré par Maven dans l'image Docker
COPY target/achat-1.0.jar app.jar

# Étape 5 : Exposer le port configuré dans l'application
EXPOSE 8089

# Étape 6 : Commande pour exécuter l'application
CMD ["java", "-jar", "app.jar"]