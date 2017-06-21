package br.com.pe.urbana.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class UtilitariosData {

	public static Date stringToDate(String stringDate)
			throws java.text.ParseException {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",
					new Locale("pt_BR"));
			date = sdf.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static int deductDates(Date initialDate, Date finalDate) {
		if (initialDate == null || finalDate == null) {
			return 0;
		}
		int days = (int) ((finalDate.getTime() - initialDate.getTime()) / (24 * 60 * 60 * 1000));
		return (days > 0 ? days : 0);
	}

	public static String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale(
				"pt_BR"));
		String dateFormated = sdf.format(date);
		return dateFormated;
	}

	public static Date clearHour(Date date) throws java.text.ParseException {
		return (stringToDate(dateToString(date)));
	}

	public static int getWorkingDays(Date initialDate, Date finalDate) {
		int workingDays = 0;
		int totalDays = deductDates(initialDate, finalDate);
		Calendar calendar = new GregorianCalendar();
		// Setando o calendar com a Data Inicial
		calendar.setTime(initialDate);
		for (int i = 0; i <= totalDays; i++) {
			if (!(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
					&& !(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
				workingDays++;
			}
			calendar.add(Calendar.DATE, 1);
		}
		return workingDays;
	}

	public static List<String> getDiasUteis(Date initialDate, Date finalDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<String> workingDays = new ArrayList<String>();
		int totalDays = deductDates(initialDate, finalDate);
		Calendar calendar = new GregorianCalendar();
		// Setando o calendar com a Data Inicial
		calendar.setTime(initialDate);
		String data = null;
		for (int i = 0; i <= totalDays; i++) {

			data = sdf.format(calendar.getTime());
			workingDays.add(data);

			calendar.add(Calendar.DATE, 1);
		}
		return workingDays;
	}

	public static long qtdHrs(Date horaInicio, Date horaFinal) {
		long difHrs = 0;
		difHrs = (horaFinal.getTime() - horaInicio.getTime())
				/ (1000 * 60 * 60);
		return difHrs;

	}

	public static List<String> getHorario(String horaInicio,
			int tempoMedioAtendimento, int qtdHrsDisponiveis) {

		List<String> listaHorario = new ArrayList<String>();

		String horario = "";
		Calendar c = Calendar.getInstance();

		Date dataIni = null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

		try {
			dataIni = sdf.parse(horaInicio);
		} catch (ParseException | java.text.ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Preenche uma lista com o horario do atendimento, já realizando
		// exclusão do horário do atendimento
		for (int a = 0; a < qtdHrsDisponiveis; a++) {
			c = Calendar.getInstance();
			c.setTime(dataIni);
			c.add(Calendar.MINUTE, +tempoMedioAtendimento * a);
			horario = sdf.format(c.getTime());

			listaHorario.add(horario);

		}

		return listaHorario;

	}

	// public static List<String> getHorario(String horaInicio,
	// int tempoMedioAtendimento, int qtdHrsDisponiveis,
	// String flagIntervalo, int qtdHrsIntervelado,
	// String horaInicioIntervalo) {
	//
	// List<String> listaHorario = new ArrayList<String>();
	// List<String> listaExclusao = null;
	// boolean flag = "S".equals(flagIntervalo);
	// String horario = "";
	// Calendar c = Calendar.getInstance();
	//
	// Date dataIni = null;
	// Date dataIniIntervalo = null;
	// SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	//
	// try {
	// dataIni = sdf.parse(horaInicio);
	// } catch (ParseException | java.text.ParseException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// // Preenche uma lista com o horario do intervalo
	// if (flag) {
	// listaExclusao = new ArrayList<String>();
	// String horarioIntervalo = "";
	// try {
	// dataIniIntervalo = sdf.parse(horaInicioIntervalo);
	//
	// } catch (java.text.ParseException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// for (int a = 0; a < qtdHrsIntervelado; a++) {
	// c = Calendar.getInstance();
	// c.setTime(dataIniIntervalo);
	// c.add(Calendar.MINUTE, +tempoMedioAtendimento * a);
	// horarioIntervalo = sdf.format(c.getTime());
	// listaExclusao.add(horarioIntervalo);
	// }
	// }
	// // Preenche uma lista com o horario do atendimento, já realizando
	// exclusão do horário do atendimento
	// for (int a = 0; a < qtdHrsDisponiveis; a++) {
	// c = Calendar.getInstance();
	// c.setTime(dataIni);
	// c.add(Calendar.MINUTE, +tempoMedioAtendimento * a);
	// horario = sdf.format(c.getTime());
	// if (flag) {
	// if (!listaExclusao.contains(horario)) {
	// listaHorario.add(horario);
	// }
	// } else {
	// listaHorario.add(horario);
	// }
	// }
	//
	// return listaHorario;
	//
	// }
}