import {useContext, useRef} from "react";

import AuthContext from "../../store/auth-context";
import classes from './ProfileForm.module.css';

const ProfileForm = () => {
    const newPasswordInputRef = useRef();
    const authCtx = useContext(AuthContext);

    const submitHandler = event => {
        event.preventDeafult();

        const enteredNewPassword = newPasswordInputRef.current.value;

    //    옵션 add validation

        fetch('http://localhost:8000/user',
            {method: 'GET',
                body: JSON.stringify({
                }),
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer '+authCtx.token
                }
            }).then(res => {
        //        항상 성공 응답이온다고 가정

        })
    };
  return (
    //  TODO 받은 내용으로 유저 정보 표시하는 부분 작성 필요
    <form className={classes.form}>
      <div className={classes.control}>
        <label htmlFor='new-password'>New Password</label>
        <input type='password' id='new-password' minLength="4" ref={newPasswordInputRef}/>
      </div>
      <div className={classes.action}>
        <button>Change Password</button>
      </div>
    </form>
  );
}

export default ProfileForm;
