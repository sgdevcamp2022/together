import io from "socket.io-client";
import { useEffect, useRef, useState } from "react";
import { convertLength } from "@mui/material/styles/cssUtils";

const socket = io("ws://localhost:3001");
let myPeerConnection;
let myStream;
let roomName;

//다른 참가자가 들어오면 실행되는 코드
socket.on("welcome", async () => {
  const offer = await myPeerConnection.createOffer();
  myPeerConnection.setLocalDescription(offer);
  console.log("sent the offer");
  socket.emit("offer", offer, roomName);
});

socket.on("offer", async (offer) => {
  console.log("receive the offer");
  myPeerConnection.setRemoteDescription(offer);
  const answer = await myPeerConnection.createAnswer();
  myPeerConnection.setLocalDescription(answer);

  socket.emit("answer", answer, roomName);
  console.log("sent the answer");
});

socket.on("answer", (answer) => {
  console.log("receive the answer");
  myPeerConnection.setRemoteDescription(answer);
});

socket.on("ice", (ice) => {
  console.log("received candidate");
  myPeerConnection.addIceCandidate(ice);
});

function VideoCall() {
  let myVideoRef = useRef(null);
  let call = useRef(null);
  let join = useRef(null);
  let otherVideoRef = useRef(null);

  async function getCameras() {
    try {
      const devices = await navigator.mediaDevices.enumerateDevices();
    } catch (e) {
      console.log(e);
    }
  }

  //Stream 얻어와서 보여주기
  async function getMedia() {
    myStream = await navigator.mediaDevices.getUserMedia({
      video: true,
      audio: true,
    });

    const video = myVideoRef.current;
    video.srcObject = myStream;
    video.play();
    getCameras();
  }

  async function initCall() {
    await getMedia();
    makeConnection();
  }

  async function makeConnection() {
    myPeerConnection = new RTCPeerConnection({
      iceServers: [
        {
          urls: [
            "stun:stun.l.google.com:19302",
            "stun:stun1.l.google.com:19302",
            "stun:stun2.l.google.com:19302",
            "stun:stun3.l.google.com:19302",
            "stun:stun4.l.google.com:19302",
          ],
        },
      ],
    });
    myPeerConnection.addEventListener("icecandidate", handleIce);
    await myStream
      .getTracks()
      .forEach((track) => myPeerConnection.addTrack(track, myStream));

    myPeerConnection.addEventListener("addstream", handleAddStream);
  }

  function handleIce(data) {
    console.log("sent candidate");
    socket.emit("ice", data.candidate, roomName);
  }

  async function handleAddStream(data) {
    console.log("got an stream from my peer");
    console.log("Peer's ", data.stream);
    console.log("My", myStream);
    let otherVideo = otherVideoRef.current;
    otherVideo.srcObject = data.stream;
    otherVideo.play();
  }

  //방에 참가
  async function joinRoom() {
    const input = "1";
    roomName = input;
    await initCall();
    socket.emit("join_room", roomName);
  }

  useEffect(() => {
    joinRoom();
  }, []);

  const styles = {
    objectFit: "fill",
    width: "710px",
    height: "500px",
    border: "5px solid grey",
  };

  return (
    <div>
      <div ref={call}>
        <video style={styles} ref={myVideoRef}></video>
        <video style={styles} ref={otherVideoRef}></video>
      </div>
    </div>
  );
}

export default VideoCall;
