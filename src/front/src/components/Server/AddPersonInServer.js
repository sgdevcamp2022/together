import React from "react";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import axios from "axios";

function AddPersonInServer({ server }) {
  const userId = localStorage.getItem("userId");
  const token = localStorage.getItem("token");

  const addPersonHandler = () => {
    const inviteEmail = prompt("Enter friend's email");
    console.log(server);
    console.log(inviteEmail);
    if (inviteEmail) {
      // 서버에 채널 생성
      axios
        .post(
          `http://localhost:8000/channel-service/server/${server}/member`,
          {
            userEmail: inviteEmail ,
          },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )
        .then((res) => {
          prompt.apply("사용자가 추가되었습니다.");
        });
    }
  };

  return (
    <>
      <PersonAddIcon style={{ color: "white" }} onClick={addPersonHandler} />
    </>
  );
}

export default AddPersonInServer;
