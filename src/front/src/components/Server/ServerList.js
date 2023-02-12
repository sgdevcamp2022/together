import { useState, useEffect } from "react";

function Server({server}) {
    return (
        <div>
            <b>{server.id}</b> <span>({server.name})</span>
        </div>
    );
}

const ServerList = ()=> {
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
    //  TODO 받은 내용으로 유저 정보 표시하는 부분 작성 필요
    <section>
      <div>
        {info.map((server,index) => (
            <Server server={server} key={index}/>
        ))}
      </div>
    </section>
  );
}

export default ServerList;
