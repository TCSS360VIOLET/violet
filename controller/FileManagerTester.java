package controller;

import java.io.File;
import java.util.List;

import model.Project;

public class FileManagerTester {
    
    public static void main(String[] args) {
        List<Project> projects = FileManager.loadProjects(new File("data/John.xml"));
        System.out.println(projects.size());
    }
}
