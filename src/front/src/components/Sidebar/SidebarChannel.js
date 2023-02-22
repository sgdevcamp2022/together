import React from "react";
import { useDispatch } from "react-redux";
import { setChannelInfo } from "../../features/counter/channelSlice";
import "./SidebarChannel.css";

function SidebarChannel({ id, channel }) {
  const dispatch = useDispatch();
  console.log({id, channel});
  return (
    <div
      className="sidebarChannel"
      onClick={() =>
        dispatch(
          setChannelInfo({
            channelId: id,
            channelName: channel,
          })
        )
      }
    >
      <h4>
        <span className="sidebarChannel__hash">#</span>{channel}
      </h4>
    </div>
  );
}

export default SidebarChannel;
