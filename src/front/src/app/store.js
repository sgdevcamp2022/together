import { configureStore } from '@reduxjs/toolkit';
import channelReducer from '../features/counter/channelSlice';
import serverReducer from '../features/counter/serverSlice';

export const store = configureStore({
  reducer: {
    server: serverReducer,
    channel: channelReducer,
  },
});
