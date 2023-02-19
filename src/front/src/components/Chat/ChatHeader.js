import { EditLocationRounded, HelpRounded, Notifications, SearchRounded, SendRounded } from "@mui/icons-material";
import React from "react";
import "./ChatHeader.css";
import FriendList from "./FriendList";

function ChatHeader({channelName}) {
  return (
    <div className="chatHeader">
      <div className="chatHeader__left">
        <h3>
          <span className="chatHeader__hash">#</span>
          {channelName}
        </h3>
      </div>
      <div className="chatHeader__right">
        <Notifications />
        <EditLocationRounded />
        <FriendList />

        <div className="chatHeader__search">
            <input placeholder="Search"/>
            <SearchRounded />
        </div>

        <SendRounded/>
        <HelpRounded/>
      </div>
    </div>
  );
}

export default ChatHeader;
