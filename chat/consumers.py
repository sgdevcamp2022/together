from asgiref.sync import async_to_sync
from channels.generic.websocket import JsonWebsocketConsumer
from .models import Chat, OnlineMember, RoomMember
from datetime import datetime
from django.utils import timezone
from json import dumps


class ChatConsumer(JsonWebsocketConsumer):

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.group_name = ""

    def connect(self):
        self.room_name = self.scope["url_route"]["kwargs"]["room_name"]
        self.user_id = self.scope["url_route"]["kwargs"]["user_id"]
        self.group_name = f"chat-{self.room_name}"
        
        if(len(OnlineMember.objects.filter(user_id= self.user_id, channel_id=self.room_name ).values())==0):
            data = OnlineMember.objects.create(
                user_id = self.user_id,
                channel_id = self.room_name,
            )

        if(len(RoomMember.objects.filter(user_id= self.user_id, channel_id=self.room_name ).values())==0):
            data = RoomMember.objects.create(
                user_id = self.user_id,
                channel_id = self.room_name,
            )

        async_to_sync(self.channel_layer.group_add)(
            self.group_name,
            self.channel_name,
        )
        
        async_to_sync(self.channel_layer.group_send)(
            self.group_name,
            {
                "type": "chat.user.join",
                "message": "",
                "sender": self.scope['url_route']['kwargs']['user_id'],
                "created_at": "",
            }
        )

        self.accept()

    def disconnect(self, code):
        data = OnlineMember.objects.filter(user_id=self.scope['url_route']['kwargs']['user_id'], channel_id=self.scope['url_route']['kwargs']['room_name'])
        data.delete()

        if self.group_name:
            async_to_sync(self.channel_layer.group_discard)(
                self.group_name,
                self.channel_name,
            )
        
        async_to_sync(self.channel_layer.group_send)(
            self.group_name,
            {
                "type": "chat.user.leave",
                "message": "",
                "sender": self.scope['url_route']['kwargs']['user_id'],
                "created_at": "",
            }
        )

    def receive_json(self, content, **kwargs):
        _type = content["type"]

        if _type == "chat.message":
            sender = content["sender"]
            message = content["message"]
            created_at = content["created_at"]
            async_to_sync(self.channel_layer.group_send)(
                self.group_name,
                {
                    "type": "chat.message",
                    "message": message,
                    "sender": sender,
                    "created_at": created_at,
                }
            )
            data = Chat.objects.create(
                text = message,
                user_id = sender,
                channel_id = self.scope["url_route"]["kwargs"]["room_name"],
                created_at = created_at,
            )

        else:
            print(f"Invalid message type : ${_type}")

    def chat_user_join(self, message_dict):
        self.send_json({
            "type": "chat.user.join",
            "message": "",
            "sender": message_dict["sender"],
            "created_at": "",
        })
    
    def chat_user_leave(self, message_dict):
        self.send_json({
            "type": "chat.user.leave",
            "message": "",
            "sender": message_dict["sender"],
            "created_at": "",
        })


    def chat_message(self, message_dict):
        self.send_json({
            "type": "chat.message",
            "message": message_dict["message"],
            "sender": message_dict["sender"],
            "created_at": message_dict["created_at"],
        })