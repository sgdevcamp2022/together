import { useRef } from "react";
import { useLocation } from "react-router-dom";

const Chatting = () => {
  const location = useLocation();
  const serverId = location.state?.serverId;
  const channelId = location.state?.channel;
  const userId = localStorage.getItem("userId");
  const iframeRef = useRef(null);

  function sendInfo() {
    iframeRef.current.contentWindow.postMessage(
      serverId+"."+channelId+"."+userId,
      `http://127.0.0.1:5500/src/components/Chat/test.html`
    );
  }

  return (
    // <div>
    //   서버ID : {serverId}, 채널id : {channelId}, 유저id : {userId}
    // </div>
    <div>
      <iframe
        onLoad={sendInfo}
        ref={iframeRef}
        src="http://127.0.0.1:5500/src/components/Chat/test.html"
      >
        로딩할 수 없습니다
      </iframe>
      <button onClick={sendInfo}>chatting 시작하기</button>
    </div>
  );
};

export default Chatting;
