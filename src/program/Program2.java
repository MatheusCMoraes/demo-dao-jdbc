package program;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {

		DepartmentDao dpDao = DaoFactory.createDepartmentDao();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("=== TEST 1: department insert =====");
	
		Department dp = new Department(7, "Toys");
		dpDao.insert(dp);
		
		
		System.out.println("=== TEST 2: department update =====");
		
		dp.setName("Pets");
		
		dpDao.update(dp);
		
		System.out.println("=== TEST 3: department delete =====");
		
		System.out.println("Enter a Department Id: ");
		int id = sc.nextInt();
		
		dpDao.deleteById(id);
		
		
		
		System.out.println("=== TEST 4: department findById =====");
		
		System.out.println("Enter a Department id");
		int id2 = sc.nextInt();
		
		dp = dpDao.findById(id2);
		
		System.out.println(dp);
		
		sc.close();
		
		System.out.println("=== TEST 5: department findAll =====");
		
		List<Department> dpList = new ArrayList<>();
		dpList = dpDao.findAll();
		
		for(Department department : dpList) {
			
			System.out.println(department);
		}
		
	}

}
