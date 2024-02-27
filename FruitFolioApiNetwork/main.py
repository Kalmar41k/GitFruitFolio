from flask import Flask, request, jsonify
from PIL import Image
import numpy as np
import tensorflow as tf

app = Flask(__name__)
model = tf.keras.models.load_model("fruits_360_model.h5")

with open("labels.txt") as f:
    content = f.readlines()
label = []
for i in content:
    label.append(i[:-1])

@app.route('/test', methods=['GET'])
def test():
    return 'Server is up and running!'

@app.route('/predict', methods=['POST'])
def predict():
    file = request.files['image']
    image = Image.open(file.stream).convert("RGB")
    image = image.resize((100, 100))
    input_arr = tf.keras.preprocessing.image.img_to_array(image)
    input_arr = np.array([input_arr])
    predictions = model.predict(input_arr)
    predicted_class = np.argmax(predictions)
    return jsonify({'description': label[predicted_class]})


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
