The ds-server include in the file is the server that running on aarch64 architecture. I have included the src file in case using of different computer system.

One other thing to keep in mind while executing this code is that, because the server is a C programme, the command line should contain the phrase “-n” to tell the computer to read this in byte format. The compile of the java code are instruct in the following:
- The file dsServer.class was created by using “javac dsServer.java”.
- The file dsJob.class was created by using “javac dsServer.java”.
- And the dsClient.class was created by using “javac dsClient.java”.

For example, the user has to open two terminal windows, and run the following command lines:
- First terminal window runs server side: ./ds-server -n -v all
- Second terminal window runs client side: java dsClient

For case of testing like week 6 testing, use “dsClient.class” with “-n” to execute the test. For example, ./S1Tests- wk6.sh dsClient.class -n.
