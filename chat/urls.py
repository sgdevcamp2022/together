from django.urls import path
from chat import views

app_name = "chat"

urlpatterns = [
    path("", views.index, name="index"),
    path("<str:room_name>/chat/<str:user_id>", views.room_chat, name="room_chat"),
    path("<str:room_name>/chat/<str:user_id>/get_chat_history/<str:page>", views.get_chat_history, name='get_chat_history'),
    path("<str:room_name>/chat/<str:user_id>/getonlinemember/", views.get_onlinemember, name='get_onlinemember'),
    path("<str:room_name>/chat/<str:user_id>/getofflinemember/", views.get_offlinemember, name='get_offlinemember'),
]