import { PeopleAltRounded } from "@mui/icons-material";
import Modal from "react-modal";
import React, { useEffect, useState } from "react";
import axios from "axios";
import ChatBubbleIcon from "@mui/icons-material/ChatBubble";
import CloseIcon from "@mui/icons-material/Close";
import DeleteIcon from "@mui/icons-material/Delete";
import AddIcon from "@mui/icons-material/Add";

function FriendList() {
  const userId = localStorage.getItem("userId");
  const token = localStorage.getItem("token");
  const [modalState, setModalState] = useState(false);
  const [info, setInfo] = useState([]);
  const [friends, setFriends] = useState([]);
  const [updated, setUpdated] = useState(false);

  useEffect(() => {
    axios
      .get(`http://localhost:8000/user-service/user/${userId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        setInfo(res.data.user);
        setFriends(res.data.user.friendList);
        setUpdated(false);
      });
  }, [updated]);

  const delFriendHandler = (email) => {
    console.log(info.email);
    console.log(email);
    axios
      .delete(`http://localhost:8000/user-service/friend`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },

        data: { myEmail: info.email, friendEmail: email },
      })
      .then((res) => {
        setUpdated(true);
        alert("친구 삭제가 성공했습니다.");
      })
      .catch((err) => {
        alert(err.message);
      });
  };

  const addFriendHandler = () => {
    const friendEmail = prompt("Enter friend's email");

    if (friendEmail) {
      axios
        .post(
          `http://localhost:8000/user-service/friend`,
          {
            myEmail: info.email,
            friendEmail: friendEmail,
          },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )
        .then((res) => {
          setUpdated(true);
          alert("친구 추가가 성공했습니다.");
        })
        .catch((err) => {
          alert(err.message);
        });
    }
  };

  return (
    <>
      <PeopleAltRounded
        onClick={() => {
          setModalState(true);
          console.log(modalState);
        }}
      />
      <Modal
        className="modal"
        isOpen={modalState}
        onRequestClose={() => setModalState(false)}
      >
        <h3>
          친구 리스트{" "}
          <AddIcon className="addbtn" onClick={() => addFriendHandler()} />
        </h3>
        <CloseIcon className="close" onClick={() => setModalState(false)} />
        {friends.map((item) => (
          <div className="listItem" key={item}>
            <h4>{item.name}</h4>
            <div>{item.email}</div>
            <ChatBubbleIcon />
            <DeleteIcon onClick={() => delFriendHandler(item.email)} />
          </div>
        ))}
      </Modal>
    </>
  );
}

export default FriendList;
