package backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

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

	public static void setDefaultConnectionParameters() {
		try {
			Properties property = new Properties();
			property.load(new FileInputStream(new File("").getAbsolutePath() + "/.conf"));
			
	        String serverType = property.getProperty("db.serverType");
	        String serverAddress = property.getProperty("db.serverAddress");
	        String dbName = property.getProperty("db.baseName");
	        String login = property.getProperty("db.login");
	        String password = property.getProperty("db.password");

			ModelDBConnection.serverType = serverType;
			ModelDBConnection.serverAddress = serverAddress;
			ModelDBConnection.dbName = dbName;
			ModelDBConnection.login = login;
			ModelDBConnection.password = password;

			ModelDBConnection.con = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
			case "aid": return "№ л/д";
			case "SName": return "Фамилия";
			case "FName": return "Имя";
			case "MName": return "Отчество";
			case "Birthday": return "Дата рождения";
			case "Birthplace": return "Место рождения";
			case "id_gender": return "Пол";
			case "id_nationality": return "Гражданство";
			case "email": return "email";
			case "phoneNumbers": return "Контактный телефон";
			case "inn": return "ИНН";
			case "needHostel": return "Нуждается в общежитии";
			case "registrationdate": return "Дата подачи заявления";
			case "returnDate": return "Дата возврата документов";
			case "id_returnReason": return "Причина возврата документов";
			case "needSpecConditions": return "Нуждается в специальных условиях на ВИ";
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

	//Общие функции
	public static String getQueryByTabName(String tabName) {
		switch(tabName) {
			case "SampleTab":
				return "SELECT * "
						+ "FROM Abiturient";
            case "АРМ по приему в ВУЗ":
                return "SELECT Abiturient.aid, Abiturient.registrationdate, " +
                        "Abiturient.SName, Abiturient.id_gender, Abiturient.id_nationality," +
                        "Abiturient.FName, Abiturient.Birthday, Abiturient.needHostel," +
                        "Abiturient.MName, Abiturient.id_returnReason," +
                        "Abiturient.returnDate, Abiturient.is_enrolled " +
                        "FROM AbiturientPassport JOIN Abiturient ON (Abiturient.aid=AbiturientPassport.id_abiturient)";
			case "Паспорт и ИНН":
				return "SELECT AbiturientPassport.id_typePassport, "
						+ "AbiturientPassport.series_document, "
						+ "AbiturientPassport.number_document, "
						+ "AbiturientPassport.dateOf_issue, "
						+ "AbiturientPassport.issued_by, "
						+ "Abiturient.Birthplace, "
						+ "Abiturient.inn "
						+ "FROM AbiturientPassport JOIN Abiturient ON (Abiturient.aid=AbiturientPassport.id_abiturient)";
			case "Образование":
				return "SELECT AbiturientEducation.id_levelEducation, "
						+ "AbiturientEducation.id_typeEducation, "
						+ "AbiturientEducation.name_eduInstitution, "
						+ "AbiturientEducation.dateOf_issue, "
						+ "AbiturientEducation.series_document, "
						+ "AbiturientEducation.number_document, "
						+ "AbiturientEducation.yearOf_graduation "
						+ "FROM AbiturientEducation JOIN Abiturient ON (Abiturient.aid = AbiturientEducation.id_abiturient)";
			case "Адрес и контакты":
				return "SELECT AbiturientAdress.id_region,  "
						+ "AbiturientAdress.id_typeSettlement, "
						+ "AbiturientAdress.postcode, "
						+ "AbiturientAdress.adress, "
						+ "Abiturient.phoneNumbers, "
						+ "Abiturient.email "
						+ "FROM AbiturientAdress JOIN Abiturient ON (Abiturient.aid = AbiturientAdress.id_abiturient)";
			case "Вступительные испытания":
				return "SELECT AbiturientEntranceExam.id_entranceExam, "
						+ "AbiturientEntranceExam.id_formOfExam, "
						+ "AbiturientEntranceExam.id_languageOfExam, "
						+ "AbiturientEntranceExam.groupExam, "
						+ "AbiturientEntranceExam.dateOf_exam, "
						+ "AbiturientEntranceExam.score, "
						+ "AbiturientEntranceExam.has_100, "
						+ "Abiturient.needSpecConditions "
						+ "FROM AbiturientEntranceExam JOIN Abiturient ON (Abiturient.aid = AbiturientEntranceExam.id_abiturient)";
			case "Доп. сведения":
				return "SELECT AbiturientExtraInfo.id_categoryOfExtraInfo, "
						+ "AbiturientExtraInfo.nameOfDocument, "
						+ "AbiturientExtraInfo.series_document, "
						+ "AbiturientExtraInfo.number_document, "
						+ "AbiturientExtraInfo.issued_by, "
						+ "AbiturientExtraInfo.dateOf_issue "
						+ "FROM AbiturientExtraInfo JOIN Abiturient ON (Abiturient.aid = AbiturientExtraInfo.id_abiturient)";
			case "100б":
				return "SELECT AbiturientDocumentsFor100balls.id_olympiad, "
						+ "AbiturientDocumentsFor100balls.nameOfDocument, "
						+ "AbiturientDocumentsFor100balls.diplomaDegree, "
						+ "AbiturientDocumentsFor100balls.diplomaSubject, "
						+ "AbiturientDocumentsFor100balls.olympLevel, "
						+ "AbiturientDocumentsFor100balls.series_document, "
						+ "AbiturientDocumentsFor100balls.number_document, "
						+ "AbiturientDocumentsFor100balls.dateOf_issue, "
						+ "AbiturientDocumentsFor100balls.issued_by "
						+ "FROM AbiturientDocumentsFor100balls JOIN Abiturient ON (Abiturient.aid = AbiturientDocumentsFor100balls.id_abiturient)";
			case "Индивидуальные достижения":
				return  "SELECT AbiturientIndividAchievement.id_individualAchievements, "
						+ "AbiturientIndividAchievement.score, "
						+ "AbiturientIndividAchievement.nameOfDocument, "
						+ "AbiturientIndividAchievement.series_document,"
						+ "AbiturientIndividAchievement.number_document, "
						+ "AbiturientIndividAchievement.dateOf_issue,"
						+ "AbiturientIndividAchievement.issued_by "
						+ "FROM AbiturientIndividAchievement JOIN Abiturient ON (Abiturient.aid = AbiturientIndividAchievement.id_abiturient)";
			case "Конкурсные группы":
				return "SELECT AbiturientCompetitiveGroup.id_speciality,"
						+ "AbiturientCompetitiveGroup.id_competitiveGroup, "
						+ "AbiturientCompetitiveGroup.id_targetOrganization, "
						+ "AbiturientCompetitiveGroup.id_formOfEducation, "
						+ "AbiturientCompetitiveGroup.havePreferredRight, "
						+ "AbiturientCompetitiveGroup.competitiveScore, "
						+ "AbiturientCompetitiveGroup.scoresIndAchievements, "
                        + "AbiturientCompetitiveGroup.originalsReceivedDate, "
                        + "AbiturientCompetitiveGroup.agreementReceivedDate, "
                        + "AbiturientCompetitiveGroup.priority "
						+ "FROM AbiturientCompetitiveGroup JOIN Abiturient ON (Abiturient.aid = AbiturientCompetitiveGroup.id_abiturient)";
			case "БВИ":
				return "SELECT AbiturientBVI.id_typeOfBVI, AbiturientDocumentsBVI.nameOfDocument, AbiturientDocumentsBVI.series_document, " +
						"AbiturientDocumentsBVI.number_document,AbiturientDocumentsBVI.issued_by, " +
						"AbiturientDocumentsBVI.dateOf_issue FROM AbiturientBVI JOIN Abiturient ON (Abiturient.aid = AbiturientBVI.id_abiturient) " +
						"JOIN AbiturientDocumentsBVI on (Abiturient.aid = AbiturientDocumentsBVI.id_abiturient)";
			case "Квота":
				return "SELECT AbiturientQuota.id_quotaType,AbiturientDocumentQuota.nameOfDocument, " +
						"AbiturientDocumentQuota.series_document, AbiturientDocumentQuota.number_document, " +
						"AbiturientDocumentQuota.issued_by, AbiturientDocumentQuota.dateOf_issue FROM " +
						"AbiturientQuota JOIN Abiturient ON (Abiturient.aid = AbiturientQuota.id_abiturient) " +
						"JOIN AbiturientDocumentQuota on (Abiturient.aid = AbiturientDocumentQuota.id_abiturient)";
			case "Преимущественное право":
				return "SELECT AbiturientPreferredRight.id_preferredRight, AbiturientDocumentsPreferredRight.nameOfDocument, " +
						"AbiturientDocumentsPreferredRight.series_document, AbiturientDocumentsPreferredRight.number_document, " +
						"AbiturientDocumentsPreferredRight.issued_by, AbiturientDocumentsPreferredRight.dateOf_issue FROM " +
						"AbiturientPreferredRight JOIN Abiturient ON (Abiturient.aid = AbiturientPreferredRight.id_abiturient) " +
						"JOIN AbiturientDocumentsPreferredRight on (Abiturient.aid = AbiturientDocumentsPreferredRight.id_abiturient)";
            case "Пол":
                return "SELECT * FROM Gender ";
            case "Гражданство":
                return "SELECT * FROM Nationality ";
            case "Причины возврата":
                return "SELECT * FROM ReturnReasons ";
            case "Типы паспортов":
                return "SELECT * FROM TypePassport ";
            case "Регионы":
                return "SELECT * FROM Region ";
            case "Типы населенных пунктов":
                return "SELECT * FROM TypeSettlement ";
            case "Формы вступительных испытаний":
                return "SELECT * FROM FormOfExam ";
            case "Языки вступительных испытаний":
                return "SELECT * FROM LanguageOfExam ";
            case "Основания для оценки":
                return "SELECT * FROM BasisMark ";
            case "Олимпиады":
                return "SELECT * FROM Olympiad ";
            case "Уровни образования":
                return "SELECT * FROM LevelEducation ";
            case "Типы образования":
                return "SELECT * FROM TypeEducation ";
            case "Специальности":
                return "SELECT * FROM Speciality ";
            case "Формы обучения":
                return "SELECT * FROM FormOfEducation ";
            case "Конкурсные группы ":
                return "SELECT * FROM CompetitiveGroup ";
            case "Целевые организации":
                return "SELECT * FROM TargetOrganization ";
            case "Типы БВИ":
                return "SELECT * FROM TypeOfBVI ";
            case "Типы Квоты":
                return "SELECT * FROM TypeOfQuote ";
            case "Типы Преимущественного права":
                return "SELECT * FROM TypeOfPreferredRight ";
            case "Категории допсведений":
                return "SELECT * FROM CategoryOfExtraInfo ";
            case "Перечень вступительных испытаний":
                return "SELECT * FROM EntranceExam ";
            case "Перечень индивидуальных достижений":
                return "SELECT * FROM IndividualAchievement ";
            case "Пользователи":
                return "SELECT * FROM Users ";
            case "План приема":
                return "SELECT * FROM AdmissionPlan ";
			default:
				return "";
		}
	}

	public static void updateElementInTableByExpression(String table, String idValue, String[] fieldsNames, String[] data, int countOfExprParams)
			throws SQLException {
		String id = "id";
		switch (table) {
		case "Abiturient":
			id = "aid";
			break;
		case "AbiturientPassport":
		case "AbiturientAdress":
		case "AbiturientEntranceExam":
		case "AbiturientDocumentsFor100balls":
		case "AbiturientEducation":
		case "AbiturientIndividAchievement":
		case "AbiturientCompetitiveGroup":
		case "AbiturientBVI":
		case "AbiturientDocumentsBVI":
		case "AbiturientQuota":
		case "AbiturientDocumentQuota":
		case "AbiturientPreferredRight":
		case "AbiturientDocumentsPreferredRight":
		case "AbiturientExtraInfo":
			id = "id_abiturient";
			break;
		case "AdmissionPlan":
			id = "id_speciality";
			break;
		default:
			id = "id";
		}

		String query = "select * from " + table + " where " + id + " = " + idValue + (countOfExprParams > 0 ? " and " : ";");
		for (int i = 0; i < countOfExprParams; i++)
			if (i == countOfExprParams - 1)
				query = query + fieldsNames[i] + (!data[i].equals("'null'") ? " = " + data[i] : " is null;");
			else
				query = query + fieldsNames[i] + (!data[i].equals("'null'") ? " = " + data[i] : " is null") + " and ";
		System.out.println(query);

		cstmt = con.prepareCall(query, 1004, 1007);

		rset = cstmt.executeQuery();

		int countStrings = rset.last() ? rset.getRow() : 0;
		rset.beforeFirst();

		if (countStrings > 0) {
			query = "update " + table + " set ";
			for (int i = 0; i < fieldsNames.length; i++) {
				if (i == fieldsNames.length - 1)
					query = query + fieldsNames[i] + " = " + data[i];
				else
					query = query + fieldsNames[i] + " = " + data[i] + ", ";
			}
			query = query + " where " + id + " = " + idValue + (countOfExprParams > 0 ? " and " : ";");
			for (int i = 0; i < countOfExprParams; i++)
				if (i == countOfExprParams - 1)
					query = query + fieldsNames[i] + (!data[i].equals("'null'") ? " = " + data[i] : " is null;");
				else
					query = query + fieldsNames[i] + (!data[i].equals("'null'") ? " = " + data[i] : " is null") + " and ";
		} else {
			query = "insert into " + table + "(" + id + ", ";
			for (int i = 0; i < fieldsNames.length; i++) {
				if (i == fieldsNames.length - 1)
					query = query + fieldsNames[i] + ") ";
				else
					query = query + fieldsNames[i] + ", ";
			}

			query = query + " values (" + idValue + ", ";
			for (int i = 0; i < data.length; i++) {
				if (i == data.length - 1)
					query = query + data[i] + ") ";
				else
					query = query + data[i] + ", ";
			}
		}
		cstmt.close();
		query = query.replaceAll("'null'", "null");
		System.out.println(query);

		stmt = con.createStatement();
		stmt.executeUpdate(query);

		stmt.close();
		rset.close();
	}

	public static void deleteElementInTableByExpression(String table, String idValue, String[] fieldsNames, String[] data, int countOfExprParams)
			throws SQLException {
		String id = "id";
		switch (table) {
		case "Abiturient":
			id = "aid";
			break;
		case "AbiturientPassport":
		case "AbiturientAdress":
		case "AbiturientEntranceExam":
		case "AbiturientDocumentsFor100balls":
		case "AbiturientEducation":
		case "AbiturientIndividAchievement":
		case "AbiturientCompetitiveGroup":
		case "AbiturientBVI":
		case "AbiturientDocumentsBVI":
		case "AbiturientQuota":
		case "AbiturientDocumentQuota":
		case "AbiturientPreferredRight":
		case "AbiturientDocumentsPreferredRight":
		case "AbiturientExtraInfo":
			id = "id_abiturient";
			break;
		case "AdmissionPlan":
			id = "id_speciality";
			break;
		default:
			id = "id";
		}

		String query = "delete from " + table + " where " + id + " = " + idValue + (countOfExprParams > 0 ? " and " : ";");
		for (int i = 0; i < countOfExprParams; i++)
			if (i == countOfExprParams - 1)
				query = query + fieldsNames[i] + (!data[i].equals("'null'") ? " = " + data[i] : " is null;");
			else
				query = query + fieldsNames[i] + (!data[i].equals("'null'") ? " = " + data[i] : " is null") + " and ";
		System.out.println(query);

		cstmt = con.prepareCall(query, 1004, 1007);

		cstmt.executeUpdate();

		cstmt.close();
	}


	//Вкладка PassportTab
	public static String[] getAbiturientPassportByID(String aid) throws SQLException {
		try {
			String query = ModelDBConnection.getQueryByTabName("Паспорт и ИНН")
							+ " WHERE Abiturient.aid = " + aid + ";";

			/*cstmt = con.prepareCall("{call getAbiturientPassportByID(?)}", 1004, 1007);

			cstmt.setString(1, aid);*/

			cstmt = con.prepareCall(query, 1004, 1007);

			rset = cstmt.executeQuery();

			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			//Случай, если данных по абитуриенту еще нет
			if (countStrings == 0) return null;

			ResultSetMetaData rsmd = rset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			String[] result = new String[numberOfColumns];
			for (int i = 0; i < result.length; i++)
				result[i] = "";

			while (rset.next()) {
				for (int i = 0; i < numberOfColumns; i++) {
					if (rset.getObject(i + 1) != null)
						if (rset.getObject(i + 1) instanceof Date) {
							SimpleDateFormat format = new SimpleDateFormat();
							format.applyPattern("yyyy-MM-dd");
							Date docDate = format.parse(rset.getObject(i + 1).toString());
							//format.applyPattern("dd.MM.yyyy");
							result[i] = format.format(docDate);
						} else
							result[i] = rset.getObject(i + 1).toString();
				}
			}
			cstmt.close();
			rset.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static void updateAbiturientPassportByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
    	String[] passportData = new String[fieldsData.length - 2],
   			 abiturientData = new String[2],
   			 passportFieldsNames = new String[fieldsData.length - 2],
   	    	 abiturientFieldsNames = new String[2];

    	abiturientData[0] = fieldsData[fieldsData.length - 1];
    	abiturientData[1] = fieldsData[fieldsData.length - 2];

    	abiturientFieldsNames[0] = fieldsNames[fieldsNames.length - 1];
    	abiturientFieldsNames[1] = fieldsNames[fieldsNames.length - 2];

	   	for (int i = 0; i < passportData.length; i++) {
	   		passportData[i] = fieldsData[i];
	   		passportFieldsNames[i] = fieldsNames[i];
	   	}

	   	ModelDBConnection.updateElementInTableByExpression("Abiturient", aid, abiturientFieldsNames, abiturientData, 0);
	   	ModelDBConnection.updateElementInTableByExpression("AbiturientPassport", aid, passportFieldsNames, passportData, 0);
	}


	//Вкладка EducationTab
	public static String[] getAbiturientEducationByID(String aid) throws SQLException {
		try {
			String query = ModelDBConnection.getQueryByTabName("Образование")
							+ " WHERE Abiturient.aid = " + aid + ";";

			/*cstmt = con.prepareCall("{call getAbiturientPassportByID(?)}", 1004, 1007);

			cstmt.setString(1, aid);*/

			cstmt = con.prepareCall(query, 1004, 1007);

			rset = cstmt.executeQuery();

			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			//Случай, если данных по абитуриенту еще нет
			if (countStrings == 0) return null;

			ResultSetMetaData rsmd = rset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			String[] result = new String[numberOfColumns];
			for (int i = 0; i < result.length; i++)
				result[i] = "";

			while (rset.next()) {
				for (int i = 0; i < numberOfColumns; i++) {
					if (rset.getObject(i + 1) != null)
						if (rset.getObject(i + 1) instanceof Date) {
							SimpleDateFormat format = new SimpleDateFormat();
							format.applyPattern("yyyy-MM-dd");
							Date docDate = format.parse(rset.getObject(i + 1).toString());
							//format.applyPattern("dd.MM.yyyy");
							result[i] = format.format(docDate);
						} else
							result[i] = rset.getObject(i + 1).toString();
				}
			}
			cstmt.close();
			rset.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static void updateAbiturientEducationByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
		ModelDBConnection.updateElementInTableByExpression("AbiturientEducation", aid, fieldsNames, fieldsData, 0);
	}


	//Вкладка EntranceExamTab
	public static String[] getAbiturientEntranceExamsByID(String aid) throws SQLException {
		try {
			String query = ModelDBConnection.getQueryByTabName("Вступительные испытания")
							+ " WHERE Abiturient.aid = " + aid + ";";

			/*cstmt = con.prepareCall("{call getAbiturientPassportByID(?)}", 1004, 1007);

			cstmt.setString(1, aid);*/

			cstmt = con.prepareCall(query, 1004, 1007);

			rset = cstmt.executeQuery();

			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			//Случай, если данных по абитуриенту еще нет
			if (countStrings == 0) return null;

			ResultSetMetaData rsmd = rset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			String[] result = new String[countStrings * numberOfColumns];
			for (int i = 0; i < result.length; i++)
					result[i] = "";
				
			int curPos = 0;
			while (rset.next()) {
				for (int i = 0; i < numberOfColumns; i++) {
					if (rset.getObject(i + 1) != null)
						if (rset.getObject(i + 1) instanceof Date) {
							SimpleDateFormat format = new SimpleDateFormat();
							format.applyPattern("yyyy-MM-dd");
							Date docDate = format.parse(rset.getObject(i + 1).toString());
							//format.applyPattern("dd.MM.yyyy");
							result[curPos] = format.format(docDate);
						} else
							result[curPos] = rset.getObject(i + 1).toString();

					curPos++;
				}
			}
			cstmt.close();
			rset.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static void updateAbiturientEntranceExamsByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
		//Удалить записи по текущему абитуриенту и добавить введенные строчки из вкладки вступительных испытаний
		ModelDBConnection.deleteElementInTableByExpression("AbiturientEntranceExam", aid, fieldsNames, fieldsData, 0);

		if(fieldsData.length == 0) return;

    	String[] entranceExamData = new String[fieldsNames.length - 1],
      			 abiturientSpecialConditionsData = new String[1],
      			 entranceExamNames = new String[fieldsNames.length - 1],
      			 abiturientSpecialConditionsNames = new String[1];

    	abiturientSpecialConditionsData[0] = fieldsData[fieldsData.length - 1];
       	abiturientSpecialConditionsNames[0] = fieldsNames[fieldsNames.length - 1];

       	ModelDBConnection.updateElementInTableByExpression("Abiturient", aid, abiturientSpecialConditionsNames, abiturientSpecialConditionsData, 0);


       	for (int i = 0, j = 0; i < fieldsData.length; i++, j++) {
       		entranceExamData[j] = fieldsData[i];
       		entranceExamNames[j] = fieldsNames[j];

       		if(i % fieldsNames.length == fieldsNames.length - 2) {
       			i++;
       			j = -1;

       			ModelDBConnection.updateElementInTableByExpression("AbiturientEntranceExam", aid, entranceExamNames, entranceExamData, 1);
       		}
       	}
	}

	public static void deleteAbiturientEntranceExamsByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
		//Удалить все вступительные испытания по aid абитуриента
		ModelDBConnection.deleteElementInTableByExpression("AbiturientEntranceExam", aid, fieldsNames, fieldsData, 0);
	}
//Вкладка AddressTab
	public static String[] getAbiturientAddressByID(String aid) throws SQLException {
		try {
			String query = ModelDBConnection.getQueryByTabName("Адрес и контакты")
					+ " WHERE Abiturient.aid = " + aid + ";";

			/*cstmt = con.prepareCall("{call getAbiturientPassportByID(?)}", 1004, 1007);

			cstmt.setString(1, aid);*/

			cstmt = con.prepareCall(query, 1004, 1007);

			rset = cstmt.executeQuery();

			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			//Случай, если данных по абитуриенту еще нет
			if (countStrings == 0) return null;

			ResultSetMetaData rsmd = rset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			String[] result = new String[numberOfColumns];
			for (int i = 0; i < result.length; i++)
				result[i] = "";

			while (rset.next()) {
				for (int i = 0; i < numberOfColumns; i++) {
					if (rset.getObject(i + 1) != null)
						if (rset.getObject(i + 1) instanceof Date) {
							SimpleDateFormat format = new SimpleDateFormat();
							format.applyPattern("yyyy-MM-dd");
							Date docDate = format.parse(rset.getObject(i + 1).toString());
							//format.applyPattern("dd.MM.yyyy");
							result[i] = format.format(docDate);
						} else
							result[i] = rset.getObject(i + 1).toString();
				}
			}
			cstmt.close();
			rset.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static void updateAbiturientAddressByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
		ModelDBConnection.updateElementInTableByExpression("AbiturientAdress", aid, fieldsNames, fieldsData, 0);
	}

	//Вкладка 100б
	public static String[] getAbiturientOlympiadsInfoByID(String aid) throws SQLException {
		try {
			String query = ModelDBConnection.getQueryByTabName("100б")
							+ " WHERE Abiturient.aid = " + aid + ";";

			/*cstmt = con.prepareCall("{call getAbiturientPassportByID(?)}", 1004, 1007);

			cstmt.setString(1, aid);*/

			cstmt = con.prepareCall(query, 1004, 1007);

			rset = cstmt.executeQuery();

			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			//Случай, если данных по абитуриенту еще нет
			if (countStrings == 0) return null;

			ResultSetMetaData rsmd = rset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			String[] result = new String[countStrings * numberOfColumns];
			for (int i = 0; i < result.length; i++)
					result[i] = "";

			int curPos = 0;

			while (rset.next()) {
				for (int i = 0; i < numberOfColumns; i++) {
					if (rset.getObject(i + 1) != null)
						if (rset.getObject(i + 1) instanceof Date) {
							SimpleDateFormat format = new SimpleDateFormat();
							format.applyPattern("yyyy-MM-dd");
							Date docDate = format.parse(rset.getObject(i + 1).toString());
							//format.applyPattern("dd.MM.yyyy");
							result[curPos] = format.format(docDate);
						} else
							result[curPos] = rset.getObject(i + 1).toString();

					curPos++;
				}
			}
			cstmt.close();
			rset.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static void updateAbiturientOlympiadsInfoByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
		//Удалить записи по текущему абитуриенту и добавить введенные строчки из вкладки оснований на 100 баллов
		ModelDBConnection.deleteElementInTableByExpression("AbiturientDocumentsFor100balls", aid, fieldsNames, fieldsData, 0);

		if(fieldsData.length == 0) return;

    	String[] olympiadsData = new String[fieldsNames.length];

       	for (int i = 0, j = 0; i < fieldsData.length; i++, j++) {
       		olympiadsData[j] = fieldsData[i];

       		if(j == fieldsNames.length - 1) {
       			j = -1;

       			ModelDBConnection.updateElementInTableByExpression("AbiturientDocumentsFor100balls", aid, fieldsNames, olympiadsData, 1);
       		}
       	}
	}

	public static void deleteAbiturientOlympiadsInfoByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
		//Удалить все основания на 100 баллов по aid абитуриента
		ModelDBConnection.deleteElementInTableByExpression("AbiturientDocumentsFor100balls", aid, fieldsNames, fieldsData, 0);
	}


	//Вкладка AdditionalInfoTab
	public static String[] getAbiturientExtraInfoByID(String aid) throws SQLException {
		try {
			String query = ModelDBConnection.getQueryByTabName("Доп. сведения")
					+ " WHERE Abiturient.aid = " + aid + ";";

			/*cstmt = con.prepareCall("{call getAbiturientPassportByID(?)}", 1004, 1007);

			cstmt.setString(1, aid);*/

			cstmt = con.prepareCall(query, 1004, 1007);

			rset = cstmt.executeQuery();

			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			//Случай, если данных по абитуриенту еще нет
			if (countStrings == 0) return null;

			ResultSetMetaData rsmd = rset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			String[] result = new String[countStrings * numberOfColumns];
			for (int i = 0; i < result.length; i++)
				result[i] = "";

			int curPos = 0;

			while (rset.next()) {
				for (int i = 0; i < numberOfColumns; i++) {
					if (rset.getObject(i + 1) != null)
						if (rset.getObject(i + 1) instanceof Date) {
							SimpleDateFormat format = new SimpleDateFormat();
							format.applyPattern("yyyy-MM-dd");
							Date docDate = format.parse(rset.getObject(i + 1).toString());
							//format.applyPattern("dd.MM.yyyy");
							result[curPos] = format.format(docDate);
						} else
							result[curPos] = rset.getObject(i + 1).toString();

					curPos++;
				}
			}
			cstmt.close();
			rset.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static void updateAbiturientExtraInfoByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
		//Удалить записи по текущему абитуриенту и добавить введенные строчки из вкладки допсведений
		ModelDBConnection.deleteElementInTableByExpression("AbiturientExtraInfo", aid, fieldsNames, fieldsData, 0);

		if(fieldsData.length == 0) return;

		String[] extraInfoData = new String[fieldsNames.length];

		for (int i = 0, j = 0; i < fieldsData.length; i++, j++) {
			extraInfoData[j] = fieldsData[i];

			if(j == fieldsNames.length - 1) {
				j = -1;

				ModelDBConnection.updateElementInTableByExpression("AbiturientExtraInfo", aid, fieldsNames, extraInfoData, 1);
			}
		}
	}

	public static void deleteAbiturientExtraInfoByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
		//Удалить все допсведения по aid абитуриента
		ModelDBConnection.deleteElementInTableByExpression("AbiturientExtraInfo", aid, fieldsNames, fieldsData, 0);
	}


	//InsertForm
	public static String[] getAbiturientGeneralInfoByID(String aid) throws SQLException {
		try {
			String query = ModelDBConnection.getQueryByTabName("SampleTab")
							+ " WHERE Abiturient.aid = " + aid + ";";

			/*cstmt = con.prepareCall("{call getAbiturientPassportByID(?)}", 1004, 1007);

			cstmt.setString(1, aid);*/

			cstmt = con.prepareCall(query, 1004, 1007);

			rset = cstmt.executeQuery();

			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			//Случай, если данных по абитуриенту еще нет
			if (countStrings == 0) return null;

			ResultSetMetaData rsmd = rset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			String[] result = new String[numberOfColumns];
			for (int i = 0; i < result.length; i++)
				result[i] = "";

			while (rset.next()) {
				for (int i = 0; i < numberOfColumns; i++) {
					if (rset.getObject(i + 1) != null)
						if (rset.getObject(i + 1) instanceof Date) {
							SimpleDateFormat format = new SimpleDateFormat();
							format.applyPattern("yyyy-MM-dd");
							Date docDate = format.parse(rset.getObject(i + 1).toString());
							//format.applyPattern("dd.MM.yyyy");
							result[i] = format.format(docDate);
						} else
							result[i] = rset.getObject(i + 1).toString();
				}
			}
			cstmt.close();
			rset.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static void updateAbiturientGeneralInfoByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
		String[] abiturientFieldsNames = new String[fieldsNames.length - 1],
				 abiturientFieldsData = new String[fieldsData.length - 1];

		for (int i = 0; i < abiturientFieldsNames.length; i++) {
			abiturientFieldsNames[i] = fieldsNames[i + 1];
			abiturientFieldsData[i] = fieldsData[i + 1];
		}

		ModelDBConnection.updateElementInTableByExpression("Abiturient", aid, abiturientFieldsNames, abiturientFieldsData, 0);
	}

    // Competitive Groups Tab
    public static String[] getAbiturientCompetitiveGroupsByID(String aid) throws SQLException {
        try {
            String query = ModelDBConnection.getQueryByTabName("Конкурсные группы")
                    + " WHERE Abiturient.aid = " + aid + ";";

            cstmt = con.prepareCall(query, 1004, 1007);

            rset = cstmt.executeQuery();

            int countStrings = rset.last() ? rset.getRow() : 0;
            rset.beforeFirst();

            if (countStrings == 0) return null;

            ResultSetMetaData rsmd = rset.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();

            String[] result = new String[countStrings * numberOfColumns];
            for (int i = 0; i < result.length; i++)
                result[i] = "";

            int curPos = 0;

            while (rset.next()) {
                for (int i = 0; i < numberOfColumns; i++) {
                    if (rset.getObject(i + 1) != null)
                        if (rset.getObject(i + 1) instanceof Date) {
                            SimpleDateFormat format = new SimpleDateFormat();
                            format.applyPattern("yyyy-MM-dd");
                            Date docDate = format.parse(rset.getObject(i + 1).toString());
                            //format.applyPattern("dd.MM.yyyy");
                            result[curPos] = format.format(docDate);
                        } else
                            result[curPos] = rset.getObject(i + 1).toString();

                    curPos++;
                }
            }
            cstmt.close();
            rset.close();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public static void updateAbiturientCompetitiveGroupsByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
    	//Удалить записи по текущему абитуриенту и добавить введенные строчки из вкладки конкурсных групп
    	ModelDBConnection.deleteElementInTableByExpression("AbiturientCompetitiveGroup", aid, fieldsNames, fieldsData, 0);

        if(fieldsData.length == 0) return;

		String[] competitiveGroupsData = new String[fieldsNames.length];

		for (int i = 0, j = 0; i < fieldsData.length; i++, j++) {
			competitiveGroupsData[j] = fieldsData[i];

			if(j == fieldsNames.length - 1) {
				j = -1;

				ModelDBConnection.updateElementInTableByExpression("AbiturientCompetitiveGroup", aid, fieldsNames, competitiveGroupsData, 3);
			}
		}
    }

	public static void deleteAbiturientCompetitiveGroupsByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
		//Удалить все конкурсные группы по aid абитуриента
		ModelDBConnection.deleteElementInTableByExpression("AbiturientCompetitiveGroup", aid, fieldsNames, fieldsData, 0);
	}


	//Вкладка IndividualAchievementsTab
	public static String[] getAbiturientIndividualAchievementsByID(String aid) throws SQLException {
		try {
			String query = ModelDBConnection.getQueryByTabName("Индивидуальные достижения")
					+ " WHERE Abiturient.aid = " + aid + ";";

			/*cstmt = con.prepareCall("{call getAbiturientPassportByID(?)}", 1004, 1007);

			cstmt.setString(1, aid);*/

			cstmt = con.prepareCall(query, 1004, 1007);

			rset = cstmt.executeQuery();

			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			//Случай, если данных по абитуриенту еще нет
			if (countStrings == 0) return null;

			ResultSetMetaData rsmd = rset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			String[] result = new String[countStrings * numberOfColumns];
			for (int i = 0; i < result.length; i++)
				result[i] = "";

			int curPos = 0;

			while (rset.next()) {
				for (int i = 0; i < numberOfColumns; i++) {
					if (rset.getObject(i + 1) != null)
						if (rset.getObject(i + 1) instanceof Date) {
							SimpleDateFormat format = new SimpleDateFormat();
							format.applyPattern("yyyy-MM-dd");
							Date docDate = format.parse(rset.getObject(i + 1).toString());
							//format.applyPattern("dd.MM.yyyy");
							result[curPos] = format.format(docDate);
						} else
							result[curPos] = rset.getObject(i + 1).toString();

					curPos++;
				}
			}
			cstmt.close();
			rset.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static void updateAbiturientIndividualAchievementsByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
		//Удалить записи по текущему абитуриенту и добавить введенные строчки из вкладки индивидуальных достижений
		ModelDBConnection.deleteElementInTableByExpression("AbiturientIndividAchievement", aid, fieldsNames, fieldsData, 0);

		if(fieldsData.length == 0) return;

		String[] individualAchievementsData = new String[fieldsNames.length];

		for (int i = 0, j = 0; i < fieldsData.length; i++, j++) {
			individualAchievementsData[j] = fieldsData[i];

			if(j == fieldsNames.length - 1) {
				j = -1;

				ModelDBConnection.updateElementInTableByExpression("AbiturientIndividAchievement", aid, fieldsNames, individualAchievementsData, 1);
			}
		}
	}

	public static void deleteAbiturientIndividualAchievementsByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
		//Удалить все индивидуальные достижения по aid абитуриента
		ModelDBConnection.deleteElementInTableByExpression("AbiturientIndividAchievement", aid, fieldsNames, fieldsData, 0);
	}

    //Вкладка PrivilegeTab
    public static String[] getAbiturientBVIByID(String aid) throws SQLException {
        try {
            String query = ModelDBConnection.getQueryByTabName("БВИ")
                    + " WHERE Abiturient.aid = " + aid + ";";

            System.out.println(query);

            cstmt = con.prepareCall(query, 1004, 1007);

            rset = cstmt.executeQuery();

            int countStrings = rset.last() ? rset.getRow() : 0;
            rset.beforeFirst();

            //Случай, если данных по абитуриенту еще нет
            if (countStrings == 0) return null;

            ResultSetMetaData rsmd = rset.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();

            String[] result = new String[countStrings * numberOfColumns];
            for (int i = 0; i < result.length; i++)
                result[i] = "";

            int curPos = 0;

            while (rset.next()) {
                for (int i = 0; i < numberOfColumns; i++) {
                    if (rset.getObject(i + 1) != null)
                        if (rset.getObject(i + 1) instanceof Date) {
                            SimpleDateFormat format = new SimpleDateFormat();
                            format.applyPattern("yyyy-MM-dd");
                            Date docDate = format.parse(rset.getObject(i + 1).toString());
                            //format.applyPattern("dd.MM.yyyy");
                            result[curPos] = format.format(docDate);
                        } else
                            result[curPos] = rset.getObject(i + 1).toString();

                    curPos++;
                }
            }
            cstmt.close();
            rset.close();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public static String[] getAbiturientQuotaByID(String aid) throws SQLException {
        try {
            String query = ModelDBConnection.getQueryByTabName("Квота")
                    + " WHERE Abiturient.aid = " + aid + ";";

            System.out.println(query);

            cstmt = con.prepareCall(query, 1004, 1007);

            rset = cstmt.executeQuery();

            int countStrings = rset.last() ? rset.getRow() : 0;
            rset.beforeFirst();

            //Случай, если данных по абитуриенту еще нет
            if (countStrings == 0) return null;

            ResultSetMetaData rsmd = rset.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();

            String[] result = new String[countStrings * numberOfColumns];
            for (int i = 0; i < result.length; i++)
                result[i] = "";

            int curPos = 0;

            while (rset.next()) {
                for (int i = 0; i < numberOfColumns; i++) {
                    if (rset.getObject(i + 1) != null)
                        if (rset.getObject(i + 1) instanceof Date) {
                            SimpleDateFormat format = new SimpleDateFormat();
                            format.applyPattern("yyyy-MM-dd");
                            Date docDate = format.parse(rset.getObject(i + 1).toString());
                            //format.applyPattern("dd.MM.yyyy");
                            result[curPos] = format.format(docDate);
                        } else
                            result[curPos] = rset.getObject(i + 1).toString();

                    curPos++;
                }
            }
            cstmt.close();
            rset.close();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public static String[] getAbiturientPreferredRightByID(String aid) throws SQLException {
        try {
            String query = ModelDBConnection.getQueryByTabName("Преимущественное право")
                    + " WHERE Abiturient.aid = " + aid + ";";

            System.out.println(query);

            cstmt = con.prepareCall(query, 1004, 1007);

            rset = cstmt.executeQuery();

            int countStrings = rset.last() ? rset.getRow() : 0;
            rset.beforeFirst();

            //Случай, если данных по абитуриенту еще нет
            if (countStrings == 0) return null;

            ResultSetMetaData rsmd = rset.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();

            String[] result = new String[countStrings * numberOfColumns];
            for (int i = 0; i < result.length; i++)
                result[i] = "";

            int curPos = 0;

            while (rset.next()) {
                for (int i = 0; i < numberOfColumns; i++) {
                    if (rset.getObject(i + 1) != null)
                        if (rset.getObject(i + 1) instanceof Date) {
                            SimpleDateFormat format = new SimpleDateFormat();
                            format.applyPattern("yyyy-MM-dd");
                            Date docDate = format.parse(rset.getObject(i + 1).toString());
                            //format.applyPattern("dd.MM.yyyy");
                            result[curPos] = format.format(docDate);
                        } else
                            result[curPos] = rset.getObject(i + 1).toString();

                    curPos++;
                }
            }
            cstmt.close();
            rset.close();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public static void updateAbiturientBVIByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
        ModelDBConnection.deleteElementInTableByExpression("AbiturientDocumentsBVI", aid, fieldsNames, fieldsData, 0);

        if(fieldsData.length == 0) return;

        String[] privilegeDocsData = new String[fieldsNames.length - 1],
                typePrivilegeData = new String[1],
                privilegeDocsNames = new String[fieldsNames.length - 1],
                typePrivilegeNames = new String[1];

        typePrivilegeData[0] = fieldsData[0];
        typePrivilegeNames[0] = fieldsNames[0];

        ModelDBConnection.updateElementInTableByExpression("AbiturientBVI", aid, typePrivilegeNames, typePrivilegeData, 0);

        for (int i = 1, j = 0; i < fieldsData.length; i++, j++) {
            privilegeDocsData[j] = fieldsData[i];
            privilegeDocsNames[j] = fieldsNames[j + 1];

            if((i + 1) % fieldsNames.length  == 0) {
                i++;
                j = -1;

                ModelDBConnection.updateElementInTableByExpression("AbiturientDocumentsBVI", aid, privilegeDocsNames, privilegeDocsData, 1);
            }
        }
    }

    public static void updateAbiturientQuotaByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
        ModelDBConnection.deleteElementInTableByExpression("AbiturientDocumentQuota", aid, fieldsNames, fieldsData, 0);

        if(fieldsData.length == 0) return;

        String[] privilegeDocsData = new String[fieldsNames.length - 1],
                typePrivilegeData = new String[1],
                privilegeDocsNames = new String[fieldsNames.length - 1],
                typePrivilegeNames = new String[1];

        typePrivilegeData[0] = fieldsData[0];
        typePrivilegeNames[0] = fieldsNames[0];

        ModelDBConnection.updateElementInTableByExpression("AbiturientQuota", aid, typePrivilegeNames, typePrivilegeData, 0);

        for (int i = 1, j = 0; i < fieldsData.length; i++, j++) {
            privilegeDocsData[j] = fieldsData[i];
            privilegeDocsNames[j] = fieldsNames[j + 1];

            if((i + 1) % fieldsNames.length == 0) {
                i++;
                j = -1;

                ModelDBConnection.updateElementInTableByExpression("AbiturientDocumentQuota", aid, privilegeDocsNames, privilegeDocsData, 1);
            }
        }
    }

    public static void updateAbiturienPreferredRightByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
        ModelDBConnection.deleteElementInTableByExpression("AbiturientDocumentsPreferredRight", aid, fieldsNames, fieldsData, 0);

        if(fieldsData.length == 0) return;

        String[] privilegeDocsData = new String[fieldsNames.length - 1],
                typePrivilegeData = new String[1],
                privilegeDocsNames = new String[fieldsNames.length - 1],
                typePrivilegeNames = new String[1];

        typePrivilegeData[0] = fieldsData[0];
        typePrivilegeNames[0] = fieldsNames[0];

        ModelDBConnection.updateElementInTableByExpression("AbiturientPreferredRight", aid, typePrivilegeNames, typePrivilegeData, 0);

        for (int i = 1, j = 0; i < fieldsData.length; i++, j++) {
            privilegeDocsData[j] = fieldsData[i];
            privilegeDocsNames[j] = fieldsNames[j + 1];

            if((i + 1) % fieldsNames.length  == 0) {
                i++;
                j = -1;

                ModelDBConnection.updateElementInTableByExpression("AbiturientDocumentsPreferredRight", aid, privilegeDocsNames, privilegeDocsData, 1);
            }
        }
    }


    public static void deleteAbiturientDocumentsBVIByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
    	ModelDBConnection.deleteElementInTableByExpression("AbiturientDocumentsBVI", aid, fieldsNames, fieldsData, 0);
    	ModelDBConnection.deleteElementInTableByExpression("AbiturientBVI", aid, fieldsNames, fieldsData, 0);
    }

    public static void deleteAbiturientDocumentsQuotaByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
        ModelDBConnection.deleteElementInTableByExpression("AbiturientDocumentQuota", aid, fieldsNames, fieldsData, 0);
        ModelDBConnection.deleteElementInTableByExpression("AbiturientQuota", aid, fieldsNames, fieldsData, 0);
    }

    public static void deleteAbiturientDocumentsPreferredRightByID(String aid, String[] fieldsNames, String[] fieldsData) throws SQLException {
        ModelDBConnection.deleteElementInTableByExpression("AbiturientDocumentsPreferredRight", aid, fieldsNames, fieldsData, 0);
        ModelDBConnection.deleteElementInTableByExpression("AbiturientPreferredRight", aid, fieldsNames, fieldsData, 0);
    }

    // Справочники
    public static String[] getCatalogData(String table){
		try {
            String query = ModelDBConnection.getQueryByTabName(table);

			/*cstmt = con.prepareCall("{call getAbiturientPassportByID(?)}", 1004, 1007);

			cstmt.setString(1, aid);*/

			cstmt = con.prepareCall(query, 1004, 1007);

			rset = cstmt.executeQuery();

			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			//Случай, если данных по абитуриенту еще нет
			if (countStrings == 0) return null;

			ResultSetMetaData rsmd = rset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			String[] result = new String[countStrings * numberOfColumns];
			for (int i = 0; i < result.length; i++)
				result[i] = "";

			int curPos = 0;

			while (rset.next()) {
				for (int i = 0; i < numberOfColumns; i++) {
					if (rset.getObject(i + 1) != null)
						if (rset.getObject(i + 1) instanceof Date) {
							SimpleDateFormat format = new SimpleDateFormat();
							format.applyPattern("yyyy-MM-dd");
							Date docDate = format.parse(rset.getObject(i + 1).toString());
							//format.applyPattern("dd.MM.yyyy");
							result[curPos] = format.format(docDate);
						} else
							result[curPos] = rset.getObject(i + 1).toString();

					curPos++;
				}
			}
			cstmt.close();
			rset.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}


    public static void updateCatalogDataById(String[] fieldsNames, String[] fieldsData, String table) throws SQLException {
        String[] catalogData = new String[fieldsNames.length - 1],
                catalogNames = new String[fieldsNames.length - 1];

        for(int i = 0; i < fieldsData.length; i += fieldsNames.length){
            for(int j = 0; j < catalogNames.length; j++){
                catalogData[j] = fieldsData[i + j + 1];
                catalogNames[j] = fieldsNames[j + 1];
            }

            ModelDBConnection.updateElementInTableByExpression(table, fieldsData[i], catalogNames, catalogData, 0);
        }
    }

    public static void deleteCatalogData(String aid, String[] fieldsNames, String[] fieldsData, String table) throws SQLException {

    }

    public static void updateAdmissionPlan(String[] fieldsNames, String[] fieldsData, String table) throws SQLException {
        String[] catalogData = new String[fieldsNames.length - 1],
                catalogNames = new String[fieldsNames.length - 1];

        for(int i = 0; i < fieldsData.length; i += fieldsNames.length){
            for(int j = 0; j < catalogNames.length; j++){
                catalogData[j] = fieldsData[i + j + 1];
                catalogNames[j] = fieldsNames[j + 1];
            }

            ModelDBConnection.updateElementInTableByExpression(table, fieldsData[i], catalogNames, catalogData, 3);
        }
    }

    public static void deleteAdmissionPlan(String aid, String[] fieldsNames, String[] fieldsData, String table) throws SQLException {
        /*String[] catalogData = new String[fieldsNames.length - 1],
                catalogNames = new String[fieldsNames.length - 1];

        for(int i = 0; i < fieldsData.length; i += fieldsNames.length){
            for(int j = 0; j < catalogNames.length; j++){
                catalogData[j] = fieldsData[i + j + 1];
                catalogNames[j] = fieldsNames[j + 1];
            }

            ModelDBConnection.deleteElementInTableByExpression(table, fieldsData[i], catalogNames, catalogData, 3);
        }*/
    }

    public static ObservableList<ObservableList> getAbiturientInfo (ObservableList<ObservableList> info) throws Exception {
		String query = ModelDBConnection.getQueryByTabName("АРМ по приему в ВУЗ") +
						" order by aid";

		cstmt = con.prepareCall(query, 1004, 1007);

		rset = cstmt.executeQuery();

		while (rset.next()) {
			ObservableList<String> row = FXCollections.observableArrayList();
			for (int i = 1; i <= rset.getMetaData().getColumnCount(); i++) {
				if (rset.getObject(i) != null) {
					if (rset.getObject(i) instanceof Date) {
						SimpleDateFormat format = new SimpleDateFormat();
						format.applyPattern("yyyy-MM-dd");
						Date docDate = format.parse(rset.getObject(i).toString());
						//format.applyPattern("dd.MM.yyyy");
						row.add(format.format(docDate));
					} else
						row.add(rset.getObject(i).toString());
				} else {
					row.add("");
				}
			}
			info.add(row);
		}
		return info;
	}
}