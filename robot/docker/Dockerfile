#FROM wdragondragon/jsp-gzhmt

FROM classmethod/openjdk-alpine-git
MAINTAINER jdragon
ENV LANG C.UTF-8
#ENV LANGUAGE en_US:en
ENV TZ=Asia/Shanghai
ENV LC_ALL C.UTF-8
COPY robot.jar robot.jar
CMD java -jar /robot.jar