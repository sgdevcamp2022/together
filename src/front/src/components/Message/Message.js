import { Avatar } from '@mui/material'
import React from 'react'
import './Message.css'

function Message() {
  return (
    <div className='message'>
        <Avatar/>
        <div className='message_info'>
            <h4>dwqwd
                <span className='message_timestamp'>timestamp</span>
            </h4>
            <p>This is a message</p>
        </div>
    </div>
  )
}

export default Message