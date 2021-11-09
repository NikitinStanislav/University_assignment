package com.example.university_assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static List<Lector> getAllLectors(JdbcTemplate jdbcTemplate, String department_name){
        return jdbcTemplate.query("select * from university where department = ?",
                new BeanPropertyRowMapper<>(Lector.class), department_name);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("What are you interesting in?");

        String question;
        Scanner scanner = new Scanner(System.in);

        while(true) {
            question = scanner.nextLine();
            if (question.equals("finish"))
                break;

            /**FIRST COMMAND**/
            if (question.startsWith("Who is head of department")) {

                String[] eachWord = question.split("\\W", 6);
                String department_name = "";

                /**CHECK FOR EMPTY DEPARTMENT NAME**/
                try {
                    department_name = eachWord[5];

                    /**CHECK FOR CORRECT DEPARTMENT NAME**/
                    try {
                        String head_of_department_name = jdbcTemplate.queryForObject("select name from university where head =?",
                                String.class,
                                department_name);
                        System.out.println("Head of " + department_name + " department is " + head_of_department_name);
                    } catch (EmptyResultDataAccessException exception) {
                        System.out.println("Department doesn't exist");
                    }

                } catch (ArrayIndexOutOfBoundsException exception) {
                    System.out.println("Please enter department");
                }
            }

            /**SECOND COMMAND**/
            else if (question.startsWith("Show") && question.endsWith("statistics")) {

                String department_name = question.replace("Show", "").replace("statistics", "").trim();

                /**CHECK FOR EMPTY DEPARTMENT NAME**/
                if (department_name.equals("")) {
                    System.out.println("Please enter department");
                }
                else {
                    List<Lector> lectorsList = getAllLectors(jdbcTemplate, department_name);
                    int assistants_count = 0;
                    int associate_professors_count = 0;
                    int professors_count = 0;
                    for (Lector lector : lectorsList) {
                         switch (lector.getPosition()) {
                             case "Assistant":
                                assistants_count++;
                                break;
                              case "Associate professor":
                                associate_professors_count++;
                                break;
                              case "Professor":
                                professors_count++;
                                break;
                    }
                }
                    /**CHECK FOR CORRECT DEPARTMENT NAME**/
                if (!lectorsList.isEmpty())
                    System.out.println("assistans - " + assistants_count +
                            "\nassociate professors - " + associate_professors_count +
                            "\nprofessors - " + professors_count);
                else System.out.println("Department doesn't exist");
            }
        }


            /**THIRD COMMAND**/
            else if(question.startsWith("Show the average salary for the department")){
                String[] eachWord = question.split("\\W", 8);
                String department_name = "";

                /**CHECK FOR EMPTY DEPARTMENT NAME**/
                try {
                    department_name = eachWord[7];

                    List<Lector> lectorsList = getAllLectors(jdbcTemplate, department_name);
                    double average_salary = 0;
                    for (Lector lector : lectorsList){
                        average_salary += lector.getSalary();
                    }
                    average_salary /= lectorsList.size();

                    /**CHECK FOR CORRECT DEPARTMENT NAME**/
                    if (!lectorsList.isEmpty())
                        System.out.println("The average salary of "+department_name+" is "+average_salary);
                    else System.out.println("Department doesn't exist");

                } catch (ArrayIndexOutOfBoundsException exception) {
                System.out.println("Please enter department");
            }
        }

            /**FOURTH COMMAND**/
            else if(question.startsWith("Show count of employee for")){
                String[] eachWord = question.split("\\W", 6);
                String department_name = "";

                /**CHECK FOR EMPTY DEPARTMENT NAME**/
                try {
                    department_name = eachWord[5];

                    List<Lector> lectorsList = getAllLectors(jdbcTemplate, department_name);

                    /**CHECK FOR CORRECT DEPARTMENT NAME**/
                    if (!lectorsList.isEmpty())
                        System.out.println(lectorsList.size());
                    else System.out.println("Department doesn't exist");

                } catch (ArrayIndexOutOfBoundsException exception) {
                    System.out.println("Please enter department");
                }
            }

            /**FIFTH COMMAND**/
            else if(question.startsWith("Global search by")){
                String[] eachWord = question.split("\\W", 4);
                String template = "";

                /**CHECK FOR EMPTY DEPARTMENT NAME**/
                try {
                    template = eachWord[3];

                    List<Lector> lectorsList = jdbcTemplate.query("select * from university",
                            new BeanPropertyRowMapper<>(Lector.class));
                    Set<String> matchedLectors = new HashSet<>();
                    for (Lector lector : lectorsList){
                        if(lector.getName().contains(template))
                            matchedLectors.add(lector.getName());
                    }

                    /**CHECK FOR MATCHES**/
                    if (!matchedLectors.isEmpty()) {
                        String result = "";
                        for (String name : matchedLectors) {
                            result += name + ", ";
                        }
                        System.out.println(result.substring(0, result.length()-2));
                    }
                    else System.out.println("No matches");

                }catch (ArrayIndexOutOfBoundsException exception) {
                    System.out.println("Please enter department");
                }
            }

            else {
                System.out.println("Wrong query");
            }

        }
        scanner.close();
        System.out.println("scanner is closed");
    }
}


