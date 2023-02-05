import { Link } from "react-router-dom";

function MainPage() {
  return (
    <div>
      <h1>메인페이지에요</h1>
      <Link to="/videoCall">
        <div>
          <button>webRTC</button>
        </div>
      </Link>
    </div>
  );
}

export default MainPage;
