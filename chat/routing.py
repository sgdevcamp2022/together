from django.urls import path

from chat import consumers

websocket_urlpatterns = [
    path("ws/chat/<str:room_name>/chat/<str:user_id>", consumers.ChatConsumer.as_asgi()),
]