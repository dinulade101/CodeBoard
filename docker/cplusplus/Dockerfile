# Get the GCC preinstalled image from Docker Hub
FROM gcc:latest

# Specify the working directory
WORKDIR /usr/src/codeexec

# Run the program output from the previous step
CMD ["g++", "-o", "main", "main.cpp", "&&", "./main"]

