package backend;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public abstract class MessageProcessing {
	static Alert alert;

	public static void displaySuccessMessage(int messageType) {
		String message = null, titleMessage = null;
		alert = new Alert(AlertType.INFORMATION);

		switch (messageType) {
		case 1:
			titleMessage = "Результат добавления абитуриента";
			message = "Абитуриент успешно добавлен!";
			break;
		case 2:
			titleMessage = "Результат редактирования абитуриента";
			message = "Данные успешно изменены!";
			break;
		case 3:
			titleMessage = "Результат удаления абитуриента";
			message = "Абитуриент успешно удален из базы!";
			break;
		case 4:
			titleMessage = "Результат редактирования справочника";
			message = "Данные успешно изменены!";
			break;
		case 5:
			titleMessage = "Результат редактирования справочника";
			message = "Данные успешно удалены!";
			break;
		case 6:
			titleMessage = "Результат редактирования паспортных данных";
			message = "Данные успешно сохранены!";
			break;
		case 7:
			titleMessage = "Результат редактирования контактной информации";
			message = "Данные успешно сохранены!";
			break;
		case 8:
			titleMessage = "Результат редактирования информации по образованию";
			message = "Данные успешно сохранены!";
			break;
		case 9:
			titleMessage = "Результат редактирования вступительных испытаний";
			message = "Данные успешно сохранены!";
			break;
		case 10:
			titleMessage = "Результат редактирования индивидуальных достижений";
			message = "Данные успешно сохранены!";
			break;
		case 11:
			titleMessage = "Результат добавления конкурсной группы";
			message = "Конкурсная группа успешно добавлена!";
			break;
		case 12:
			titleMessage = "Результат удаления конкурсной группы";
			message = "Конкурсная группа успешно удалена!";
			break;
		case 13:
			titleMessage = "Результат редактирования конкурсной группы";
			message = "Изменения в конкурсной группе успешно сохранены!";
			break;
		case 14:
			titleMessage = "Результат редактирования сведений по аккредитации";
			message = "Данные успешно сохранены!";
			break;
		}

	    alert.setTitle(titleMessage);
	    alert.setHeaderText(null);
	    alert.setContentText(message);

	    alert.showAndWait();
	}

	public static void displayErrorMessage(Exception e) {
		alert = new Alert(AlertType.ERROR);
		alert.setTitle("Ошибка");
		alert.setHeaderText(null);
		alert.setContentText("В процессе выполнения операции возникла непредвиденная ошибка:");

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exceptionText = sw.toString();

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(textArea, 0, 0);

		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}

	public static void displayErrorMessage(int messageType) {
		String message = null, titleMessage = null;
		alert = new Alert(AlertType.ERROR);

		switch (messageType) {
		//MainWindow
		case 100:
			titleMessage = "Результат добавления абитуриента";
			message = "Абитуриент не может быть добавлен";
			break;
		case 101:
			titleMessage = "Результат обновления сведений по абитуриенту";
			message = "Возникла ошибка в процессе обновления сведений по абитуриенту";
			break;
		case 102:
			titleMessage = "Результат удаления абитуриента";
			message = "Абитуриент не может быть удален";
			break;
		case 103:
			titleMessage = "Результат проверки данных";
			message = "Абитуриент с таким идентификатором уже существует!\nПопробуйте еще раз";
			break;
		case 110:
			titleMessage = "Результат проверки данных";
			message = "№Л/д не указан либо указан неверно";
			break;
		case 111:
			titleMessage = "Результат проверки данных";
			message = "Некорректный формат Фамилии";
			break;
		case 112:
			titleMessage = "Результат проверки данных";
			message = "Некорректный формат Имени";
			break;
		case 113:
			titleMessage = "Результат проверки данных";
			message = "Некорректный формат Отчества";
			break;
		case 114:
			titleMessage = "Результат проверки данных";
			message = "Дата подачи заявления не задана либо указана неверно";
			break;
		case 115:
			titleMessage = "Результат проверки данных";
			message = "Дата рождения не задана либо указана неверно";
			break;
		case 116:
			titleMessage = "Результат проверки данных";
			message = "Не указано Гражданство";
			break;
		case 117:
			titleMessage = "Результат проверки данных";
			message = "Не указана Причина возврата документов";
			break;
		case 118:
			titleMessage = "Результат проверки данных";
			message = "Дата возврата документов не задана либо указана неверно";
			break;
		case 119:
			titleMessage = "Результат проверки данных";
			message = "Не указан Пол";
			break;

		//Конкурсные группы
		case 200:
			titleMessage = "Результат добавления конкурсной группы";
			message = "Конкурсная группа не может быть добавлена";
			break;
		case 201:
			titleMessage = "Результат обновления сведений по конкурсным группам";
			message = "Возникла ошибка в процессе обновления сведений по конкурсным группам";
			break;
		case 202:
			titleMessage = "Результат удаления конкурсной группы";
			message = "Конкурсная группа не может быть удалена";
			break;
		case 203:
			titleMessage = "Результат проверки данных";
			message = "Конкурсные группы повторяются!\nУдалите повторяющиеся записи и попробуйте еще раз";
			break;
		case 210:
			titleMessage = "Результат проверки данных";
			message = "Не указана Специальность";
			break;
		case 211:
			titleMessage = "Результат проверки данных";
			message = "Не указана Конкурсная группа";
			break;
		case 212:
			titleMessage = "Результат проверки данных";
			message = "Не указана Целевая организация";
			break;
		case 213:
			titleMessage = "Результат проверки данных";
			message = "Не указана Форма обучения";
			break;
		case 214:
			titleMessage = "Результат проверки данных";
			message = "Конкурсный балл не задан либо указан неверно";
			break;
		case 215:
			titleMessage = "Результат проверки данных";
			message = "Балл за индивидуальные достижения не задан либо указан неверно";
			break;
		case 216:
			titleMessage = "Результат проверки данных";
			message = "Дата предоставления оригиналов документов не задана либо указана неверно";
			break;
		case 217:
			titleMessage = "Результат проверки данных";
			message = "Дата предоставления согласия на зачисление не задана либо указана неверно";
			break;
		case 218:
			titleMessage = "Результат проверки данных";
			message = "Приоритет не задан либо указан неверно";
			break;
		case 219:
			titleMessage = "Результат проверки данных";
			message = "Оригинал не может быть предоставлен в рамках нескольких конкурсных групп одновременно!";
			break;
		case 220:
			titleMessage = "Результат проверки данных";
			message = "Согласие на зачисление не может быть предоставлен в рамках нескольких конкурсных групп одновременно!";
			break;

		//Вступительные испытания
		case 300:
			titleMessage = "Результат добавления вступительного испытания";
			message = "Вступительное испытание не может быть добавлена";
			break;
		case 301:
			titleMessage = "Результат обновления сведений по вступительным испытаниям";
			message = "Возникла ошибка в процессе обновления сведений по вступительным испытаниям";
			break;
		case 302:
			titleMessage = "Результат удаления вступительного испытания";
			message = "Вступительное испытание не может быть удалено";
			break;
		case 303:
			titleMessage = "Результат проверки данных";
			message = "Вступительные испытания повторяются!\nУдалите повторяющиеся записи и попробуйте еще раз";
			break;
		case 310:
			titleMessage = "Результат проверки данных";
			message = "Не указано Вступительное испытание";
			break;
		case 311:
			titleMessage = "Результат проверки данных";
			message = "Не указана Форма вступительного испытания";
			break;
		case 312:
			titleMessage = "Результат проверки данных";
			message = "Не указан Язык вступительного испытания";
			break;
		case 313:
			titleMessage = "Результат проверки данных";
			message = "Группа вступительного испытания не задана либо указана неверно";
			break;
		case 314:
			titleMessage = "Результат проверки данных";
			message = "Дата вступительного испытания не задана либо указана неверно";
			break;
		case 315:
			titleMessage = "Результат проверки данных";
			message = "Балл за вступительное испытание не задан либо указан неверно";
			break;

		//Индивидуальные достижения
		case 400:
			titleMessage = "Результат добавления индивидуального достижения";
			message = "Индивидуальное достижение не может быть добавлено";
			break;
		case 401:
			titleMessage = "Результат обновления сведений по индивидуальным достижениям";
			message = "Возникла ошибка в процессе обновления сведений по индивидуальным достижениям";
			break;
		case 402:
			titleMessage = "Результат удаления индивидуального достижения";
			message = "Индивидуальное достижение не может быть удалено";
			break;
		case 403:
			titleMessage = "Результат проверки данных";
			message = "Индивидуальные достижения повторяются!\nУдалите повторяющиеся записи и попробуйте еще раз";
			break;
		case 410:
			titleMessage = "Результат проверки данных";
			message = "Не указана Категория индивидуального достижения";
			break;
		case 411:
			titleMessage = "Результат проверки данных";
			message = "Балл за индивидуальное достижение не задан либо указан неверно";
			break;
		case 412:
			titleMessage = "Результат проверки данных";
			message = "Наименование документа-основания индивидуального достижения не задано либо указано неверно";
			break;
		case 413:
			titleMessage = "Результат проверки данных";
			message = "Серия документа-основания индивидуального достижения не задана либо указана неверно";
			break;
		case 414:
			titleMessage = "Результат проверки данных";
			message = "Номер документа-основания индивидуального достижения не задан либо указан неверно";
			break;
		case 415:
			titleMessage = "Результат проверки данных";
			message = "Дата выдачи документа-основания индивидуального достижения не задана либо указана неверно";
			break;
		case 416:
			titleMessage = "Результат проверки данных";
			message = "Кем выдан документ-основание индивидуального достижения не задано либо указано неверно";
			break;

		//Привилегии
		//БВИ
		case 500:
			titleMessage = "Результат добавления оснований для зачисления без вступительных испытаний";
			message = "Основание для зачисления без вступительных испытаний не может быть добавлено";
			break;
		case 501:
			titleMessage = "Результат обновления оснований для зачисления без вступительных испытаний";
			message = "Возникла ошибка в процессе обновления оснований для зачисления без вступительных испытаний";
			break;
		case 502:
			titleMessage = "Результат удаления оснований для зачисления без вступительных испытаний";
			message = "Основание для зачисления без вступительных испытаний не может быть удалено";
			break;
		case 503:
			titleMessage = "Результат проверки данных";
			message = "Основания для зачисления без вступительных испытаний повторяются!\nУдалите повторяющиеся записи и попробуйте еще раз";
			break;
		case 510:
		case 610:
		case 710:
			titleMessage = "Результат проверки данных";
			message = "Не указано основание для зачисления без вступительных испытаний";
			break;
		case 511:
		case 611:
		case 711:
			titleMessage = "Результат проверки данных";
			message = "Наименование документа, подтверждающего заданные привилегии, не задано либо указано неверно";
			break;
		case 512:
		case 612:
		case 712:
			titleMessage = "Результат проверки данных";
			message = "Серия документа, подтверждающего заданные привилегии, не задана либо указана неверно";
			break;
		case 513:
		case 613:
		case 713:
			titleMessage = "Результат проверки данных";
			message = "Номер документа, подтверждающего заданные привилегии, не задан либо указан неверно";
			break;
		case 514:
		case 614:
		case 714:
			titleMessage = "Результат проверки данных";
			message = "Дата выдачи документа, подтверждающего заданные привилегии, не задана либо указана неверно";
			break;
		case 515:
		case 615:
		case 715:
			titleMessage = "Результат проверки данных";
			message = "Кем выдан документ, подтверждающий заданные привилегии, не задано либо указано неверно";
			break;
		case 516:
		case 616:
		case 716:
			titleMessage = "Результат проверки данных";
			message = "Для применения привилегии необходимо добавить хотя бы один подтверждающий документ";
			break;

		//Квота
		case 600:
			titleMessage = "Результат добавления оснований для зачисления в рамках квоты приема";
			message = "Основание для зачисления в рамках квоты приема не может быть добавлено";
			break;
		case 601:
			titleMessage = "Результат обновления оснований для зачисления в рамках квоты приема";
			message = "Возникла ошибка в процессе обновления оснований для зачисления в рамках квоты приема";
			break;
		case 602:
			titleMessage = "Результат удаления оснований для зачисления в рамках квоты приема";
			message = "Основание для зачисления в рамках квоты приема не может быть удалено";
			break;
		case 603:
			titleMessage = "Результат проверки данных";
			message = "Основания для зачисления в рамках квоты приема повторяются!\nУдалите повторяющиеся записи и попробуйте еще раз";
			break;

		//Преимущественное право
		case 700:
			titleMessage = "Результат добавления оснований для зачисления c преимущественным правом";
			message = "Основание для зачисления c преимущественным правом не может быть добавлено";
			break;
		case 701:
			titleMessage = "Результат обновления оснований для зачисления c преимущественным правом";
			message = "Возникла ошибка в процессе обновления оснований для зачисления c преимущественным правом";
			break;
		case 702:
			titleMessage = "Результат удаления оснований для зачисления c преимущественным правом";
			message = "Основание для зачисления c преимущественным правом не может быть удалено";
			break;
		case 703:
			titleMessage = "Результат проверки данных";
			message = "Основания для зачисления c преимущественным правом повторяются!\nУдалите повторяющиеся записи и попробуйте еще раз";
			break;

		//100 баллов
		case 800:
			titleMessage = "Результат добавления основания на 100 баллов по предметам";
			message = "Основание на 100 баллов по предметам не может быть добавлено";
			break;
		case 801:
			titleMessage = "Результат обновления сведений по основаниям на 100 баллов по предметам";
			message = "Возникла ошибка в процессе обновления оснований на 100 баллов по предметам";
			break;
		case 802:
			titleMessage = "Результат удаления основания на 100 баллов по предметам";
			message = "Основание на 100 баллов по предметам не может быть удалено";
			break;
		case 803:
			titleMessage = "Результат проверки данных";
			message = "Основания на 100 баллов по предметам повторяются!\nУдалите повторяющиеся записи и попробуйте еще раз";
			break;
		case 810:
			titleMessage = "Результат проверки данных";
			message = "Не указано основание на 100 баллов по предметам";
			break;
		case 811:
			titleMessage = "Результат проверки данных";
			message = "Наименование документа-основания на 100 баллов по предметам не задано либо указано неверно";
			break;
		case 812:
			titleMessage = "Результат проверки данных";
			message = "Степень диплома-основания на 100 баллов по предметам не задана либо указана неверно";
			break;
		case 813:
			titleMessage = "Результат проверки данных";
			message = "Предмет олимпиады-основания на 100 баллов по предметам не задан либо указан неверно";
			break;
		case 814:
			titleMessage = "Результат проверки данных";
			message = "Уровень олимпиады-основания на 100 баллов по предметам не задан либо указан неверно";
			break;
		case 815:
			titleMessage = "Результат проверки данных";
			message = "Серия документа-основания на 100 баллов по предметам не задана либо указана неверно";
			break;
		case 816:
			titleMessage = "Результат проверки данных";
			message = "Номер документа-основания на 100 баллов по предметам не задан либо указан неверно";
			break;
		case 817:
			titleMessage = "Результат проверки данных";
			message = "Дата выдачи документа-основания на 100 баллов по предметам не задана либо указана неверно";
			break;
		case 818:
			titleMessage = "Результат проверки данных";
			message = "Кем выдан документ-основание на 100 баллов по предметам не задано либо указано неверно";
			break;
			
		//Образование
		case 900:
			titleMessage = "Результат добавления сведений об образовании абитуриента";
			message = "Сведения об образовании абитуриента не могут быть добавлены";
			break;
		case 901:
			titleMessage = "Результат обновления сведений об образовании абитуриента";
			message = "Возникла ошибка в процессе обновления сведений об образовании абитуриента";
			break;
		case 902:
			titleMessage = "Результат удаления сведений об образовании абитуриента";
			message = "Сведения об образовании абитуриента не могут быть удалены";
			break;
		case 910:
			titleMessage = "Результат проверки данных";
			message = "Не указан уровень образования";
			break;
		case 911:
			titleMessage = "Результат проверки данных";
			message = "Не указан тип образования";
			break;
		case 912:
			titleMessage = "Результат проверки данных";
			message = "Название учебного заведения не задано либо указано неверно";
			break;
		case 913:
			titleMessage = "Результат проверки данных";
			message = "Дата выдачи документа об образовании не задана либо указана неверно";
			break;
		case 914:
			titleMessage = "Результат проверки данных";
			message = "Серия документа об образовании не задана либо указана неверно";
			break;
		case 915:
			titleMessage = "Результат проверки данных";
			message = "Номер документа об образовании не задан либо указан неверно";
			break;
		case 916:
			titleMessage = "Результат проверки данных";
			message = "Год окончания учебного заведения не задан либо указан неверно";
			break;

		//Адрес и контакты
		case 1000:
			titleMessage = "Результат добавления сведений об адресе и контактах абитуриента";
			message = "Сведения об адресе и контактах абитуриента не могут быть добавлены";
			break;
		case 1001:
			titleMessage = "Результат обновления сведений об адресе и контактах абитуриента";
			message = "Возникла ошибка в процессе обновления сведений об адресе и контактах абитуриента";
			break;
		case 1002:
			titleMessage = "Результат удаления сведений об адресе и контактах абитуриента";
			message = "Сведения об адресе и контактах абитуриента не могут быть удалены";
			break;
		case 1010:
			titleMessage = "Результат проверки данных";
			message = "Не указан регион";
			break;
		case 1011:
			titleMessage = "Результат проверки данных";
			message = "Не указан тип населенного пункта";
			break;
		case 1012:
			titleMessage = "Результат проверки данных";
			message = "Индекс не задан либо указан неверно";
			break;
		case 1013:
			titleMessage = "Результат проверки данных";
			message = "Адрес не задан либо указан неверно";
			break;

		//Паспорт и ИНН
		case 1100:
			titleMessage = "Результат добавления паспортных данных абитуриента";
			message = "Паспортные данные абитуриента не могут быть добавлены";
			break;
		case 1101:
			titleMessage = "Результат обновления паспортных данных абитуриента";
			message = "Возникла ошибка в процессе обновления паспортных данных абитуриента";
			break;
		case 1102:
			titleMessage = "Результат удаления паспортных данных абитуриента";
			message = "Паспортные данные абитуриента не могут быть удалены";
			break;
		case 1110:
			titleMessage = "Результат проверки данных";
			message = "Не указан тип документа, удостоверяющего личность";
			break;
		case 1111:
			titleMessage = "Результат проверки данных";
			message = "Серия документа, удостоверяющего личность, не задана либо указана неверно";
			break;
		case 1112:
			titleMessage = "Результат проверки данных";
			message = "Номер документа, удостоверяющего личность, не задан либо указан неверно";
			break;
		case 1113:
			titleMessage = "Результат проверки данных";
			message = "Дата выдачи документа, удостоверяющего личность, не задана либо указана неверно";
			break;
		case 1114:
			titleMessage = "Результат проверки данных";
			message = "Кем выдан документ, удостоверяющий личность, не задано либо указано неверно";
			break;
		case 1115:
			titleMessage = "Результат проверки данных";
			message = "Место рождения абитуриента не задано либо указано неверно";
			break;
		case 1116:
			titleMessage = "Результат проверки данных";
			message = "ИНН абитуриента не задан либо указан неверно";
			break;

		//Доп. сведения
		case 1200:
			titleMessage = "Результат добавления допсведений";
			message = "Новая запись допсведений не может быть добавлена";
			break;
		case 1201:
			titleMessage = "Результат обновления допсведений";
			message = "Возникла ошибка в процессе обновления допсведений";
			break;
		case 1202:
			titleMessage = "Результат удаления допсведений";
			message = "Запись допсведений не может быть удалена";
			break;
		case 1203:
			titleMessage = "Результат проверки данных";
			message = "Записи допсведений повторяются!\nУдалите повторяющиеся записи и попробуйте еще раз";
			break;
		case 1210:
			titleMessage = "Результат проверки данных";
			message = "Не указана Категория допсведений";
			break;
		case 1211:
			titleMessage = "Результат проверки данных";
			message = "Наименование документа не задано либо указано неверно";
			break;
		case 1212:
			titleMessage = "Результат проверки данных";
			message = "Серия документа не задана либо указана неверно";
			break;
		case 1213:
			titleMessage = "Результат проверки данных";
			message = "Номер документа не задан либо указан неверно";
			break;
		case 1214:
			titleMessage = "Результат проверки данных";
			message = "Кем выдан документ не задано либо указано неверно";
			break;
		case 1215:
			titleMessage = "Результат проверки данных";
			message = "Дата выдачи документа не задана либо указана неверно";
			break;

		//Справочники
		case 1300:
			titleMessage = "Результат добавления сведений в справочник";
			message = "Новая запись не может быть добавлена";
			break;
		case 1301:
			titleMessage = "Результат обновления сведений в справочнике";
			message = "Возникла ошибка в процессе обновления сведений в справочнике";
			break;
		case 1302:
			titleMessage = "Результат удаления сведений в справочнике";
			message = "Запись не может быть удалена";
			break;
		case 1303:
			titleMessage = "Результат проверки данных";
			message = "Записи повторяются!\nУдалите повторяющиеся записи и попробуйте еще раз";
			break;
		case 1304:
			titleMessage = "Результат проверки данных";
			message = "Идентификатор элемента справочника не задан либо указан неверно";
			break;
		case 1305:
			titleMessage = "Результат проверки данных";
			message = "Название элемента справочника не задано либо указано неверно";
			break;
		case 1306:
			titleMessage = "Результат проверки данных";
			message = "Не указана специальность";
			break;
		case 1307:
			titleMessage = "Результат проверки данных";
			message = "Не указана форма обучения";
			break;
		case 1308:
			titleMessage = "Результат проверки данных";
			message = "Не указана конкурсная группа";
			break;
		case 1309:
			titleMessage = "Результат проверки данных";
			message = "Не указана целевая организация";
			break;
		case 1310:
			titleMessage = "Результат проверки данных";
			message = "Количество мест в рамках конкурсной группы приема не задано либо указано неверно";
			break;
		case 1311:
			titleMessage = "Результат проверки данных";
			message = "Количество мест приема в рамках квоты не задано либо указано неверно";
			break;

		//Авторизация
		case 1400:
			titleMessage = "Результат проверки данных";
			message = "Ошибка входа! Неверный логин или пароль";
			break;

		//Ошибки вывода в документы
		//Заявление
		case 1510:
			titleMessage = "Результат вывода заявления";
			message = "Ошибка";
			break;
		case 1511:
			titleMessage = "Результат вывода заявления";
			message = "Список специальностей пуст. Вывод невозможен!";
			break;
		case 1512:
			titleMessage = "Результат вывода заявления";
			message = "Список вступительных испытаний пуст. Вывод невозможен!";
			break;

		//Приложение к заявлению
		case 1610:
			titleMessage = "Результат вывода приложения к заявлению";
			message = "Ошибка";
			break;
		case 1611:
			titleMessage = "Результат вывода приложения к заявлению";
			message = "Список специальностей пуст. Вывод невозможен!";
			break;

		//Лист вступительных испытаний
		case 1710:
			titleMessage = "Результат вывода листа вступительных испытаний";
			message = "Ошибка";
			break;
		case 1711:
			titleMessage = "Результат вывода листа вступительных испытаний";
			message = "Список вступительных испытаний пуст. Вывод невозможен!";
			break;

		default:
			titleMessage = "Неизвестная ошибка";
			message = "Произошла неизвестная ошибка. Обратитесь к администратору!";
		}

	    alert.setTitle(titleMessage);
	    alert.setHeaderText(null);
	    alert.setContentText(message);

	    alert.showAndWait();
	}

	public static boolean displayDialogMessage(int messageType) {
		String message = null, titleMessage = null;
		alert = new Alert(AlertType.CONFIRMATION);

		switch (messageType) {
		case 1:
			titleMessage = "Удаление абитуриента";
			message = "Вы действительно хотите удалить текущего абитуриента?";
			break;
		}

		alert.setTitle(titleMessage);
		alert.setHeaderText(null);
		alert.setContentText(message);

		Optional<ButtonType> result = alert.showAndWait();

		return result.get() == ButtonType.OK ? true : false;
	}
}
