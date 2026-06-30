from rest_framework.serializers import ModelSerializer

from .models import Post


class PostModelSerializer(ModelSerializer):
    class Meta:
        model = Post
        fields = '__all__'


class PostListSerializer(PostModelSerializer):
    pass


class PostRetrieveSerializer(PostModelSerializer):
    class Meta(PostModelSerializer.Meta):
        depth = 1


class PostCreateSerializer(PostModelSerializer):
    class Meta(PostModelSerializer.Meta):
        fields = ['image', 'content']