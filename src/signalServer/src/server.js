import express from "express";
import http from "http";
import SocketIO from "socket.io";

const app = express();

const httpServer = http.createServer(app);
//const wsServer = SocketIO(httpServer);

const wsServer = SocketIO(httpServer, {
  cors: {
    origin: "http://localhost:3000",
  },
});

wsServer.on("connection", (socket) => {
  socket.on("join_room", (roomName) => {
    socket.join(roomName);
    socket.to(roomName).emit("welcome");
  });
  socket.on("offer", (offer, roomName) => {
    socket.to(roomName).emit("offer", offer);
  });
  socket.on("answer", (answer, roomName) => {
    socket.to(roomName).emit("answer", answer);
  });

  socket.on("ice", (ice, roomName) => {
    socket.to(roomName).emit("ice", ice);
  });
});

const handleListen = () => console.log("Listening on ws://localhost:3001");
httpServer.listen(3001, handleListen);
