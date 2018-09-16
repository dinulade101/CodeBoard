import flask


app = flask.Flask(__name__)


app.route('/')
def home():
    return "I am running"


if __name__ == '__main__':
    app.run('0.0.0.0', port=8090, debug=True)