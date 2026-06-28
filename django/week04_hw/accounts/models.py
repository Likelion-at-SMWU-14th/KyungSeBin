from django.db import models

# Create your models here.

class User(models.Model):
    username=models.CharField(max_length=50,unique=True)
    password=models.CharField(max_length=200)
    email=models.EmailField(unique=True) #중복 불가능하게 -> unique=True
    created_at=models.DateTimeField(auto_now_add=True) #회원가입일
    