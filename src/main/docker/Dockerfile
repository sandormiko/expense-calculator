from openjdk:8

ADD expense-calculator-0.1.0.jar expense-calculator.jar
ADD server-app.yml app-config.yml
RUN sh -c 'touch expense-calculator app-config.yml'
CMD java -jar expense-calculator server app-config.yml
