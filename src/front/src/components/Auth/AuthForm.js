import { useState, useRef, useContext } from 'react';
import {useHistory} from "react-router-dom";

import classes from './AuthForm.module.css';
import AuthContext from "../../store/auth-context";

const AuthForm = () => {
  const history = useHistory();
  const emailInputRef = useRef();
  const passwordInputRef = useRef();
  const nameInputRef = useRef();

  const authCtx = useContext(AuthContext);

  const [isLogin, setIsLogin] = useState(true);
  const [isLoading, setIsLoading] = useState(false);

  const switchAuthModeHandler = () => {
    setIsLogin((prevState) => !prevState);
  };

  const submitHandler = (event) =>{
    event.preventDefault();

    // ref를 지정한 email, password 영역에서 데이터를 추출해올 수 있다.
    const enteredEmail = emailInputRef.current.value;
    const enteredPassword = passwordInputRef.current.value;

  //  옵션 : validation 추가 가능

    //
    setIsLoading(true);
    if (isLogin) {
      //서버에 로그인 요청
      fetch('http://localhost:8000/user-service/login',
          {
            method : 'POST',
            body : JSON.stringify({
              email: enteredEmail,
              password : enteredPassword
            }),
            headers: {
              'Content-Type': 'application/json'
            }
          }
      ).then((res)=>res.json())
        .then(res => {
          setIsLoading(false);
          //  로그인 통신이 완료된 경우
          if(res.atk != null) {
            const expirationTime = new Date(
                new Date().getTime() + (res.expirationTime * 1000)
            );
            // context에 저장
            authCtx.login(res.atk, expirationTime.toISOString());
            // 사용자를 홈 페이지로 redirect.
            history.replace('/');
          }else {
            // 통신이 실패하면 에러 내용 출력
            let errorMessage = 'Authentication failed!';
            alert(errorMessage);
          }
      })}
    else {
      const enteredName = nameInputRef.current.value;
      //서버에 회원가입 요청
      fetch('http://localhost:8000/user-service/user',
          {
            method : 'POST',
            body : JSON.stringify({
              email: enteredEmail,
              name: enteredName,
              pwd : enteredPassword
            }),
            headers: {
              'Content-Type': 'application/json'
            }
          }
      ).then(res => {
        setIsLoading(false);
        if(res.ok) {
        //  회원가입 통신이 완료된 경우
        } else {
        // 통신이 실패하면 에러 내용 출력
          return res.json().then(data => {
            let errorMessage = 'Authentication failed!';
            // if (data && data.error && data.error.message) {
            //   errorMessage = data.error.message;
            // } // 서버에서 받은 에러 그대로 출력 가능
            throw new Error(errorMessage);
          });
        }
      }).then((res)=>{
        // 사용자를 홈 페이지로 redirect.
        history.replace('/');
      })
        .catch((err) => {
          alert(err.message);
      });
    }
  };

  return (
    <section className={classes.auth}>
      <h1>{isLogin ? 'Login' : 'Sign Up'}</h1>
      <form onSubmit={submitHandler}>
        <div className={classes.control}>
          <label htmlFor='email'>Your Email</label>
          <input type='email' id='email' required ref={emailInputRef} />
        </div>
        {!isLogin && <div className={classes.control}>
          <label htmlFor='name'>Your Name</label>
          <input type='name' id='name' required ref={nameInputRef} />
        </div>}
        <div className={classes.control}>
          <label htmlFor='password'>Your Password</label>
          <input type='password' id='password' required ref={passwordInputRef} />
        </div>
        <div className={classes.actions}>
          {!isLoading && <button>{isLogin ? 'Login' : 'Create Account'}</button>}
          {isLoading && <p>Sending request.</p>} {/* 로딩중임을 표시 */}
          <button
            type='button'
            className={classes.toggle}
            onClick={switchAuthModeHandler}
          >
            {isLogin ? 'Create new account' : 'Login with existing account'}
          </button>
        </div>
      </form>
    </section>
  );
};

export default AuthForm;
