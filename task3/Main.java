// 12.09.2021
// LevSergeev
//
// Task3
//
// Обязательные входные параметры:
// -fileName - tests.json
// -fileName - values.json
//

package task3;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    // Параметры вывода файла
    public static final String SYSTEM_PROPERTY_USERDIR = "user.dir";
    public static final String OUTPUT_FILENAME = "report.json";

    public static void main(String[] args) {
		System.out.println(System.getProperty(SYSTEM_PROPERTY_USERDIR));
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(new File(args[0])));

            // Файл tests.json
            StringBuffer fileTests = new StringBuffer();

            // Файл values.json
            StringBuffer fileValues = new StringBuffer();

            // Файл reports.json
            StringBuffer fileReports = new StringBuffer();

            // Читаем файл tests.json
            while(fileReader.ready()){
                fileTests.append(fileReader.readLine()).append(System.lineSeparator());
            }

            fileReader.close();
            fileReader = new BufferedReader(new FileReader(new File(args[1])));

            // Читаем файл values.json
            while(fileReader.ready()){
                fileValues.append(fileReader.readLine()).append(System.lineSeparator());
            }

            fileReader.close();

            // Паттерн "id": 0 ... "value": " // где ... - любые символы
            Pattern patternID = Pattern.compile("\"id\": (\\d+)[,\\s\":\\w\\d]*\"value\": \"(\\w*)");
            Matcher matcher = patternID.matcher(fileValues);  // values.json

            // Map для хренения id = values из values.json
            Map<Integer, String> values = new HashMap<>();

            // Заполняем мэп значениями из файла values.json
            while (matcher.find()){
                values.put(Integer.parseInt(matcher.group(1)), matcher.group(2));
            }

            matcher = patternID.matcher(fileTests); // tests.json

            // Ищем "id": 0 ... "value": " и вставляем в новый файл value из мэпа по id
            while (matcher.find()){
                String replacement = values.get(Integer.parseInt(matcher.group(1)));
                matcher.appendReplacement(fileReports, matcher.group() + replacement);
            }
            matcher.appendTail(fileReports);

            // Пишем reports.json
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(new File(System.getProperty(SYSTEM_PROPERTY_USERDIR) + OUTPUT_FILENAME)));
            fileWriter.write(fileReports.toString());

            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


// Попытка с com.fasterxml.jackson.core

/*
// 11.09.2021
// LevSergeev
//
// Task3
//
// Обязательные входные параметры:
// -fileName - tests.json
// -fileName - values.json
//
/* example:


tests
{	"id": 	122,
	"title": 	"Security test",
	"value": 	"",
	"values":	[{	"id": 	    5321,
			        "title": 	"Confidentiality",
			        "value": 	""},

                 {	"id": 	    5322,
                    "title": 	"Integrity",
                    "value": 	""}]
}


values
{	"values":	 [{	"id":	    122,
			        "value": 	"failed"},

                  {	"id": 	    5321,
                    "value": 	"passed"},

                  {	"id": 	    5322,
                    "value":	"failed"}]}


report
{	"id": 	122,
	"title": 	"Security test",
	"value": 	"failed",
	"values":	[{	"id": 	5321,
			        "title": 	"Confidentiality",
			        "value": 	"passed"},

                 {	"id": 	5322,
                    "title": 	"Integrity",
                    "value": 	"failed"}]
}
 */

// com.fasterxml.jackson.core
/*
package task3.ver1;

        import com.fasterxml.jackson.databind.ObjectMapper;

        import java.io.File;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Comparator;
        import java.util.List;

public class Main {

    public static final String TESTS_FILE = "C:\\Users\\User\\IdeaProjects\\2021_09_10_PLTestTasks\\src\\task3\\tests.json";
    public static final String VALUES_FILE = ""C:\\Users\\User\\IdeaProjects\\2021_09_10_PLTestTasks\\src\\task3\\values.json"";

    public static void main(String[] args) throws IOException {
        List<Test> testList = new ArrayList<>();
        List<Value> valuesList = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        Tests tests = objectMapper.readValue(new File(TESTS_FILE), Tests.class);
        addAllTests(tests.getTests(), testList);

        Values values = objectMapper.readValue(new File(VALUES_FILE), Values.class);
        addAllValues(values.getValues(), valuesList);

         /*
            Collections.sort(testList, new Comparator<Test>() {
                @Override
                public int compare(Test o1, Test o2) {
                    if(o1.getId() < o2.getId()){
                        return -1;
                    }else if(o1.getId() > o2.getId()){
                        return 1;
                    }else{
                        return 0;
                    }
                }
            });

        testList.sort(Comparator.comparingInt(Test::getId));
        valuesList.sort(Comparator.comparingInt(Value::getId));

        System.out.println(tests);
        System.out.println(values);

        for (Test test : testList){
            System.out.println(test);
        }

        for (Value value : valuesList){
            System.out.println(value);
        }

        for (Test test : testList){
            for (Value value : valuesList){
                if(test.getId() != value.getId()){
                    continue;
                }
                test.setValue(value.getValue());
            }
        }

        String result = objectMapper.writeValueAsString(testList);
        objectMapper.writeValue(new File("C:\\Users\\User\\IdeaProjects\\2021_09_10_PLTestTasks\\src\\task3\\report.json"), testList);
        System.out.println(result);
    }

    public static void addAllTests(Test[] tests, List<Test> testList){
        for (Test test : tests){
            if(test.getValues() != null){
                addAllTests(test.getValues(), testList);
            }
            testList.add(test);
        }
    }

    public static void addAllValues(Value[] values, List<Value> valuesList){
        valuesList.addAll(Arrays.asList(values));
    }
}

 */

/*
package task3.ver1;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Arrays;

@JsonAutoDetect
public class Tests {
    private Test[] tests;

    public Tests() {

    }

    public Tests(Test[] tests) {
        this.tests = tests;
    }

    public Test[] getTests() {
        return tests;
    }

    public void setTests(Test[] tests) {
        this.tests = tests;
    }

    @Override
    public String toString() {
        return "Tests{" +
                "tests=" + Arrays.toString(tests) +
                '}';
    }

}

 */
/*
package task3.ver1;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Arrays;

@JsonAutoDetect
public class Test {
    private int id;
    private String title;
    private String value;
    private Test[] values;

    public Test(){

    }

    public Test(Test[] values){
        this.values = values;
    }

    public Test(int id, String title, String value, Test[] values) {
        this.id = id;
        this.title = title;
        this.value = value;
        this.values = values;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Test[] getValues() {
        return values;
    }

    public void setValues(Test[] values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", value='" + value + '\'' +
                ", values=" + Arrays.toString(values) +
                '}';
    }
}

 */
/*
package task3.ver1;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Arrays;

@JsonAutoDetect
public class Values {
    private Value[] values;

    public Values() {
    }

    public Values(Value[] values) {
        this.values = values;
    }

    public Value[] getValues() {
        return values;
    }

    public void setValues(Value[] values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "Values{" +
                "values=" + Arrays.toString(values) +
                '}';
    }
}

 */
/*
package task3.ver1;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Value {
    private int id;
    private String value;

    public Value() {
    }

    public Value(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Value{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}

 */