# Sprout Platform Engineering Homework

To run the project, you'll need [Java 11 JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) installed and setup on your system along with [Apache Maven](https://maven.apache.org/install.html).

To use maven to install required dependencies and build the project, run the following at the root of the project:

```sh
mvn package
```

To start a Dropwizard server running your code, run the following at the root of the project:

```sh
java -jar target/homework-1.0.0-SNAPSHOT.jar server config.yml
```

Then you can hit the example endpoint using your browser or curl:

```sh
curl http://localhost:8080/hello
```````

To run unit tests, run the following at the root of the project:

```sh
mvn test
```

You can also use an IDE like IntelliJ to make the above easier.

When you are ready to submit your work, run the following to create a zip file:

```sh
make
```

Be aware that the resulting zip file will exclude the `target` directory, as well as any dotfiles. If you want to verify
the contents of the file, simply run:

```sh
unzip -l <YOUR ZIP FILE>
```

If you would like to leave any notes or instructions for our graders, please do so in a file named `README`.
