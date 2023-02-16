import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import ChannelList from "../Channel/ChannelList";

function Server({ server }) {
  const serverId = server.id;
  return (
    <Link to={{
      pathname: "/channel",
      state: {
        serverId: serverId
      }
    }}>
      <div>
        <b>{server.id}</b> <span>서버명 : {server.name}</span>
        <span>서버 정보 : {server.info}</span>
      </div>
    </Link>
  );
}

const ServerList = () => {
  const userId = localStorage.getItem("userId");
  const token = localStorage.getItem("token");

  const [info, setInfo] = useState([]);

  console.log(`http://localhost:8000/channel-service/${userId}/servers`);
  useEffect(() => {
    fetch(`http://localhost:8000/channel-service/${userId}/servers`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: token,
      },
    })
      .then((res) => res.json())
      .then((res) => {
        console.log(res);
        setInfo(res);
      })
      .catch((error) => console.log(error));
  }, [token, userId]);

  return (
    <section>
      <div>
        {info.map((server, index) => (
          <Server server={server} key={index} />
        ))}
      </div>
    </section>
  );
};

export default ServerList;
