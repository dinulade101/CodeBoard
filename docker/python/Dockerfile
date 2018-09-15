FROM ubuntu:latest

RUN apt-get update && \
    apt-get install -y \
            python \
    && rm -rf /var/lib/apt/lists/*

RUN  pip3 install numpy && \
     pip3 install matplotlib

WORKDIR /root/examples

COPY ./main.py .

CMD ["python3 main.py"]