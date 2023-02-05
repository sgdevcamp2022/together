import io from "socket.io-client";
import { useEffect, useRef, useState } from "react";

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

function VideoCallPage() {
  let myVideoRef = useRef(null);
  let call = useRef(null);
  let join = useRef(null);
  let otherVideoRef = useRef(null);

  async function getCameras() {
    try {
      const devices = await navigator.mediaDevices.enumerateDevices();
      //console.log(devices);
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
    myPeerConnection = new RTCPeerConnection();
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
    console.log("으아아");
  }

  //방에 참가
  async function joinRoom() {
    const input = "1";
    roomName = input;
    await initCall();
    socket.emit("join_room", roomName);
  }

  return (
    <div>
      <h1>Together WebRTC</h1>
      <div ref={call}>
        <button ref={join} onClick={joinRoom}>
          join
        </button>
        <video ref={myVideoRef} width="400" height="400"></video>
        <video ref={otherVideoRef} width="400" height="400"></video>
      </div>
    </div>
  );
}

export default VideoCallPage;
