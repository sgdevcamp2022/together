import { Avatar } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
import Modal from "react-modal";
import axios from "axios";
import React, { useEffect, useState } from "react";
import './MyInfo.css';

function MyInfo() {
  const userId = localStorage.getItem("userId");
  const token = localStorage.getItem("token");
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [info, setInfo] = useState([]);
  const [updated, setUpdated] = useState(false);

  // 사용자 수정 모드
  const [mode, setMode] = useState(false);
  const [inputName, setInputName] = useState("");
  const [inputPwd, setInputPwd] = useState("");

  // 내 정보 가져오기
  useEffect(() => {
    axios
      .get(`http://localhost:8000/user-service/user/${userId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        setInfo(res.data.user);
        setUpdated(false);
      });
  }, [token, userId, modalIsOpen, updated]);

  // 정보 수정
  const submitHandler = (event) => {
    event.preventDefault();

    axios
      .post(
        `http://localhost:8000/user-service/user/${userId}`,
        {
          name: `${inputName}`,
          pwd: `${inputPwd}`,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((res) => {
        setUpdated(true);
      });

    setInputName("");
    setInputPwd("");
  };

  return (
    <>
      <Avatar onClick={() => setModalIsOpen(true)} />
      <Modal className="modal" isOpen={modalIsOpen} onRequestClose={() => setModalIsOpen(false)}>
        <form onSubmit={submitHandler}>
          <h3>내 정보</h3>
          <CloseIcon className="close" onClick={()=>setModalIsOpen(false)}/>
          <div>
            {mode ? (
              <>
                이름 :
                <input
                  placeholder={info.name}
                  required
                  onChange={(event) => setInputName(event.target.value)}
                ></input>
              </>
            ) : (
              <p>이름 : {info.name}</p>
            )}
            <p>email : {info.email}</p>
            {mode ? (
              <>
                비밀번호 :
                <input
                  type="password"
                  required
                  placeholder="변경할 비밀번호"
                  onChange={(event) => setInputPwd(event.target.value)}
                ></input>
              </>
            ) : (
              <p>
                비밀번호 : <input value="****" readOnly disabled></input>
              </p>
            )}
            <p>생성 날짜 : {info.createdAt}</p>
            <p>최근 수정 : {info.updatedAt}</p>
          </div>
          <button
            onClick={() => {
              setMode(!mode);
            }}
          >
            수정하기
          </button>
        </form>
      </Modal>
    </>
  );
}

export default MyInfo;
