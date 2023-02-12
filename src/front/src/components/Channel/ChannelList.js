import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";

function Channel({ channel }) {
  // const channelId = channel.id;
  return (
    <div>
      <span>채널명 : {channel.name}</span>
      <p/>
      <span>채널 정보 : {channel.info}</span>
    </div>
  );
}

const ChannelList = () => {
  const userId = localStorage.getItem("userId");
  const token = localStorage.getItem("token");
  const location = useLocation();
  const serverId = location.state?.serverId;
  console.log(location);

  const [info, setInfo] = useState([]);

  console.log(`http://localhost:8000/channel-service/server/${serverId}`);
  useEffect(() => {
    fetch(`http://localhost:8000/channel-service/server/${serverId}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: token,
      },
    })
      .then((res) => res.json())
      .then((res) => {
        console.log(res.channelList);
        setInfo(res.channelList);
      })
      .catch((error) => console.log(error));
  }, [token, userId, serverId]);

  return (
    <section>
      <div>
        {info.map((channel, index) => (
          <Channel channel={channel} key={index} />
        ))}
      </div>
    </section>
  );
};

export default ChannelList;
