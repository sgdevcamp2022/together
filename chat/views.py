from django.shortcuts import render
from django.http import JsonResponse
from .models import Chat, RoomMember, OnlineMember
import json

def list_to_json(my_list):
    json_array = json.dumps(my_list)
    return json_array

def index(request):
    return render(request, "chat/index.html")


def room_chat(request, room_name, user_id):
    return render(request, "chat/room_chat.html", {
        "room_name": room_name,
        "user_id": user_id,
    })


def get_chat_history(request, room_name, user_id, page):
    messages = Chat.objects.filter(channel_id = room_name, created_at__lt=int(page)).order_by('-created_at').values()[:10]
    a = []
    nums = len(messages)-1
    for i in range(0,len(messages)):
        data = {'message': messages[i]['text'], 'sender': messages[i]['user_id'], 'created_at':messages[i]['created_at'],}
        a.append(data)
    json_array = json.dumps(a)
    return JsonResponse(json.loads(json_array), safe=False)

def get_onlinemember(request, room_name, user_id):
    username_list = OnlineMember.objects.filter(channel_id= room_name).values_list('user_id', flat=True)
    print(list(username_list))
    return JsonResponse({
        "username_list": list(username_list),
    })

def get_offlinemember(request, room_name, user_id):
    q1 = OnlineMember.objects.filter(channel_id= room_name).values_list('user_id', flat=True)
    q2 = RoomMember.objects.filter(channel_id= room_name).values_list('user_id', flat=True)
    username_list = q2.difference(q1).values_list('user_id', flat=True)
    print(list(username_list))
    return JsonResponse({
        "username_list": list(username_list),
    })
    