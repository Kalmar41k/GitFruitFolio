import numpy as np
import tensorflow as tf

with open("labels.txt") as f:
    content = f.readlines()
label = []
for i in content:
    label.append(i[:-1])

def predict(test_image):
    model = tf.keras.models.load_model("fruits_360_model.h5")
    image = tf.keras.preprocessing.image.load_img(test_image, target_size=(100, 100))
    input_arr = tf.keras.preprocessing.image.img_to_array(image)
    input_arr = np.array([input_arr])
    predictions = model.predict(input_arr)
    return np.argmax(predictions)

test_image = "williams.jpg"
predict = predict(test_image)
print(label[predict])