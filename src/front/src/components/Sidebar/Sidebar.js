import React, { useState, useEffect } from "react";
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




function Sidebar() {
  const userId = localStorage.getItem("userId");
  const token = localStorage.getItem("token");
  //localStorage에서 사용자 데이터 가져와 프로필 부분에 넣기
  const [channel, setChannel] = useState([]);
  const serverId = useSelector(selectServerId);
  const [user, setUser] = useState("");
  const [updated,setupdated] = useState(false);

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
      .post(`http://localhost:8000/channel-service/${serverId}/channel`,{
        name: `${channelName}`,
        info: "thi is info",
        type: 1
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        
      }).then((res)=>{
        setupdated(true);
      })
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

      <div className="sidebar__channels">
        <div className="sidebar__channelsHeader">
          <div className="sidebar__header">
            <ExpandMoreIcon />
            <h4>Text Channels</h4>
          </div>

          <AddIcon onClick={handleAddChannel} className="sidebar__addChannel" />
        </div>

        <div className="sidebar__channelsList">
          {/* map으로 id, 이름 넘기기 */}
          {channel.map(({ id, channel }) => (
            <SidebarChannel id={id} key={id} channel={channel} />
          ))}
        </div>
      </div>

      <div className="sidebar__voice">
        <SignalCellularAlt className="sidebar__voiceIcon" fontSize="large" />
        <div className="sidebar__voiceInfo">
          <h3>Voice Connected</h3>
          <p>Stream</p>
        </div>

        <div className="sidebar__voiceIcons">
          <InfoOutlined />
          <Call />
        </div>
      </div>
      <div className="sidebar__profile">
        <Avatar />
        <div className="sidebar__profileInfo">
          {
            !!!user ? <></> : <>
            <h3>@{user.name}</h3>
          <p>#{user.userId.substring(0,4)}</p>
            </>
          }
        </div>
        <div className="sidebar__profileIcons">
          <Mic fontSize="large" />
          <Headset fontSize="large" />
          <Settings fontSize="large" />
        </div>
      </div>
    </div>
  );
}

export default Sidebar;
