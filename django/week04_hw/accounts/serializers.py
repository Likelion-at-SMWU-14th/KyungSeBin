from rest_framework import serializers
from .models import User

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model=User
        fields='__all__'
        
#Python -> JSON으로 변환 ; @api_view에서 return Response

