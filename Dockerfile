FROM ubuntu:latest
LABEL authors="faruh"

ENTRYPOINT ["top", "-b"]