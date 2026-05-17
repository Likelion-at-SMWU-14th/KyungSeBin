from django.shortcuts import render
from django.views.generic import ListView,CreateView
from django.urls import reverse_lazy
from .models import Diary
# Create your views here.

class DiaryCreateView(CreateView):
    model = Diary
    template_name = 'diary_form.html'
    fields = ['title', 'content', 'mood']
    success_url = reverse_lazy('diary-list')

def introduction(request):
    context={
        'name':'경세빈',
        'age': 25,
        'hobby':['음악감상','여행','맛집탐방'],
        'introduce':'안녕하세요. 저는 경세빈입니다. 취미는 음악감상, 여행, 맛집탐방입니다.'
    }
    return render(request,'introduction.html',context)

class DiaryListView(ListView):
    model = Diary
    template_name = 'diary_list.html'
    context_object_name = 'diaries'
    ordering=['-date']