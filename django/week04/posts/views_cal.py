from rest_framework.decorators import api_view
from rest_framework.response import Response


@api_view(['GET'])
def calculator_query(request):
    num1 = request.GET.get('num1', 0)
    num2 = request.GET.get('num2', 0)
    op = request.GET.get('op')

    num1 = int(num1)
    num2 = int(num2)

    if op == '+':
        result = num1 + num2
    elif op == '-':
        result = num1 - num2
    elif op == '*':
        result = num1 * num2
    elif op == '/':
        result = num1 / num2
    else:
        result = 0

    data = {
        'op': op,
        'result': result,
    }

    return Response(data)


@api_view(['POST'])
def calculator_body(request):
    data = request.data

    num1 = data.get('num1', 0)
    num2 = data.get('num2', 0)
    op = data.get('op')

    num1 = int(num1)
    num2 = int(num2)

    if op == '+':
        result = num1 + num2
    elif op == '-':
        result = num1 - num2
    elif op == '*':
        result = num1 * num2
    elif op == '/':
        result = num1 / num2
    else:
        result = 0

    data = {
        'op': op,
        'result': result,
    }

    return Response(data)