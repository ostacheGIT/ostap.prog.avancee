Distributed version of MC for PI using Java socket.

usage on a localhost:
One terminal for Master
One terminal for each Worker

on each server terminal (Worker):
make
java WorkerSocket <port>

on client terminal (Master):
make
make run