package backend;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ModelDBConnection {
	static String login;
	static String password;
	static String serverAddress;
	static String serverType;
	static String dbName;

	static Connection con = null;
	static CallableStatement cstmt = null;
	static ResultSet rset = null;
	static Statement stmt = null;

	static boolean DEBUG = false;

	public static void setConnectionParameters(String serverType, String serverAddress, String dbName, String login,
			String password) {
		ModelDBConnection.serverType = serverType;
		ModelDBConnection.serverAddress = serverAddress;
		ModelDBConnection.dbName = dbName;
		ModelDBConnection.login = login;
		ModelDBConnection.password = password;

		ModelDBConnection.con = null;
	}

	public static String getLogin() {
		return login;
	}

	public static String getPassword() {
		return password;
	}

	public static String getServerAddress() {
		return serverAddress;
	}

	public static String getServerType() {
		return serverType;
	}

	public static String getDBName() {
		return dbName;
	}

	public static Connection getConnection() {
		return con;
	}

	public static boolean initConnection() {
		if (con == null) {
			try {
				String connectionUrl = null;
				switch (serverType) {
				case "MSServer":
					connectionUrl = "jdbc:sqlserver://" + serverAddress + ":1433;databaseName=" + dbName + ";user="
							+ login + ";password=" + password + ";";
					break;
				}

				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				con = DriverManager.getConnection(connectionUrl);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public static int getCountOfStrings(String tableName) {
		if (initConnection()) {
			try {
				int count = 0;
				/*cstmt = con.prepareCall("{? = call getCount(?)}");

				cstmt.registerOutParameter(1, Types.INTEGER);
				cstmt.setString(2, tableName);*/
				
				cstmt = con.prepareCall("select count(*) from " + tableName);

				cstmt.execute();

				count = cstmt.getInt(1);
				// System.out.println(count);
				return count;
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		}
		return -1;
	}
	
	public static ResultSetMetaData getQueryMetaData(String query) {
		if (initConnection()) {
			try {
				cstmt = con.prepareCall(query, 1004, 1007);
				rset = cstmt.executeQuery();
				
				return rset.getMetaData();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public static ObservableList<String> getNamesFromTableOrderedById(String tableName) {
		try {
			ObservableList<String> listOfNames = FXCollections.observableArrayList();
			listOfNames.add("");

			/*cstmt = con.prepareCall("{call getNamesFromTableOrderedById(?)}", 1004, 1007);

			cstmt.setString(1, tableName);*/

			cstmt = con.prepareCall("select name from " + tableName + " order by id", 1004, 1007);

			rset = cstmt.executeQuery();

			while (rset.next()) {
				listOfNames.add(rset.getString(1));
			}

			cstmt.close();
			rset.close();

			return listOfNames;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getTranslationOfField(String field, String tableName) {
		switch(field) {
			// Вспомогательные таблицы
			case "id": return "Идентификатор";
			case "codeFIS": return "Код в ФИС";
			case "name":
				switch(tableName) {
					case "Gender" : return "Пол";
					case "Nationality" : return "Гражданство";
					case "ReturnReasons" : return "Причина возврата документов";
					case "TypePassport" : return "Тип документа (паспорт)";
					case "Region" : return "Регион (область/край/республика)";
					case "TypeSettlement" : return "Тип населенного пункта";
					case "EntranceExam": return "Название ВИ";
					case "FormOfExam": return "Форма ВИ";
					case "LanguageOfExam": return "Язык ВИ";
					case "BasisMark": return "Основание для оценки";
					case "Olympiad": return "Олимпиада";
					case "LevelEducation": return "Уровень образования";
					case "TypeEducation": return "Тип образования";
					case "IndividualAchievement": return "Категория ИД";
					case "Speciality": return "Специальность";
					case "FormOfEducation": return "Форма обучения";
					case "CompetitiveGroup": return "Конкурсная группа";
					case "TargetOrganization": return "Целевая организация";
					case "TypeOfBVI": return "Категория БВИ";
					case "CategoryOfExtraInfo": return "Категория допсведений";
					case "TypeOfQuote": return "Квота";
					case "TypeOfPreferredRight": return "ПП";
				}
			case "min_score": return "Минимальный балл";
			case "max_score": return "Максимальный балл за ИД";
			case "login": return "Логин";
			case "password": return "Пароль";
			case "fio": return "ФИО оператора";
			//Таблица Abiturient
			case "aid": return "№ Личного дела";
			case "SName": return "Фамилия";
			case "Fname": return "Имя";
			case "MName": return "Отчество";
			case "Birthday": return "Дата рождения";
			case "Birthplace": return "Место рождения";
			case "id_gender": return "Пол";
			case "id_nationality": return "Гражданство";
			case "email": return "email";
			case "phoneNumbers": return "Контактный телефон";
			case "inn": return "ИНН";
			case "needHostel": return "Нуждается в общежитии";
			case "registrationDate": return "Дата подачи заявления";
			case "returnDate": return "Дата возврата документов";
			case "id_returnReason": return "Причина возврата документов";
			case "needSpecConditions": return "Нуждается в спеицальных условиях на ВИ";
			case "is_enrolled": return "Зачислен";
			//Таблица AbiturientPassport
			case "id_abiturient": return "№ Личного дела";
			case "id_typePassport": return "Тип документа (паспорт)";
			case "series_document": return "Серия";
			case "number_document": return "Номер";
			case "issued_by": return "Кем выдан";
			case "dateOf_issue": return "Дата выдачи";
			//Таблица AbiturientAdress
			case "id_region": return "Регион (область/край/республика)";
			case "id_typeSettlement": return "Тип населенного пункта";
			case "postcode": return "Индекс";
			case "adress": return "Адрес";
			//Таблица AbiturientEntranceExam
			case "id_entranceExam": return "Вступительное испытание";
			case "id_formOfExam": return "Форма ВИ";
			case "id_languageOfExam": return "Язык ВИ";
			case "groupExam": return "Группа";
			case "numberIn_group": return "№п/п в группе";
			case "dateOf_exam": return "Дата ВИ";
			case "score": return "Балл";
			case "id_basisMark": return "Основание для оценки";
			case "pass_mark": return "Сдал";
			case "has_100": return "Имеет право на 100б.";
			//Таблица AbiturientDocumentsFor100balls
			case "id_olympiad": return "Олимпиада";
			case "nameOfDocument": return "Наименование документа";
			case "diplomaDegree": return "Степень диплома";
			case "diplomaSubject": return "Предмет олимпиады";
			case "olympLevel": return "Уровень олимпиады";
			//Таблица AbiturientEducation
			case "id_levelEducation": return "Уровень образования";
			case "id_typeEducation": return "Тип образования";
			case "name_eduInstitution": return "Название учебного заведения";
			case "yearOf_graduation": return "Год окончания";
			//Таблица AbiturientIndividAchievement
			case "id_individualAchievements": return "Категория ИД";
			//Таблица AdmissionPlan
			case "id_speciality": return "Специальность";
			case "id_formOfEducation": return "Форма обучения";
			case "id_competitiveGroup": return "Конкурсная группа";
			case "id_targetOrganization": return "Целевая организация";
			case "amount_of_places": return "Всего мест";
			case "amount_of_places_quota": return "Мест по квоте";
			//Таблица AbiturientCompetitiveGroup
			case "haveBasisForBVI": return "БВИ";
			case "haveBasisForQuota": return "Квота";
			case "havePreferredRight": return "ПП";
			case "competitiveScore": return "Конкурсный балл";
			case "scoresIndAchievements": return "Балл за ИД";
			case "priority": return "Приоритет";
			case "originalsReceivedDate": return "Дата предоставления оригиналов";
			case "agreementReceivedDate": return "Дата предоставления согласия";
			//Таблица AbiturientBVI
			case "id_typeOfBVI": return "Категория БВИ";
			//Таблица AbiturientExtraInfo
			case "id_categoryOfExtraInfo": return "Категория допсведений";
			//Таблица AbiturientQuota
			case "id_quotaType": return "Квота";
			//Таблица AbiturientDocumentQuota
			//Таблица AbiturientPreferredRight
			case "id_preferredRight": return "ПП";
		
			default: return "";
		}
	}

}