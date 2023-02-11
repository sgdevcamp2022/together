import React, {useCallback, useEffect, useState} from "react";

let logoutTimer;

const AuthContext = React.createContext({
    token: '',
    userId: '',
    isLoggedIn: false,
    login: (token) => {},
    logout: () => {}
});

const retrieveStoredToken = () => {
    //새로고침 했을 때 남은 토큰과 유효시간 가져오기
    const storedToken = localStorage.getItem('token');
    const storedExpirationDate = localStorage.getItem('expirationTime');

    const remainingTime = calculateRemainingTime(storedExpirationDate);

    if (remainingTime <= 3600) {
        localStorage.removeItem('token');
        localStorage.removeItem('expirationTime');
        return null;
    }

    //남은 시간이 있다면 저장된 토큰을 리턴
    return {
        token: storedToken,
        duration: remainingTime
    };
};

const calculateRemainingTime = (expirationTime) => {
    const currentTime = new Date().getTime();
    const adjExpirationTime = new Date(expirationTime).getTime();

    const remainingDuration = adjExpirationTime - currentTime;

    return remainingDuration;
};

// 이 컴포넌트를 다른 컴포넌트를 감싸는 래퍼로 사용하면
// 다른 컴포넌트가 이 컨텍스트에 접근할 수 있다.
export const AuthContextProvider = (props) => {
    const tokenData = retrieveStoredToken();
    let initialToken;
    if (tokenData) {
        initialToken = tokenData.token;
    }
    const [token, setToken] = useState(initialToken);

    const userIsLoggedIn = !!token; // 토큰이 있다면 로그인 상태로 인식

    const logoutHandler = useCallback(()=>{
        setToken(null);
        localStorage.removeItem('token');
        localStorage.removeItem('expirationTime');
        localStorage.removeItem('userId');

        if(logoutTimer) {
            clearTimeout(logoutTimer);
        }
    },[]);

    const loginHandler = (token, expirationTime, userId)=>{
        setToken(token);
        localStorage.setItem('token',token);
        localStorage.setItem('expirationTime', expirationTime);
        localStorage.setItem('userId',userId);
        const remainingTime = calculateRemainingTime(expirationTime);

        //만료 시간이 되면 로그아웃
        logoutTimer = setTimeout(logoutHandler, remainingTime);
    };

    useEffect(() =>{
        if (tokenData) {
            //자동 로그인으로 로그인 하여 토큰 데이터를 가져왔다면
            //만료 기간을 설정한다.
            console.log(tokenData.duration);
            logoutTimer = setTimeout(logoutHandler, tokenData.duration);
        }
    }, [tokenData, logoutHandler]);
    //무한루프에 빠지지 않도록 핸들러에 콜백설정 필요

    const contextValue = {
        token: token,
        isLoggedIn: userIsLoggedIn,
        login: loginHandler,
        logout: logoutHandler,
    };

    return <AuthContext.Provider value={contextValue}>{props.children}</AuthContext.Provider>
};

export default AuthContext;