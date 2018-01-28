# PowerMockito Action

## 1. dependency
  - JUnit 版本 4.4 以上
    ```xml
    <dependencies> 
        <dependency> 
            <groupId>org.powermock</groupId> 
            <artifactId>powermock-module-junit4</artifactId> 
            <version>1.4.10</version> 
            <scope>test</scope> 
        </dependency> 
        
        <dependency> 
            <groupId>org.powermock</groupId> 
            <artifactId>powermock-api-mockito</artifactId> 
            <version>1.4.10</version> 
            <scope>test</scope> 
        </dependency> 
    </dependencies>
    ```
  - JUnit 版本 4.0-4.3
    ```xml
    <dependencies> 
        <dependency> 
        <groupId>org.powermock</groupId> 
        <artifactId>powermock-module-junit4-legacy</artifactId> 
        <version>1.4.10</version> 
        <scope>test</scope> 
    </dependency> 
    
    <dependency> 
        <groupId>org.powermock</groupId> 
        <artifactId>powermock-api-mockito</artifactId> 
        <version>1.4.10</version> 
        <scope>test</scope> 
        </dependency> 
    </dependencies>
    ```
  - JUnit 版本 3
    ```xml
    <dependencies>
        <dependency>
            <groupId>org.powermock</groupId>
            <artifactId>powermock-module-junit3</artifactId>
            <version>1.4.10</version>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>org.powermock</groupId>
            <artifactId>powermock-api-mockito</artifactId>
            <version>1.4.10</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    ```
    
## 2. PowerMock基本用法
  - 测试对象
    ```java
     public class IdGenerator {
          public static long generateNewId() { 
            return 0L;
          } 
     }
    
    public class Student {
        private Long id;
        private String name; 
        private String description;
    
        public ClassUnderTest(Long id, String name, String description){
            this.id = id;
            this.name = name;
            this.description = description;
        }
    
        public String getName(){
            return name;
        }
    
        public long getId(){
            return id;
        }
    
        public String getDescription(){
            return description;
        }

    }

    public class School{
        private Map<Long, Student> students = new HashMap<>();
        
        public boolean isStudentExist(Long id){
            return students.contains(id);
        }
    
        public Long createStudent(String name){
               Student student = new Student(name);
               long id = genStudentId();
               String description = description(name, id);
               students.put(id, student, description);
               return id;
        }
    
        public Student getStudent(Long id){
            return students.get(id);
        }
    
        private long genStudentId() { 
             return IdGenerator.generateNewId(); 
        }
        
        private String description(String name, Long id){
            return name + "_" + id;
        }
    }
    ```

  - 模拟 Static 方法
    ```java
    @RunWith(PowerMockRunner.class)
    @PrepareForTest(IdGenerator.class)
    public class TestStatic {
        @Test
        public void testCreateStudent() throws Exception {
              PowerMockito.mockStatic(IdGenerator.class);
              PowerMockito.when(IdGenerator.generateId()).thenReturn(15L);
          
              Long id = new School().createStudent("smilly");
              Assert.assertEquals(15L, id);
              PowerMockito.verifyStatic();
      }
    }
    ```
  - 模拟构造函数
   ```java
      @RunWith(PowerMockRunner.class)
      @PrepareForTest({Student.class})
      public class SchoolTest{ 
          @Test
          public void createStudentTest() throws Exception { 
                  final String name = "smilly";
                  
                  Student student = mock(Student.class);
                  whenNew(Student.class).withArguments(name).thenReturn(student); 
                    
                  School school = new School();
                  Long id = school.createStudent(name);
                  
                  Assert.assertEqual(student, school.getStudent(id));
                  verifyNew(Student.class).withArguments(directoryPath); 
          } 
      }
   ```
  - 模拟私有以及 Final 方法
  ```java
    @RunWith(PowerMockRunner.class)
    @PrepareForTest(School.class)
    public class SchoolTest {
        @Test
        public void createStudentTest() throws Exception {
                final String name = "smilly";
                final Long id = 15L;
                final String description = "chally_16";

                School school = spy(new School());
                when(school, "genStudentId").thenReturn(id);
                when(school, "description", name, id).thenReturn(description);
                
                school.createStudent(name);
                Student student = school.getStudent(id);
                
                assertEquals(description, student.getDescription());
                verifyPrivate(school).invoke("genStudentId");
                verifyPrivate(school).invoke("description", name, id);
        }
    }
  ``` 
  
## 3. Issues
  - 使用Powermock后会提示classloader错误<br/>
    **Solution**<br/>
    加入注解：@PowerMockIgnore("javax.management.*")