from django.db import models
from django.contrib.auth.models import AbstractUser, UserManager as DjangoUserManager

# Create your models here.
class UserManager(DjangoUserManager):
    def _create_user(self, username, email, password, phone=None, **extra_fields):
        if not phone:
            raise ValueError("전화번호는 필수값입니다.")
        user = self.model(username=username, email=self.normalize_email(email), phone=phone, **extra_fields)
        user.set_password(password)
        user.save(using=self._db)
        return user
    
    def create_user(self, username, email=None, password=None, phone=None, **extra_fields):
        extra_fields.setdefault('is_staff', False)
        extra_fields.setdefault('is_superuser', False)
        return self._create_user(username, email, password, phone, **extra_fields)
    
    def create_superuser(self, username, email=None, password=None, phone=None, **extra_fields):
        extra_fields.setdefault('is_staff', True)
        extra_fields.setdefault('is_superuser', True)
        return self._create_user(username, email, password, phone, **extra_fields)

class User(AbstractUser):
    phone = models.CharField(verbose_name='전화번호', max_length=11, unique=True)
    REQUIRED_FIELDS = ['phone']
    objects = UserManager()
