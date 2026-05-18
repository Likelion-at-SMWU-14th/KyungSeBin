from django.db import models

# Create your models here.
from django.db import models

class Diary(models.Model):
    title = models.CharField(max_length=100)
    content = models.TextField()
    date = models.DateField(auto_now_add=True)
    mood = models.CharField(max_length=20, default='😊')

    def __str__(self):
        return self.title