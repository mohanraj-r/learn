import tensorflow as tf 

sess = tf.compat.v1.Session()

helloStr = tf.constant("Hello")
print(sess.run(helloStr))

a = tf.constant(20)
b = tf.constant(30)
print('a+b = {0}'.format(sess.run(a+b)))

