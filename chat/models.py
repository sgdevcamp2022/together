from django.db import models

# Create your models here.
class Chat(models.Model):
    text = models.CharField(max_length=200)
    channel_id = models.CharField(max_length=200)
    created_at = models.IntegerField()
    user_id = models.CharField(max_length=200)

    def __str__(self):
        return self.text

class RoomMember(models.Model):
    user_id = models.CharField(max_length=200)
    channel_id = models.CharField(max_length=200)

class OnlineMember(models.Model):
    user_id = models.CharField(max_length=200)
    channel_id = models.CharField(max_length=200)