import { configureStore } from "@reduxjs/toolkit";
import channelReducer from "../features/counter/channelSlice";
import serverReducer from "../features/counter/serverSlice";
import connectReducer from "../features/counter/connectSlice";

export const store = configureStore({
  reducer: {
    server: serverReducer,
    channel: channelReducer,
    connect: connectReducer,
  },
});
