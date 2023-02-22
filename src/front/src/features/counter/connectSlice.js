import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  value: false,
};

export const connectSlice = createSlice({
  name: "connect",
  initialState,
  reducers: {
    setConnect: (state, action) => {
      state.value = action.payload.value;
    },
  },
});

export const { setConnect } = connectSlice.actions;

export const selectConnectValue = (state) => state.connect.value;

export default connectSlice.reducer;
