from yapf.yapflib.yapf_api import FormatCode
from pylint import epylint as lint

inputCode = "print('hello'      )"

#format code example
output = FormatCode(inputCode)
print(output)

(pylint_stdout, pylint_stderr) = lint.py_run('test.py', return_std='True')

print(pylint_stdout, pylint_stderr)

