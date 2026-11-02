FROM maven:3.9-eclipse-temurin-26 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package dependency:copy-dependencies

FROM eclipse-temurin:26-jre

RUN apt-get update && apt-get install -y \
    xvfb \
    fluxbox \
    x11vnc \
    novnc \
    websockify \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=build /app/target/Optica-1.0-SNAPSHOT.jar ./Optica.jar
COPY --from=build /app/target/dependency ./dependency

ENV DISPLAY=:0

EXPOSE 10000

CMD ["bash", "-c", "Xvfb :0 -screen 0 1280x800x24 & sleep 2; fluxbox & java -cp 'Optica.jar:dependency/*' com.optica.Optica & x11vnc -display :0 -forever -shared -nopw -rfbport 5900 & websockify --web=/usr/share/novnc ${PORT:-10000} localhost:5900"]
