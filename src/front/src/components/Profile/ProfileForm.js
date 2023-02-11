import { useEffect, useState } from "react";

import classes from "./ProfileForm.module.css";
import UserDetailInfo from "./UserDetailInfo";

const ProfileForm = (props) => {
  const userId = localStorage.getItem("userId");
  const token = localStorage.getItem("token");

  const [info, setInfo] = useState([]);


  console.log(`http://localhost:8000/user-service/user/${userId}`);
  useEffect(() => {
    fetch(`http://localhost:8000/user-service/user/${userId}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: token,
      },
    })
      .then((res) => res.json()) // json은 body에 있는 정보만 바꿔준다.
      .then((res) => {
        //        항상 성공 응답이온다고 가정
        console.log(res.user);
        setInfo(res.user);
      })
      .catch((error) => console.log(error));
  }, [token, userId]);

  return (
    //  TODO 받은 내용으로 유저 정보 표시하는 부분 작성 필요
    <section className={classes.profile}>
      <UserDetailInfo info={info}/>
    </section>
  );
};
export default ProfileForm;
