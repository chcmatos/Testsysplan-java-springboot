FROM gradle:jdk11 AS build

ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11-jre-slim AS final


RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar

WORKDIR /app

SHELL ["/bin/sh", "-c"]

EXPOSE 5005
EXPOSE 8080

CMD java ${ADDITIONAL_OPTS} -jar app.jar --spring.profiles.active=${PROFILE}