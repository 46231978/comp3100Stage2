All the executable files are include inside /pre-compile/aarch64

One other thing to keep in mind while executing this code is that, because the server is a C programme, the command line should contain the phrase “-n” to tell the computer to read this in byte format. The compile of the java code are instruct in the following:
- The file dsServer.class was created by using “javac dsServer.java”.
- The file dsJob.class was created by using “javac dsServer.java”.
- And the dsClient.class was created by using “javac dsClient.java”.

For example, the user has to open two terminal windows, and run the following command lines:
- First terminal window runs server side: ./ds-server -n -v all
- Second terminal window runs client side: java dsClient

For case of testing like week 11 testing, use command such as: python3 ./s2_test.py "java dsClient" -n -r results/ref_reults.json
