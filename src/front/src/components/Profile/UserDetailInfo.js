function Friend({f}) {
    return (
        <div>
            <b>{f.email}</b>
            <b>{f.name}</b>
        </div>
    );
}

function UserDetailInfo (props) {
    const email = props.info.email;
    const name = props.info.name;
    const createdAt = props.info.createdAt;
    const updatedAt = props.info.updatedAt;
    
    // json array인데 렌더링 변경 필요
    const friendList = props.info.friendList;
    const serverList = props.info.serverList;

    return (
    <ul>
        <li>email : {email}</li>
        <li>이름 : {name}</li>
        <li>생성날짜 : {createdAt}</li>
        <li>최근수정 : {updatedAt} </li>
        <li>친구 목록 : {JSON.stringify(friendList)}</li>
        <li>서버 목록 : {JSON.stringify(serverList)}</li>
    </ul>);
}

export default UserDetailInfo;