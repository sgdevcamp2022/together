import {
  AddCircle,
  CardGiftcard,
  EmojiEmotions,
  GifTwoTone,
} from "@mui/icons-material";
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import {
  selectChannelId,
  selectChannelName,
} from "../../features/counter/channelSlice";
import {
  selectServerId,
  selectServerName,
} from "../../features/counter/serverSlice";
import "./Chat.css";
import ChatHeader from "./ChatHeader";
import VideoCall from "../VideoCall/VideoCall";
import { selectConnectValue } from "../../features/counter/connectSlice";

function Chat() {
  //   iframe으로 보낼 메세지
  const [roomId, setRoomId] = useState("");

  const channelId = useSelector(selectChannelId);
  const channelName = useSelector(selectChannelName);
  const serverId = useSelector(selectServerId);
  const serverName = useSelector(selectServerName);

  const userId = localStorage.getItem("userId");

  const connect = useSelector((state) => state.connect.value);

  // 채널 id가 변경되면 채팅이 변경
  useEffect(() => {
    setRoomId(`http://127.0.0.1:8001/chat/${channelId}/chat/${userId}`);

    return () => {};
  }, [channelId,userId]);

  let ConditionalComponent;

  if (!!!channelId) {
    ConditionalComponent = <></>;
  } else {
    if (connect == true) {
      ConditionalComponent = <VideoCall />;
    } else if (connect == false) {
      ConditionalComponent = (
        <>
          <div>{roomId}</div>
          <iframe src="http://127.0.0.1:8000/chat/${channlId}/chat/${userId}"></iframe>
        </>
      );
    }
  }

  return (
    <div className="chat">
      <ChatHeader channelName={channelName} />
      <div className="chat__messages">
        {!!!channelId ? (
          <></>
        ) : (
          <>
            <iframe title="No content" height="650" src={roomId} width="1200"></iframe>
          </>
        )}
      </div>
    </div>
  );
}
export default Chat;