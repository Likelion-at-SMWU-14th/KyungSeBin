from django import forms
from .models import Post,Comment

class PostBaseForm(forms.Form):
    image=forms.ImageField()
    content=forms.CharField(min_length=5)
class PostModelForm(forms.ModelForm):
    class Meta:
        model=Post
        fields='__all__'  
class CommentForm(forms.ModelForm):
    class Meta:
        model=Comment
        fields=['content']
