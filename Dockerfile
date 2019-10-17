FROM registry.do.x5.ru/bdsu/deploy-java:latest as gradle_image
ENV JAVA_OPTS ""
RUN mkdir app_tmp
COPY . app_tmp/
RUN cd app_tmp && sh /usr/bin/gradle clean build -x check

FROM registry.do.x5.ru/bdsu/openjdk11
RUN mkdir -p /srv/
COPY --from=gradle_image /home/gradle/app_tmp/center-communication/build/libs/*.war /srv/app.war
RUN rm -rf app_tmp
WORKDIR /srv
ENTRYPOINT java $JAVA_OPTS -jar app.war
