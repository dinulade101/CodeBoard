from yapf.yapflib.yapf_api import FormatCode
from pylint import epylint as lint
import autopep8

'''inputCode = "printhello      )"

#format code example
try:
    output = FormatFile("test.py", in_place=True)
except:
    print("hello")
print(open("test.py").read())
'''
#(pylint_stdout, pylint_stderr) = lint.py_run('test.py', return_std='True')

#print pylint_stdout.read()

#FormatCode('def Foo(): print("hello")')
print(autopep8.fix_code('def Foo(): \n print("hello")'))

