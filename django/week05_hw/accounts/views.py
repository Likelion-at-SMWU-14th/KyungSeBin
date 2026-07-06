from django.shortcuts import render

# Create your views here.
from rest_framework import generics
from .serializers import SignupSerializer

class SignupView(generics.CreateAPIView):
    serializer_class=SignupSerializer
    permission_classes=[] #회원가입 모두가 가능하게
    
    