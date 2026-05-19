from django import forms

class PostBaseForm(forms.Form):
    image=forms.ImageField()
    content=forms.CharField(min_length=5)
    