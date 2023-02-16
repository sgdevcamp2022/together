import { AddCircle, CardGiftcard, EmojiEmotions, GifTwoTone } from '@mui/icons-material'
import React from 'react'
import { useSelector } from 'react-redux'
import { selectChannelId, selectChannelName } from '../../features/counter/channelSlice'
import { selectServerId, selectServerName } from '../../features/counter/serverSlice'
import Message from '../Message/Message'
import './Chat.css'
import ChatHeader from './ChatHeader'

function Chat() {
    const channelId = useSelector(selectChannelId);
    const channelName = useSelector(selectChannelName);
    const serverId = useSelector(selectServerId);
    const serverName = useSelector(selectServerName);

    console.log(channelId, serverId);
  return (
    <div className='chat'>
        <ChatHeader channelName={channelName} />

        <div className='chat__messages'>
            <Message />
        </div>
        <div className='chat__input'>
            <AddCircle fontSize='large'/>
            <form>
                <input placeholder={`Message #TestChannel`}/>
                <button className='chat__inputButton' type='submit'>Send Message</button>
            </form>
            <div className='chat__inputIcons'>
                <CardGiftcard fontSize='large'/>
                <GifTwoTone fontSize='large'/>
                <EmojiEmotions fontSize='large'/>
            </div>
        </div>
    </div>
  )
}

export default Chat