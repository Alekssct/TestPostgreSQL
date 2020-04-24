package bobruisk.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import bobruisk.test.utils.MyConnection;

public class Main {
	public static void main(String[] args) throws SQLException, ClassNotFoundException, SQLException {
		final String TABLE_NAME = "employee";
		Scanner scr = new Scanner(System.in);
		String query = "SELECT * FROM " + TABLE_NAME;

		String searchString = "";
		int number = -1;

		do {
			System.out.println("Выберите вариант:");
			System.out.println("1. Получить все данные");
			System.out.println("2. Получить данные по критериям отбора");
			System.out.println("3. Тест 1");
			System.out.println("4. Тест 2");
			System.out.println("0. Выход из программы");
			searchString = scr.next();

			try {
				number = Integer.parseInt(searchString);
				// Выбор
				if (number == 1) {
					System.out.println(query);
					search(query);
				} else if (number == 2) {
					// Добавляем критерии
					searchCriteria(searchString, query);

					// Возвращаем значения по умолчанию
					number = -1;
					query = "SELECT * FROM " + TABLE_NAME;
				} else if (number == 3) {
					// Тест 1
					query += " where firstname like '%Мария%' or lastname like '%Морская%'"
							+ " or patronymic like '%Васильевна%'";
					System.out.println(query);
					search(query);
					// Возвращаем значения по умолчанию
					number = -1;
					query = "SELECT * FROM " + TABLE_NAME;
				} else if (number == 4) {
						// Тест 2
					query += " where lastname like '%ов' or sex like 'женщина'";
					System.out.println(query);
					search(query);
						// Возвращаем значения по умолчанию
					number = -1;
					query = "SELECT * FROM " + TABLE_NAME;
				}
			} catch (NumberFormatException e) {
				System.err.println("Ошибка ввода данных");
			}
		} while (number != 0);
		scr.close();
	}

	// Поиск и вывод результатов
	public static void search(String query) throws ClassNotFoundException, SQLException {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = MyConnection.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				System.out.println("Сотрудник:");
				System.out.println("Имя: " + resultSet.getString(2));
				System.out.println("Фамилия: " + resultSet.getString(3));
				System.out.println("Отчество: " + resultSet.getString(4));
				System.out.println("Дата рождения: " + resultSet.getString(5));
				System.out.println("Контакты: " + resultSet.getString(6));
				System.out.println("Пол: " + resultSet.getString(7));
				System.out.println("Владение технологиями: " + resultSet.getString(8) + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			connection.close();
		}
	}
	
	// Поиск по критериям
	public static void searchCriteria(String searchString, String query)
			throws ClassNotFoundException, SQLException {
		
		Scanner scr = new Scanner(System.in);
		boolean where = false;
		
		String[] criteriaEng = { "FIRSTNAME", "LASTNAME", "PATRONYMIC", "DATE", "CONTACTS",
				"SEX", "TECHNOLOGY" };
		String[] criteriaRus = { "имени", "фамилии", "отчеству", "дате", "контактам",
				"полу", "технологиям" };
		
		for (int i = 0; i < criteriaEng.length; i++) {
			System.out.println("Добавить поиск по " + criteriaRus[i] + "? (Введите текст"
					+ " поиска или слово \"нет\" для отмены)");
			searchString = scr.next();
			if (!searchString.equals("нет") & (!where)) {
				query += " where " + criteriaEng[i] + " like '%" + searchString + "%'";
				where = true;
			} else if (!searchString.equals("нет") & (where)){
				query += " or " + criteriaEng[i] + " like '%" + searchString + "%'";
			}
		}
		System.out.println(query);
		search(query);
	}
}
