from django.shortcuts import render
from rest_framework import generics
from rest_framework.viewsets import ModelViewSet

from .models import Post
from .serializers import (
    PostModelSerializer,
    PostListSerializer,
    PostRetrieveSerializer,
    PostCreateSerializer,
)


class PostListView(generics.ListAPIView,generics.CreateAPIView):
    queryset = Post.objects.all()
    
    def get_serializer_class(self):
        if self.request.method=='POST':
            return PostCreateSerializer
        return PostListSerializer


class PostModelViewSet(ModelViewSet):
    queryset = Post.objects.all()
    
    def get_serializer_class(self):
        if self.action=='list':
            return PostListSerializer
        elif self.action=='retrieve':
            return PostRetrieveSerializer
        elif self.action in ['create','update','partial_update']:
            return PostCreateSerializer
        return PostListSerializer
    
class PostRetrieveView(generics.RetrieveAPIView,generics.UpdateAPIView,generics.DestroyAPIView):
    queryset = Post.objects.all()
    
    def get_serializer_class(self):
        if self.request.method in ['PUT','PATCH']:
            return PostCreateSerializer
        return PostRetrieveSerializer