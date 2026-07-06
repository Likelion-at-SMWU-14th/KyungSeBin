from django.shortcuts import render
from rest_framework import generics
from rest_framework.viewsets import ModelViewSet
from rest_framework import permissions

from .models import Post
from .serializers import (
    PostModelSerializer,
    PostListSerializer,
    PostRetrieveSerializer,
    PostCreateSerializer,
)
from .permissions import IsOwnerOrReadOnly
from rest_framework.permissions import IsAuthenticatedOrReadOnly


class PostListView(generics.ListAPIView):
    queryset = Post.objects.all()
    serializer_class = PostListSerializer


class PostModelViewSet(ModelViewSet):
    queryset = Post.objects.all()
    
    permission_classes=[IsAuthenticatedOrReadOnly, IsOwnerOrReadOnly]
    def get_serializer_class(self):
        if self.action == "list":
            return PostListSerializer
        elif self.action == "retrieve":
            return PostRetrieveSerializer
        elif self.action in ["create", "update", "partial_update"]:
            return PostCreateSerializer
        return PostListSerializer
    
    def perform_create(self, serializer):
        serializer.save(writer=self.request.user)
        #로그인한 사용지를 writer로 자동 저장하기
        
        