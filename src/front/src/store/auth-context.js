import React, {useState} from "react";

const AuthContext = React.createContext({
    token: '',
    userId: '',
    isLoggedIn: false,
    login: (token) => {},
    logout: () => {}
});

// 이 컴포넌트를 다른 컴포넌트를 감싸는 래퍼로 사용하면
// 다른 컴포넌트가 이 컨텍스트에 접근할 수 있다.
export const AuthContextProvider = (props) => {
    const [token, setToken] = useState(null)

    const userIsLoggedIn = !!token; // 토큰이 있다면 로그인 상태로 인식

    const loginHandler = (token)=>{
        setToken(token);
    }
    const loggoutHandler = ()=>{
        setToken(null);
    }
    const contextValue = {
        token: token,
        isLoggedIn: userIsLoggedIn,
        login: loginHandler,
        logout: loggoutHandler,
    };

    return <AuthContext.Provider>{props.children}</AuthContext.Provider>
};

export default AuthContext;