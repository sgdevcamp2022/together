/*
import io from "socket.io-client";
import { useRef } from "react";

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
      <div style={{ width: "100vw" }} ref={call}>
        <button ref={join} onClick={joinRoom}>
          join
        </button>
        <video ref={myVideoRef} width="30%"></video>
        <video ref={otherVideoRef} width="30%"></video>
      </div>
    </div>
  );
}

export default VideoCallPage;
*/
import React, { useState, useEffect, useContext, useRef } from "react";
import axios from "axios";
import "./Sidebar.css";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import AddIcon from "@mui/icons-material/Add";
import SidebarChannel from "./SidebarChannel";
import {
  Call,
  Headset,
  InfoOutlined,
  Mic,
  Settings,
  SignalCellularAlt,
} from "@mui/icons-material";
import { Avatar } from "@mui/material";
import Dropdown from "../Server/Dropdown";
import { useSelector } from "react-redux";
import { selectServerId } from "../../features/counter/serverSlice";
import AuthContext from "../../store/auth-context";
import MyInfo from "./MyInfo";

import io from "socket.io-client";

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
  const authCtx = useContext(AuthContext);
  const userId = localStorage.getItem("userId");
  const token = localStorage.getItem("token");
  //localStorage에서 사용자 데이터 가져와 프로필 부분에 넣기
  const [channel, setChannel] = useState([]);
  const serverId = useSelector(selectServerId);
  const [user, setUser] = useState("");
  const [updated, setupdated] = useState(false);
  const [connected, setConnected] = useState(false);

  {
    /*미디어 관련 함수 시작 */
  }

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
    setConnected(true);
    const input = "1";
    roomName = input;
    await initCall();
    socket.emit("join_room", roomName);
  }
  {
    /* 미디어 관련 함수 끝*/
  }

  const loggoutHandler = () => {
    authCtx.logout();
  };

  // 서버에서 채널데이터 가져오기
  useEffect(() => {
    const readChannelHandler = axios
      .get(`http://localhost:8000/channel-service/server/${serverId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        setChannel(
          res.data.channelList.map((ch) => ({
            id: ch.id,
            channel: ch.name,
          }))
        );
        setupdated(false);
      });
  }, [serverId, token, userId, updated]);

  const handleAddChannel = () => {
    const channelName = prompt("Enter a new channel name");
    console.log(channelName);
    if (channelName) {
      // 서버에 채널 생성
      axios
        .post(
          `http://localhost:8000/channel-service/${serverId}/channel`,
          {
            name: `${channelName}`,
            info: "thi is info",
            type: 1,
          },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )
        .then((res) => {
          setupdated(true);
        });
    }
  };

  // 사용자 정보 가져오기
  useEffect(() => {
    const readChannelHandler = axios
      .get(`http://localhost:8000/user-service/user/${userId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        setUser(res.data.user);
      });
  }, [token, userId]);

  return (
    <div className="sidebar">
      <div className="sidebar__top">
        <Dropdown />
      </div>

      {/*중간부분 시작*/}
      {/*
      <div className="sidebar__channels">
        <div className="sidebar__channelsHeader">
          <div className="sidebar__header">
            <ExpandMoreIcon />
            <h4>Channels</h4>
          </div>

          <AddIcon onClick={handleAddChannel} className="sidebar__addChannel" />
        </div>
      </div>
  */}
      <div>
        <div style={{ width: "100vw" }} ref={call}>
          <video ref={myVideoRef} width="30%"></video>
          <video ref={otherVideoRef} width="30%"></video>
        </div>
      </div>

      {/*중간부분 끝*/}

      <div className="sidebar__voice">
        <SignalCellularAlt className="sidebar__voiceIcon" fontSize="large" />
        <div className="sidebar__voiceInfo">
          {connected === true ? (
            <h3>Voice Connected</h3>
          ) : (
            <h3>Voice UnConnected</h3>
          )}

          <p>Stream</p>
        </div>

        <div className="sidebar__voiceIcons">
          <InfoOutlined />
          <Call ref={join} onClick={joinRoom} />
        </div>
      </div>
      <div className="sidebar__profile">
        <MyInfo />
        <div className="sidebar__profileInfo">
          {!!!user ? (
            <></>
          ) : (
            <>
              <h3>@{user.name}</h3>
              <p>#{user.userId.substring(0, 4)}</p>
            </>
          )}
        </div>
        <div className="sidebar__profileIcons">
          <Mic fontSize="large" />
          <Headset fontSize="large" />
          <Settings fontSize="large" onClick={loggoutHandler} />
        </div>
      </div>
    </div>
  );
}

export default VideoCallPage;
