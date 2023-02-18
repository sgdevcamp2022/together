import React, { useEffect, useState } from "react";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import AddIcon from "@mui/icons-material/Add";
import FormControl from "@mui/material/FormControl";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import { useDispatch } from "react-redux";
import { setServerInfo } from "../../features/counter/serverSlice";
import axios from "axios";

function Dropdown() {
  const dispatch = useDispatch();
  const userId = localStorage.getItem("userId");
  const token = localStorage.getItem("token");
  const [server, setServer] = useState("");
  const [servers, setServers] = useState([]);
  const [updated,setupdated] = useState(false);

  const handleChange = (event: SelectChangeEvent) => {
    setServer(event.target.value);
    var obj = servers.find((e) => e.id === event.target.value);
    const serverId = obj.id;
    const serverName = obj.name;
    console.log({ serverId, serverName });
    dispatch(
      setServerInfo({
        serverId: serverId,
        serverName: serverName,
      })
    );
  };

  useEffect(() => {
    axios
      .get(`http://localhost:8000/channel-service/${userId}/servers`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        setServers(
          res.data.map((item) => ({
            id: item.id,
            name: item.name,
          }))
        );
        setupdated(false);
      });
  }, [token, userId,updated]);

  const handleAddServer = () => {
    const serverName = prompt("Enter a new server name");
    console.log(serverName);
    if (serverName) {
      // 서버에 채널 생성
      axios
      .post(`http://localhost:8000/channel-service/server`,{
        name: `${serverName}`,
        info: "this is info",
        userId: `${userId}`
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        
      }).then((res)=>{
        setupdated(true);
      })
    }
  };

  return (
    <>
      <FormControl sx={{ m: 1, minWidth: 180 }}>
        <InputLabel id="demo-simple-select-autowidth-label">
          Select Server
        </InputLabel>
        <Select
          labelId="demo-simple-select-autowidth-label"
          id="demo-simple-select-autowidth"
          value={server}
          onChange={handleChange}
          autoWidth
          label="Server"
        >
          {servers.map((item) => (
            <MenuItem name={item.name} key={item.id} value={item.id}>
              {item.name}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
      <AddIcon onClick={handleAddServer} />
    </>
  );
}

export default Dropdown;
