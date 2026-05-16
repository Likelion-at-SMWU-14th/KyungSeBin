from django.shortcuts import render

# Create your views here.

def introduction(request):
    context={
        'name':'경세빈',
        'age': 25,
        'hobby':['음악감상','여행','맛집탐방'],
        'introduce':'안녕하세요. 저는 경세빈입니다. 취미는 음악감상, 여행, 맛집탐방입니다.'
    }
    return render(request,'introduction.html',context)