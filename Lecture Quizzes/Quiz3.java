//Creating Students

public class Students {
    private String name;
    private int age;
    
    public Student(String name, int age) {
        this.name=name;
        this.age=age;
    }
    
    public String getName(){
        return this.name; 
    }
    
    public int getAge(){
        return this.agel 
    }
    
    public boolean equals(Students s) {
        if (s == null)
            return false;
        return this.getName() == s.getName();
    }
}

 

//Creating the Driver

public class Driver {
    
    public static void main(String[] args) {
        
        Student johnS = new Student("John", 17);
        Student emilyW = new Student("Emily", 20);
        Student joeL = new Student("Joe", 19);
        Student rohanR = new Student("Rohan", 16);
        
        LL<Student> studentList = new LL<Student>();
        
        studentList.addFront(johnS);
        studentList.addFront(emilyW);
        studentList.addFront(joeL);
        studentList.addFront(rohanR);
        
        //create a new student with the name you are looking for
        Student tempStudent = new Student(name, 99);
        
        Student studentWeAreLookingFor = studentList.searchNode(tempStudent);
    }    
}