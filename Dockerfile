FROM docker.io/eclipse-temurin:8

ARG OPENAI_API_KEY
ENV OPENAI_API_KEY=${OPENAI_API_KEY}
# ENV OPENAI_API_KEY=/run/secrets/openai_api_key

COPY . /usr/src/lso-client
WORKDIR /usr/src/lso-client

RUN ./gradlew clean shadowJar -Dfurhatos.skills.brokeraddress=192.168.1.122

# Try this if skill crashes due to "Error:Could not find or load main class"
# ENTRYPOINT ["java", "-cp", "build/libs/MyAdvancedSkill_0.0.0.skill", "furhatos.skills.Skill"]
ENTRYPOINT ["java", "-jar", "build/libs/MyAdvancedSkill_0.0.0.skill"]
# CMD ["-Dfurhatos.skills.brokeraddress=192.168.1.122:1932"]
