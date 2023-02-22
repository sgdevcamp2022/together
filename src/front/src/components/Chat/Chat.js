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

function Chat() {
  //   iframe으로 보낼 메세지
  const [roomId, setRoomId] = useState("");

  const channelId = useSelector(selectChannelId);
  const channelName = useSelector(selectChannelName);
  const serverId = useSelector(selectServerId);
  const serverName = useSelector(selectServerName);

  const userId = localStorage.getItem("userId");

  // 채널 id가 변경되면 채팅이 변경
  useEffect(() => {
    setRoomId("channelId+serverId+userId");
    return () => {};
  }, [channelId]);

  return (
    <div className="chat">
      <ChatHeader channelName={channelName} />

      <div className="chat__messages">
        {!!!channelId ? (
          <></>
        ) : (
          <>
          <div>{roomId}</div>
            <iframe src="http://127.0.0.1:8000/chat/${channlId}/chat/${userId}"></iframe>
          </>
        )}
      </div>
      <div className="chat__input">
        <AddCircle fontSize="large" />
        <form>
          <input placeholder={`Message #TestChannel`} />
          <button className="chat__inputButton" type="submit">
            Send Message
          </button>
        </form>
        <div className="chat__inputIcons">
          <CardGiftcard fontSize="large" />
          <GifTwoTone fontSize="large" />
          <EmojiEmotions fontSize="large" />
        </div>
      </div>
    </div>
  );
}

export default Chat;
